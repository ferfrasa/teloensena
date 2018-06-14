package com.udistrital.presentacion.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udistrital.presentacion.usuario.CreditosActivity;
import com.udistrital.R;
import com.udistrital.Utils.ConfiguracionYoutube;
import com.udistrital.Utils.UtilsBottomNavigation;
import com.udistrital.adatos.Respuesta;
import com.udistrital.adatos.Test;
import com.udistrital.adatos.Unidad;
import com.udistrital.adatos.Video;
import com.udistrital.negocio.VideoOperacion;
import com.udistrital.presentacion.InicioSesionActivity;
import com.udistrital.presentacion.MenuPrincipalActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestUnidadActivity  extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youTubePlayerView;
    VideoOperacion obVideOperacion;
    YouTubePlayer player;

    Test obTest;
    Respuesta obRespuesta;
    Unidad obUnidad;
    Video obVideo;

    private BottomNavigationView bottomNavigationView;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceDos;
    private DatabaseReference databaseReferenceTres;

    ArrayList<Test> testPreguntasUnidad;

    ArrayList<Respuesta> respuestaUnidad;
    ArrayList<Respuesta> respuestaEstudiante;

    ArrayList<Respuesta> arrayTemporal;


    TextView txtEnunciado;
    TextView txtEnunciadoInicio;
    Button btSiguiente;
    Button btIniciar;

    RadioGroup radioGroup;
    RadioButton respuesta1;
    RadioButton respuesta2;
    RadioButton respuesta3;
    RadioButton respuesta4;
    int pregunta;
    AtomicBoolean unidad1;
    AtomicBoolean unidad2;
    AtomicBoolean unidad3;
    int selecciono;
    int respuestaTexto;
    int numeroRespuestas;
    int incorrectas;
    int numeroPreguntas;
    String unidadAc;
    String unidadPasar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_unidad);

        iniciarComponentes();
        inicarMenu();


        fireAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                if (!(user!=null&& user.isEmailVerified())){
                    goLogInScreen();
                }

            }
        };



         numeroPreguntas=UtilsBottomNavigation.obtenerNumeroP(unidadAc);



        iniciarDabaseReference(UtilsBottomNavigation.NODO_TEST,UtilsBottomNavigation.NODO_RESPUESTA_TEST,unidadAc);

        txtEnunciadoInicio.setVisibility(View.VISIBLE);

        //Para guardar las preguntas de unidad 1,2 y 3

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String key="";
                for(DataSnapshot pregunta : dataSnapshot.getChildren()){

                    recorrePregunta(pregunta,testPreguntasUnidad);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Para guardar las respuestas de unidad 1,2 y 3

        databaseReferenceDos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot unidad : dataSnapshot.getChildren()) {

                    recorreRespuesta(unidad,respuestaUnidad);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



       btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarTest();

            }
       });

        btSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    validarEscogido();
                    if (selecciono==-1){
                        Toast.makeText(getApplicationContext(),"Selecciona alguna opción de respuesta",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        obtenerObjeto();
                        guardarRespuestas();
                        pregunta++;
                        numeroRespuestas++;



                        buscarPreguntas(testPreguntasUnidad,respuestaUnidad,
                                numeroPreguntas,unidad1,
                                UtilsBottomNavigation.NODO_UNIDAD_UNO);

                    }
            }
