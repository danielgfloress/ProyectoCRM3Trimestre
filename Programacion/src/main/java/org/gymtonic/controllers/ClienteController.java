package org.gymtonic.controllers;

import org.gymtonic.models.Cliente;
import org.gymtonic.service.ClienteService;

import java.util.List;

public class ClienteController {

    private final ClienteService clienteService = new ClienteService();

    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    public Cliente findById(Long id) {
        return clienteService.findById(id);
    }

    public void addCliente(Cliente cliente) {
        clienteService.addCliente(cliente);
    }

    public boolean deleteCliente(Long id) {
        return clienteService.deleteCliente(id);
    }

    public boolean modifyCliente(Long id, Cliente cliente) {
        return clienteService.modifyCliente(id, cliente);
    }

}
