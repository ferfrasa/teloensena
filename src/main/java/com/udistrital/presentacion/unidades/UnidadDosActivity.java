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
import com.udistrital.presentacion.fragments.YoutubeFragment;
import java.io.Serializable;
import java.util.ArrayList;

public class UnidadDosActivity extends AppCompatActivity implements LeccionesFragment.LeccionListener , Serializable {



    TextView nombreleccion;
    ArrayList<Leccion> leccion;
    private BottomNavigationView bottomNavigationView;
    Button botonTestUnidad;

    LeccionesFragment frgListado ;
    DetalleLeccionesFragment frgListadoD;
    YoutubeFragment youtubeFragment;
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
        setContentView(R.layout.activity_unidad_dos);

        inicarMenu();
        inicializarComponentes();
        iniciarComponentes();
       // botonAumentada= (Button) findViewById(R.id.realidadA);
        botonTestUnidad=(Button) findViewById(R.id.testUnidad2) ;

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


                    obEstudiante.setUnidadB3(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_UNIDAD_TRES).
                            getValue().toString()));

                    botonTestUnidad.setEnabled(!obEstudiante.isUnidadB3());
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
            //fragmentTransaction.replace(R.id.video, youtubeFragment);


            botonTestUnidad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), TestUnidadActivity.class);
                    intent.putExtra("Unidad", UtilsBottomNavigation.NODO_UNIDAD_DOS2);
                    intent.putExtra("UnidadPasar", UtilsBottomNavigation.NODO_UNIDAD_TRES);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);


                }
            });
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }catch (Exception e){
            Log.v("Mensaje Error",e.getMessage().toString());
        }


        //LeccionesFragment fragment = LeccionesFragment.newInstance(leccion);

    }

    private void inicializarComponentes(){

        obEstudiante= new Estudiante();

        nombreleccion =(TextView) findViewById(R.id.leccion);
        leccion= new ArrayList<>();
        // final ArrayList<Leccion> leccion = new ArrayList<>();
        leccion.add(new Leccion("Lección 1","Vocales",R.drawable.vocales,
                R.string.contenido_leccion1_uniad2,R.drawable.marcado_vocales,
                R.string.video_leccion1_unidad2));

        leccion.add(new Leccion("Lección 2","Abecedario 1",R.drawable.letras,
                R.string.contenido_leccion2_uniad2,
                R.drawable.marcado_letras_1,
                R.string.video_leccion2_unidad2));
        leccion.add(new Leccion("Lección 3","Abecedario 2",R.drawable.letras,
                R.string.contenido_leccion3_uniad2,
                R.drawable.marcado_letras_2,
                R.string.video_leccion3_unidad2));
        leccion.add(new Leccion("Lección 4","Abecedario 3",R.drawable.letras,
                R.string.contenido_leccion4_uniad2,
                R.drawable.marcado_letras_3,
                R.string.video_leccion4_unidad2));
        leccion.add(new Leccion("Lección 5","Abecedario 4",R.drawable.letras,
                R.string.contenido_leccion5_uniad2,
                R.drawable.marcado_letras_4,
                R.string.video_leccion5_unidad2));

        leccion.add(new Leccion("Lección 6","Números 1-9",R.drawable.numeros,
                R.string.contenido_leccion6_uniad2,R.drawable.marcado_numeros,
                R.string.video_leccion6_unidad2));
        leccion.add(new Leccion("Lección 7","Números 10-19",R.drawable.diez,
                R.string.contenido_leccion7_uniad2,R.drawable.marcado_numeros10_19,
                R.string.video_leccion7_unidad2));
        leccion.add(new Leccion("Lección 8","Números 20-90",R.drawable.diez,
                R.string.contenido_leccion8_uniad2,R.drawable.marcado_numeros_do,
                R.string.video_leccion8_unidad2));

        leccion.add(new Leccion("Lección 9","Números Grandes",R.drawable.cien,
                R.string.contenido_leccion9_uniad2,R.drawable.marcado_numeros_tres,
                R.string.video_leccion9_unidad2));
        leccion.add(new Leccion("Lección 10","Deletrear",R.drawable.vocales,
                R.string.contenido_leccion10_uniad2,R.drawable.marcador_deletreo,
                R.string.video_leccion10_unidad2));
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

        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        String myvideo =getResources().getString(l.getRecursoVideo());
        bundle.putString("video", myvideo );
        Log.v("mi videdo", myvideo);
        youtubeFragment  = new YoutubeFragment();
        youtubeFragment.setArguments(bundle);
        fragmentTransaction2.replace(R.id.video, youtubeFragment);
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.commit();



        nombreleccion.setText(l.getIdLeccion());
        frgListadoD.mostrarDetalle(l.getNombreLeccion(),l.getRecursoContendio(),
                l.getRecursoImagen1(),l.getRecursoImagen2(),l.getRecursoImagen3());

        ((DetalleLeccionesFragment)getSupportFragmentManager()
                .findFragmentById(R.id.mod)).mostrarDetalle(l.getNombreLeccion(),l.getRecursoContendio(),
                l.getRecursoImagen1(),l.getRecursoImagen2(),l.getRecursoImagen3());



    }



}
