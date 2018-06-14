package com.udistrital.adatos;

import java.util.ArrayList;

/**
 * Created by erika on 6/02/18.
 */

public class Unidad {

    private String nombreUnidad;
    private String idUnidad;
    private ArrayList<Leccion> arrayLeccion;


    public Unidad(String nombreUnidad, String idUnidad, ArrayList<Leccion> arrayLeccion) {
        this.nombreUnidad = nombreUnidad;
        this.idUnidad = idUnidad;
        this.arrayLeccion = arrayLeccion;
    }

    public Unidad(String nombreUnidad, String idUnidad) {
        this.nombreUnidad = nombreUnidad;
        this.idUnidad = idUnidad;
    }

    public Unidad() {

    }


    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(String idUnidad) {
        this.idUnidad = idUnidad;
    }

    public ArrayList<Leccion> getArrayLeccion() {
        return arrayLeccion;
    }

    public void setArrayLeccion(ArrayList<Leccion> arrayLeccion) {
        this.arrayLeccion = arrayLeccion;
    }
}
