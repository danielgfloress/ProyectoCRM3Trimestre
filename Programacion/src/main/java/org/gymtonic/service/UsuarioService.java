package org.gymtonic.service;

import org.gymtonic.models.Usuario;
import org.gymtonic.repository.UsuarioRepository;
import org.gymtonic.repository.UsuarioRepositoryImpl;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository = new UsuarioRepositoryImpl();

    private static final List<String> ROLES_VALIDOS = List.of("admin", "recepcionista", "entrenador", "cliente");

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido.");
        }
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró ningún usuario con ID: " + id);
        }
        return usuario;
    }

    public void addUsuario(Usuario usuario) {
        validarUsuario(usuario);
        usuarioRepository.addUsuario(usuario);
    }

    public boolean deleteUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido.");
        }
        return usuarioRepository.deleteUsuario(id);
    }

    public boolean modifyUsuario(Long id, Usuario usuario) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido.");
        }
        validarUsuario(usuario);
        return usuarioRepository.modifyUsuario(id, usuario);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio.");
        }
        if (usuario.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede superar los 100 caracteres.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email del usuario es obligatorio.");
        }
        if (!usuario.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El formato del email no es válido.");
        }
        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            throw new IllegalArgumentException("El rol del usuario es obligatorio.");
        }
        if (!ROLES_VALIDOS.contains(usuario.getRol())) {
            throw new IllegalArgumentException("El rol debe ser: admin, recepcionista, entrenador o cliente.");
        }
        if (usuario.getPasswordHash() == null || usuario.getPasswordHash().isBlank()) {
            throw new IllegalArgumentException("La contraseña del usuario es obligatoria.");
        }
    }

}

