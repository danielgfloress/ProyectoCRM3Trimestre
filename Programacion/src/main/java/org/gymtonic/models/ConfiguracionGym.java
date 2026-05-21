package org.gymtonic.models;

public class ConfiguracionGym {

    private Long id;
    private String gymNombre;
    private String gymEmail;
    private String gymTelefono;
    private String gymDireccion;
    private String horarioLvAbre;
    private String horarioLvCierra;
    private String horarioSabAbre;
    private String horarioSabCierra;
    private Long usuarioId;

    public ConfiguracionGym() {}

    public ConfiguracionGym(String gymNombre,String gymEmail, String gymTelefono, String gymDireccion, String horarioLvAbre, String horarioLvCierra, String horarioSabAbre, String horarioSabCierra, Long usuarioId) {
        this.gymNombre = gymNombre;
        this.gymEmail = gymEmail;
        this.gymTelefono = gymTelefono;
        this.gymDireccion = gymDireccion;
        this.horarioLvAbre = horarioLvAbre;
        this.horarioLvCierra = horarioLvCierra;
        this.horarioSabAbre = horarioSabAbre;
        this.horarioSabCierra = horarioSabCierra;
        this.usuarioId = usuarioId;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }

    public String getGymNombre() { return gymNombre; }
    public void setGymNombre(String gymNombre) { this.gymNombre = gymNombre; }

    public String getGymEmail() { return gymEmail; }
    public void setGymEmail(String gymEmail) { this.gymEmail = gymEmail; }

    public String getGymTelefono() { return gymTelefono; }
    public void setGymTelefono(String gymTelefono) { this.gymTelefono = gymTelefono; }

    public String getGymDireccion() { return gymDireccion; }
    public void setGymDireccion(String gymDireccion) { this.gymDireccion = gymDireccion; }

    public String getHorarioLvAbre() { return horarioLvAbre; }
    public void setHorarioLvAbre(String horarioLvAbre) { this.horarioLvAbre = horarioLvAbre; }

    public String getHorarioLvCierra() { return horarioLvCierra; }
    public void setHorarioLvCierra(String horarioLvCierra) { this.horarioLvCierra = horarioLvCierra; }

    public String getHorarioSabAbre() { return horarioSabAbre; }
    public void setHorarioSabAbre(String horarioSabAbre) { this.horarioSabAbre = horarioSabAbre; }

    public String getHorarioSabCierra() { return horarioSabCierra; }
    public void setHorarioSabCierra(String horarioSabCierra) { this.horarioSabCierra = horarioSabCierra; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    @Override
    public String toString() {
        return "Configuracion: " +
                "id=" + id +
                ", gymNombre= " + gymNombre +
                ", gymEmail= " + gymEmail +
                ", gymTelefono= " + gymTelefono +
                ", gymDireccion= " + gymDireccion +
                ", horarioLvAbre= " + horarioLvAbre +
                ", horarioLvCierra= " + horarioLvCierra +
                ", horarioSabAbre= " + horarioSabAbre +
                ", horarioSabCierra= " + horarioSabCierra +
                ", usuarioId= " + usuarioId;
    }

}
