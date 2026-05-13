package org.gymtonic.repository;

import org.gymtonic.models.Clase;

import java.util.List;

public interface ClaseRepository {

    List<Clase> findAll();
    Clase findById(Long id);
    void addClase(Clase clase);
    boolean deleteClase(Long id);
    boolean modifyClase(Long id, Clase clase);

}