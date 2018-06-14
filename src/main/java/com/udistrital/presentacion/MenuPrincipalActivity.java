package com.udistrital.presentacion;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udistrital.presentacion.usuario.CreditosActivity;
import com.udistrital.R;
import com.udistrital.Utils.UtilsBottomNavigation;
import com.udistrital.adatos.Estudiante;
import com.udistrital.presentacion.test.TestFinalActivity;
import com.udistrital.presentacion.unidades.UnidadCincoActivity;
import com.udistrital.presentacion.unidades.UnidadCuatroActivity;
import com.udistrital.presentacion.unidades.UnidadDosActivity;
import com.udistrital.presentacion.unidades.UnidadTresActivity;
import com.udistrital.presentacion.unidades.UnidadUnoActivity;

public class MenuPrincipalActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceDos;

    private Button unidad1;
    private Button unidad2;
    private Button unidad3;
    private Button unidad4;
    private Button unidad5;
    private Button testFinal;

    Estudiante obEstudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        inicarMenu();
        iniciarComponentes();


        fireAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                if (!(user!=null&& user.isEmailVerified())){
                    goLogInScreen();
                }

            }
        };

        iniciarDabaseReference(UtilsBottomNavigation.NODO_USUARIOS,user.getUid());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                recorrePregunta(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        unidad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), UnidadUnoActivity.class);
                finish();
            }
        });

        unidad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), UnidadDosActivity.class);
                finish();
            }
        });

        unidad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), UnidadTresActivity.class);
                finish();
            }
        });

        unidad4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), UnidadCuatroActivity.class);
                finish();
            }
        });

        unidad5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), UnidadCincoActivity.class);
                finish();
            }
        });

        testFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsBottomNavigation.intentInicio(getApplicationContext(), TestFinalActivity.class);
                finish();
            }
        });


    }

    private void iniciarComponentes(){

        obEstudiante= new Estudiante();
        unidad1= (Button) findViewById(R.id.unidad1);
        unidad2= (Button) findViewById(R.id.unidad2);
        unidad3= (Button) findViewById(R.id.unidad3);
        unidad4= (Button) findViewById(R.id.unidad4);
        unidad5= (Button) findViewById(R.id.unidad5);
        testFinal=(Button) findViewById(R.id.test_final);
        firebaseAuth=FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();

    }

    private void inicarMenu(){

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        Log.d("Error","aca es"+bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                UtilsBottomNavigation.seleccionMenuBottom(item, getApplicationContext());
                finish();

                return true;
            }
        });


    }

    public void logOut(View view) {
        firebaseAuth.signOut();

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

    private void goLogInScreen() {
        Intent intent = new Intent(this, InicioSesionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

    private void iniciarDabaseReference(String nodo1,String nodo2){

        databaseReference= FirebaseDatabase.getInstance().getReference().child(nodo1).child(nodo2);
       // databaseReferenceDos= obVideOperacion.buscarNodo(nodo2);

       // databaseReference= FirebaseDatabase.getInstance().getReference().child(nivel1);
        //return databaseReference;

    }


    private void recorrePregunta(DataSnapshot usuario){

        obEstudiante.setUnidadB1(Boolean.valueOf(usuario.child(UtilsBottomNavigation.NODO_UNIDAD_UNO).
                getValue().toString()));
        obEstudiante.setUnidadB2(Boolean.valueOf(usuario.child(UtilsBottomNavigation.NODO_UNIDAD_DOS).
                getValue().toString()));
        obEstudiante.setUnidadB3(Boolean.valueOf(usuario.child(UtilsBottomNavigation.NODO_UNIDAD_TRES).
                getValue().toString()));
        obEstudiante.setUnidadB4(Boolean.valueOf(usuario.child(UtilsBottomNavigation.NODO_UNIDAD_CUATRO).
                getValue().toString()));
        obEstudiante.setUnidadB5(Boolean.valueOf(usuario.child(UtilsBottomNavigation.NODO_UNIDAD_CINCO).
                getValue().toString()));
        obEstudiante.setTestFinal(Boolean.valueOf(usuario.child(UtilsBottomNavigation.NODO_TEST_FINAL).
                getValue().toString()));
        obEstudiante.setTestInicial(Boolean.valueOf(usuario.child(UtilsBottomNavigation.NODO_TEST_INICIAL).
                getValue().toString()));

        unidad1.setEnabled(obEstudiante.isUnidadB1());
        unidad2.setEnabled(obEstudiante.isUnidadB2());
        unidad3.setEnabled(obEstudiante.isUnidadB3());
        unidad4.setEnabled(obEstudiante.isUnidadB4());
        unidad5.setEnabled(obEstudiante.isUnidadB5());
        testFinal.setEnabled(obEstudiante.isTestFinal());


    }
}
