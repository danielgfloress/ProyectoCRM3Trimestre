package org.gymtonic.service;

import org.gymtonic.models.ConfiguracionGym;
import org.gymtonic.repository.ConfiguracionGymRepository;
import org.gymtonic.repository.ConfiguracionGymRepositoryImpl;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ConfiguracionGymService {

    private final ConfiguracionGymRepository configuracionGymRepository = new ConfiguracionGymRepositoryImpl();

    public List<ConfiguracionGym> findAll() {
        return configuracionGymRepository.findAll();
    }

    public ConfiguracionGym findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la configuración no es válido.");
        }
        ConfiguracionGym configuracionGym = configuracionGymRepository.findById(id);
        if (configuracionGym == null) {
            throw new IllegalArgumentException("No se encontró ninguna configuración con ID: " + id);
        }
        return configuracionGym;
    }

    public void addConfiguracion(ConfiguracionGym configuracionGym) {
        validarConfiguracion(configuracionGym);
        configuracionGymRepository.addConfiguracion(configuracionGym);
    }

    public boolean deleteConfiguracion(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la configuración no es válido.");
        }
        return configuracionGymRepository.deleteConfiguracion(id);
    }

    public boolean modifyConfiguracion(Long id, ConfiguracionGym configuracionGym) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la configuración no es válido.");
        }
        validarConfiguracion(configuracionGym);
        return configuracionGymRepository.modifyConfiguracion(id, configuracionGym);
    }

    private void validarConfiguracion(ConfiguracionGym configuracionGym) {
        if (configuracionGym == null) {
            throw new IllegalArgumentException("La configuración no puede ser nula.");
        }
        if (configuracionGym.getGymNombre() == null || configuracionGym.getGymNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del gimnasio es obligatorio.");
        }
        if (configuracionGym.getGymEmail() == null || configuracionGym.getGymEmail().isBlank()) {
            throw new IllegalArgumentException("El email del gimnasio es obligatorio.");
        }
        if (!configuracionGym.getGymEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El formato del email del gimnasio no es válido.");
        }
        if (configuracionGym.getGymTelefono() == null || configuracionGym.getGymTelefono().isBlank()) {
            throw new IllegalArgumentException("El teléfono del gimnasio es obligatorio.");
        }
        if (!configuracionGym.getGymTelefono().matches("^[0-9+\\s-]{6,20}$")) {
            throw new IllegalArgumentException("El formato del teléfono del gimnasio no es válido.");
        }
        if (configuracionGym.getGymDireccion() == null || configuracionGym.getGymDireccion().isBlank()) {
            throw new IllegalArgumentException("La dirección del gimnasio es obligatoria.");
        }
        validarHorario(configuracionGym.getHorarioLvAbre(), "Horario L-V apertura");
        validarHorario(configuracionGym.getHorarioLvCierra(), "Horario L-V cierre");
        validarHorario(configuracionGym.getHorarioSabAbre(), "Horario sábado apertura");
        validarHorario(configuracionGym.getHorarioSabCierra(), "Horario sábado cierre");
        validarOrdenHorario(configuracionGym.getHorarioLvAbre(), configuracionGym.getHorarioLvCierra(), "L-V");
        validarOrdenHorario(configuracionGym.getHorarioSabAbre(), configuracionGym.getHorarioSabCierra(), "sábado");
    }

    private void validarHorario(String horario, String campo) {
        if (horario == null || horario.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio.");
        }
        try {
            LocalTime.parse(horario);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(campo + " debe tener formato HH:MM.");
        }
    }

    private void validarOrdenHorario(String abre, String cierra, String dia) {
        try {
            LocalTime horaAbre = LocalTime.parse(abre);
            LocalTime horaCierra = LocalTime.parse(cierra);
            if (!horaAbre.isBefore(horaCierra)) {
                throw new IllegalArgumentException("El horario de apertura " + dia + " debe ser anterior al de cierre.");
            }
        } catch (DateTimeParseException ignored) {}
    }

}
