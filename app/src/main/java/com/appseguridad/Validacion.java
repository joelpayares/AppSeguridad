package com.appseguridad;

class Validacion {

    boolean regla_vacio(String cadena){//validar numeros
        return cadena.isEmpty();
    }

    boolean regla_lleno(String cadena){
        return !cadena.isEmpty();
    }

    boolean regla_nument(String cadena){
        //validar numeros
        String patron = "^[0-9]+$";
        return cadena.matches(patron);
    }

    boolean regla_letcesp(String cadena){
        //validar letras con un espacio entre ellas
        String patron = "^[A-zñáéíóúÁÉÍÓÚÑ]*(\\s[A-zñáéíóúÁÉÍÓÚÑ]+)*$";
        return cadena.matches(patron);
    }

    boolean regla_letsesp(String cadena){
        //validar letras con un espacio entre ellas
        String patron = "[A-Za-z]";
        return patron.matches(cadena);
    }

    boolean regla_numtel(String cadena){
        //validar telefono
        String patron = "^(\\+?((\\d+)|([(]\\d+[)]){1,2})([- ]?\\d+)+)?$";
        return cadena.matches(patron);
    }

    boolean regla_fec(String cadena){
        //validar fecha ^(\d{4}\-\d{2}\-\d{2})
        String patron = "^(\\d{4}\\-\\d{2}\\-\\d{2}){0,1}";
        return cadena.matches(patron);
    }

    boolean regla_corele(String cadena){
        //validar correo
        String patron = "^[0-9A-z]+(([A-z]+)|([0-9A-z]+[.\\-_]?)+)[@][A-z]+[.]?[A-z]+[.][A-z]+$";
        return cadena.matches(patron);
    }
}