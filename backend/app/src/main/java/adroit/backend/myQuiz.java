package adroit.backend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class myQuiz extends AppCompatActivity {


    ArrayList<String> games = new ArrayList<String>();
    ArrayAdapter <String> gameAdapter;

    static JSONObject json;

    public static void setJson(JSONObject j){
        json = j;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quiz);

        try{

            JSONArray quizArray = json.getJSONArray("Quiz");
            for(int i = 0; i<quizArray.length();i++){
                JSONObject quizObj = quizArray.getJSONObject(i);
                String name = quizObj.getString("Name");
                games.add(name);
                Log.d("1. ", "a" + name);

            }


        }catch(JSONException e){
            e.printStackTrace();
        }









    }
}
