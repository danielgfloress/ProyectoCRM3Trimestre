package org.gymtonic.menus;

import org.gymtonic.controllers.AgendaController;
import org.gymtonic.controllers.ClaseController;
import org.gymtonic.models.Agenda;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Consumer;


public class AgendaMenu {

    private final Scanner sc;
    private final AgendaController agendaController = new AgendaController();
    private final ClaseController claseController = new ClaseController();

    public AgendaMenu(Scanner sc) {
        this.sc = sc;
    }

    public void mostrar() {

        int opcion;

        do {

            System.out.println("\n--- AGENDA ---");
            System.out.println("1. Listar todos los eventos");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Añadir evento");
            System.out.println("4. Modificar evento");
            System.out.println("5. Eliminar evento");
            System.out.println("6.Volver");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    agendaController.findAll().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("ID: ");
                    System.out.println(agendaController.findById(sc.nextLong()));
                    break;
                case 3:
                    añadirAgenda();
                    break;
                case 4:
                    agendaController.findAll().forEach(System.out::println);
                    modificarAgenda();
                    break;
                case 5:
                    agendaController.findAll().forEach(System.out::println);
                    eliminarAgenda();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción no válida.");

            }

        }while (opcion != 6) ;

    }

    private void añadirAgenda() {

        System.out.println("\n-- Clases disponibles --");
        claseController.findAll().forEach(System.out::println);

        long claseId = pedirLong("ID de clase: ", v -> {
            if (v <= 0) throw new IllegalArgumentException("El ID de clase debe ser mayor que 0.");
            if (claseController.findById(v) == null)
                throw new IllegalArgumentException("No existe ninguna clase con ese ID.");
        });
        long clienteRaw = pedirLong("ID de cliente (0 = sin cliente): ", v -> {
            if (v < 0) throw new IllegalArgumentException("El ID de cliente no puede ser negativo.");
        });
        Long clienteId = clienteRaw == 0 ? null : clienteRaw;

        String fecha = pedirCampo("Fecha (YYYY-MM-DD): ", v -> {
            try {
                LocalDate.parse(v);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("El formato de la fecha debe ser YYYY-MM-DD.");
            }
        });
        String hora = pedirCampo("Hora (HH:MM): ", v -> {
            try {
                LocalTime.parse(v);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("El formato de la hora debe ser HH:MM.");
            }
        });
        String estado = pedirCampo("Estado (reservado/cancelado): ", v -> {
            if (!v.equalsIgnoreCase("reservado") && !v.equalsIgnoreCase("cancelado"))
                throw new IllegalArgumentException("El estado debe ser: reservado o cancelado.");
        });
        agendaController.addAgenda(new Agenda(claseId, clienteId, fecha, hora, estado));
        System.out.println("Evento añadido correctamente.");

    }

    private void modificarAgenda() {

        System.out.print("ID a modificar: ");
        long id = sc.nextLong();
        sc.nextLine();
        long claseId = pedirLong("Nuevo ID de clase: ", v -> {
            if (v <= 0) throw new IllegalArgumentException("El ID de clase debe ser mayor que 0.");
            if (claseController.findById(v) == null)
                throw new IllegalArgumentException("No existe ninguna clase con ese ID.");
        });
        long clienteRaw = pedirLong("Nuevo ID de cliente (0 = sin cliente): ", v -> {
            if (v < 0) throw new IllegalArgumentException("El ID de cliente no puede ser negativo.");
        });
        Long clienteId = clienteRaw == 0 ? null : clienteRaw;
        sc.nextLine();
        String fecha = pedirCampo("Nueva fecha (YYYY-MM-DD): ", v -> {
            try {
                LocalDate.parse(v);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("El formato de la fecha debe ser YYYY-MM-DD.");
            }
        });
        String hora = pedirCampo("Nueva hora (HH:MM): ", v -> {
            try {
                LocalTime.parse(v);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("El formato de la hora debe ser HH:MM.");
            }
        });
        String estado = pedirCampo("Nuevo estado (reservado/cancelado): ", v -> {
            if (!v.equalsIgnoreCase("reservado") && !v.equalsIgnoreCase("cancelado"))
                throw new IllegalArgumentException("El estado debe ser: reservado o cancelado.");
        });
        System.out.println(agendaController.modifyAgenda(id, new Agenda(claseId, clienteId, fecha, hora, estado)) ? "Evento modificado." : "No encontrado.");

    }

    private void eliminarAgenda() {

        System.out.print("ID a eliminar: ");
        System.out.println(agendaController.deleteAgenda(sc.nextLong()) ? "Evento eliminado." : "No encontrado.");

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



