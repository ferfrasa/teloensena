package com.udistrital.presentacion.usuario;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udistrital.presentacion.InicioSesionActivity;
import com.udistrital.R;
import com.udistrital.Utils.UtilsBottomNavigation;
import com.udistrital.adatos.Estudiante;
import com.udistrital.presentacion.test.TestInicialActivity;


public class PerfilActivity extends AppCompatActivity {

    private ImageView fotoPerfil;
    private TextView nombre;
    private TextView correo;
    private TextView unidad1;
    private TextView unidad2;
    private TextView unidad3;
    private TextView unidad4;
    private TextView unidad5;
    private TextView avance;
    private TextView testInicial;
    private TextView testFinal;
    String texto="NO HAS REALIZADO EL TEST INICIAL";

    private TextView idUsuario;
    private BottomNavigationView bottomNavigationView;
    private Estudiante obEstudiante;
    private ProgressBar progressBarTutorial;
    private Button btTestInicial;

    private DatabaseReference databaseReference;


    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        iniciarComponentes();

        firebaseAuth =FirebaseAuth.getInstance();
        fireAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user!=null&& user.isEmailVerified()){
                    obtenerDatosUser(user);

                }else{
                    goLogInScreen();
                }

            }
        };

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                UtilsBottomNavigation.seleccionMenuBottom(item,getApplicationContext());
                finish();

                return true;
            }
        });

        btTestInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), TestInicialActivity.class);
                finish();
            }
        });



    }

    private void iniciarComponentes() {
        obEstudiante= new Estudiante();
        fotoPerfil = (ImageView) findViewById(R.id.photoImageView);
        nombre= (TextView) findViewById(R.id.nameTextView);
        correo=(TextView) findViewById(R.id.emailTextView);
        avance= (TextView) findViewById(R.id.avance);
        progressBarTutorial=(ProgressBar) findViewById(R.id.progresoTutorial);
        unidad1=(TextView) findViewById(R.id.unidad1);
        unidad2=(TextView) findViewById(R.id.unidad2);
        unidad3=(TextView) findViewById(R.id.unidad3);
        unidad4=(TextView) findViewById(R.id.unidad4);
        unidad5=(TextView) findViewById(R.id.unidad5);
        testInicial=(TextView) findViewById(R.id.texto_test);
        testFinal=(TextView) findViewById(R.id.testFinal);
        btTestInicial=(Button) findViewById(R.id.testIncial);
        //  idUsuario=(TextView) findViewById(R.id.idTextView);

        unidad1.setText("UNIDAD 1 BLOQUEADA");
        unidad2.setText("UNIDAD 2 BLOQUEADA");
        unidad3.setText("UNIDAD 3 BLOQUEADA");
        unidad4.setText("UNIDAD 4 BLOQUEADA");
        unidad5.setText("UNIDAD 5 BLOQUEADA");
        testInicial.setText(texto);
        testFinal.setText("NO PASADO");


    }

    private void obtenerDatosUser(FirebaseUser user) {


        consultarFirebase(user.getUid());

        Log.v("CONSULTA por fuera"," "+obEstudiante.getCorreoUsuario());
        Log.v("CONSULTA por fuera"," "+obEstudiante.getNombreUsuario());

    }

    private void consultarFirebase(String idUsuario){



        databaseReference= FirebaseDatabase.getInstance().
                getReference().child(UtilsBottomNavigation.NODO_USUARIOS).child(idUsuario);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                Log.v("CONSULTA"," "+dataSnapshot.child(UtilsBottomNavigation.NODO_CORREO).getValue().toString());


             //   obEstudiante= dataSnapshot.getValue(Estudiante.class);
                obEstudiante.setCorreoUsuario(dataSnapshot.child(UtilsBottomNavigation.NODO_CORREO)
                        .getValue().toString());
                Log.v("CONSULTA"," "+obEstudiante.getCorreoUsuario());



                obEstudiante.setNombreUsuario(dataSnapshot.child(UtilsBottomNavigation.NODO_NOMBRE_USUARIO)
                        .getValue().toString());

                Log.v("CONSULTA"," "+obEstudiante.getNombreUsuario());

                Log.v("CONSULTA"," "+dataSnapshot.child(UtilsBottomNavigation.NODO_NOMBRE_USUARIO).getValue().toString());

                obEstudiante.setEstado(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_ESTADO)
                        .getValue().toString()));

                obEstudiante.setNivel(Integer.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_NIVEL).
                        getValue().toString()));

                obEstudiante.setIdUsuario(dataSnapshot.child(UtilsBottomNavigation.NODO_IDUSUARIO).
                        getValue().toString());


                obEstudiante.setUnidadB1(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_UNIDAD_UNO).
                        getValue().toString()));

                obEstudiante.setUnidadB2(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_UNIDAD_DOS).
                        getValue().toString()));

                obEstudiante.setUnidadB3(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_UNIDAD_TRES).
                        getValue().toString()));

                obEstudiante.setUnidadB4(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_UNIDAD_CUATRO).
                        getValue().toString()));

                obEstudiante.setUnidadB5(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_UNIDAD_CINCO).
                        getValue().toString()));

                obEstudiante.setUnidadB5(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_TEST_INICIAL).
                        getValue().toString()));
                obEstudiante.setTestInicial(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_TEST_INICIAL).
                        getValue().toString()));
                obEstudiante.setTestFinal(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_TEST_FINAL).
                        getValue().toString()));
                obEstudiante.setTestFinalP(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_TEST_FINALP).
                        getValue().toString()));




                nombre.setText(obEstudiante.getNombreUsuario());
                correo.setText(obEstudiante.getCorreoUsuario());
                avance.setText(obEstudiante.getNivel()+getString(R.string.unidades_completadas));
                progressBarTutorial.setProgress(obEstudiante.getNivel());
                btTestInicial.setEnabled(!obEstudiante.isTestInicial());

                if (obEstudiante.isUnidadB1()){
                    unidad1.setText("UNIDAD 1 DESBLOQUEADA");
                }
                if (obEstudiante.isUnidadB2()){
                    unidad2.setText("UNIDAD 2 DESBLOQUEADA");
                }

                if (obEstudiante.isUnidadB3()){
                    unidad3.setText("UNIDAD 3 3DESBLOQUEADA");
                }

                if (obEstudiante.isUnidadB4()){
                    unidad4.setText("UNIDAD 4 DESBLOQUEADA");
                }
                if (obEstudiante.isUnidadB5()){
                    unidad5.setText("UNIDAD 5 DESBLOQUEADA");
                }

                if (obEstudiante.isTestFinalP()){
                    testFinal.setText("TEST FINAL PASADO");
                }


                if (obEstudiante.isTestInicial()){
                    testInicial.setText("YA REALIZASTE EL TEST INICIAL, CONTINUA CON LAS UNIDADES");
                }else {
                    testInicial.setText(texto);
                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(fireAuthStateListener);

    }


    private void goLogInScreen() {
        Intent intent = new Intent(this, InicioSesionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        firebaseAuth.signOut();

    }

    public void revoke(View view) {
        firebaseAuth.signOut();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // remoer el oyete de firebase


        if(fireAuthStateListener!= null){
            firebaseAuth.removeAuthStateListener(fireAuthStateListener);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.salir){

            firebaseAuth.signOut();

             return true;

        }else if(item.getItemId()==R.id.acercade){
            UtilsBottomNavigation.intentInicio(getApplicationContext(), CreditosActivity.class);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
