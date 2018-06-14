package com.udistrital.presentacion.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.udistrital.R;
import com.udistrital.adatos.Leccion;
import com.udistrital.negocio.LeccionAdapter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by erika on 25/02/18.
 */

public class LeccionesFragment extends Fragment implements Serializable {

    private LeccionListener listener;

    public static LeccionesFragment newInstance(ArrayList<Leccion> arguments){
        LeccionesFragment f = new LeccionesFragment();
        Bundle bundle= new Bundle();
        bundle.putSerializable("Array", arguments);
        f.setArguments(bundle);
        return f;
    }

    public LeccionesFragment() {


    }


    public void setLeccionListener(LeccionListener listener) {
        this.listener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_words, container, false);
       final ArrayList<Leccion> leccion= (ArrayList<Leccion>) getArguments().getSerializable("Array");;



       /* final ArrayList<Leccion> leccion = new ArrayList<>();
        leccion.add(new Leccion("Leccion 1","Lengua de señas",R.drawable.mano1,
                R.string.contenido_leccion1_uniad1,R.drawable.ls,
                R.drawable.family_daughter,R.drawable.family_daughter));
        leccion.add(new Leccion("Leccion 2","Lengua de señas colombiano",R.drawable.mano2,
                R.string.contenido_leccion2_uniad1,
                R.drawable.lsc,R.drawable.family_daughter,R.drawable.family_daughter));
        leccion.add(new Leccion("Leccion 3","Persona Sorda",R.drawable.mano3,
                R.string.contenido_leccion3_uniad1,R.drawable.oido,R.drawable.mujers,
                R.drawable.hombres));
        leccion.add(new Leccion("Leccion 4","Seña Personal",R.drawable.mano4,
                R.string.contenido_leccion4_uniad1,R.drawable.personal,R.drawable.personal1,
                R.drawable.personal2));
        leccion.add(new Leccion("Leccion 5","Otras definiciones",R.drawable.mano5,
                R.string.contenido_leccion5_uniad1,R.drawable.otra,R.drawable.otra1,R.drawable.otra2));*/


        LeccionAdapter leccionAdapter= new LeccionAdapter(getActivity(),leccion,R.color.unidad1);

        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(leccionAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (listener!=null) {
                    listener.onLeccionSeleccionado(
                            (Leccion) listView.getAdapter().getItem(i));
                }
            }

        });

        return rootView;

    }

    @Override
    public void onStop() {
        super.onStop();
       // releaseMediaPlayer();
    }

    public interface LeccionListener {
        void onLeccionSeleccionado(Leccion l);
    }




}
