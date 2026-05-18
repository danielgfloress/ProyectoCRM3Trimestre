package org.gymtonic.repository;

import org.gymtonic.database.DatabaseConnection;
import org.gymtonic.models.Membresia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembresiaRepositoryImpl implements MembresiaRepository {

    Connection connection;

    public MembresiaRepositoryImpl() {

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Membresia> findAll() {

        List<Membresia> membresias = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM membresias")) {

                while (resultSet.next()) {
                    membresias.add(getMembresia(resultSet));
                }

                return membresias;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Membresia findById(Long id) {

        String query = "SELECT * FROM membresias WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    return getMembresia(resultSet);
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
    public void addMembresia(Membresia membresia) {

        String query = "INSERT INTO membresias (nombre, precio, duracion) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, membresia.getNombre());
            preparedStatement.setDouble(2, membresia.getPrecio());
            preparedStatement.setString(3, membresia.getDuracion());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteMembresia(Long id) {

        String query = "DELETE FROM membresias WHERE id = ?";

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
    public boolean modifyMembresia(Long id, Membresia membresia) {

        String query = "UPDATE membresias SET nombre = ?, precio = ?, duracion = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, membresia.getNombre());
            preparedStatement.setDouble(2, membresia.getPrecio());
            preparedStatement.setString(3, membresia.getDuracion());
            preparedStatement.setLong(4, id);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    private static Membresia getMembresia(ResultSet resultSet) throws SQLException {

        Membresia membresia = new Membresia();

        membresia.setId(resultSet.getLong("id"));
        membresia.setNombre(resultSet.getString("nombre"));
        membresia.setPrecio(resultSet.getDouble("precio"));
        membresia.setDuracion(resultSet.getString("duracion"));

        return membresia;

    }

}
