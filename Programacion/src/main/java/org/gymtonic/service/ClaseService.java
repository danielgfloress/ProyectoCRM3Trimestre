package org.gymtonic.service;

import org.gymtonic.models.Clase;
import org.gymtonic.repository.ClaseRepository;
import org.gymtonic.repository.ClaseRepositoryImpl;

import java.util.List;

public class ClaseService {

    private final ClaseRepository claseRepository = new ClaseRepositoryImpl();

    private static final String[] NIVELES_VALIDOS = {"principiante", "intermedio", "avanzado"};

    public List<Clase> findAll() {
        return claseRepository.findAll();
    }

    public Clase findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la clase no es válido.");
        }
        Clase clase = claseRepository.findById(id);
        if (clase == null) {
            throw new IllegalArgumentException("No se encontró ninguna clase con ID: " + id);
        }
        return clase;
    }

    public void addClase(Clase clase) {
        validarClase(clase);
        claseRepository.addClase(clase);
    }

    public boolean deleteClase(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la clase no es válido.");
        }
        return claseRepository.deleteClase(id);
    }

    public boolean modifyClase(Long id, Clase clase) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID de la clase no es válido.");
        }
        validarClase(clase);
        return claseRepository.modifyClase(id, clase);
    }

    private void validarClase(Clase clase) {
        if (clase == null) {
            throw new IllegalArgumentException("La clase no puede ser nula.");
        }
        if (clase.getNombre() == null || clase.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la clase es obligatorio.");
        }
        if (clase.getInstructor() == null || clase.getInstructor().isBlank()) {
            throw new IllegalArgumentException("El instructor de la clase es obligatorio.");
        }
        if (clase.getHorario() == null || clase.getHorario().isBlank()) {
            throw new IllegalArgumentException("El horario de la clase es obligatorio.");
        }
        if (clase.getCapacidadMaxima() <= 0) {
            throw new IllegalArgumentException("La capacidad máxima debe ser mayor que 0.");
        }
        if (clase.getCapacidadMaxima() > 500) {
            throw new IllegalArgumentException("La capacidad máxima no puede superar 500.");
        }
        if (clase.getNivel() == null || clase.getNivel().isBlank()) {
            throw new IllegalArgumentException("El nivel de la clase es obligatorio.");
        }
        boolean nivelValido = false;
        for (String n : NIVELES_VALIDOS) {
            if (n.equalsIgnoreCase(clase.getNivel())) {
                nivelValido = true;
                break;
            }
        }
        if (!nivelValido) {
            throw new IllegalArgumentException("El nivel debe ser: principiante, intermedio o avanzado.");
        }
    }

}
