package com.udistrital.negocio;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udistrital.R;
import com.udistrital.adatos.Video;

import java.util.ArrayList;

/**
 * Created by erika on 1/04/18.
 */

public class VideoAdaptador extends ArrayAdapter<Video> {


    public VideoAdaptador(Activity context, ArrayList<Video> libroArrayAdapter) {
        super(context,0,libroArrayAdapter);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemViewLibro = convertView;

        if(listItemViewLibro == null) {
            listItemViewLibro = LayoutInflater.from(getContext()).inflate(
                    R.layout.lista_videos, parent, false);
        }

        Video objetoLibro = getItem(position);

        TextView nombre = (TextView) listItemViewLibro.findViewById(R.id.nombre_libro) ;


        nombre.setText(objetoLibro.getNombreVideo());
       // autor.setText(objetoLibro.getAutor());


        return listItemViewLibro;
    }
}
