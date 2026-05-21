package org.gymtonic.menus;

import org.gymtonic.controllers.ConfiguracionGymController;
import org.gymtonic.models.ConfiguracionGym;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConfiguracionGymMenu {

    private final Scanner sc;
    private final ConfiguracionGymController configuracionGymController = new ConfiguracionGymController();

    public ConfiguracionGymMenu(Scanner sc) {
        this.sc = sc;
    }

    public void mostrar() {

        int opcion;

        do {
            System.out.println("\n--- CONFIGURACIÓN GYM ---");
            System.out.println("1. Ver Configuración");
            System.out.println("2. Modificar configuración");
            System.out.println("3. Volver");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    configuracionGymController.findAll().forEach(System.out::println);
                    break;
                case 2:
                    modificarConfiguracion();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 3);

    }


    private void modificarConfiguracion() {

        String gymNombre = pedirCampo("Nuevo nombre del gimnasio: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
        });
        String gymEmail = pedirCampo("Nuevo email: ", v -> {
            if (!v.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
                throw new IllegalArgumentException("El formato del email no es válido.");
        });
        String gymTelefono = pedirCampo("Nuevo teléfono: ", v -> {
            if (!v.matches("^[0-9+\\s-]{6,20}$"))
                throw new IllegalArgumentException("El formato del teléfono no es válido.");
        });
        String gymDireccion = pedirCampo("Nueva dirección: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("La dirección es obligatoria.");
        });
        String lvAbre = pedirHorario("Horario L-V abre (HH:MM): ");
        String lvCierra = pedirHorarioDespues("Horario L-V cierra (HH:MM): ", lvAbre);
        String sabAbre = pedirHorario("Horario sábado abre (HH:MM): ");
        String sabCierra = pedirHorarioDespues("Horario sábado cierra (HH:MM): ", sabAbre);
        long usuarioRaw = pedirLong("ID de usuario (0 = ninguno): ", v -> {
            if (v < 0) throw new IllegalArgumentException("El ID no puede ser negativo.");
        });
        Long usuarioId = usuarioRaw == 0 ? null : usuarioRaw;
        System.out.println(configuracionGymController.modifyConfiguracion(1L, new ConfiguracionGym(gymNombre,gymEmail, gymTelefono, gymDireccion, lvAbre, lvCierra, sabAbre, sabCierra, usuarioId)) ? "Configuración modificada." : "No encontrada.");
    }

    private String pedirCampo(String etiqueta, Consumer<String> validacion) {
        while (true) {
            System.out.print(etiqueta);
            String valor = sc.nextLine();
            try {
                validacion.accept(valor);
                return valor;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private String pedirHorario(String etiqueta) {
        return pedirCampo(etiqueta, v -> {
            try {
                LocalTime.parse(v);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("El formato debe ser HH:MM.");
            }
        });
    }

    private String pedirHorarioDespues(String etiqueta, String horarioAntes) {
        return pedirCampo(etiqueta, v -> {
            try {
                LocalTime antes = LocalTime.parse(horarioAntes);
                LocalTime despues = LocalTime.parse(v);
                if (!antes.isBefore(despues))
                    throw new IllegalArgumentException("La hora de cierre debe ser posterior a la de apertura (" + horarioAntes + ").");
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("El formato debe ser HH:MM.");
            }
        });
    }

    private long pedirLong(String etiqueta, Consumer<Long> validacion) {
        while (true) {
            System.out.print(etiqueta);
            try {
                long valor = Long.parseLong(sc.nextLine());
                validacion.accept(valor);
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un número válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
