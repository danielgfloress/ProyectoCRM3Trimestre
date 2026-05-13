package org.gymtonic.repository;

import org.gymtonic.database.DatabaseConnection;
import org.gymtonic.models.Agenda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaRepositoryImpl implements AgendaRepository {

    Connection connection;

    public AgendaRepositoryImpl() {

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Agenda> findAll() {

        List<Agenda> eventos = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM agenda")) {

                while (resultSet.next()) {
                    eventos.add(getAgenda(resultSet));
                }

                return eventos;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Agenda findById(Long id) {

        String query = "SELECT * FROM agenda WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    return getAgenda(resultSet);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void addAgenda(Agenda agenda) {

        String query = "INSERT INTO agenda (clase_id, cliente_id, fecha, hora, estado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, agenda.getClaseId());

            if (agenda.getClienteId() != null) {
                preparedStatement.setLong(2, agenda.getClienteId());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }

            preparedStatement.setString(3, agenda.getFecha());
            preparedStatement.setString(4, agenda.getHora());
            preparedStatement.setString(5, agenda.getEstado());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteAgenda(Long id) {

        String query = "DELETE FROM agenda WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean modifyAgenda(Long id, Agenda agenda) {

        String query = "UPDATE agenda SET clase_id = ?, cliente_id = ?, fecha = ?, hora = ?, estado = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, agenda.getClaseId());

            if (agenda.getClienteId() != null) {
                preparedStatement.setLong(2, agenda.getClienteId());
            } else {
                preparedStatement.setNull(2, Types.INTEGER);
            }

            preparedStatement.setString(3, agenda.getFecha());
            preparedStatement.setString(4, agenda.getHora());
            preparedStatement.setString(5, agenda.getEstado());
            preparedStatement.setLong(6, id);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    private static Agenda getAgenda(ResultSet resultSet) throws SQLException {

        Agenda agenda = new Agenda();

        agenda.setId(resultSet.getLong("id"));
        agenda.setClaseId(resultSet.getLong("clase_id"));

        long clienteId = resultSet.getLong("cliente_id");
        agenda.setClienteId(resultSet.wasNull() ? null : clienteId);

        agenda.setFecha(resultSet.getString("fecha"));
        agenda.setHora(resultSet.getString("hora"));
        agenda.setEstado(resultSet.getString("estado"));

        return agenda;

    }

}
