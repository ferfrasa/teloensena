package com.udistrital.presentacion.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.udistrital.R;
import com.udistrital.Utils.ConfiguracionYoutube;
import com.udistrital.Utils.UtilsBottomNavigation;
import com.udistrital.unity.UnityPlayerActivity;

import java.io.Serializable;


/**
 * Created by erika on 25/02/18.
 */

public class DetalleLeccionesFragment extends Fragment implements Serializable {

    Button botonAumentada;

    public DetalleLeccionesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.fragment_detalle, container, false);




       botonAumentada= (Button) rootView.findViewById(R.id.realidadA);

        botonAumentada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               UtilsBottomNavigation.intentInicio(getContext(), UnityPlayerActivity.class);

            }
       });

     return rootView;


    }



    public void mostrarDetalle(String textoTitulo, int recursoContenido, int imagen1, int imagen2, int imagen3) {
        TextView txtDetalle =  (TextView)getView().findViewById(R.id.detalle);
        TextView txtDetalle22 =  (TextView)getView().findViewById(R.id.detalle22);
        ImageView image1= (ImageView)getView().findViewById(R.id.image1);
        ImageView image2= (ImageView)getView().findViewById(R.id.image2);
        ImageView image3= (ImageView)getView().findViewById(R.id.image3);


        txtDetalle.setText(textoTitulo);
        txtDetalle22.setText(getResources().getString(recursoContenido));
        image1.setImageResource(imagen1);
        image2.setImageResource(imagen2);
        image3.setImageResource(imagen3);
    }


}
