package org.gymtonic.controllers;

import org.gymtonic.models.ConfiguracionGym;
import org.gymtonic.service.ConfiguracionGymService;

import java.util.List;

public class ConfiguracionGymController {

    private final ConfiguracionGymService configuracionGymService = new ConfiguracionGymService();

    public List<ConfiguracionGym> findAll() {
        return configuracionGymService.findAll();
    }

    public ConfiguracionGym findById(Long id) {
        return configuracionGymService.findById(id);
    }
    public boolean modifyConfiguracion(Long id, ConfiguracionGym configuracionGym) {
        return configuracionGymService.modifyConfiguracion(id, configuracionGym);
    }

}
