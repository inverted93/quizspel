package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class quizMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);


        String[] games = {"Star Wars", "Potato-quiz", "Stromstad", "Horses", "Dogs", "WW2", "Trump"};
        ListAdapter gameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games);
        ListView gameList = (ListView) findViewById(R.id.gameList);
        gameList.setAdapter(gameAdapter);

    }


    public void changePage(View view){



        Intent myIntent = new Intent(this, quizInfo.class);


        startActivity(myIntent);


    }



}
