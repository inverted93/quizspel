package adroit.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class createQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
    }


    public void changePage(View v){


        Intent myIntent = new Intent(this, hub.class);


        startActivity(myIntent);





    }

    EditText question;

    EditText ans1;
    EditText ans2;
    EditText ans3;
    EditText ans4;



    public void readLines(View v){

        question = (EditText) findViewById(R.id.editText5);

        ans1 = (EditText) findViewById(R.id.editText6);
        ans2 = (EditText) findViewById(R.id.editText7);
        ans3 = (EditText) findViewById(R.id.editText8);
        ans4 = (EditText) findViewById(R.id.editText9);


        String questionString = question.getText().toString();
        String ans1String = question.getText().toString();
        String ans2String = question.getText().toString();
        String ans3String = question.getText().toString();
        String ans4String = question.getText().toString();



        if(questionString.isEmpty()||ans1String.isEmpty()||ans2String.isEmpty()||ans3String.isEmpty()){


            Log.d("True", "1");


        }else{

            Log.d("false", "1");

        }








    }





}
