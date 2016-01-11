package adroit.quiz;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by axelholm on 2015-12-14.
 */



public class updateData extends AsyncTask<String, String, String>{




    static JSONObject jobj = new JSONObject();


    public static void setJson(JSONObject j){

        jobj = j;

    }


    @Override
    protected String doInBackground(String... arg0){

        update(jobj);
        return "";
    }


    public static void update(JSONObject j){



        try{
            URL u = new URL("https://api.myjson.com/bins/58xch");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(j.toString());
            writer.flush();
            writer.close();

            Log.d("1121423432" + conn.getRequestMethod(), "HEj");
            String response =conn.getInputStream().toString();
            Log.d("Svar: "+ response, "Okej");

        }catch(MalformedURLException e){

            System.out.println("Fuck");
            e.printStackTrace();

        }catch(IOException e){
            System.out.println("Fuck2");
            e.printStackTrace();
        }

        MainActivity.runRetrieve();

    }



    public static void createJson(JSONArray quizArr){





    }






   /* private static String readAll(Reader rd)throws IOException{
        StringBuilder sb = new StringBuilder();
        int count;
        while((count = rd.read()) != -1){
            sb.append((char) count);

        }
        return sb.toString();


    }*/









}