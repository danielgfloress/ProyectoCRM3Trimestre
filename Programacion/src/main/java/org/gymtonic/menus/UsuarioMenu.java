package org.gymtonic.menus;

import org.gymtonic.controllers.UsuarioController;
import org.gymtonic.models.Usuario;

import java.util.Scanner;
import java.util.function.Consumer;

public class UsuarioMenu {

    private final Scanner sc;
    private final UsuarioController usuarioController = new UsuarioController();

    public UsuarioMenu(Scanner sc) {
        this.sc = sc;
    }

    public void mostrar() {

        int opcion;

        do {
            System.out.println("\n--- USUARIOS ---");
            System.out.println("1. Listar todos");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Añadir usuario");
            System.out.println("4. Modificar usuario");
            System.out.println("5. Eliminar usuario");
            System.out.println("6. Volver");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    usuarioController.findAll().forEach(System.out::println);
                    break;
                case 2:
                    System.out.print("ID: ");
                    System.out.println(usuarioController.findById(sc.nextLong()));
                    break;
                case 3:
                    añadirUsuario();
                    break;
                case 4:
                    usuarioController.findAll().forEach(System.out::println);
                    modificarUsuario();
                    break;
                case 5:
                    usuarioController.findAll().forEach(System.out::println);
                    eliminarUsuario();
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 6);

    }

    private void añadirUsuario() {
        sc.nextLine();
        String nombre = pedirCampo("Nombre: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
            if (v.length() > 100) throw new IllegalArgumentException("El nombre no puede superar 100 caracteres.");
        });
        String email = pedirCampo("Email: ", v -> {
            if (!v.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))
                throw new IllegalArgumentException("El formato del email no es válido.");
        });
        String rol = pedirCampo("Rol (admin / recepcionista / entrenador / cliente): ", v -> {
            if (!java.util.List.of("admin", "recepcionista", "entrenador", "cliente").contains(v))
                throw new IllegalArgumentException("El rol debe ser: admin, recepcionista, entrenador o cliente.");
        });
        String password = pedirCampo("Contraseña: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("La contraseña es obligatoria.");
        });
        usuarioController.addUsuario(new Usuario(nombre, email, rol, password));
        System.out.println("Usuario añadido correctamente.");
    }

    private void modificarUsuario() {
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
        String rol = pedirCampo("Nuevo rol (admin / recepcionista / entrenador / cliente): ", v -> {
            if (!java.util.List.of("admin", "recepcionista", "entrenador", "cliente").contains(v))
                throw new IllegalArgumentException("El rol debe ser: admin, recepcionista, entrenador o cliente.");
        });
        String password = pedirCampo("Nueva contraseña: ", v -> {
            if (v.isBlank()) throw new IllegalArgumentException("La contraseña es obligatoria.");
        });
        System.out.println(usuarioController.modifyUsuario(id, new Usuario(nombre, email, rol, password)) ? "Usuario modificado." : "No encontrado.");
    }

    private void eliminarUsuario() {
        System.out.print("ID a eliminar: ");
        System.out.println(usuarioController.deleteUsuario(sc.nextLong()) ? "Usuario eliminado." : "No encontrado.");
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

