package org.gymtonic.models;

public class Clase {

    private Long id;
    private String nombre;
    private String instructor;
    private String horario;
    private int capacidadMaxima;
    private int inscritos;
    private String nivel;

    public Clase() {}

    public Clase(String nombre, String instructor, String horario, int capacidadMaxima, String nivel) {
        this.nombre = nombre;
        this.instructor = instructor;
        this.horario = horario;
        this.capacidadMaxima = capacidadMaxima;
        this.nivel = nivel;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public int getInscritos() { return inscritos; }
    public void setInscritos(int inscritos) { this.inscritos = inscritos; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    @Override
    public String toString() {
        return "id= " + id +
                ", nombre= " + nombre+
                ", instructor= " + instructor +
                ", horario= " + horario +
                ", capacidadMaxima= " + capacidadMaxima +
                ", inscritos= " + inscritos +
                ", nivel= " + nivel ;
    }

}
