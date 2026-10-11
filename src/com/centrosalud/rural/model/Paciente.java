/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.centrosalud.rural.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Paciente {
    private String dniHash; // Anonimizado vía SHA-256
    private String nombre;
    private String apellido;
    private int edad;
    private List<String> diagnósticosCIE10;

    public Paciente(String dniPlano, String nombre, String apellido, int edad) 
            throws ExcepcionValidacionDatos {
        if (!dniPlano.matches("\\d{8}")) {
            throw new ExcepcionValidacionDatos("El DNI debe contener exactamente 8 dígitos numéricos.");
        }
        this.dniHash = Usuario.generarHashSHA256(dniPlano);
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.diagnósticosCIE10 = new ArrayList<>();
    }

    public void agregarDiagnostico(String codigoCIE10) {
        this.diagnósticosCIE10.add(codigoCIE10);
    }

    // Anonimización dinámica para logs conforme Ley N.° 29733
    public String getNombreAnonimizado() {
        String nAnon = (nombre != null && !nombre.isEmpty()) ? nombre.charAt(0) + "***" : "***";
        String aAnon = (apellido != null && !apellido.isEmpty()) ? apellido.charAt(0) + "***" : "***";
        return nAnon + " " + aAnon;
    }

    public String getDniHash() { return dniHash; }
    public int getEdad() { return edad; }
    public List<String> getDiagnósticosCIE10() { return Collections.unmodifiableList(diagnósticosCIE10); }
}
