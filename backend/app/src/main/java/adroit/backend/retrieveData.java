package adroit.backend;

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


    jsonConnection jsonClass = new jsonConnection();
    TextView tv;
    String ab;
    JSONObject jobjResp;





    static InputStream stream = null;
    static JSONObject jobj=null;
    static String json = "";


    public void changeJSON(){





    }



    @Override
    protected String doInBackground(String... arg0){


        try{

            jobjResp= requestJson("https://api.myjson.com/bins/36jy3");


        }catch(JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return ab;
    }



    public static JSONObject requestJson(String url)throws IOException, JSONException{

        stream = new URL(url).openStream();




        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
        json = readAll(reader);   //anropar metoden neranför som bugger en string av BufferedReadern
        jobj = new JSONObject(json); //skapar ett nytt JSON-objekt av stringen.





        Log.d("Vi kom in i metoden", "1" + json.toString());

        return jobj;

    }






    private static String readAll(Reader rd)throws IOException{
        StringBuilder sb = new StringBuilder();
        int count;
        while((count = rd.read()) != -1){
            sb.append((char) count);
        }




        return sb.toString();


    }












        protected void onPostExecute(String ab){
            MainActivity.setJson(jobj);
            myQuiz.setJson(jobj);
            createQuestion.setJson(jobj);
            updateData.setJSON(jobj);



            createQuestion.createJSON(); ///// SKA BORT SENARE
            Log.d("22222HERHE RJE", "fasfe" + jobj.toString());

        }
}