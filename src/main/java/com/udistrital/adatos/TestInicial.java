package com.udistrital.adatos;

/**
 * Created by erika on 6/02/18.
 */

public class TestInicial extends Test {

    private int numeroPreguntas;


    public TestInicial(String enunciado, Video idVideo, Respuesta respuesta, Unidad unidad, int numeroPreguntas) {
        super(enunciado, idVideo, respuesta, unidad);
        this.numeroPreguntas=0;
    }

    public TestInicial() {
        super();
        this.numeroPreguntas=0;
    }

    public int getNumeroPreguntas() {
        return numeroPreguntas;
    }

    public void setNumeroPreguntas(int numeroPreguntas) {
        this.numeroPreguntas = numeroPreguntas;
    }
}
