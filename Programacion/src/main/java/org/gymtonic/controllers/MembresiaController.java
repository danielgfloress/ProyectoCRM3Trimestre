package org.gymtonic.controllers;

import org.gymtonic.models.Membresia;
import org.gymtonic.service.MembresiaService;

import java.util.List;

public class MembresiaController {

    private final MembresiaService membresiaService = new MembresiaService();

    public List<Membresia> findAll() {
        return membresiaService.findAll();
    }

    public Membresia findById(Long id) {
        return membresiaService.findById(id);
    }

    public void addMembresia(Membresia membresia) {
        membresiaService.addMembresia(membresia);
    }

    public boolean deleteMembresia(Long id) {
        return membresiaService.deleteMembresia(id);
    }

    public boolean modifyMembresia(Long id, Membresia membresia) {
        return membresiaService.modifyMembresia(id, membresia);
    }

}
