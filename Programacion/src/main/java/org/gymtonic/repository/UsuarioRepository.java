package org.gymtonic.repository;

import org.gymtonic.models.Usuario;

import java.util.List;

public interface UsuarioRepository {

    List<Usuario> findAll();
    Usuario findById(Long id);
    void addUsuario(Usuario usuario);
    boolean deleteUsuario(Long id);
    boolean modifyUsuario(Long id, Usuario usuario);

}

