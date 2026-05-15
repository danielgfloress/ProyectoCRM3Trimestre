package org.gymtonic.service;

import org.gymtonic.models.Agenda;
import org.gymtonic.repository.AgendaRepository;
import org.gymtonic.repository.AgendaRepositoryImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AgendaService {

    private final AgendaRepository agendaRepository = new AgendaRepositoryImpl();

    private static final String[] ESTADOS_VALIDOS = {"reservado", "cancelado"};

    public List<Agenda> findAll() {
        return agendaRepository.findAll();
    }

    public Agenda findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la agenda no es válido.");
        }
        Agenda agenda = agendaRepository.findById(id);
        if (agenda == null) {
            throw new IllegalArgumentException("No se encontró ningún evento con ID: " + id);
        }
        return agenda;
    }

    public void addAgenda(Agenda agenda) {
        validarAgenda(agenda);
        agendaRepository.addAgenda(agenda);
    }

    public boolean deleteAgenda(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la agenda no es válido.");
        }
        return agendaRepository.deleteAgenda(id);
    }

    public boolean modifyAgenda(Long id, Agenda agenda) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la agenda no es válido.");
        }
        validarAgenda(agenda);
        return agendaRepository.modifyAgenda(id, agenda);
    }

    private void validarAgenda(Agenda agenda) {
        if (agenda == null) {
            throw new IllegalArgumentException("La agenda no puede ser nula.");
        }
        if (agenda.getClaseId() == null || agenda.getClaseId() <= 0) {
            throw new IllegalArgumentException("El ID de la clase es obligatorio y debe ser válido.");
        }
        if (agenda.getFecha() == null || agenda.getFecha().isBlank()) {
            throw new IllegalArgumentException("La fecha es obligatoria.");
        }
        try {
            LocalDate.parse(agenda.getFecha());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El formato de la fecha debe ser YYYY-MM-DD.");
        }
        if (agenda.getHora() == null || agenda.getHora().isBlank()) {
            throw new IllegalArgumentException("La hora es obligatoria.");
        }
        try {
            LocalTime.parse(agenda.getHora());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El formato de la hora debe ser HH:MM.");
        }
        if (agenda.getEstado() == null || agenda.getEstado().isBlank()) {
            throw new IllegalArgumentException("El estado es obligatorio.");
        }
        boolean estadoValido = false;
        for (String e : ESTADOS_VALIDOS) {
            if (e.equalsIgnoreCase(agenda.getEstado())) {
                estadoValido = true;
                break;
            }
        }
        if (!estadoValido) {
            throw new IllegalArgumentException("El estado debe ser: reservado o cancelado.");
        }
    }

}
