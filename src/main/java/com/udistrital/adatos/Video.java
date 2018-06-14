package com.udistrital.adatos;

import java.io.Serializable;

/**
 * Created by erika on 12/02/18.
 */

public class Video implements Serializable {

    private String nombreVideo;
    private String idVideo;

    public Video(String nombreVideo, String idVideo) {
        this.nombreVideo = nombreVideo;
        this.idVideo = idVideo;
    }

    public Video() {
        this.nombreVideo = "";
        this.idVideo = "";
    }

    public String getNombreVideo() {
        return nombreVideo;
    }

    public void setNombreVideo(String nombreVideo) {
        this.nombreVideo = nombreVideo;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }
}
