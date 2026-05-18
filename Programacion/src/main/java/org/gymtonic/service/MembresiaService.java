package org.gymtonic.service;

import org.gymtonic.models.Membresia;
import org.gymtonic.repository.MembresiaRepository;
import org.gymtonic.repository.MembresiaRepositoryImpl;

import java.util.List;

public class MembresiaService {

    private final MembresiaRepository membresiaRepository = new MembresiaRepositoryImpl();

    public List<Membresia> findAll() {
        return membresiaRepository.findAll();
    }

    public Membresia findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la membresía no es válido.");
        }
        Membresia membresia = membresiaRepository.findById(id);
        if (membresia == null) {
            throw new IllegalArgumentException("No se encontró ninguna membresía con ID: " + id);
        }
        return membresia;
    }

    public void addMembresia(Membresia membresia) {
        validarMembresia(membresia);
        membresiaRepository.addMembresia(membresia);
    }

    public boolean deleteMembresia(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la membresía no es válido.");
        }
        return membresiaRepository.deleteMembresia(id);
    }

    public boolean modifyMembresia(Long id, Membresia membresia) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la membresía no es válido.");
        }
        validarMembresia(membresia);
        return membresiaRepository.modifyMembresia(id, membresia);
    }

    private void validarMembresia(Membresia membresia) {
        if (membresia == null) {
            throw new IllegalArgumentException("La membresía no puede ser nula.");
        }
        if (membresia.getNombre() == null || membresia.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la membresía es obligatorio.");
        }
        if (membresia.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres.");
        }
        if (membresia.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0.");
        }
        if (membresia.getPrecio() > 99999) {
            throw new IllegalArgumentException("El precio no puede superar 99.999.");
        }
        if (membresia.getDuracion() == null || membresia.getDuracion().isBlank()) {
            throw new IllegalArgumentException("La duración de la membresía es obligatoria.");
        }
    }

}
