/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.centrosalud.rural.model;
public class Medico extends Usuario {
    private String colegiaturaCMP;

    public Medico(String idUsuario, String nombreCompleto, String password, String colegiaturaCMP) 
            throws ExcepcionValidacionDatos {
        super(idUsuario, nombreCompleto, password);
        if (!colegiaturaCMP.matches("\\d{5,6}")) {
            throw new ExcepcionValidacionDatos("N° de Colegiatura CMP inválido: Debe tener 5 o 6 dígitos.");
        }
        this.colegiaturaCMP = colegiaturaCMP;
    }

    @Override
    public String getRol() {
        return "MEDICO_TITULAR";
    }

    public String getColegiaturaCMP() { 
        return colegiaturaCMP; 
    }
}

