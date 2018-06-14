package com.udistrital.negocio;

import com.udistrital.Utils.JSONUtils;
import com.udistrital.adatos.Video;

import java.util.List;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

/**
 * Created by erika on 1/04/18.
 */

public class VideoLoader extends AsyncTaskLoader<List<Video>>  {

    private static final String LOG_TAG = VideoLoader.class.getName();
    private String urlPalabra;


    public VideoLoader(Context context, String urlPalabra) {
        super(context);
        this.urlPalabra=urlPalabra;
    }

    @Override
    public List<Video> loadInBackground()   {
        Log.i(LOG_TAG,"Test metodo loadInBackground()");


        if(urlPalabra == null){
            return null;
        }


        List<Video> listadoLibro= JSONUtils.retornoLibro(urlPalabra);
        return  listadoLibro;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }




}
