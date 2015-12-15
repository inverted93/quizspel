package adroit.quiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
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
        ListView gameList = (ListView) findViewById(R.id.gameList);
        gameList.setAdapter(new GameAdapter(this));

        /*JSONArray quizArr = jsonResponse.getJSONArray("Quiz");
        JSONObject test = quizArr.getJSONObject(0);
        String stringen = test.getString("Name");*/


        /*try {
            JSONArray cast = jsonResponse.getJSONArray("Quiz");
            for (int i = 0; i < cast.length(); i++) {
                JSONObject actor = cast.getJSONObject(i);
                String name = actor.getString("Name");
                games.add(name);
            }
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/


        /*gameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games);
        ListView gameList = (ListView) findViewById(R.id.gameList);
        gameList.setAdapter(gameAdapter);*/

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
                finish();
                overridePendingTransition(0, 0);
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

    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i = new Intent(this, hub.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);

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



    class SingleRow{
        String title;
        String rating;
        SingleRow(String title, String rating){
            this.title=title;
            this.rating=rating;
        }
    }


    class GameAdapter extends BaseAdapter{

        static JSONObject jsonResponse = new JSONObject();
        public static void setJson(JSONObject json){
            jsonResponse = json;
        }

        ArrayList<SingleRow> list;
        Context context;
        GameAdapter(Context c){
            context = c;
            list = new ArrayList<SingleRow>();

            //Här ska värdena för listan laddas in
            ArrayList<String> games = new ArrayList<String>();
            ArrayList<String> ratings = new ArrayList<String>();

            try {
                JSONArray cast = jsonResponse.getJSONArray("Quiz");
                for (int i = 0; i < cast.length(); i++) {
                    JSONObject actor = cast.getJSONObject(i);
                    String name = actor.getString("Name");
                    String rating = actor.getString("Rating");
                    games.add(name);
                    ratings.add(rating);


                }
            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("Här har spelen laddats", ";");
            int listSize = ratings.size();
            for (int i = 0; i < listSize; i++) {
                Log.d("1", ratings.get(i));
            }

            String[] title = new String[games.size()];
            title = games.toArray(title);

            /*try {
                JSONArray cast = jsonResponse.getJSONArray("Quiz");
                for (int i = 0; i < cast.length(); i++) {
                    JSONObject actor = cast.getJSONObject(i);
                    String rating = actor.getString("Rating");
                    ratings.add(rating);
                }
            }catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            String[] rating = new String[ratings.size()];
            rating = ratings.toArray(rating);
            //till hit

            for(int i=0; i<games.size(); i++){
                list.add(new SingleRow(title[i], rating[i]));
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.single_row, parent, false);

            TextView title = (TextView) row.findViewById(R.id.textView2);
            RatingBar rating = (RatingBar) row.findViewById(R.id.ratingBar2);

            SingleRow temp = list.get(position);

            Log.d("111111111", "2" + temp.rating);

            title.setText(temp.title);
            rating.setRating(Float.parseFloat(temp.rating));

            return row;
        }
    }