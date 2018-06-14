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

public class UnidadTresActivity extends AppCompatActivity implements  LeccionesFragment.LeccionListener, Serializable{


    TextView nombreleccion;
    Estudiante obEstudiante;
    ArrayList<Leccion> leccion;
    private BottomNavigationView bottomNavigationView;
    LeccionesFragment frgListado ;
    DetalleLeccionesFragment frgListadoD;
    YoutubeFragment youtubeFragment;
    FragmentManager fragmentManager;
    Button botonTestUnidad;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceDos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidad_tres);

        inicarMenu();
        inicializarComponentes();
        // botonAumentada= (Button) findViewById(R.id.realidadA);
        iniciarComponentes();
        // botonAumentada= (Button) findViewById(R.id.realidadA);
        botonTestUnidad=(Button) findViewById(R.id.testUnidad3) ;

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


                    obEstudiante.setUnidadB4(Boolean.valueOf(dataSnapshot.child(UtilsBottomNavigation.NODO_UNIDAD_CUATRO).
                            getValue().toString()));

                    botonTestUnidad.setEnabled(!obEstudiante.isUnidadB4());
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
                intent.putExtra("Unidad", UtilsBottomNavigation.NODO_UNIDAD_TRES2);
                intent.putExtra("UnidadPasar", UtilsBottomNavigation.NODO_UNIDAD_CUATRO);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
         }catch (Exception e){

             Log.v("Exepcion create" ,e.getMessage().toString());
         }

    }

    private void inicializarComponentes(){
        obEstudiante=new Estudiante();
        nombreleccion =(TextView) findViewById(R.id.leccion);

        leccion= new ArrayList<>();
        // final ArrayList<Leccion> leccion = new ArrayList<>();
        leccion.add(new Leccion("Lección 1","Familia 1",R.drawable.family_older_brother,
                R.string.contenido_leccion1_uniad3,R.drawable.marcador_familia_base,
                R.string.video_leccion1_unidad3));

        leccion.add(new Leccion("Lección 2","Familia 2",R.drawable.family_grandmother,
                R.string.contenido_leccion2_uniad3,
                R.drawable.marcador_familia_nivel_2,
                R.string.video_leccion2_unidad3));
        leccion.add(new Leccion("Lección 3","Comida 1",R.drawable.comida,
                R.string.contenido_leccion3_uniad3,
                R.drawable.marcador_comida_11,
                R.string.video_leccion3_unidad3));
        leccion.add(new Leccion("Lección 4","Comida 2",R.drawable.comida2,
                R.string.contenido_leccion4_uniad3,
                R.drawable.marcador_comida_12,
                R.string.video_leccion4_unidad3));
        leccion.add(new Leccion("Lección 5","Animales 1",R.drawable.animal1,
                R.string.contenido_leccion5_uniad3,
                R.drawable.animales_1,
                R.string.video_leccion5_unidad3));

        leccion.add(new Leccion("Lección 6","Animales 2",R.drawable.animal2,
                R.string.contenido_leccion6_uniad3,R.drawable.animales_2,
                R.string.video_leccion6_unidad3));
        leccion.add(new Leccion("Lección 7","Tiempo 1",R.drawable.tiempo1,
                R.string.contenido_leccion7_uniad3,R.drawable.marcador_tiempo,
                R.string.video_leccion7_unidad3));
        leccion.add(new Leccion("Lección 8","Tiempo 2",R.drawable.tiempo2,
                R.string.contenido_leccion8_uniad3,R.drawable.marcador_tiempo_2,
                R.string.video_leccion8_unidad3));

        leccion.add(new Leccion("Lección 9","Color 1",R.drawable.color1,
                R.string.contenido_leccion9_uniad3,R.drawable.colores,
                R.string.video_leccion9_unidad3));
        leccion.add(new Leccion("Lección 10","Color 2",R.drawable.color2,
                R.string.contenido_leccion10_uniad3,R.drawable.colores_2,
                R.string.video_leccion10_unidad3));

        leccion.add(new Leccion("Lección 11","Emociones 1",R.drawable.emocion1,
                R.string.contenido_leccion10_uniad3,R.drawable.emociones_1,
                R.string.video_leccion10_unidad3));
        leccion.add(new Leccion("Lección 12","Emociones 2",R.drawable.emocion2,
                R.string.contenido_leccion10_uniad3,R.drawable.emociones_2,
                R.string.video_leccion10_unidad3));
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

        firebaseAuth= FirebaseAuth.getInstance();
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

        try {

            FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle();
            String myvideo = getResources().getString(l.getRecursoVideo());
            bundle.putString("video", myvideo);
            Log.v("mi videdo", myvideo);
            youtubeFragment = new YoutubeFragment();
            youtubeFragment.setArguments(bundle);
            fragmentTransaction2.replace(R.id.video, youtubeFragment);
            fragmentTransaction2.addToBackStack(null);
            fragmentTransaction2.commit();


            nombreleccion.setText(l.getIdLeccion());
            frgListadoD.mostrarDetalle(l.getNombreLeccion(), l.getRecursoContendio(),
                    l.getRecursoImagen1(), l.getRecursoImagen2(), l.getRecursoImagen3());

            ((DetalleLeccionesFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mod)).mostrarDetalle(l.getNombreLeccion(), l.getRecursoContendio(),
                    l.getRecursoImagen1(), l.getRecursoImagen2(), l.getRecursoImagen3());

        }catch (Exception e){

            Log.v("Exepcion LecconListener" ,e.getMessage().toString());

        }

    }
}
