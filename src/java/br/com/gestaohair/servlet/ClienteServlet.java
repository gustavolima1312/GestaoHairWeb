package br.com.gestaohair.servlet;

import br.com.gestaohair.dao.ClienteDAO;
import br.com.gestaohair.model.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/clientes")
public class ClienteServlet extends HttpServlet {

    private final ClienteDAO clienteDAO = new ClienteDAO();

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
            List<Cliente> clientes = clienteDAO.listar();
            respostaJson = gerarJsonClientes(clientes);

        } catch (Exception erro) {
            erro.printStackTrace();

            statusHttp = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            respostaJson = "{\"erro\":\""
                    + escapar("Erro ao consultar clientes: " + erro.getMessage())
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

        String nome = request.getParameter("nome");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String dataNascimento = request.getParameter("dataNascimento");
        String observacao = request.getParameter("observacao");

        String respostaJson;
        int statusHttp;

        if (
                estaVazio(nome)
                || estaVazio(telefone)
                || estaVazio(email)
                || estaVazio(dataNascimento)
        ) {
            statusHttp = HttpServletResponse.SC_BAD_REQUEST;
            respostaJson = "{\"erro\":\"Preencha os campos obrigatórios.\"}";

        } else if (!email.contains("@") || !email.contains(".")) {
            statusHttp = HttpServletResponse.SC_BAD_REQUEST;
            respostaJson = "{\"erro\":\"Digite um e-mail válido.\"}";

        } else {
            try {
                Cliente cliente = new Cliente(
                        nome.trim(),
                        telefone.trim(),
                        email.trim(),
                        LocalDate.parse(dataNascimento),
                        observacao == null ? "" : observacao.trim()
                );

                clienteDAO.inserir(cliente);

                statusHttp = HttpServletResponse.SC_CREATED;
                respostaJson = "{\"mensagem\":\"Cliente cadastrado com sucesso.\"}";

            } catch (Exception erro) {
                erro.printStackTrace();

                statusHttp = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                respostaJson = "{\"erro\":\""
                        + escapar("Erro ao cadastrar cliente: " + erro.getMessage())
                        + "\"}";
            }
        }

        response.setStatus(statusHttp);

        try (PrintWriter saida = response.getWriter()) {
            saida.print(respostaJson);
        }
    }

    private String gerarJsonClientes(List<Cliente> clientes) {
        StringBuilder json = new StringBuilder("[");
        String separador = "";

        for (Cliente cliente : clientes) {
            json.append(separador);
            json.append("{");
            json.append("\"id\":").append(cliente.getId()).append(",");
            json.append("\"nome\":\"")
                    .append(escapar(cliente.getNome()))
                    .append("\",");
            json.append("\"telefone\":\"")
                    .append(escapar(cliente.getTelefone()))
                    .append("\",");
            json.append("\"email\":\"")
                    .append(escapar(cliente.getEmail()))
                    .append("\",");
            json.append("\"dataNascimento\":\"")
                    .append(cliente.getDataNascimento())
                    .append("\",");
            json.append("\"observacao\":\"")
                    .append(escapar(cliente.getObservacao()))
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