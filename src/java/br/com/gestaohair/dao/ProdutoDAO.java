package br.com.gestaohair.dao;

import br.com.gestaohair.model.Produto;
import br.com.gestaohair.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public List<Produto> listar() throws SQLException {
        List<Produto> produtos = new ArrayList<>();

        String sql = "SELECT id, nome, categoria, preco, quantidade, "
                + "estoque_minimo, fornecedor "
                + "FROM produtos ORDER BY nome";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                Produto produto = new Produto();

                produto.setId(resultado.getInt("id"));
                produto.setNome(resultado.getString("nome"));
                produto.setCategoria(resultado.getString("categoria"));
                produto.setPreco(resultado.getBigDecimal("preco"));
                produto.setQuantidade(resultado.getInt("quantidade"));
                produto.setEstoqueMinimo(
                        resultado.getInt("estoque_minimo")
                );
                produto.setFornecedor(resultado.getString("fornecedor"));

                produtos.add(produto);
            }
        }

        return produtos;
    }

    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos "
                + "(nome, categoria, preco, quantidade, estoque_minimo, fornecedor) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, produto.getNome());
            comando.setString(2, produto.getCategoria());
            comando.setBigDecimal(3, produto.getPreco());
            comando.setInt(4, produto.getQuantidade());
            comando.setInt(5, produto.getEstoqueMinimo());
            comando.setString(6, produto.getFornecedor());

            comando.executeUpdate();
        }
    }
}