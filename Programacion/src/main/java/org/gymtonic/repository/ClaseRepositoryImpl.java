package org.gymtonic.repository;

import org.gymtonic.database.DatabaseConnection;
import org.gymtonic.models.Clase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaseRepositoryImpl implements ClaseRepository {

    Connection connection;

    public ClaseRepositoryImpl() {

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Clase> findAll() {

        List<Clase> clases = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM clases")) {

                while (resultSet.next()) {
                    clases.add(getClase(resultSet));
                }

                return clases;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Clase findById(Long id) {

        String query = "SELECT * FROM clases WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    return getClase(resultSet);
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
    public void addClase(Clase clase) {

        String query = "INSERT INTO clases (nombre, instructor, horario, capacidad_maxima, nivel) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, clase.getNombre());
            preparedStatement.setString(2, clase.getInstructor());
            preparedStatement.setString(3, clase.getHorario());
            preparedStatement.setInt(4, clase.getCapacidadMaxima());
            preparedStatement.setString(5, clase.getNivel());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteClase(Long id) {

        String query = "DELETE FROM clases WHERE id = ?";

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
    public boolean modifyClase(Long id, Clase clase) {

        String query = "UPDATE clases SET nombre = ?, instructor = ?, horario = ?, capacidad_maxima = ?, nivel = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, clase.getNombre());
            preparedStatement.setString(2, clase.getInstructor());
            preparedStatement.setString(3, clase.getHorario());
            preparedStatement.setInt(4, clase.getCapacidadMaxima());
            preparedStatement.setString(5, clase.getNivel());
            preparedStatement.setLong(6, id);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    private static Clase getClase(ResultSet resultSet) throws SQLException {

        Clase clase = new Clase();

        clase.setId(resultSet.getLong("id"));
        clase.setNombre(resultSet.getString("nombre"));
        clase.setInstructor(resultSet.getString("instructor"));
        clase.setHorario(resultSet.getString("horario"));
        clase.setCapacidadMaxima(resultSet.getInt("capacidad_maxima"));
        clase.setInscritos(resultSet.getInt("inscritos"));
        clase.setNivel(resultSet.getString("nivel"));

        return clase;

    }

}
