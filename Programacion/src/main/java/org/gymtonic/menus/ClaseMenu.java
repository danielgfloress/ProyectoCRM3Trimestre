package org.gymtonic.menus;

import org.gymtonic.controllers.ClaseController;
import org.gymtonic.models.Clase;

import java.util.Scanner;
import java.util.function.Consumer;


public class ClaseMenu {

    private final Scanner sc;
    private final ClaseController claseController = new ClaseController();

    public ClaseMenu(Scanner sc) {
        this.sc = sc;
    }

    public void mostrar() {

        int opcion;

        do {
            System.out.println("\n--- CLASES ---");
            System.out.println("1. Listar todas");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Añadir clase");
            System.out.println("4. Modificar clase");
            System.out.println("5. Eliminar clase");
            System.out.println("6. Volver");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
            }

        } while (opcion != 6);

    }

    private void añadirClase() {

        sc.nextLine();
        String nombre = pedirCampo("Nombre: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
        });
        String instructor = pedirCampo("Instructor: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El instructor es obligatorio.");
        });
        String horario = pedirCampo("Horario: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El horario es obligatorio.");
        });
        int capacidad = pedirEntero("Capacidad máxima: ", v -> {
            if (v <= 0) throw new IllegalArgumentException("La capacidad debe ser mayor que 0.");
            if (v > 500) throw new IllegalArgumentException("La capacidad no puede superar 500.");
        });
        String nivel = pedirCampo("Nivel (principiante/intermedio/avanzado): ", v -> {
            if (!v.equalsIgnoreCase("principiante") && !v.equalsIgnoreCase("intermedio") && !v.equalsIgnoreCase("avanzado"))
                throw new IllegalArgumentException("El nivel debe ser: principiante, intermedio o avanzado.");
        });
        claseController.addClase(new Clase(nombre, instructor, horario, capacidad, nivel));
        System.out.println("Clase añadida correctamente.");

    }

    private void modificarClase() {
        System.out.print("ID a modificar: ");
        long id = sc.nextLong();
        sc.nextLine();
        String nombre = pedirCampo("Nuevo nombre: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
        });
        String instructor = pedirCampo("Nuevo instructor: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El instructor es obligatorio.");
        });
        String horario = pedirCampo("Nuevo horario: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El horario es obligatorio.");
        });
        int capacidad = pedirEntero("Nueva capacidad máxima: ", v -> {
            if (v <= 0) throw new IllegalArgumentException("La capacidad debe ser mayor que 0.");
            if (v > 500) throw new IllegalArgumentException("La capacidad no puede superar 500.");
        });
        String nivel = pedirCampo("Nuevo nivel (principiante/intermedio/avanzado): ", v -> {
            if (!v.equalsIgnoreCase("principiante") && !v.equalsIgnoreCase("intermedio") && !v.equalsIgnoreCase("avanzado"))
                throw new IllegalArgumentException("El nivel debe ser: principiante, intermedio o avanzado.");
        });
        System.out.println(claseController.modifyClase(id, new Clase(nombre, instructor, horario, capacidad, nivel)) ? "Clase modificada." : "No encontrada.");
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

    private int pedirEntero(String etiqueta, Consumer<Integer> validacion) {

        while (true) {

            System.out.print(etiqueta);

            try {
                int valor = Integer.parseInt(sc.nextLine());
                validacion.accept(valor);
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un número entero válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }

}
