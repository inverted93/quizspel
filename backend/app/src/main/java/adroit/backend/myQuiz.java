package adroit.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class myQuiz extends AppCompatActivity {


    ArrayList<String> games = new ArrayList<String>();
    ArrayAdapter <String> gameAdapter;

    static JSONObject json;
    String idInt = MainActivity.getId();

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
                String id = quizObj.getString("UserID");
                if(idInt.equals(id)){

                    games.add(name);

                }

            }


        }catch(JSONException e){
            e.printStackTrace();
        }


        gameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games);
        ListView gameList = (ListView) findViewById(R.id.gameList);
        gameList.setAdapter(gameAdapter);


        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                String s = String.valueOf(parent.getItemAtPosition(position));

                Bundle b = new Bundle();
                b.putString("QuizTitle", s);
                Intent myIntent = new Intent(view.getContext(), hub.class);
                myIntent.putExtras(b);
                startActivity(myIntent);
            }




        });








    }
}
