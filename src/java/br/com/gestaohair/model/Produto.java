package br.com.gestaohair.model;

import java.math.BigDecimal;

public class Produto {

    private int id;
    private String nome;
    private String categoria;
    private BigDecimal preco;
    private int quantidade;
    private int estoqueMinimo;
    private String fornecedor;

    public Produto() {
    }

    public Produto(
            int id,
            String nome,
            String categoria,
            BigDecimal preco,
            int quantidade,
            int estoqueMinimo,
            String fornecedor
    ) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.fornecedor = fornecedor;
    }

    public Produto(
            String nome,
            String categoria,
            BigDecimal preco,
            int quantidade,
            int estoqueMinimo,
            String fornecedor
    ) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.fornecedor = fornecedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
}