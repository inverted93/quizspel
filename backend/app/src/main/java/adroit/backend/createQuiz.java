package adroit.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

public class createQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
    }

    public void changePage(View v){ //SKA BORT

        Intent myIntent = new Intent(this, createQuestion.class);

        startActivity(myIntent);

    }

    EditText nameLine;
    EditText descLine;

    String nameLineString;
    String descLineString;
    JSONObject jobj = retrievedata.getJson();

    public void readLines(View v){


        nameLine = (EditText) findViewById(R.id.editText3);
        descLine = (EditText) findViewById(R.id.editText4);

        nameLineString = nameLine.getText().toString();
        descLineString = descLine.getText().toString();

        Log.d( nameLineString,  descLineString);


        // firstName    lastName







    }






}
