package com.udistrital.adatos;

import android.net.Uri;

/**
 * Created by erika on 5/02/18.
 */

public class Usuario {

    private String nombreUsuario;
    private String correoUsuario;
    private String idUsuario;


    public Usuario(){
        this.nombreUsuario="Nombre Usuario";
        this.correoUsuario="";
        this.idUsuario="";
    }

    public Usuario(String nombreUsuario, String correoUsuario, String idUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
