package org.gymtonic.models;

public class Membresia {

    private Long id;
    private String nombre;
    private double precio;
    private String duracion;

    public Membresia() {}

    public Membresia(String nombre, double precio, String duracion) {
        this.nombre = nombre;
        this.precio = precio;
        this.duracion = duracion;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    @Override
    public String toString() {
        return "id=" + id +
                ", nombre= " + nombre +
                ", precio=" + precio +
                ", duracion= " + duracion;
    }

}