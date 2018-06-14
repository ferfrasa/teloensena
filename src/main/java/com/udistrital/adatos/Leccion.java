package com.udistrital.adatos;

import java.io.Serializable;

/**
 * Created by erika on 6/02/18.
 */

public class Leccion implements Serializable {

    private String idLeccion;
    private String nombreLeccion;
    private int recursoImage;
    private int recursoContendio;
    private int recursoImagen1;
    private int recursoImagen2;
    private int recursoImagen3;
    private int recursoVideo;


    public String getIdLeccion() {
        return idLeccion;
    }

    public void setIdLeccion(String idLeccion) {
        this.idLeccion = idLeccion;
    }

    public String getNombreLeccion() {
        return nombreLeccion;
    }

    public void setNombreLeccion(String nombreLeccion) {
        this.nombreLeccion = nombreLeccion;
    }

    public int getRecursoImage() {
        return recursoImage;
    }

    public void setRecursoImage(int recursoImage) {
        this.recursoImage = recursoImage;
    }

    public Leccion(String idLeccion, String nombreLeccion, int recursoImage, int recursoContendio) {
        this.idLeccion = idLeccion;
        this.nombreLeccion = nombreLeccion;
        this.recursoImage=recursoImage;
        this.recursoContendio=recursoContendio;
    }

    public Leccion(String idLeccion, String nombreLeccion, int recursoImage, int recursoContendio,
                   int recursoImagen1,int recursoVideo) {
        this.idLeccion = idLeccion;
        this.nombreLeccion = nombreLeccion;
        this.recursoImage = recursoImage;
        this.recursoContendio = recursoContendio;
        this.recursoImagen1 = recursoImagen1;
        this.recursoVideo=recursoVideo;
    }

    public Leccion(String idLeccion, String nombreLeccion, int recursoImage, int recursoContendio,
                   int recursoImagen1, int recursoImagen2,int recursoVideo) {
        this.idLeccion = idLeccion;
        this.nombreLeccion = nombreLeccion;
        this.recursoImage = recursoImage;
        this.recursoContendio = recursoContendio;
        this.recursoImagen1 = recursoImagen1;
        this.recursoImagen2 = recursoImagen2;
        this.recursoVideo=recursoVideo;
    }





    public Leccion(String idLeccion, String nombreLeccion, int recursoImage, int recursoContendio,
                   int recursoImagen1, int recursoImagen2, int recursoImagen3,int recursoVideo) {
        this.idLeccion = idLeccion;
        this.nombreLeccion = nombreLeccion;
        this.recursoImage = recursoImage;
        this.recursoContendio = recursoContendio;
        this.recursoImagen1 = recursoImagen1;
        this.recursoImagen2 = recursoImagen2;
        this.recursoImagen3 = recursoImagen3;
    }

    public int getRecursoContendio() {
        return recursoContendio;
    }

    public void setContendio(int contendio) {
        this.recursoContendio = contendio;
    }

    public void setRecursoContendio(int recursoContendio) {
        this.recursoContendio = recursoContendio;
    }

    public int getRecursoImagen1() {
        return recursoImagen1;
    }

    public void setRecursoImagen1(int recursoImagen1) {
        this.recursoImagen1 = recursoImagen1;
    }

    public int getRecursoImagen2() {
        return recursoImagen2;
    }

    public void setRecursoImagen2(int recursoImagen2) {
        this.recursoImagen2 = recursoImagen2;
    }

    public int getRecursoImagen3() {
        return recursoImagen3;
    }

    public void setRecursoImagen3(int recursoImagen3) {
        this.recursoImagen3 = recursoImagen3;
    }

    public int getRecursoVideo() {
        return recursoVideo;
    }

    public void setRecursoVideo(int recursoVideo) {
        this.recursoVideo = recursoVideo;
    }
}
