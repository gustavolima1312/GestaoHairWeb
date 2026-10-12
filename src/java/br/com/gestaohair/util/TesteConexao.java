package br.com.gestaohair.util;

import java.sql.Connection;

public class TesteConexao {

    public static void main(String[] args) {
        try (Connection conexao = Conexao.conectar()) {
            System.out.println("Conexão com MySQL realizada com sucesso.");
        } catch (Exception erro) {
            System.out.println("Falha na conexão com MySQL:");
            erro.printStackTrace();
        }
    }
}