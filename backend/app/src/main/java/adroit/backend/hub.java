package adroit.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;


public class hub extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
    }



    public void changePage(View v){

        Intent myIntent = new Intent(this, createQuiz.class);


        startActivity(myIntent);


    }

    public void changePageMyQuiz(View v) throws IOException, JSONException{

        //Intent myIntent = new Intent(this, myQuiz.class);     DETTA SKA TILLBAKA SEN
        //startActivity(myIntent);

        System.out.println("Vi kom till hubben");

        JSONObject json = jsonConnection.readJsonFromUrl("https://www.dropbox.com/s/834wejq3i3w302v/test.json?dl=0");

        System.out.println("Rad:   " + json.toString());
        System.out.println("ID:    " + json.get("id"));

        jsonConnection.testMetod();






    }






}
