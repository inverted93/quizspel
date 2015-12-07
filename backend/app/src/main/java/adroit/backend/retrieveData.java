package adroit.backend;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class retrieveData extends AsyncTask<String, String, String> {


    jsonConnection jsonClass = new jsonConnection();
    TextView tv;
    String ab;
    JSONObject jobj;

    JSONArray memberArr;


    @Override
    protected String doInBackground(String... arg0){

        try{
            jobj= jsonConnection.requestJson("https://api.myjson.com/bins/1vp4j");

        }catch(JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            memberArr = jobj.getJSONArray("Members");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return ab;
    }

        protected void onPostExecute(String ab){
            MainActivity.setJson(jobj);
            myQuiz.setJson(jobj);

        }
}