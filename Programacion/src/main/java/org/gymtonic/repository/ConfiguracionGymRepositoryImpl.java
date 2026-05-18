package org.gymtonic.repository;

import org.gymtonic.database.DatabaseConnection;
import org.gymtonic.models.ConfiguracionGym;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfiguracionGymRepositoryImpl implements ConfiguracionGymRepository {

    Connection connection;

    public ConfiguracionGymRepositoryImpl() {

        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ConfiguracionGym> findAll() {

        List<ConfiguracionGym> configuraciones = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM configuracion")) {

                while (resultSet.next()) {
                    configuraciones.add(getConfiguracion(resultSet));
                }

                return configuraciones;

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public ConfiguracionGym findById(Long id) {

        String query = "SELECT * FROM configuracion WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    return getConfiguracion(resultSet);
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
    public void addConfiguracion(ConfiguracionGym configuracionGym) {

        String query = "INSERT INTO configuracion (gym_nombre, gym_email, gym_telefono, gym_direccion, " +
                "horario_lv_abre, horario_lv_cierra, horario_sab_abre, horario_sab_cierra, usuario_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, configuracionGym.getGymNombre());
            preparedStatement.setString(2, configuracionGym.getGymEmail());
            preparedStatement.setString(3, configuracionGym.getGymTelefono());
            preparedStatement.setString(4, configuracionGym.getGymDireccion());
            preparedStatement.setString(5, configuracionGym.getHorarioLvAbre());
            preparedStatement.setString(6, configuracionGym.getHorarioLvCierra());
            preparedStatement.setString(7, configuracionGym.getHorarioSabAbre());
            preparedStatement.setString(8, configuracionGym.getHorarioSabCierra());

            if (configuracionGym.getUsuarioId() != null) {
                preparedStatement.setLong(9, configuracionGym.getUsuarioId());
            } else {
                preparedStatement.setNull(9, Types.INTEGER);
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteConfiguracion(Long id) {

        String query = "DELETE FROM configuracion WHERE id = ?";

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
    public boolean modifyConfiguracion(Long id, ConfiguracionGym configuracionGym) {

        String query = "UPDATE configuracion SET gym_nombre = ?, gym_email = ?, gym_telefono = ?, gym_direccion = ?, " +
                "horario_lv_abre = ?, horario_lv_cierra = ?, horario_sab_abre = ?, horario_sab_cierra = ?, usuario_id = ? " +
                "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, configuracionGym.getGymNombre());
            preparedStatement.setString(2, configuracionGym.getGymEmail());
            preparedStatement.setString(3, configuracionGym.getGymTelefono());
            preparedStatement.setString(4, configuracionGym.getGymDireccion());
            preparedStatement.setString(5, configuracionGym.getHorarioLvAbre());
            preparedStatement.setString(6, configuracionGym.getHorarioLvCierra());
            preparedStatement.setString(7, configuracionGym.getHorarioSabAbre());
            preparedStatement.setString(8, configuracionGym.getHorarioSabCierra());

            if (configuracionGym.getUsuarioId() != null) {
                preparedStatement.setLong(9, configuracionGym.getUsuarioId());
            } else {
                preparedStatement.setNull(9, Types.INTEGER);
            }

            preparedStatement.setLong(10, id);

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    private static ConfiguracionGym getConfiguracion(ResultSet resultSet) throws SQLException {

        ConfiguracionGym configuracionGym = new ConfiguracionGym();

        configuracionGym.setId(resultSet.getLong("id"));
        configuracionGym.setGymNombre(resultSet.getString("gym_nombre"));
        configuracionGym.setGymEmail(resultSet.getString("gym_email"));
        configuracionGym.setGymTelefono(resultSet.getString("gym_telefono"));
        configuracionGym.setGymDireccion(resultSet.getString("gym_direccion"));
        configuracionGym.setHorarioLvAbre(resultSet.getString("horario_lv_abre"));
        configuracionGym.setHorarioLvCierra(resultSet.getString("horario_lv_cierra"));
        configuracionGym.setHorarioSabAbre(resultSet.getString("horario_sab_abre"));
        configuracionGym.setHorarioSabCierra(resultSet.getString("horario_sab_cierra"));

        long usuarioId = resultSet.getLong("usuario_id");
        configuracionGym.setUsuarioId(resultSet.wasNull() ? null : usuarioId);

        return configuracionGym;

    }

}
