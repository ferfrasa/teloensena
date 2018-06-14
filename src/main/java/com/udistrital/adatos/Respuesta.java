package com.udistrital.adatos;

/**
 * Created by erika on 12/02/18.
 */

public class Respuesta {

    private String nombreRespuesta;
    private int idRespuesta;
    private boolean correcta;

    public Respuesta(String nombreRespuesta, int idRespuesta) {
        this.nombreRespuesta = nombreRespuesta;
        this.idRespuesta = idRespuesta;
        this.correcta=false;
    }

    public Respuesta() {
        this.nombreRespuesta = "";
        this.idRespuesta = 0;
        this.correcta=false;
    }


    public String getNombreRespuesta() {
        return nombreRespuesta;
    }

    public void setNombreRespuesta(String nombreRespuesta) {
        this.nombreRespuesta = nombreRespuesta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }
}
