package com.udistrital.Utils;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.udistrital.presentacion.MenuPrincipalActivity;
import com.udistrital.R;
import com.udistrital.presentacion.usuario.AyudaActivity;
import com.udistrital.presentacion.usuario.DiccionarioActivity;
import com.udistrital.presentacion.usuario.PerfilActivity;

/**
 * Created by erika on 5/02/18.
 */

public final class UtilsBottomNavigation {

    public final static int NUMERO_PREGUNTAS_UNIDAD1 = 3;
    public final static int NUMERO_PREGUNTAS_UNIDAD2 = 10;
    public final static int NUMERO_PREGUNTAS_UNIDAD3 = 10;
    public final static int NUMERO_PREGUNTAS_UNIDAD4 = 10;
    public final static int NUMERO_PREGUNTAS_UNIDAD5 = 7;
    public final static int NUMERO_RESPUESTAS = 4;
    public final static String NODO_USUARIOS = "usuario";
    public final static String NODO_TEST_FINALP = "testFinalP";
    public final static String NODO_VIDEO = "video";
    public final static String NODO_CORREO = "correoUsuario";
    public final static String NODO_ESTADO = "estado";
    public final static String NODO_IDUSUARIO = "idUsuario";
    public final static String NODO_NIVEL = "nivel";
    public final static String NODO_NOMBRE_USUARIO = "nombreUsuario";
    public final static String NODO_ENUNCIADO = "enunciado";
    public final static String NODO_RESPUESTA = "respuesta";
    public final static String NODO_RESPUESTA_TEST = "respuestasTest";
    public final static String NODO_UNIDAD_UNO = "unidadB1";
    public final static String NODO_UNIDAD_UNO2 = "unidad1";
    public final static String NODO_UNIDAD_DOS = "unidadB2";
    public final static String NODO_UNIDAD_DOS2 = "unidad2";
    public final static String NODO_UNIDAD_TRES = "unidadB3";
    public final static String NODO_UNIDAD_TRES2 = "unidad3";
    public final static String NODO_UNIDAD_CUATRO2 = "unidad4";
    public final static String NODO_UNIDAD_CUATRO = "unidadB4";

    public final static String NODO_UNIDAD_CINCO2 = "unidad5";
    public final static String NODO_UNIDAD_CINCO = "unidadB5";
    public final static String NODO_TEST_INICIAL = "testInicial";
    public final static String NODO_TEST_FINAL = "testFinal";
    public final static String NODO_TEST = "preguntasTest";

    static Intent intent;


    public static void intentInicio(Context context, Class clase) {
        intent = new Intent(context, clase);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public static void seleccionMenuBottom(MenuItem item, Context context) {

        switch (item.getItemId()) {

            case R.id.inicio:

                intent = new Intent(context, MenuPrincipalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


                break;
            case R.id.perfil:
                intent = new Intent(context, PerfilActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                break;
            case R.id.ayuda:
                intent = new Intent(context, AyudaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            case R.id.diccionario:
                intent = new Intent(context, DiccionarioActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                break;
            default:


        }

    }

    public static int actualizarAvance(String unidadP) {
        int dato = 0;

        switch (unidadP) {

            case UtilsBottomNavigation.NODO_UNIDAD_UNO:
                dato = 0;
                break;
            case UtilsBottomNavigation.NODO_UNIDAD_DOS:
                dato = 20;
                break;
            case UtilsBottomNavigation.NODO_UNIDAD_TRES:
                dato = 40;
                break;
            case UtilsBottomNavigation.NODO_UNIDAD_CUATRO:
                dato = 60;
                break;
            case UtilsBottomNavigation.NODO_UNIDAD_CINCO:
                dato = 80;
                break;
            default:
                dato = 0;
        }
        return dato;

    }

    public static int obtenerNumeroP(String unidadP) {
        int dato = 0;

        switch (unidadP) {

            case UtilsBottomNavigation.NODO_UNIDAD_UNO2:
                dato = UtilsBottomNavigation.NUMERO_PREGUNTAS_UNIDAD1;
                break;
            case UtilsBottomNavigation.NODO_UNIDAD_DOS2:
                dato = UtilsBottomNavigation.NUMERO_PREGUNTAS_UNIDAD2;
                break;
            case UtilsBottomNavigation.NODO_UNIDAD_TRES2:
                dato = UtilsBottomNavigation.NUMERO_PREGUNTAS_UNIDAD3;
                break;
            case UtilsBottomNavigation.NODO_UNIDAD_CUATRO2:
                dato = UtilsBottomNavigation.NUMERO_PREGUNTAS_UNIDAD4;
                break;
            case UtilsBottomNavigation.NODO_UNIDAD_CINCO2:
                dato = UtilsBottomNavigation.NUMERO_PREGUNTAS_UNIDAD5;
                break;
            default:
                dato = 0;
        }
        return dato;

    }


}
