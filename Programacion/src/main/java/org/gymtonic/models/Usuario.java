package org.gymtonic.models;

public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private String passwordHash;

    public Usuario() {}

    public Usuario(String nombre, String email, String rol, String passwordHash) {
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.passwordHash = passwordHash;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    @Override
    public String toString() {
        return "Usuario: " +
                "id=" + id +
                ", nombre= " + nombre +
                ", email= " + email +
                ", rol= " + rol +
                ", contraseña= " + passwordHash;
    }

}


