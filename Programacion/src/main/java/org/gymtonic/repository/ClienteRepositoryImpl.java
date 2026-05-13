package org.gymtonic.repository;

import org.gymtonic.database.DatabaseConnection;
import org.gymtonic.models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryImpl implements ClienteRepository {

    Connection connection;

    public ClienteRepositoryImpl() {

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Cliente> findAll() {

        List<Cliente> clientes = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM clientes")) {

                while (resultSet.next()) {
                    clientes.add(getCliente(resultSet));
                }

                return clientes;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Cliente findById(Long id) {

        String query = "SELECT * FROM clientes WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    return getCliente(resultSet);
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
    public void addCliente(Cliente cliente) {

        String query = "INSERT INTO clientes (nombre, email, telefono, direccion) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getEmail());
            preparedStatement.setString(3, cliente.getTelefono());
            preparedStatement.setString(4, cliente.getDireccion());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteCliente(Long id) {

        String query = "DELETE FROM clientes WHERE id = ?";

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
    public boolean modifyCliente(Long id, Cliente cliente) {

        String query = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, direccion = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getEmail());
            preparedStatement.setString(3, cliente.getTelefono());
            preparedStatement.setString(4, cliente.getDireccion());
            preparedStatement.setLong(5, id);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    private static Cliente getCliente(ResultSet resultSet) throws SQLException {

        Cliente cliente = new Cliente();

        cliente.setId(resultSet.getLong("id"));
        cliente.setNombre(resultSet.getString("nombre"));
        cliente.setEmail(resultSet.getString("email"));
        cliente.setTelefono(resultSet.getString("telefono"));
        cliente.setDireccion(resultSet.getString("direccion"));
        cliente.setFechaAlta(resultSet.getString("fecha_alta"));

        return cliente;

    }

}