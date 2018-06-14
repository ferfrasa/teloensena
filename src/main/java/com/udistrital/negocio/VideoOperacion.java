package com.udistrital.negocio;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by erika on 12/02/18.
 */

public class VideoOperacion {

    private FirebaseAuth firebaseAuth2;
    private DatabaseReference databaseReference;


    public VideoOperacion(FirebaseAuth firebaseAuth) {
        this.firebaseAuth2 =firebaseAuth.getInstance();
    }



    public DatabaseReference buscarNodo(String nivel1, String nivel2){
        databaseReference= FirebaseDatabase.getInstance().
                getReference().child(nivel1).child(nivel2);
        return databaseReference;
    }


    public DatabaseReference buscarNodo(String nivel1){
        databaseReference= FirebaseDatabase.getInstance().
                getReference().child(nivel1);
        return databaseReference;
    }

    public DatabaseReference buscarNodo(String nivel1, String nivel2,String nodo3){
        databaseReference= FirebaseDatabase.getInstance().
                getReference().child(nivel1).child(nivel2).child(nodo3);
        return databaseReference;

    }



}
