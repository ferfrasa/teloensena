package com.udistrital.adatos;

import android.net.Uri;

/**
 * Created by erika on 5/02/18.
 */

public class Estudiante extends Usuario {

    private int nivel;
    private boolean estado;
    private boolean unidadB1;
    private boolean unidadB2;
    private boolean unidadB3;
    private boolean unidadB4;
    private boolean unidadB5;
    private boolean testInicial;
    private boolean testFinalP;
    private boolean testFinal;


    public Estudiante(String nombreUsuario, String correoUsuario, String idUsuario,
                      int nivel, boolean estado) {
        super(nombreUsuario, correoUsuario, idUsuario);
        this.nivel= nivel;
        this.estado=estado;
        this.testInicial=false;
        this.unidadB1=false;
        this.unidadB2=false;
        this.unidadB3=false;
        this.unidadB4=false;
        this.unidadB5=false;
        this.testFinal=false;
        this.testFinalP=false;
    }




    public Estudiante() {
        super();
        this.estado=true;
        this.nivel=0;
        this.unidadB1=false;
        this.unidadB2=false;
        this.unidadB3=false;
        this.unidadB4=false;
        this.unidadB5=false;
        this.testFinal=false;
        this.testInicial=false;
        this.testFinalP=false;

    }

    public boolean isUnidadB1() {
        return unidadB1;
    }

    public void setUnidadB1(boolean unidadB1) {
        this.unidadB1 = unidadB1;
    }

    public boolean isUnidadB2() {
        return unidadB2;
    }

    public void setUnidadB2(boolean unidadB2) {
        this.unidadB2 = unidadB2;
    }

    public boolean isUnidadB3() {
        return unidadB3;
    }

    public void setUnidadB3(boolean unidadB3) {
        this.unidadB3 = unidadB3;
    }

    public boolean isUnidadB4() {
        return unidadB4;
    }

    public void setUnidadB4(boolean unidadB4) {
        this.unidadB4 = unidadB4;
    }

    public boolean isUnidadB5() {
        return unidadB5;
    }

    public void setUnidadB5(boolean unidadB5) {
        this.unidadB5 = unidadB5;
    }

    public boolean isTestFinal() {
        return testFinal;
    }

    public void setTestFinal(boolean testFinal) {
        this.testFinal = testFinal;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }



    public boolean isTestInicial() {
        return testInicial;
    }

    public void setTestInicial(boolean testInicial) {
        this.testInicial = testInicial;
    }


    public boolean isTestFinalP() {
        return testFinalP;
    }

    public void setTestFinalP(boolean testFinalP) {
        this.testFinalP = testFinalP;
    }
}
