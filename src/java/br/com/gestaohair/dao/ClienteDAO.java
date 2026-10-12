package br.com.gestaohair.dao;

import br.com.gestaohair.model.Cliente;
import br.com.gestaohair.util.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> listar() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT id, nome, telefone, email, data_nascimento, observacao "
                + "FROM clientes ORDER BY nome";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                Cliente cliente = new Cliente();

                cliente.setId(resultado.getInt("id"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setTelefone(resultado.getString("telefone"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setDataNascimento(
                        resultado.getDate("data_nascimento").toLocalDate()
                );
                cliente.setObservacao(resultado.getString("observacao"));

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes "
                + "(nome, telefone, email, data_nascimento, observacao) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, cliente.getNome());
            comando.setString(2, cliente.getTelefone());
            comando.setString(3, cliente.getEmail());
            comando.setDate(
                    4,
                    Date.valueOf(cliente.getDataNascimento())
            );
            comando.setString(5, cliente.getObservacao());

            comando.executeUpdate();
        }
    }
}