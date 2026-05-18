package org.gymtonic.repository;

import org.gymtonic.database.DatabaseConnection;
import org.gymtonic.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    Connection connection;

    public UsuarioRepositoryImpl() {

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Usuario> findAll() {

        List<Usuario> usuarios = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios")) {

                while (resultSet.next()) {
                    usuarios.add(getUsuario(resultSet));
                }

                return usuarios;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Usuario findById(Long id) {

        String query = "SELECT * FROM usuarios WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    return getUsuario(resultSet);
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
    public void addUsuario(Usuario usuario) {

        String query = "INSERT INTO usuarios (nombre, email, rol, password_hash) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getRol());
            preparedStatement.setString(4, usuario.getPasswordHash());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteUsuario(Long id) {

        String query = "DELETE FROM usuarios WHERE id = ?";

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
    public boolean modifyUsuario(Long id, Usuario usuario) {

        String query = "UPDATE usuarios SET nombre = ?, email = ?, rol = ?, password_hash = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getRol());
            preparedStatement.setString(4, usuario.getPasswordHash());
            preparedStatement.setLong(5, id);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    private static Usuario getUsuario(ResultSet resultSet) throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setId(resultSet.getLong("id"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setRol(resultSet.getString("rol"));
        usuario.setPasswordHash(resultSet.getString("password_hash"));

        return usuario;

    }

}
