package br.com.gestaohair.servlet;

import br.com.gestaohair.dao.ProdutoDAO;
import br.com.gestaohair.model.Produto;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/produtos")
public class ProdutoServlet extends HttpServlet {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

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
            List<Produto> produtos = produtoDAO.listar();
            respostaJson = gerarJsonProdutos(produtos);

        } catch (Exception erro) {
            erro.printStackTrace();

            statusHttp = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            respostaJson = "{\"erro\":\""
                    + escapar("Erro ao consultar produtos: " + erro.getMessage())
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
        String categoria = request.getParameter("categoria");
        String precoTexto = request.getParameter("preco");
        String quantidadeTexto = request.getParameter("quantidade");
        String estoqueMinimoTexto = request.getParameter("estoqueMinimo");
        String fornecedor = request.getParameter("fornecedor");

        String respostaJson;
        int statusHttp;

        if (
                estaVazio(nome)
                || estaVazio(categoria)
                || estaVazio(precoTexto)
                || estaVazio(quantidadeTexto)
                || estaVazio(estoqueMinimoTexto)
                || estaVazio(fornecedor)
        ) {
            statusHttp = HttpServletResponse.SC_BAD_REQUEST;
            respostaJson = "{\"erro\":\"Preencha todos os campos do produto.\"}";

        } else {
            try {
                BigDecimal preco = new BigDecimal(precoTexto);
                int quantidade = Integer.parseInt(quantidadeTexto);
                int estoqueMinimo = Integer.parseInt(estoqueMinimoTexto);

                if (preco.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new IllegalArgumentException(
                            "O preço deve ser maior que zero."
                    );
                }

                if (quantidade < 0 || estoqueMinimo < 0) {
                    throw new IllegalArgumentException(
                            "Quantidade e estoque mínimo não podem ser negativos."
                    );
                }

                Produto produto = new Produto(
                        nome.trim(),
                        categoria.trim(),
                        preco,
                        quantidade,
                        estoqueMinimo,
                        fornecedor.trim()
                );

                produtoDAO.inserir(produto);

                statusHttp = HttpServletResponse.SC_CREATED;
                respostaJson = "{\"mensagem\":\"Produto cadastrado com sucesso.\"}";

            } catch (NumberFormatException erro) {
                statusHttp = HttpServletResponse.SC_BAD_REQUEST;
                respostaJson = "{\"erro\":\"Informe valores numéricos válidos.\"}";

            } catch (IllegalArgumentException erro) {
                statusHttp = HttpServletResponse.SC_BAD_REQUEST;
                respostaJson = "{\"erro\":\""
                        + escapar(erro.getMessage())
                        + "\"}";

            } catch (Exception erro) {
                erro.printStackTrace();

                statusHttp = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                respostaJson = "{\"erro\":\""
                        + escapar("Erro ao cadastrar produto: " + erro.getMessage())
                        + "\"}";
            }
        }

        response.setStatus(statusHttp);

        try (PrintWriter saida = response.getWriter()) {
            saida.print(respostaJson);
        }
    }

    private String gerarJsonProdutos(List<Produto> produtos) {
        StringBuilder json = new StringBuilder("[");
        String separador = "";

        for (Produto produto : produtos) {
            json.append(separador);
            json.append("{");
            json.append("\"id\":").append(produto.getId()).append(",");
            json.append("\"nome\":\"")
                    .append(escapar(produto.getNome()))
                    .append("\",");
            json.append("\"categoria\":\"")
                    .append(escapar(produto.getCategoria()))
                    .append("\",");
            json.append("\"preco\":")
                    .append(produto.getPreco().toPlainString())
                    .append(",");
            json.append("\"quantidade\":")
                    .append(produto.getQuantidade())
                    .append(",");
            json.append("\"estoqueMinimo\":")
                    .append(produto.getEstoqueMinimo())
                    .append(",");
            json.append("\"fornecedor\":\"")
                    .append(escapar(produto.getFornecedor()))
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