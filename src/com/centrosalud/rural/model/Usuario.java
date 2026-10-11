/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.centrosalud.rural.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public abstract class Usuario {
    protected String idUsuario;
    protected String nombreCompleto;
    protected String hashPassword;

    public Usuario(String idUsuario, String nombreCompleto, String password) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.hashPassword = generarHashSHA256(password);
    }

    public abstract String getRol();

    protected static String generarHashSHA256(String textoPlano) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(textoPlano.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error crítico: Algoritmo SHA-256 no disponible", e);
        }
    }
}
