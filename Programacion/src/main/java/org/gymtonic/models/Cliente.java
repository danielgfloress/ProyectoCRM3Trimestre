package org.gymtonic.models;

public class Cliente {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String fechaAlta;

    public Cliente() {}

    public Cliente(String nombre, String email, String telefono, String direccion) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
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

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }

    @Override
    public String toString() {
        return "id= " + id +
                ", nombre= " + nombre +
                ", email= " + email +
                ", telefono= " + telefono +
                ", direccion= " + direccion +
                ", fechaAlta= " + fechaAlta;
    }

}