//
        });
    }


    private void iniciarComponentes() {

        unidadAc= getIntent().getExtras().getString("Unidad");
        unidadPasar= getIntent().getExtras().getString("UnidadPasar");
        numeroRespuestas=0;
        incorrectas=0;
        respuestaTexto=0;
        selecciono=-1;
        unidad1=new AtomicBoolean(false);
        unidad2=new AtomicBoolean(false);
        unidad3=new AtomicBoolean(false);
        txtEnunciado =(TextView) findViewById(R.id.enunciado);
        txtEnunciadoInicio =(TextView) findViewById(R.id.textoInicio);
        btSiguiente=(Button) findViewById(R.id.btSiguiente);
        btIniciar=(Button) findViewById(R.id.btIniciarTest);
        radioGroup =(RadioGroup) findViewById(R.id.rgRespuestas);
        respuesta1=(RadioButton) findViewById(R.id.respuesta1);
        respuesta2=(RadioButton) findViewById(R.id.respuesta2);
        respuesta3=(RadioButton) findViewById(R.id.respuesta3);
        respuesta4=(RadioButton) findViewById(R.id.respuesta4);

        firebaseAuth=FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();


        youTubePlayerView =(YouTubePlayerView) findViewById(R.id.video_test_incial);
        youTubePlayerView.initialize(ConfiguracionYoutube.CLAVE_API,this);
        obVideOperacion= new VideoOperacion(firebaseAuth);

        obTest= new Test();
        obRespuesta = new Respuesta();
        obVideo = new Video();
        obUnidad = new Unidad();

        testPreguntasUnidad= new ArrayList<>();

        respuestaUnidad= new ArrayList<>();

        respuestaEstudiante =new ArrayList<>();

        pregunta=0;

        youTubePlayerView.setVisibility(View.VISIBLE);




    }

    private void recorrePregunta(DataSnapshot pregunta, ArrayList<Test> test){

        try {

            obTest.setEnunciado(pregunta.child(UtilsBottomNavigation.NODO_ENUNCIADO)
                    .getValue().toString());

            obRespuesta.setIdRespuesta(Integer.valueOf(pregunta.child(UtilsBottomNavigation
                    .NODO_RESPUESTA).getValue().toString()));
            obTest.setRespuesta(obRespuesta);
            obVideo.setIdVideo(pregunta.child(UtilsBottomNavigation.NODO_VIDEO)
                    .getValue().toString());
            obTest.setIdVideo(obVideo);
            test.add(obTest);

            Log.v("DATO1", obTest.getIdVideo().getIdVideo());
            Log.v("DATO1", obTest.getEnunciado());
            Log.v("DATO1", "" + obTest.getRespuesta().getIdRespuesta());

            obTest = new Test();
            obRespuesta = new Respuesta();
            obVideo = new Video();
            obUnidad = new Unidad();
        }catch (Exception e){
            Log.v("Una excep", e.getMessage().toString());
        }
    }

    private void recorreRespuesta(DataSnapshot pregunta, ArrayList<Respuesta> respuestas){

        obRespuesta.setIdRespuesta(Integer.valueOf(pregunta.getKey().toString()));
        obRespuesta.setNombreRespuesta(pregunta.getValue().toString());
        respuestas.add(obRespuesta);
        obRespuesta = new Respuesta();
    }

    private void iniciarDabaseReference(String nodo1,String nodo2,String unidad){
        databaseReference= obVideOperacion.buscarNodo(nodo1,unidad);
        databaseReferenceDos= obVideOperacion.buscarNodo(nodo2,unidad);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        this.player= youTubePlayer;
        if(!b){

            btIniciar.setVisibility(View.VISIBLE);

            //    player.loadVideo(testPreguntasUnidad1.get(pregunta).getIdVideo().getIdVideo());

            // youTubePlayer.loadVideo(testPreguntasUnidad1.get(0).getIdVideo().getIdVideo());
            // Log.v("OBJETOVIDEO5j",obVideo.getIdVideo());
            // preguntasUnidad1();
            // preguntasUnidad1();

        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if (youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,1).show();
        }else{

            Toast.makeText(getApplicationContext(),"Error al iniciar Youtube"
                    +youTubeInitializationResult.toString(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode==1){
            getYoutubeProvider().initialize(ConfiguracionYoutube.CLAVE_API,this);

        }
    }

    protected YouTubePlayer.Provider getYoutubeProvider(){
        return youTubePlayerView;
    }

    private void buscarPreguntas(ArrayList<Test> preguntas,ArrayList<Respuesta> respuestas
            ,int numeroPregunta, AtomicBoolean unidad,String unidadS){

        //almacena las respuestas
        try {

            if (numeroRespuestas == numeroPregunta+1) {
              //  pregunta = 0;
                //numeroRespuestas=0;
                validarPreguntasUnidad(unidadS);

            }else {


                arrayTemporal = new ArrayList<>();

                txtEnunciado.setText(preguntas.get(pregunta).getEnunciado());

                player.pause();
                player.loadVideo(preguntas.get(pregunta).getIdVideo().getIdVideo());

                int respuesta = preguntas.get(pregunta).getRespuesta().getIdRespuesta();


                for (int i = 0; i < respuestas.size(); i++) {

                    if (respuesta == respuestas.get(i).getIdRespuesta()) {
                        arrayTemporal.add(respuestas.get(i));
                        i = respuestas.size();

                    }

                }

                Collections.shuffle(respuestas);
                for (int i = 0; i < respuestas.size(); i++) {
                    if (!(respuesta == respuestas.get(i).getIdRespuesta())) {
                        arrayTemporal.add(respuestas.get(i));

                        if (arrayTemporal.size() == UtilsBottomNavigation.NUMERO_RESPUESTAS) {
                            i = respuestas.size();
                        }
                    }
                }

                Collections.shuffle(arrayTemporal);

                respuesta1.setText(arrayTemporal.get(0).getNombreRespuesta());
                respuesta2.setText(arrayTemporal.get(1).getNombreRespuesta());
                respuesta3.setText(arrayTemporal.get(2).getNombreRespuesta());
                respuesta4.setText(arrayTemporal.get(3).getNombreRespuesta());
                mostrarPreguntas();
                //unidad=false;
                Log.v("BOOL", "" + unidad.get());


            }


        }catch (Exception e){

        }
    }

    private void iniciarTest(){

        btIniciar.setVisibility(View.GONE);
        Collections.shuffle(testPreguntasUnidad); //ordena aleatoriamente el array de preguntas
        buscarPreguntas(testPreguntasUnidad,respuestaUnidad,
                numeroPreguntas,unidad1,
                UtilsBottomNavigation.NODO_UNIDAD_UNO); //Boton para iniciar las preguntas

    }

    private void mostrarPreguntas(){

        txtEnunciado.setVisibility(View.VISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
        btSiguiente.setVisibility(View.VISIBLE);
        youTubePlayerView.setVisibility(View.VISIBLE);
    }

    private void validarEscogido(){

        selecciono=radioGroup.getCheckedRadioButtonId();
        Log.v("selecciono", ""+selecciono);

    }

    private void guardarRespuestas(){

        if(testPreguntasUnidad.get(pregunta).getRespuesta().getIdRespuesta() == respuestaTexto) {
            obRespuesta.setCorrecta(true);
            respuestaEstudiante.add(obRespuesta);
            obRespuesta = new Respuesta();


            Toast.makeText(getApplicationContext(), "RESPUESTA CORRECTA", Toast.LENGTH_SHORT).show();
        }else {
            obRespuesta.setCorrecta(false);
            Toast.makeText(getApplicationContext(), "RESPUESTA INCORRECTA", Toast.LENGTH_SHORT).show();
            respuestaEstudiante.add(obRespuesta);
            obRespuesta = new Respuesta();
            incorrectas++;

        }

    }


    private void validarPreguntasUnidad(String unidadP){

        // Toast.makeText(getApplicationContext(),"INCORRECTAS "+incorrectas,Toast.LENGTH_LONG).show();

        Log.v("usuarionom",""+user.getUid());

        iniciarDabaseReference2(UtilsBottomNavigation.NODO_USUARIOS,user.getUid());
        Map<String, Object> datos = new HashMap<>();


        if (incorrectas>=(respuestaEstudiante.size()/2)+1){
              Toast.makeText(getApplicationContext(),"NO HAS PASADO EL TEST DE LA UNIDAD," +
                    " PRÁCTICA Y REPÍTELO",Toast.LENGTH_SHORT).show();
              Log.v("usuarionom",""+user.getDisplayName().toString());
              UtilsBottomNavigation.intentInicio(getApplicationContext(), MenuPrincipalActivity.class);
              finish();
            }else{

            int dato= UtilsBottomNavigation.actualizarAvance(unidadPasar);

            datos.put(UtilsBottomNavigation.NODO_NIVEL,dato);

              datos.put(unidadPasar, true);
              databaseReferenceTres.updateChildren(datos);
              Toast.makeText(getApplicationContext(),"HAS PASADO LA UNIDAD Y DESBLOQUEADO LA "+unidadPasar+"."+" Sigue Asi"
                    ,Toast.LENGTH_SHORT).show();
              UtilsBottomNavigation.intentInicio(getApplicationContext(), MenuPrincipalActivity.class);
              finish();

            }
        Log.v("usuarionom",""+user.getDisplayName().toString());
        incorrectas=0;


    }

    private void obtenerObjeto(){

        if (respuesta1.isChecked()){
            obRespuesta= arrayTemporal.get(0);
            respuestaTexto=arrayTemporal.get(0).getIdRespuesta();

        }else if (respuesta2.isChecked()){
            obRespuesta=arrayTemporal.get(1);
            respuestaTexto=arrayTemporal.get(1).getIdRespuesta();
        }else if (respuesta3.isChecked()){
            obRespuesta=arrayTemporal.get(2);
            respuestaTexto=arrayTemporal.get(2).getIdRespuesta();
        }else if (respuesta4.isChecked()){
            obRespuesta=arrayTemporal.get(3);
            respuestaTexto=arrayTemporal.get(3).getIdRespuesta();
        }

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


    private void iniciarDabaseReference2(String nodo1,String nodo2){

        databaseReferenceTres= FirebaseDatabase.getInstance().getReference().child(nodo1).child(nodo2);
        // databaseReferenceDos= obVideOperacion.buscarNodo(nodo2);

        // databaseReference= FirebaseDatabase.getInstance().getReference().child(nivel1);
        //return databaseReference;

    }


}
