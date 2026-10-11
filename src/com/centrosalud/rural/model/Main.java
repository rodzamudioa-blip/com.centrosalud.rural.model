package com.centrosalud.rural.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ExcepcionValidacionDatos {
        Scanner sc = new Scanner(System.in);
        mostrarEncabezado();

        Medico medico = registrarMedico(sc);
        mostrarConfirmacionMedico(medico);

        List<Paciente> pacientes = capturarPacientes(sc);
        mostrarReportePacientes(pacientes);

        procesarEpidemiologia(sc, pacientes);
        sc.close();
    }

    private static void mostrarEncabezado() {
        System.out.println("=================================================");
        System.out.println("   SISTEMA DE GESTIÓN RURAL - REGISTRO EN VIVO   ");
        System.out.println("=================================================");
    }

    private static Medico registrarMedico(Scanner sc) throws ExcepcionValidacionDatos {
        System.out.println("\n--- REGISTRO DE MÉDICO ---");
        System.out.print("Ingrese ID del Médico: ");
        String id = sc.nextLine();
        System.out.print("Ingrese Nombre Completo: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese Contraseña: ");
        String pass = sc.nextLine();
        System.out.print("Ingrese N° Colegiatura CMP (5 o 6 dígitos): ");
        String cmp = sc.nextLine();
        return new Medico(id, nombre, pass, cmp);
    }

    private static void mostrarConfirmacionMedico(Medico m) {
        System.out.println("\n[OK] Médico verificado:");
        System.out.println("     Rol asignado : " + m.getRol());
        System.out.println("     CMP          : " + m.getColegiaturaCMP());
    }

    private static List<Paciente> capturarPacientes(Scanner sc) throws ExcepcionValidacionDatos {
        System.out.println("\n--- REGISTRO DE PACIENTES ---");
        System.out.print("¿Cuántos pacientes desea ingresar?: ");
        int total = Integer.parseInt(sc.nextLine());

        List<Paciente> lista = new ArrayList<>();
        for (int i = 1; i <= total; i++) {
            System.out.println("\n-> Datos del Paciente #" + i);
            System.out.print("Ingrese DNI (8 dígitos): ");
            String dni = sc.nextLine();
            System.out.print("Ingrese Nombre: ");
            String nom = sc.nextLine();
            System.out.print("Ingrese Apellido: ");
            String ape = sc.nextLine();
            System.out.print("Ingrese Edad: ");
            int edad = Integer.parseInt(sc.nextLine());

            Paciente p = new Paciente(dni, nom, ape, edad);
            System.out.print("Ingrese código de diagnóstico CIE-10 (ej. A71.9): ");
            p.agregarDiagnostico(sc.nextLine());
            lista.add(p);
        }
        return lista;
    }

    private static void mostrarReportePacientes(List<Paciente> lista) {
        System.out.println("\n=================================================");
        System.out.println("        REPORTE DE PACIENTES (LEY N.° 29733)      ");
        System.out.println("=================================================");
        for (Paciente p : lista) {
            System.out.println("Nombre Anonimizado : " + p.getNombreAnonimizado());
            System.out.println("Edad               : " + p.getEdad() + " años");
            System.out.println("Hash DNI (SHA-256) : " + p.getDniHash());
            System.out.println("Diagnósticos       : " + p.getDiagnósticosCIE10());
            System.out.println("-------------------------------------------------");
        }
    }

    private static void procesarEpidemiologia(Scanner sc, List<Paciente> lista) {
        System.out.print("\nIngrese el código CIE-10 a consultar (ej. A71.9): ");
        String filtro = sc.nextLine();

        ServicioEpidemiologico servicio = new ServicioEpidemiologico();
        List<Paciente> encontrados = servicio.filtrarPacientesPorDiagnostico(lista, filtro);

        System.out.println("\n--- RESULTADO DE FILTRADO EPIDEMIOLÓGICO (" + filtro + ") ---");
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron pacientes con ese diagnóstico.");
        } else {
            for (Paciente p : encontrados) {
                System.out.println("Paciente: " + p.getNombreAnonimizado() + " | Diagnósticos: " + p.getDiagnósticosCIE10());
            }
        }

        double promedio = servicio.calcularPromedioEdad(lista);
        System.out.println("\n[OK] Promedio de edad de los pacientes ingresados: " + promedio + " años.");
        System.out.println("\n=== FIN DE LA EJECUCIÓN (LOS DATOS FUERON DESCARTADOS) ===");
    }
}