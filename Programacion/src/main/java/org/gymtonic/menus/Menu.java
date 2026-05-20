package org.gymtonic.menus;

import java.util.Scanner;

public class Menu {
    public void menu(){

        Scanner sc = new Scanner(System.in);

        ClienteMenu clienteMenu = new ClienteMenu(sc);
        ClaseMenu claseMenu = new ClaseMenu(sc);
        AgendaMenu agendaMenu = new AgendaMenu(sc);
        MembresiaMenu membresiaMenu = new MembresiaMenu(sc);
        ConfiguracionGymMenu configuracionMenu = new ConfiguracionGymMenu(sc);
        UsuarioMenu usuarioMenu = new UsuarioMenu(sc);

        int opcion;

        do {
            System.out.println("\n========== GymCRM ==========");
            System.out.println("1. Clientes");
            System.out.println("2. Clases");
            System.out.println("3. Agenda");
            System.out.println("4. Membresías");
            System.out.println("5. Configuración Gym");
            System.out.println("6. Usuarios");
            System.out.println("7. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    clienteMenu.mostrar();
                    break;
                case 2:
                    claseMenu.mostrar();
                    break;
                case 3:
                    agendaMenu.mostrar();
                    break;
                case 4:
                    membresiaMenu.mostrar();
                    break;
                case 5:
                    configuracionMenu.mostrar();
                    break;
                case 6:
                    usuarioMenu.mostrar();
                    break;
                case 7:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 7);

        sc.close();

    }
}
