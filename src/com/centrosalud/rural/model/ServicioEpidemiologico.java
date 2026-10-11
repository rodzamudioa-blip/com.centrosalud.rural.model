/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.centrosalud.rural.model;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioEpidemiologico {
        
    // Función HOF 1: Filtrado inmutable de pacientes por diagnóstico usando Streams
    public List<Paciente> filtrarPacientesPorDiagnostico(List<Paciente> pacientes, String codigoCIE10) {
        return pacientes.stream()
                .filter(p -> p.getDiagnósticosCIE10().contains(codigoCIE10))
                .collect(Collectors.toList());
    }

    // Funktion HOF 2: Mapeo y cálculo de promedio de edad sin efectos secundarios
    public double calcularPromedioEdad(List<Paciente> pacientes) {
        return pacientes.stream()
                .mapToInt(Paciente::getEdad)
                .average()
                .orElse(0.0);
    }
}
