package adroit.backend;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
                Log.d("RIP" + idInt, "fritid");
                JSONObject quizObj = quizArray.getJSONObject(i);
                Log.d("RIP" + idInt, "fritid" + quizObj.toString());


                String name = quizObj.getString("Name"); //Hittar namnet
                String id = quizObj.getString("UserID"); //Hittar id
                Log.d("RIP" + idInt, "fritid" + id);
                if(idInt.equals(id)){
                    //Lagger bara till de som har samma id
                    games.add(name);
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }






        gameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);

                if(position %2 == 1)
                {
                    view.setBackgroundColor(getResources().getColor(R.color.textviewBG));
                }
                else
                {
                    view.setBackgroundColor(getResources().getColor(R.color.hubuserstatusBorder));
                }
                return view;
            }
        };
        ListView gameList = (ListView) findViewById(R.id.gameList);
        gameList.setAdapter(gameAdapter);


        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                String s = String.valueOf(parent.getItemAtPosition(position));
                String str ="";
                String rating="";
                String rated="";
                String played="";
                String key="";

                try{
                    JSONArray quiz = json.getJSONArray("Quiz");
                    for(int i=0; i<quiz.length(); i++){
                        JSONObject info = quiz.getJSONObject(i);
                        String cmpr = info.getString("Name");
                        //Log.d("101", ": " + cmpr);                     //BORT
                        if(cmpr.equals(s)){
                            str = info.getString("Description");
                            rating = info.getString("Rating");
                            rated = info.getString("Rated");
                            played = info.getString("Played");
                            key = info.getString("QID");

                            //Log.d("101", "hej");
                            //Log.d("101", ":" + str + rating + rated + played + key);
                        }else{

                        }
                    }
                }catch(JSONException e){

                    e.printStackTrace();

                }




                Bundle b = new Bundle();
                b.putString("QuizTitle", s);
                b.putString("QuizDesc", str);
                b.putString("QuizRating", rating);
                b.putString("QuizRated", rated);
                b.putString("QuizPlayed", played);
                b.putString("QuizID", key);

                Intent myIntent = new Intent(view.getContext(), backendQuizInfo.class);
                myIntent.putExtras(b);
                startActivity(myIntent);
            }




        });








    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i = new Intent(this, hub.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);

    }

}
