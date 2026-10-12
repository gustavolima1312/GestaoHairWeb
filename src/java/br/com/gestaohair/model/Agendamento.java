package br.com.gestaohair.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {

    private int id;
    private String cliente;
    private String servico;
    private String profissional;
    private String status;
    private LocalDate dataAgendamento;
    private LocalTime horaAgendamento;

    public Agendamento() {
    }

    public Agendamento(
            int id,
            String cliente,
            String servico,
            String profissional,
            String status,
            LocalDate dataAgendamento,
            LocalTime horaAgendamento
    ) {
        this.id = id;
        this.cliente = cliente;
        this.servico = servico;
        this.profissional = profissional;
        this.status = status;
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
    }

    public Agendamento(
            String cliente,
            String servico,
            String profissional,
            String status,
            LocalDate dataAgendamento,
            LocalTime horaAgendamento
    ) {
        this.cliente = cliente;
        this.servico = servico;
        this.profissional = profissional;
        this.status = status;
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public LocalTime getHoraAgendamento() {
        return horaAgendamento;
    }

    public void setHoraAgendamento(LocalTime horaAgendamento) {
        this.horaAgendamento = horaAgendamento;
    }
}