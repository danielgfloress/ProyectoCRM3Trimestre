package org.gymtonic.menus;

import org.gymtonic.controllers.ClienteController;
import org.gymtonic.models.Cliente;

import java.util.Scanner;
import java.util.function.Consumer;

public class ClienteMenu {

    private final Scanner sc;
    private final ClienteController clienteController = new ClienteController();

    public ClienteMenu(Scanner sc) {
        this.sc = sc;
    }

    public void mostrar() {

        int opcion;

        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Listar todos");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Añadir cliente");
            System.out.println("4. Modificar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("6. Volver");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    clienteController.findAll().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("ID: ");
                    System.out.println(clienteController.findById(sc.nextLong()));
                    break;
                case 3:
                    clienteController.findAll().forEach(System.out::println);
                    añadirCliente();
                    break;
                case 4:
                    clienteController.findAll().forEach(System.out::println);
                    modificarCliente();
                    break;
                case 5:
                    eliminarCliente();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 6);

    }

    private void añadirCliente() {
        sc.nextLine();
        String nombre = pedirCampo("Nombre: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
            if (v.length() > 100) throw new IllegalArgumentException("El nombre no puede superar 100 caracteres.");
        });
        String email = pedirCampo("Email: ", v -> {
            if (!v.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
                throw new IllegalArgumentException("El formato del email no es válido.");
        });
        String telefono = pedirCampo("Teléfono: ", v -> {
            if (!v.matches("^[0-9+\\s-]{6,20}$"))
                throw new IllegalArgumentException("El formato del teléfono no es válido.");
        });
        String direccion = pedirCampo("Dirección: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("La dirección es obligatoria.");
        });
        clienteController.addCliente(new Cliente(nombre, email, telefono, direccion));
        System.out.println("Cliente añadido correctamente.");try {
            clienteController.addCliente(new Cliente(nombre, email, telefono, direccion));
            System.out.println("Cliente añadido correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al añadir cliente: " + e.getMessage());
        }
    }

    private void modificarCliente() {
        System.out.print("ID a modificar: ");
        long id = sc.nextLong();
        sc.nextLine();
        String nombre = pedirCampo("Nuevo nombre: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
            if (v.length() > 100) throw new IllegalArgumentException("El nombre no puede superar 100 caracteres.");
        });
        String email = pedirCampo("Nuevo email: ", v -> {
            if (!v.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
                throw new IllegalArgumentException("El formato del email no es válido.");
        });
        String telefono = pedirCampo("Nuevo teléfono: ", v -> {
            if (!v.matches("^[0-9+\\s-]{6,20}$"))
                throw new IllegalArgumentException("El formato del teléfono no es válido.");
        });
        String direccion = pedirCampo("Nueva dirección: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("La dirección es obligatoria.");
        });
        System.out.println(clienteController.modifyCliente(id, new Cliente(nombre, email, telefono, direccion)) ? "Cliente modificado." : "No encontrado.");
    }

    private void eliminarCliente() {
        System.out.print("ID a eliminar: ");
        System.out.println(clienteController.deleteCliente(sc.nextLong()) ? "Cliente eliminado." : "No encontrado.");
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

}