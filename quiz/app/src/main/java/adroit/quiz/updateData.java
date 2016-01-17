package adroit.quiz;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by axelholm on 2015-12-14.
 */



public class updateData extends AsyncTask<String, String, String>{

    //Klassen används för att uppdatera json hos hosten

    //Ett nytt jsonobjekt skapas
    static JSONObject jobj = new JSONObject();


    public static void setJson(JSONObject j){
        //Anropas av klasser som vill uppdatera sina json
        jobj = j;

    }


    @Override
    protected String doInBackground(String... arg0){
        //Metoden update körs i bakgrunden när updateData skapas.
        update(jobj);
        return "";
    }


    public static void update(JSONObject j){



        try{
            //Länken till hosten skapas
            URL u = new URL("https://api.myjson.com/bins/2u5fd");
            //En anslutning upprättas
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            //Argument för anslutningen sätts, bl.a. PUT eftersom vi vill uppdatera json.
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            //En writer skapas och skrivs till. Här uppdateras datan hos hosten .
            //Den töms och stängs när den använts
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(j.toString());
            writer.flush();
            writer.close();

            Log.d("1121423432" + conn.getRequestMethod(), "HEj");
            String response =conn.getInputStream().toString();
            Log.d("Svar: "+ response, "Okej");

        }catch(MalformedURLException e){

            System.out.println("Det blev fel, Malformed URL");
            e.printStackTrace();

        }catch(IOException e){
            System.out.println("IOException i updateData");
            e.printStackTrace();
        }

        MainActivity.runRetrieve();

    }


}