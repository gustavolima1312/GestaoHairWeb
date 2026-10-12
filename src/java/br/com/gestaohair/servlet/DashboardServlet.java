package br.com.gestaohair.servlet;

import br.com.gestaohair.util.Conexao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/dashboard")
public class DashboardServlet extends HttpServlet {

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
            int totalClientes = contarRegistros(
                    "SELECT COUNT(*) FROM clientes"
            );

            int totalProdutos = contarRegistros(
                    "SELECT COUNT(*) FROM produtos"
            );

            int produtosEstoqueBaixo = contarRegistros(
                    "SELECT COUNT(*) FROM produtos "
                    + "WHERE quantidade <= estoque_minimo"
            );

            int totalAgendamentos = contarRegistros(
                    "SELECT COUNT(*) FROM agendamentos"
            );

            int agendamentosHoje = contarRegistros(
                    "SELECT COUNT(*) FROM agendamentos "
                    + "WHERE data_agendamento = CURDATE()"
            );

            respostaJson = "{"
                    + "\"totalClientes\":" + totalClientes + ","
                    + "\"totalProdutos\":" + totalProdutos + ","
                    + "\"produtosEstoqueBaixo\":" + produtosEstoqueBaixo + ","
                    + "\"totalAgendamentos\":" + totalAgendamentos + ","
                    + "\"agendamentosHoje\":" + agendamentosHoje
                    + "}";

        } catch (Exception erro) {
            erro.printStackTrace();

            statusHttp = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            respostaJson = "{\"erro\":\""
                    + escapar(
                            "Erro ao carregar indicadores do dashboard: "
                            + erro.getMessage()
                    )
                    + "\"}";
        }

        response.setStatus(statusHttp);

        try (PrintWriter saida = response.getWriter()) {
            saida.print(respostaJson);
        }
    }

    private int contarRegistros(String sql) throws SQLException {
        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
        ) {
            resultado.next();
            return resultado.getInt(1);
        }
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