package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class quizMain extends AppCompatActivity {

    boolean switcher;
    ArrayList<String> games = new ArrayList<String>();
    ArrayAdapter<String> gameAdapter;

    static JSONObject jsonResponse = new JSONObject();

    public static void setJson(JSONObject json){

        jsonResponse = json;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);


        /*JSONArray quizArr = jsonResponse.getJSONArray("Quiz");
        JSONObject test = quizArr.getJSONObject(0);
        String stringen = test.getString("Name");*/


        try {
            JSONArray cast = jsonResponse.getJSONArray("Quiz");
            for (int i = 0; i < cast.length(); i++) {
                JSONObject actor = cast.getJSONObject(i);
                String name = actor.getString("Name");
                games.add(name);
            }
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        gameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games);
        ListView gameList = (ListView) findViewById(R.id.gameList);
        gameList.setAdapter(gameAdapter);

        EditText inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                quizMain.this.gameAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = String.valueOf(parent.getItemAtPosition(position));
                String str = "";
                String rating = "";
                String rated = "";
                String played = "";
                String key = "";

                try {
                    JSONArray quiz = jsonResponse.getJSONArray("Quiz");
                    for (int i = 0; i < quiz.length(); i++) {
                        JSONObject info = quiz.getJSONObject(i);
                        String check = info.getString("Name");
                        if (check.equals(s)) {
                            //JSONObject desc = quiz.getJSONObject(i);
                            str = info.getString("Description");
                            rating = info.getString("Rating");
                            rated = info.getString("Rated");
                            played = info.getString("Played");
                            key = info.getString("QID");
                        }else{}
                    }
                }catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                Bundle b = new Bundle();
                b.putString("QuizTitle", s);
                b.putString("QuizDesc", str);
                b.putString("QuizRating", rating);
                b.putString("QuizRated", rated);
                b.putString("QuizPlayed", played);
                b.putString("QuizID", key);

                Intent myIntent = new Intent(view.getContext(), quizInfo.class);
                myIntent.putExtras(b);
                startActivity(myIntent);
            }
        });

    }

    public void sortList (View view) {

        ListView gameList = (ListView) findViewById(R.id.gameList);

        if (switcher == false) {
            Collections.sort(games, new SortingComparatorAsc());
            gameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games);
            gameList.setAdapter(gameAdapter);
            switcher = true;
        } else {
            Collections.sort(games, new SortingComparatorDsc());
            gameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games);
            gameList.setAdapter(gameAdapter);
            switcher = false;
        }
    }

}

    class SortingComparatorAsc implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);
        }
    }

    class SortingComparatorDsc implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return -s1.compareTo(s2);
        }
    }