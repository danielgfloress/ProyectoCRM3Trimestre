package org.gymtonic.repository;

import org.gymtonic.models.Cliente;

import java.util.List;

public interface ClienteRepository {

    List<Cliente> findAll();
    Cliente findById(Long id);
    void addCliente(Cliente cliente);
    boolean deleteCliente(Long id);
    boolean modifyCliente(Long id, Cliente cliente);

}
