package adroit.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class createQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);
    }

    public void changePage(View v){ //SKA BORT



    }





    EditText nameLine;
    EditText descLine;

    String nameLineString;
    String descLineString;
    //JSONObject jobj = retrievedata.getJson();
    TextView felMed;

    public void readLines(View v){

        felMed = (TextView)findViewById(R.id.textView6);

        nameLine = (EditText) findViewById(R.id.editText3);
        descLine = (EditText) findViewById(R.id.editText4);

        nameLineString = nameLine.getText().toString();  //Here´s the string
        descLineString = descLine.getText().toString();  //Here´s the string

        Log.d( nameLineString,  descLineString);

        felMed.setVisibility(View.INVISIBLE);



        // firstName    lastName

        if(nameLineString.equals("")||descLineString.equals("")){

            Log.d("De har gick inte sa bra", " eller hur ");
            felMed.setVisibility(View.VISIBLE);


        }else{

            Intent myIntent = new Intent(this, createQuestion.class);
            startActivity(myIntent);
        }








    }





    public String getStrings(){ //Kanske ska goras pa annat satt

        String s = nameLineString + ";" + descLineString;

        return s;
    }





}
