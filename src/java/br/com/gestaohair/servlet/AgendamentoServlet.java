package br.com.gestaohair.servlet;

import br.com.gestaohair.dao.AgendamentoDAO;
import br.com.gestaohair.model.Agendamento;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/agendamentos")
public class AgendamentoServlet extends HttpServlet {

    private final AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String respostaJson;
        int statusHttp = HttpServletResponse.SC_OK;

        try {
            List<Agendamento> agendamentos = agendamentoDAO.listar();
            respostaJson = gerarJsonAgendamentos(agendamentos);

        } catch (Exception erro) {
            erro.printStackTrace();

            statusHttp = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            respostaJson = "{\"erro\":\""
                    + escapar(
                            "Erro ao consultar agendamentos: "
                            + erro.getMessage()
                    )
                    + "\"}";
        }

        response.setStatus(statusHttp);

        try (PrintWriter saida = response.getWriter()) {
            saida.print(respostaJson);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String cliente = request.getParameter("cliente");
        String servico = request.getParameter("servico");
        String profissional = request.getParameter("profissional");
        String status = request.getParameter("status");
        String dataTexto = request.getParameter("data");
        String horaTexto = request.getParameter("hora");

        String respostaJson;
        int statusHttp;

        if (
                estaVazio(cliente)
                || estaVazio(servico)
                || estaVazio(profissional)
                || estaVazio(status)
                || estaVazio(dataTexto)
                || estaVazio(horaTexto)
        ) {
            statusHttp = HttpServletResponse.SC_BAD_REQUEST;
            respostaJson = "{\"erro\":\"Preencha todos os campos do agendamento.\"}";

        } else {
            try {
                LocalDate data = LocalDate.parse(dataTexto);
                LocalTime hora = LocalTime.parse(horaTexto);

                if (data.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException(
                            "Não é possível cadastrar um agendamento em data passada."
                    );
                }

                LocalTime inicioExpediente = LocalTime.of(8, 0);
                LocalTime fimExpediente = LocalTime.of(20, 0);

                if (
                        hora.isBefore(inicioExpediente)
                        || hora.isAfter(fimExpediente)
                ) {
                    throw new IllegalArgumentException(
                            "Selecione um horário entre 08:00 e 20:00."
                    );
                }

                if (agendamentoDAO.existeConflito(profissional, data, hora)) {
                    statusHttp = HttpServletResponse.SC_CONFLICT;
                    respostaJson = "{\"erro\":\""
                            + escapar(
                                    "Este profissional já possui um atendimento "
                                    + "nesse dia e horário."
                            )
                            + "\"}";

                } else {
                    Agendamento agendamento = new Agendamento(
                            cliente.trim(),
                            servico.trim(),
                            profissional.trim(),
                            status.trim(),
                            data,
                            hora
                    );

                    agendamentoDAO.inserir(agendamento);

                    statusHttp = HttpServletResponse.SC_CREATED;
                    respostaJson = "{\"mensagem\":\"Agendamento salvo com sucesso.\"}";
                }

            } catch (SQLIntegrityConstraintViolationException erro) {
                statusHttp = HttpServletResponse.SC_CONFLICT;
                respostaJson = "{\"erro\":\""
                        + escapar(
                                "Este profissional já possui um atendimento "
                                + "nesse dia e horário."
                        )
                        + "\"}";

            } catch (IllegalArgumentException erro) {
                statusHttp = HttpServletResponse.SC_BAD_REQUEST;
                respostaJson = "{\"erro\":\""
                        + escapar(erro.getMessage())
                        + "\"}";

            } catch (Exception erro) {
                erro.printStackTrace();

                statusHttp = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                respostaJson = "{\"erro\":\""
                        + escapar(
                                "Erro ao cadastrar agendamento: "
                                + erro.getMessage()
                        )
                        + "\"}";
            }
        }

        response.setStatus(statusHttp);

        try (PrintWriter saida = response.getWriter()) {
            saida.print(respostaJson);
        }
    }

    private String gerarJsonAgendamentos(
            List<Agendamento> agendamentos
    ) {
        StringBuilder json = new StringBuilder("[");
        String separador = "";

        for (Agendamento agendamento : agendamentos) {
            json.append(separador);
            json.append("{");
            json.append("\"id\":").append(agendamento.getId()).append(",");
            json.append("\"cliente\":\"")
                    .append(escapar(agendamento.getCliente()))
                    .append("\",");
            json.append("\"servico\":\"")
                    .append(escapar(agendamento.getServico()))
                    .append("\",");
            json.append("\"profissional\":\"")
                    .append(escapar(agendamento.getProfissional()))
                    .append("\",");
            json.append("\"status\":\"")
                    .append(escapar(agendamento.getStatus()))
                    .append("\",");
            json.append("\"data\":\"")
                    .append(agendamento.getDataAgendamento())
                    .append("\",");
            json.append("\"hora\":\"")
                    .append(agendamento.getHoraAgendamento())
                    .append("\"");
            json.append("}");

            separador = ",";
        }

        json.append("]");
        return json.toString();
    }

    private boolean estaVazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private String escapar(String texto) {
        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
}