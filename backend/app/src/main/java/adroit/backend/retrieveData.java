package adroit.backend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

public class retrieveData extends AsyncTask<String, String, String> {


    String ab;
    JSONObject jobjResp;

    static InputStream stream = null;
    static JSONObject jobj=null;
    static String json = "";

    @Override
    protected String doInBackground(String... arg0){

        try{
            //Metoden requestJson anropas med urln som arguemt
            jobjResp= requestJson("https://api.myjson.com/bins/2u5fd");

        }catch(JSONException e){
            Log.d("Inget internet", "1337");
            e.printStackTrace();
        }catch (IOException e) {
            Log.d("Inget internet", "794");
            e.printStackTrace();
        }

        return ab;
    }



    public static JSONObject requestJson(String url)throws IOException, JSONException{

        //En ny stream skapas, där input kommer ifrån urln (hosten)
        stream = new URL(url).openStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
        //anropar metoden neranför som bugger en string av BufferedReadern
        json = readAll(reader);
        //skapar ett nytt JSON-objekt av stringen.
        jobj = new JSONObject(json);

        // Returnerar jobj
        return jobj;

    }

    private static String readAll(Reader rd)throws IOException{

        //Metoden används för att läsa inputen ifrån hosten
        StringBuilder sb = new StringBuilder();
        int count;
        while((count = rd.read()) != -1){
            sb.append((char) count);
        }
        //Returnerar stringen
        return sb.toString();


    }

        protected void onPostExecute(String ab){
            //Här skickas den nya json ut till alla klasser som behöver använda det.
            MainActivity.setJson(jobj);
            myQuiz.setJson(jobj);
            createQuestion.setJson(jobj);
            updateData.setJSON(jobj);
            hub.setJson(jobj);



        }
}