package org.gymtonic.repository;

import org.gymtonic.models.ConfiguracionGym;

import java.util.List;

public interface ConfiguracionGymRepository {

    List<ConfiguracionGym> findAll();
    ConfiguracionGym findById(Long id);
    void addConfiguracion(ConfiguracionGym configuracionGym);
    boolean deleteConfiguracion(Long id);
    boolean modifyConfiguracion(Long id, ConfiguracionGym configuracionGym);

}
