package org.gymtonic.menus;

import org.gymtonic.controllers.AgendaController;

import java.util.Scanner;


public class AgendaMenu {

    private final Scanner sc;
    private final AgendaController agendaController = new AgendaController();

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

            }

        }while (opcion != 6) ;

    }

}

