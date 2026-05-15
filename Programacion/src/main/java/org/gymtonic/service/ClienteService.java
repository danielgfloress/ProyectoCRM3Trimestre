package org.gymtonic.service;

import org.gymtonic.models.Cliente;
import org.gymtonic.repository.ClienteRepository;
import org.gymtonic.repository.ClienteRepositoryImpl;

import java.util.List;

public class ClienteService {

    private final ClienteRepository clienteRepository = new ClienteRepositoryImpl();

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del cliente no es válido.");
        }
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new IllegalArgumentException("No se encontró ningún cliente con ID: " + id);
        }
        return cliente;
    }

    public void addCliente(Cliente cliente) {
        validarCliente(cliente);
        clienteRepository.addCliente(cliente);
    }

    public boolean deleteCliente(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del cliente no es válido.");
        }
        return clienteRepository.deleteCliente(id);
    }

    public boolean modifyCliente(Long id, Cliente cliente) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del cliente no es válido.");
        }
        validarCliente(cliente);
        return clienteRepository.modifyCliente(id, cliente);
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        if (cliente.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres.");
        }
        if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email del cliente es obligatorio.");
        }
        if (!cliente.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El formato del email no es válido.");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().isBlank()) {
            throw new IllegalArgumentException("El teléfono del cliente es obligatorio.");
        }
        if (!cliente.getTelefono().matches("^[0-9+\\s-]{6,20}$")) {
            throw new IllegalArgumentException("El formato del teléfono no es válido.");
        }
        if (cliente.getDireccion() == null || cliente.getDireccion().isBlank()) {
            throw new IllegalArgumentException("La dirección del cliente es obligatoria.");
        }
    }

}

