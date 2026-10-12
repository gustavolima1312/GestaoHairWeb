package br.com.gestaohair.model;

import java.time.LocalDate;

public class Cliente {

    private int id;
    private String nome;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private String observacao;

    public Cliente() {
    }

    public Cliente(
            int id,
            String nome,
            String telefone,
            String email,
            LocalDate dataNascimento,
            String observacao
    ) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.observacao = observacao;
    }

    public Cliente(
            String nome,
            String telefone,
            String email,
            LocalDate dataNascimento,
            String observacao
    ) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.observacao = observacao;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}