package org.gymtonic.repository;

import org.gymtonic.models.Agenda;

import java.util.List;

public interface AgendaRepository {

    List<Agenda> findAll();
    Agenda findById(Long id);
    void addAgenda(Agenda agenda);
    boolean deleteAgenda(Long id);
    boolean modifyAgenda(Long id, Agenda agenda);

}
