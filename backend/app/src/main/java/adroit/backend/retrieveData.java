package adroit.backend;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class retrieveData extends AsyncTask<String, String, String> {


    jsonConnection jsonClass = new jsonConnection();
    TextView tv;
    String ab;
    JSONObject jobj;

    JSONArray memberArr;


    public void changeJSON(){





    }



    @Override
    protected String doInBackground(String... arg0){

        updateData.update();

        try{


            //HashMap<String, String> params = new HashMap<>();
           //params.put("firstName", "Axel");
            //params.put("lastName", "Holm");


            //ftp.getFtp();


            //urlConnect.changeData();

            //HttpResponse e = updateData.updateShit("https://api.myjson.com/bins/3lyn9"); // temp json

            jobj= jsonConnection.requestJson("https://api.myjson.com/bins/1vp4j");







            Log.d("DrontTest", jobj.toString());


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
            createQuestion.setJSON(jobj);

        }
}