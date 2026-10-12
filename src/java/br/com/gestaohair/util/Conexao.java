package br.com.gestaohair.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
            "jdbc:mysql://localhost:3306/gestaohair_web"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=America/Sao_Paulo";

    private static final String USUARIO = "root";

    private static final String SENHA = "LadyGa13@";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException erro) {
            throw new SQLException(
                    "Driver JDBC do MySQL não encontrado.",
                    erro
            );
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}