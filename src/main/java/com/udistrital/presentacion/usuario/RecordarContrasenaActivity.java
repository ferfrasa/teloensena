package com.udistrital.presentacion.usuario;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.udistrital.presentacion.InicioSesionActivity;
import com.udistrital.R;
import com.udistrital.Utils.UtilsBottomNavigation;

public class RecordarContrasenaActivity extends AppCompatActivity {

    private Button btIniciar;
    private Button btRecordar;
    private Button btRegistrar;
    private FirebaseAuth  firebaseAuth;
    private ProgressBar progressBarRecordar;
    private EditText txtCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordar_contrasena);

        btIniciar=(Button) findViewById(R.id.btIniciarS);
        btRecordar=(Button) findViewById(R.id.btRecordarC);
        btRegistrar=(Button) findViewById(R.id.btRegistrarU);
        progressBarRecordar =(ProgressBar) findViewById(R.id.progressLogin);
        txtCorreo=(EditText) findViewById(R.id.txtcorreo);

        firebaseAuth = FirebaseAuth.getInstance();


        btRecordar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordarContrasena();
            }
        });

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(),
                        RegistrarUsuarioActivity.class);
            }
        });

        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(),InicioSesionActivity.class);
            }
        });
    }


    private void recordarContrasena(){
        String correo = txtCorreo.getText().toString().trim();

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(getApplication(), "Por favor ingrese el un correo eletronico", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBarRecordar.setVisibility(View.VISIBLE);

        firebaseAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.mensaje_correo_exito, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.mensaje_error_correo, Toast.LENGTH_SHORT).show();
                }

                progressBarRecordar.setVisibility(View.GONE);

            }
        });




    }
}
