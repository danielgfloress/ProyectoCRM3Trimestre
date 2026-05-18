package org.gymtonic;

import org.gymtonic.models.Cliente;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Export {

    private static final String DIRECTORIO = "exports/";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public String exportarClientesTxt(List<Cliente> clientes) throws IOException {

        crearDirectorioSiNoExiste();

        String nombreArchivo = DIRECTORIO + "clientes_" + LocalDateTime.now().format(FORMATO_FECHA) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {

            writer.write("============================================================");
            writer.newLine();
            writer.write("           INFORME DE CLIENTES - GymTonic              ");
            writer.newLine();
            writer.write("Generado el: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.newLine();
            writer.write("Total de clientes: " + clientes.size());
            writer.newLine();
            writer.write("============================================================");
            writer.newLine();
            writer.newLine();

            if (clientes.isEmpty()) {

                writer.write("No hay clientes registrados.");
                writer.newLine();

            } else {
                for (Cliente cliente : clientes) {

                    writer.write("ID:        " + cliente.getId());
                    writer.newLine();
                    writer.write("Nombre:    " + cliente.getNombre());
                    writer.newLine();
                    writer.write("Email:     " + cliente.getEmail());
                    writer.newLine();
                    writer.write("Teléfono:  " + cliente.getTelefono());
                    writer.newLine();
                    writer.write("Dirección: " + cliente.getDireccion());
                    writer.newLine();
                    writer.write("Alta:      " + cliente.getFechaAlta());
                    writer.newLine();
                    writer.write("------------------------------------------------------------");
                    writer.newLine();

                }
            }

            writer.newLine();
            writer.write("INFORME FINALIZADO");
            writer.newLine();

        }

        return nombreArchivo;}

    public String exportarClientesCsv(List<Cliente> clientes) throws IOException {

        crearDirectorioSiNoExiste();

        String nombreArchivo = DIRECTORIO + "clientes_" + LocalDateTime.now().format(FORMATO_FECHA) + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {

            writer.write("ID,Nombre,Email,Telefono,Direccion,FechaAlta");
            writer.newLine();

            for (Cliente cliente : clientes) {

                writer.write(
                        cliente.getId() + "," +
                                escaparCsv(cliente.getNombre()) + "," +
                                escaparCsv(cliente.getEmail()) + "," +
                                escaparCsv(cliente.getTelefono()) + "," +
                                escaparCsv(cliente.getDireccion()) + "," +
                                escaparCsv(cliente.getFechaAlta())
                );

                writer.newLine();

            }
        }

        return nombreArchivo;}

    private String escaparCsv(String valor) {

        if (valor == null) return "";

        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {

            return "\"" + valor.replace("\"", "\"\"") + "\"";

        }

        return valor;
    }

    private void crearDirectorioSiNoExiste() throws IOException {

        java.io.File directorio = new java.io.File(DIRECTORIO);

        if (!directorio.exists() && !directorio.mkdirs()) {

            throw new IOException("No se pudo crear el directorio de exportaciones: " + DIRECTORIO);

        }
    }
}