package org.gymtonic.menus;

import org.gymtonic.controllers.ClaseController;

import java.util.Scanner;


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
}
