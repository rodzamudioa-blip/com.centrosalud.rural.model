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


}
