package org.gymtonic.controllers;

import org.gymtonic.models.Usuario;
import org.gymtonic.service.UsuarioService;

import java.util.List;

public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioService.findById(id);
    }

    public void addUsuario(Usuario usuario) {
        usuarioService.addUsuario(usuario);
    }

    public boolean deleteUsuario(Long id) {
        return usuarioService.deleteUsuario(id);
    }

    public boolean modifyUsuario(Long id, Usuario usuario) {
        return usuarioService.modifyUsuario(id, usuario);
    }

}

