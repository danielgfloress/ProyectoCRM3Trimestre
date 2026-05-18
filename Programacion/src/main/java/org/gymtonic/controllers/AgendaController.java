package org.gymtonic.controllers;

import org.gymtonic.models.Agenda;
import org.gymtonic.service.AgendaService;

import java.util.List;

public class AgendaController {

    private final AgendaService agendaService = new AgendaService();

    public List<Agenda> findAll() {
        return agendaService.findAll();
    }

    public Agenda findById(Long id) {
        return agendaService.findById(id);
    }

    public void addAgenda(Agenda agenda) {
        agendaService.addAgenda(agenda);
    }

    public boolean deleteAgenda(Long id) {
        return agendaService.deleteAgenda(id);
    }

    public boolean modifyAgenda(Long id, Agenda agenda) {
        return agendaService.modifyAgenda(id, agenda);
    }

}
