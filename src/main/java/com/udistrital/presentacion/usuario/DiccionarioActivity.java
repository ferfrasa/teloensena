package com.udistrital.presentacion.usuario;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.udistrital.R;
import com.udistrital.Utils.UtilsBottomNavigation;
import com.udistrital.adatos.Video;
import com.udistrital.negocio.VideoAdaptador;
import com.udistrital.negocio.VideoLoader;
import com.udistrital.presentacion.InicioSesionActivity;

import java.util.ArrayList;
import java.util.List;

public class DiccionarioActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Video>> {
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;

    public static final String LOG_TAG = DiccionarioActivity.class.getName();
    public String palabra;
    private VideoAdaptador librosAdaptador;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diccionario);

        firebaseAuth= FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();

        fireAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = firebaseAuth.getCurrentUser();

                if (!(user!=null&& user.isEmailVerified())){
                    goLogInScreen();
                }

            }
        };


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                UtilsBottomNavigation.seleccionMenuBottom(item,getApplicationContext());
                finish();

                return true;
            }
        });




        palabra=" ";
        // cargadorLista();

        final EditText palabraBuscar = (EditText) findViewById(R.id.palabraBuscar);
        final Button botonBuscar = (Button) findViewById(R.id.botonBuscar);
        //    botonBuscar.
        botonBuscar.performClick();


        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if(palabraBuscar.getText().toString().isEmpty()){
                //   Toast toast = Toast.makeText(getApplicationContext(),"Ingresa una palabra a buscar",Toast.LENGTH_SHORT);
                // toast.show();
                //}else {

                palabra= palabraBuscar.getText().toString();
                System.out.println("escribio"+palabra);


                cargadorLista();

                // }
            }
        });

        librosAdaptador = new VideoAdaptador(this, new ArrayList<Video>());
        ListView listaLibro = (ListView) findViewById(R.id.list);

        listaLibro.setEmptyView(mEmptyStateTextView);

        listaLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Video objetoEq = librosAdaptador.getItem(position);
                //  Uri urlPage = Uri.parse(objetoEq.getUrl());

                // Intent intecion = new Intent(Intent.ACTION_VIEW, urlPage);
                //startActivity(intecion);

                Intent intent = new Intent(getApplicationContext(), DetalleActivity.class);
                intent.putExtra("video", objetoEq);
                startActivity(intent);




            }
        });

        listaLibro.setAdapter(librosAdaptador);
        //    cargadorLista();




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

    public void cargadorLista(){


        //se crea la variable LoaderManager para poder utilizar los cargadores
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        ConnectivityManager connMgr = (ConnectivityManager)

                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {

            getLoaderManager().restartLoader(0, null, this);
            LoaderManager cargardor = getLoaderManager();
            Log.i(LOG_TAG, "Test metodo initLoader()");
            cargardor.initLoader(1, null, this);

            View loadingIndicator = findViewById(R.id.emptyprog);
            loadingIndicator.setVisibility(View.GONE);


        }else{

            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            //  View loadingIndicator = findViewById(R.id.emptyprog);
            // loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText("No hay conexion");

        }



    }

    @Override
    public Loader<List<Video>> onCreateLoader(int id, Bundle args) {
        Log.i(LOG_TAG,"Test metodo onCreateLoader()");

        return new VideoLoader(this,palabra);
    }

    @Override
    public void onLoadFinished(Loader<List<Video>> loader, List<Video> data) {
        Log.i(LOG_TAG,"Test metodo onLoadFinished()");





        //  View loadingIndicator = findViewById(R.id.emptyprog);
        //loadingIndicator.setVisibility(View.GONE);



        if (data.isEmpty()) {
            mEmptyStateTextView.setText("NO SE ENCUNTRAN RESULTADOS");
            librosAdaptador.clear(); // actualizar en pantalla el adapater
        }


        // Clear the adapter of previous earthquake data
        librosAdaptador.clear(); // actualizar en pantalla el adapater

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        if (data != null && !data.isEmpty()) {

            librosAdaptador.addAll(data);
        }
    }

    //  View loadingIndicator = findViewById(R.id.emptyprog);
    // loadingIndicator.setVisibility(View.GONE);


    @Override
    public void onLoaderReset(Loader<List<Video>> loader) {

        Log.i(LOG_TAG,"Test metodo onLoaderReset()");

        // Loader reset, so we can clear out our existing data.
        librosAdaptador.clear();


    }




}
