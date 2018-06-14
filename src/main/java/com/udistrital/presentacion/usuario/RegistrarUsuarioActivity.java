package com.udistrital.presentacion.usuario;

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
import com.google.firebase.database.FirebaseDatabase;
import com.udistrital.presentacion.InicioSesionActivity;
import com.udistrital.R;
import com.udistrital.Utils.UtilsBottomNavigation;
import com.udistrital.adatos.Estudiante;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private Button botonRegistrar;
    private Button btiniciarSesion;
    private Button btRestaurar;
    private EditText txtUsuario;
    private EditText txtCostrasena;
    private EditText txtNombre;
    private ProgressBar progressBarRegistrar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;

    private String usuario;
    private String contrasena;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        botonRegistrar = (Button) findViewById(R.id.btregistrar);
        btiniciarSesion = (Button) findViewById(R.id.btIniciar);
        btRestaurar = (Button) findViewById(R.id.btRecordar);
        txtUsuario = (EditText) findViewById(R.id.txtusuario);
        txtCostrasena =(EditText) findViewById(R.id.txtcontrasena);
        txtNombre=(EditText) findViewById(R.id.txtNombre);
        progressBarRegistrar =(ProgressBar) findViewById(R.id.progressLogin);


        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // FirebaseUser user = firebaseAuth.getCurrentUser();

                // if(user != null){

                // }else{

                //  }
            }
        };

        btiniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), InicioSesionActivity.class);
            }
        });

        btRestaurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), RecordarContrasenaActivity.class);

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }


    @Override
    protected void onStop() {
        super.onStop();

        if(authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    public void registrarEstudiante(View view){
        if(!validarBlancos()){
            return;

        }
        progressBarRegistrar.setVisibility(View.VISIBLE);
        botonRegistrar.setVisibility(View.GONE);
        mAuth.createUserWithEmailAndPassword(usuario,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    Log.w("ERROR REGISTRO", "Error de registro", task.getException());

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(getApplicationContext(), "El correo registrado no es valido. Escriba uno valido", Toast.LENGTH_SHORT).show();
                          vaciasEditText();
                    }else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "El correo registrado ya esta con otro usuario", Toast.LENGTH_SHORT).show();
                         vaciasEditText();
                    }else{
                        Toast.makeText(getApplicationContext(), "A ocurrido un error al realizar el registro"
                                , Toast.LENGTH_LONG).show();
                         vaciasEditText();
                    }
                }else{


                    verificarCorreo();

                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    FirebaseUser user = mAuth.getCurrentUser();

                    String key=databaseReference.push().push().getKey();

                    Estudiante objetto = new Estudiante(nombre,user.getEmail(),user.getUid(),0,true);
                    databaseReference.child("usuario").child(objetto.getIdUsuario()).setValue(objetto);

                    //databaseReference.child("user").

                    Toast.makeText(getApplicationContext(),"Se ha registrado el usuario con exito verifica tu correo",
                            Toast.LENGTH_LONG).show();
                      vaciasEditText();

                }

                progressBarRegistrar.setVisibility(View.GONE);
                botonRegistrar.setVisibility(View.VISIBLE);
            }
        });


    }

    public void vaciasEditText(){

        txtNombre.setText("");
        txtUsuario.setText("");
        txtCostrasena.setText("");
    }


    private void verificarCorreo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Verificacion email", "Email sent.");
                }else{

                    Toast.makeText(getApplicationContext(),"La cuenta de correo no existe ", Toast.LENGTH_LONG)
                            .show();

                    vaciasEditText();
                    return;


                }
            }
        });
    }


    private boolean validarBlancos() {
        boolean valida = true;

        usuario=txtUsuario.getText().toString();
        contrasena= txtCostrasena.getText().toString();
        nombre=txtNombre.getText().toString();


        if(usuario.equals("")||
                contrasena.equals("")||nombre.equals(""))
        {
            Toast.makeText(this,"Ingrese los campos de nombre, usuario y contraseña",
                    Toast.LENGTH_LONG).show();
               valida=false;


        }else if(contrasena.length()<6 || contrasena.contains(" ")){
            Toast.makeText(this,"La contraseña debe tener mínimo 6 caracteres y no tener espacios en blanco",
                    Toast.LENGTH_LONG).show();
            valida=false;
            txtCostrasena.setText("");
        }

        return valida;
    }
}
