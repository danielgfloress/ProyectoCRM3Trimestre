package org.gymtonic.repository;

import org.gymtonic.models.Membresia;

import java.util.List;

public interface MembresiaRepository {

    List<Membresia> findAll();
    Membresia findById(Long id);
    void addMembresia(Membresia membresia);
    boolean deleteMembresia(Long id);
    boolean modifyMembresia(Long id, Membresia membresia);

}
