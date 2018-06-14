package com.udistrital.presentacion.unidades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udistrital.presentacion.usuario.CreditosActivity;
import com.udistrital.adatos.Estudiante;
import com.udistrital.presentacion.InicioSesionActivity;
import com.udistrital.R;
import com.udistrital.Utils.UtilsBottomNavigation;
import com.udistrital.adatos.Leccion;
import com.udistrital.presentacion.test.TestUnidadActivity;
import com.udistrital.presentacion.fragments.DetalleLeccionesFragment;
import com.udistrital.presentacion.fragments.LeccionesFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class UnidadUnoActivity extends AppCompatActivity implements LeccionesFragment.LeccionListener, Serializable{


    TextView nombreleccion;
    ArrayList<Leccion> leccion;
    Button botonTestUnidad;
    private BottomNavigationView bottomNavigationView;

    LeccionesFragment frgListado ;
    DetalleLeccionesFragment frgListadoD;
    FragmentManager fragmentManager;
    Estudiante obEstudiante;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceDos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidad_uno);


        inicarMenu();
        inicializarComponentes();
        iniciarComponentes();
        try {
            fireAuthStateListener= new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                     user = firebaseAuth.getCurrentUser();
                    if (!(user!=null&& user.isEmailVerified())){
                        goLogInScreen();
                    }
                }
            };


            databaseReference= FirebaseDatabase.getInstance().
                    getReference().child(UtilsBottomNavigation.NODO_USUARIOS).child(user.getUid());

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    obEstudiante.setUnidadB2(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_UNIDAD_DOS).
                            getValue().toString()));

                    botonTestUnidad.setEnabled(!obEstudiante.isUnidadB2());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            frgListado = LeccionesFragment.newInstance(leccion);
            frgListadoD = new DetalleLeccionesFragment();
            frgListado.setLeccionListener(this);







            fragmentTransaction.replace(R.id.lista, frgListado);
            fragmentTransaction.replace(R.id.mod, frgListadoD);


            botonTestUnidad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), TestUnidadActivity.class);
                    intent.putExtra("Unidad", UtilsBottomNavigation.NODO_UNIDAD_UNO2);
                    intent.putExtra("UnidadPasar", UtilsBottomNavigation.NODO_UNIDAD_DOS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                   // UtilsBottomNavigation.intentInicio(getApplicationContext(), TestUnidadActivity.class);

                }
            });
            fragmentTransaction.commit();

            //Fragment fragment = CommentsFragment.newInstance(mDescribable);
            //ft.replace(R.id.comments_fragment, fragment);
            //ft.commit();

          //  LeccionesFragment fragment = LeccionesFragment.newInstance(leccion);

        }catch (Exception e){
            Log.v("Error log", e.getMessage().toString());
        }

      //LeccionesFragment frgListado =(LeccionesFragment)getSupportFragmentManager().findFragmentById(R.id.lista);
       // frgListado.setLeccionListener(this);


    }

    private void inicializarComponentes(){

        obEstudiante= new Estudiante();
        nombreleccion =(TextView) findViewById(R.id.leccion);
        leccion= new ArrayList<>();
        // final ArrayList<Leccion> leccion = new ArrayList<>();
        leccion.add(new Leccion("Leccion 1","Lengua de señas",R.drawable.mano1,
                R.string.contenido_leccion1_uniad1,R.drawable.ls,
                R.drawable.family_daughter,R.drawable.family_daughter,0));
        leccion.add(new Leccion("Leccion 2","Lengua de señas colombiano",R.drawable.mano2,
                R.string.contenido_leccion2_uniad1,
                R.drawable.lsc,R.drawable.family_daughter,R.drawable.family_daughter,0));
        leccion.add(new Leccion("Leccion 3","Persona Sorda",R.drawable.mano3,
                R.string.contenido_leccion3_uniad1,R.drawable.oido,R.drawable.mujers,
                R.drawable.hombres,0));
        leccion.add(new Leccion("Leccion 4","Seña Personal",R.drawable.mano4,
                R.string.contenido_leccion4_uniad1,R.drawable.personal,
                R.drawable.personal1, R.drawable.personal2,0));
        leccion.add(new Leccion("Leccion 5","Hipoacusia",R.drawable.mano5,
                R.string.contenido_leccion5_uniad1,R.drawable.otra,
                R.drawable.otra1,R.drawable.otra2,0));

        botonTestUnidad =(Button) findViewById(R.id.testUnidad1);
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


    private void iniciarComponentes(){

        firebaseAuth=FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();

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


    @Override
    public void onLeccionSeleccionado(Leccion l) {

        nombreleccion.setText(l.getIdLeccion());
        frgListadoD.mostrarDetalle(l.getNombreLeccion(),l.getRecursoContendio(),
                l.getRecursoImagen1(),l.getRecursoImagen2(),l.getRecursoImagen3());
            ((DetalleLeccionesFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.mod)).mostrarDetalle(l.getNombreLeccion(),l.getRecursoContendio(),
                    l.getRecursoImagen1(),l.getRecursoImagen2(),l.getRecursoImagen3());

    }

}
