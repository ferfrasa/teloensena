package com.udistrital.negocio;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udistrital.R;
import com.udistrital.adatos.Leccion;


import java.util.ArrayList;

/**
 * Created by erika on 25/02/18.
 */

public class LeccionAdapter extends ArrayAdapter<Leccion> {

   private int leccionID;

    public LeccionAdapter(Activity context, ArrayList<Leccion> androidFlavors, int leccionId) {

        super(context, 0, androidFlavors);
        this.leccionID=leccionId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_lecciones, parent, false);
        }

      //obtener la posicon de la lista de leccions
        Leccion lecccionAndroidFlavor = getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.id_leccion);

        nameTextView.setText(lecccionAndroidFlavor.getIdLeccion());


        TextView numberTextView = (TextView) listItemView.findViewById(R.id.nombre_leccion);

        numberTextView.setText(lecccionAndroidFlavor.getNombreLeccion());


        ImageView iconView = (ImageView) listItemView.findViewById(R.id.images_leccion);

        iconView.setImageResource(lecccionAndroidFlavor.getRecursoImage());
        if(lecccionAndroidFlavor.getRecursoImage()==0){
            iconView.setVisibility(View.GONE);
        }

        View contenido = listItemView.findViewById(R.id.text_container_leccion);
        contenido.setBackgroundColor(ContextCompat.getColor(getContext(),leccionID));




        return listItemView;
    }






}
