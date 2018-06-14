package com.udistrital.negocio;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by erika on 5/02/18.
 */

public class FirebaseConexion {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;

    public FirebaseConexion(){

        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void stopFirebase(){

        if(fireAuthStateListener!= null){
            firebaseAuth.removeAuthStateListener(fireAuthStateListener);
        }
    }

    public void startFirebase(){
        firebaseAuth.addAuthStateListener(fireAuthStateListener);

    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public FirebaseAuth.AuthStateListener getFireAuthStateListener() {
        return fireAuthStateListener;
    }

    public void setFireAuthStateListener(FirebaseAuth.AuthStateListener fireAuthStateListener) {
        this.fireAuthStateListener = fireAuthStateListener;
    }
}
