package org.gymtonic.contorollers;

import org.gymtonic.models.Clase;
import org.gymtonic.service.ClaseService;

import java.util.List;

public class ClaseController {

    private final ClaseService claseService = new ClaseService();

    public List<Clase> findAll() {
        return claseService.findAll();
    }

    public Clase findById(Long id) {
        return claseService.findById(id);
    }

    public void addClase(Clase clase) {
        claseService.addClase(clase);
    }

    public boolean deleteClase(Long id) {
        return claseService.deleteClase(id);
    }

    public boolean modifyClase(Long id, Clase clase) {
        return claseService.modifyClase(id, clase);
    }

}
