package com.udistrital.adatos;

/**
 * Created by erika on 6/02/18.
 */

public  class Test {

    private String enunciado;
    private Video idVideo;
    private Respuesta respuesta;
    private Unidad unidad;


    public Test(String enunciado, Video idVideo, Respuesta respuesta, Unidad unidad) {
        this.enunciado = enunciado;
        this.idVideo = idVideo;
        this.respuesta = respuesta;
        this.unidad = unidad;
    }

    public Test(){

    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Video getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(Video idVideo) {
        this.idVideo = idVideo;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
}
