package org.gymtonic.models;

public class Agenda {

    private Long id;
    private Long claseId;
    private Long clienteId;
    private String fecha;
    private String hora;
    private String estado;

    public Agenda() {}

    public Agenda(Long claseId, Long clienteId, String fecha, String hora, String estado) {
        this.claseId = claseId;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public Long getClaseId() { return claseId; }
    public void setClaseId(Long claseId) { this.claseId = claseId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "id= " + id +
                ", claseId= " + claseId +
                ", clienteId= " + clienteId +
                ", fecha= " + fecha +
                ", hora= " + hora +
                ", estado= " + estado;
    }

}
