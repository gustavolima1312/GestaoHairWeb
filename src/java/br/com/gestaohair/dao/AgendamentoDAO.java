package br.com.gestaohair.dao;

import br.com.gestaohair.model.Agendamento;
import br.com.gestaohair.util.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public List<Agendamento> listar() throws SQLException {
        List<Agendamento> agendamentos = new ArrayList<>();

        String sql = "SELECT id, cliente, servico, profissional, status, "
                + "data_agendamento, hora_agendamento "
                + "FROM agendamentos "
                + "ORDER BY data_agendamento, hora_agendamento";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                Agendamento agendamento = new Agendamento();

                agendamento.setId(resultado.getInt("id"));
                agendamento.setCliente(resultado.getString("cliente"));
                agendamento.setServico(resultado.getString("servico"));
                agendamento.setProfissional(
                        resultado.getString("profissional")
                );
                agendamento.setStatus(resultado.getString("status"));
                agendamento.setDataAgendamento(
                        resultado.getDate("data_agendamento").toLocalDate()
                );
                agendamento.setHoraAgendamento(
                        resultado.getTime("hora_agendamento").toLocalTime()
                );

                agendamentos.add(agendamento);
            }
        }

        return agendamentos;
    }

    public boolean existeConflito(
            String profissional,
            java.time.LocalDate data,
            java.time.LocalTime hora
    ) throws SQLException {

        String sql = "SELECT COUNT(*) "
                + "FROM agendamentos "
                + "WHERE profissional = ? "
                + "AND data_agendamento = ? "
                + "AND hora_agendamento = ?";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, profissional);
            comando.setDate(2, Date.valueOf(data));
            comando.setTime(3, Time.valueOf(hora));

            try (ResultSet resultado = comando.executeQuery()) {
                resultado.next();

                return resultado.getInt(1) > 0;
            }
        }
    }

    public void inserir(Agendamento agendamento) throws SQLException {
        String sql = "INSERT INTO agendamentos "
                + "(cliente, servico, profissional, status, "
                + "data_agendamento, hora_agendamento) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, agendamento.getCliente());
            comando.setString(2, agendamento.getServico());
            comando.setString(3, agendamento.getProfissional());
            comando.setString(4, agendamento.getStatus());
            comando.setDate(
                    5,
                    Date.valueOf(agendamento.getDataAgendamento())
            );
            comando.setTime(
                    6,
                    Time.valueOf(agendamento.getHoraAgendamento())
            );

            comando.executeUpdate();
        }
    }
}