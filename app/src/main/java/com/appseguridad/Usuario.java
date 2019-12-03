package com.appseguridad;

public class Usuario {
    private String Uid;
    private String email;

    public Usuario() {}

    public Usuario(String id, String correo) {
        this.Uid = id;
        this.email = correo;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}