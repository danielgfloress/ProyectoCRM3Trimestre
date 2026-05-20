package org.gymtonic.menus;

import org.gymtonic.controllers.MembresiaController;
import org.gymtonic.models.Membresia;

import java.util.Scanner;
import java.util.function.Consumer;

public class MembresiaMenu {

    private final Scanner sc;
    private final MembresiaController membresiaController = new MembresiaController();

    public MembresiaMenu(Scanner sc) {
        this.sc = sc;
    }

    public void mostrar() {

        int opcion;

        do {
            System.out.println("\n--- MEMBRESÍAS ---");
            System.out.println("1. Listar todas");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Añadir membresía");
            System.out.println("4. Modificar membresía");
            System.out.println("5. Eliminar membresía");
            System.out.println("6. Volver");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    membresiaController.findAll().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("ID: ");
                    System.out.println(membresiaController.findById(sc.nextLong()));
                    break;
                case 3:
                    añadirMembresia();
                    break;
                case 4:
                    membresiaController.findAll().forEach(System.out::println);
                    modificarMembresia();
                    break;
                case 5:
                    membresiaController.findAll().forEach(System.out::println);
                    eliminarMembresia();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 6);

    }

    private void añadirMembresia() {
        sc.nextLine();
        String nombre = pedirCampo("Nombre: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
            if (v.length() > 100) throw new IllegalArgumentException("El nombre no puede superar 100 caracteres.");
        });
        double precio = pedirDecimal("Precio: ", v -> {
            if (v <= 0) throw new IllegalArgumentException("El precio debe ser mayor que 0.");
            if (v > 99999) throw new IllegalArgumentException("El precio no puede superar 99.999.");
        });
        String duracion = pedirCampo("Duración (días): ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("La duración es obligatoria.");
        });
        membresiaController.addMembresia(new Membresia(nombre, precio, duracion));
        System.out.println("Membresía añadida correctamente.");
    }

    private void modificarMembresia() {
        System.out.print("ID a modificar: ");
        long id = sc.nextLong();
        sc.nextLine();
        String nombre = pedirCampo("Nuevo nombre: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
            if (v.length() > 100) throw new IllegalArgumentException("El nombre no puede superar 100 caracteres.");
        });
        double precio = pedirDecimal("Nuevo precio: ", v -> {
            if (v <= 0) throw new IllegalArgumentException("El precio debe ser mayor que 0.");
            if (v > 99999) throw new IllegalArgumentException("El precio no puede superar 99.999.");
        });
        String duracion = pedirCampo("Nueva duración: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("La duración es obligatoria.");
        });
        System.out.println(membresiaController.modifyMembresia(id, new Membresia(nombre, precio, duracion)) ? "Membresía modificada." : "No encontrada.");
    }

    private void eliminarMembresia() {
        System.out.print("ID a eliminar: ");
        System.out.println(membresiaController.deleteMembresia(sc.nextLong()) ? "Membresía eliminada." : "No encontrada.");
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

    private double pedirDecimal(String etiqueta, Consumer<Double> validacion) {
        while (true) {
            System.out.print(etiqueta);
            try {
                double valor = Double.parseDouble(sc.nextLine());
                validacion.accept(valor);
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: Introduce un número decimal válido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
