package com.udistrital.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.udistrital.adatos.Video;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by erika on 1/04/18.
 */

public final class JSONUtils {

    public static final String LOG_TAG = JSONUtils.class.getSimpleName();




    private static final URL crearURL(String palabra){

        // String UrlJson = "https://www.googleapis.com/books/v1/volumes?q="+palabra;
        String UrlJson = "https://teloensena.firebaseio.com/diccionario." +
                "json?orderBy=\"$key\"&startAt=\""+palabra+"\"&print=pretty";
        System.out.println("la url es "+UrlJson);


        URL url = null;

        try {
            url = new URL(UrlJson);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the URL ", e);

        }
        return url;

    }

    private static final String conexionURL(URL urlJson) throws IOException {

        HttpURLConnection urlConnection= null;
        InputStream inputStream = null;
        String lecturaJSON= "";

        try {

            urlConnection= (HttpURLConnection) urlJson.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            lecturaJSON= leerInput(inputStream);


        }catch (IOException e){

        }finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return lecturaJSON;
    }

    private static final String leerInput(InputStream inputStream) throws IOException{

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }

    private static final List<Video> extraerDatosJSON(String jsonDato) throws JSONException {


        if (TextUtils.isEmpty(jsonDato)) {
            return null;
        }

        List<Video> listaLibro = new ArrayList<Video>();
        try {
            //JSONArray jsonArray = new JSONArray(jsonDato);

            JSONObject jsonObject = new JSONObject(jsonDato);
            // JSONArray jsonArray = jsonObject.getJSONArray(" ");

            //recorrer array guardado
            Iterator<String> iter = jsonObject.keys();


            while (iter.hasNext()) {
                String key = iter.next();
                try {

                    // Object value = json.get(key);


                    //JSONObject objetoJSON = jsonObject.getJSONObject(i);
                    //   Object value = json.get(key);
                    JSONObject propiedades = (JSONObject) jsonObject.get(key);

                    //JSONObject propiedades = objetoJSON.getJSONObject("volumeInfo");


                    String autor="";


                    String nombre=propiedades.optString("nombre");
                    System.out.println(nombre);
                    String id =propiedades.optString("id");
                    System.out.println(id);



                    Video book= new Video(nombre, id);
                    System.out.println("LO CREO");

                    listaLibro.add(book);
                    System.out.println("lo agrego");









                } catch (JSONException e) {
                    // Something went wrong!
                }
            }








        }catch (JSONException e){

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);



        }
        return listaLibro;
    }

    public static ArrayList<Video> retornoLibro(String urlD) {


        // Create URL object
        URL url = crearURL(urlD);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = conexionURL(url);



            // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
            List<Video> libroArray = extraerDatosJSON(jsonResponse);

            // Return the list of {@link Earthquake}s




            return (ArrayList<Video>) libroArray;

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }catch (JSONException e){
            System.out.println(e);
        }
        return null;
    }




}
