package com.centrosalud.rural.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExcepcionValidacionDatos {
        Scanner scanner = new Scanner(System.in);
        List<Paciente> listaPacientes = new ArrayList<>();

        System.out.println("=================================================");
        System.out.println("   SISTEMA DE GESTIÓN RURAL - REGISTRO EN VIVO   ");
        System.out.println("=================================================");

        // 1. Ingreso de datos del Médico
        System.out.println("\n--- REGISTRO DE MÉDICO ---");
        System.out.print("Ingrese ID del Médico: ");
        String idMedico = scanner.nextLine();

        System.out.print("Ingrese Nombre Completo: ");
        String nombreMedico = scanner.nextLine();

        System.out.print("Ingrese Contraseña: ");
        String passwordMedico = scanner.nextLine();

        System.out.print("Ingrese N° Colegiatura CMP (5 o 6 dígitos): ");
        String cmp = scanner.nextLine();

        Medico medico = new Medico(idMedico, nombreMedico, passwordMedico, cmp);
        System.out.println("\n[OK] Médico verificado:");
        System.out.println("     Rol asignado : " + medico.getRol());
        System.out.println("     CMP          : " + medico.getColegiaturaCMP());

        // 2. Ingreso dinámico de Pacientes
        System.out.println("\n--- REGISTRO DE PACIENTES ---");
        System.out.print("¿Cuántos pacientes desea ingresar?: ");
        int cantidad = Integer.parseInt(scanner.nextLine());

        for (int i = 1; i <= cantidad; i++) {
            System.out.println("\n-> Datos del Paciente #" + i);
            
            System.out.print("Ingrese DNI (8 dígitos): ");
            String dni = scanner.nextLine();

            System.out.print("Ingrese Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Ingrese Apellido: ");
            String apellido = scanner.nextLine();

            System.out.print("Ingrese Edad: ");
            int edad = Integer.parseInt(scanner.nextLine());

            Paciente paciente = new Paciente(dni, nombre, apellido, edad);

            System.out.print("Ingrese código de diagnóstico CIE-10 (ej. A71.9): ");
            String diagnostico = scanner.nextLine();
            paciente.agregarDiagnostico(diagnostico);

            listaPacientes.add(paciente);
        }

        // 3. Visualización en consola de datos procesados (Cifrado y Anonimizado)
        System.out.println("\n=================================================");
        System.out.println("        REPORTE DE PACIENTES (LEY N.° 29733)      ");
        System.out.println("=================================================");
        for (Paciente p : listaPacientes) {
            System.out.println("Nombre Anonimizado : " + p.getNombreAnonimizado());
            System.out.println("Edad               : " + p.getEdad() + " años");
            System.out.println("Hash DNI (SHA-256) : " + p.getDniHash());
            System.out.println("Diagnósticos       : " + p.getDiagnósticosCIE10());
            System.out.println("-------------------------------------------------");
        }

        // 4. Búsqueda y filtrado epidemiológico
        System.out.print("\nIngrese el código CIE-10 a consultar (ej. A71.9): ");
        String filtroCIE = scanner.nextLine();

        ServicioEpidemiologico servicio = new ServicioEpidemiologico();
        List<Paciente> encontrados = servicio.filtrarPacientesPorDiagnostico(listaPacientes, filtroCIE);

        System.out.println("\n--- RESULTADO DE FILTRADO EPIDEMIOLÓGICO (" + filtroCIE + ") ---");
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron pacientes con ese diagnóstico.");
        } else {
            for (Paciente p : encontrados) {
                System.out.println("Paciente: " + p.getNombreAnonimizado() + " | Diagnósticos: " + p.getDiagnósticosCIE10());
            }
        }

        // 5. Estadísticas calculadas al vuelo
        double promedioEdad = servicio.calcularPromedioEdad(listaPacientes);
        System.out.println("\n[OK] Promedio de edad de los pacientes ingresados: " + promedioEdad + " años.");
        System.out.println("\n=== FIN DE LA EJECUCIÓN (LOS DATOS FUERON DESCARTADOS) ===");

        scanner.close();
    }
}