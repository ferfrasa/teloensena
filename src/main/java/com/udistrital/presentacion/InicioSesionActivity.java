package com.udistrital.presentacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.udistrital.R;
import com.udistrital.Utils.UtilsBottomNavigation;
import com.udistrital.presentacion.usuario.PerfilActivity;
import com.udistrital.presentacion.usuario.RecordarContrasenaActivity;
import com.udistrital.presentacion.usuario.RegistrarUsuarioActivity;


public class InicioSesionActivity extends AppCompatActivity {

    private ProgressBar progressBarLogin;
    private Button btnRegistar;
    private Button btnIniciar;
    private Button btRecordar;
    private EditText txtUsuario;
    private EditText txtContrasena;
    private String usuario;
    private String contrasena;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;

    public static final int CODIGO_RESPUESTA = 777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //objeto de opciones paara la autenticación
        firebaseAuth = FirebaseAuth.getInstance();

        // llamar al boton login SIngIN
        btnRegistar = (Button) findViewById(R.id.btregistrar);
        btnIniciar=(Button) findViewById(R.id.btiniciar);
        btRecordar =(Button) findViewById(R.id.btrecordar);
        txtUsuario = (EditText) findViewById(R.id.txtcorreo);
        txtContrasena = (EditText) findViewById(R.id.txtcontrasena);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarUsuarioAnonimo();
            }
        });

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarEstudiante();

            }
        });

        btRecordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordarContrasena();
            }
        });

        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); // saber si se esta autenticad

                if (user!= null && user.isEmailVerified()){
                    loginGoogle(); // si existe el usuario inicar la actividad
                }
            }
        };

        progressBarLogin =(ProgressBar) findViewById(R.id.progressLogin);

    }

    private void iniciarUsuarioAnonimo() {
            Log.v("Inicio ANONIMO","Entro1");

        if(!validarBlancos()){
            return;
        }
        progressBarLogin.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(usuario,contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarLogin.setVisibility(View.GONE);

                        if(!task.isSuccessful()){
                            Log.w("Inicio ANONIMO", "Error de inicio", task.getException());


                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "El usuario no esta registrado", Toast.LENGTH_SHORT).show();
                                txtContrasena.setText("");


                            }else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){

                                Toast.makeText(getApplicationContext(), "La contraseña o el usuario son incorrectos", Toast.LENGTH_SHORT).show();
                                txtContrasena.setText("");
                            }

                            else{

                                Log.w("Inicio ANONIMO", "Error de inicio", task.getException());

                                Toast.makeText(getApplicationContext(), "Ha ocurrido un error al iniciar sesion", Toast.LENGTH_LONG)
                                        .show();
                             txtContrasena.setText("");

                            }
                        }else
                        {
                            Log.v("Inicio ANONIMO","Entro2");
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                           if(user.isEmailVerified()) {

                               loginGoogle();
                           }else{
                               Toast.makeText(getApplicationContext(),
                                       "EL correo electronico no ha sido verficiado",
                                       Toast.LENGTH_LONG
                                       ).show();
                               txtUsuario.setText(" ");
                           }
                        }


                    }
                });
    }

    private boolean validarBlancos() {
        Log.v("Inicio ANONIMO","Entro2");

        boolean valido= true;

            usuario=txtUsuario.getText().toString();
            contrasena= txtContrasena.getText().toString();


            if(usuario.equals("")||
                    contrasena.equals(""))
            {
                Log.v("Inicio ANONIMO","Entro3");
                Toast.makeText(this,"Ingrese los campos de usuario y contraseña",
                        Toast.LENGTH_LONG).show();
                valido= false;

            }

           return valido;

        }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(fireAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // remoer el oyete de firebase


        if(fireAuthStateListener!= null){
            firebaseAuth.removeAuthStateListener(fireAuthStateListener);
        }
    }

    private void loginGoogle() {


        Intent intent = new Intent(this,PerfilActivity.class);
        // AL ir atras esta actividad no ira de nuevo al lgin
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

    }

    private void registrarEstudiante(){
        UtilsBottomNavigation.intentInicio(getApplicationContext(),RegistrarUsuarioActivity.class);
    }

    private void recordarContrasena(){
        UtilsBottomNavigation.intentInicio(getApplicationContext(),RecordarContrasenaActivity.class);

    }
}
