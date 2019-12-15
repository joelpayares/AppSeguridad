package com.appseguridad;

public class Usuario {
    private String Uid;
    private String nombre;
    private String celular;
    private String email;
    private String contraseña;

    public Usuario(String string, String cString, String anInt, String s) {}

    public Usuario(String id, String nombre, String celular, String correo, String contraseña) {
        this.Uid = id;
        this.nombre = nombre;
        this.celular = celular;
        this.email = correo;
        this.contraseña = contraseña;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}