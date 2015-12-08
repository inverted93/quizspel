package adroit.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class createQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
    }



    static JSONObject questTmp = new JSONObject();
    static JSONObject ansTmp = new JSONObject();

    static JSONArray questArr = new JSONArray();
    static JSONArray ansArr = new JSONArray();


    public static void fillList(){

        try{





            questTmp.put("id", "1 ");
            questTmp.put("name", "Axel");

            questArr.put(0, questTmp);

            questTmp.put("id", "2 ");
            questTmp.put("name", "Mattis");

            questArr.put(1, questTmp);


            questTmp.put("id", "3 ");
            questTmp.put("name", "Holm");

            questArr.put(2, questTmp);

            //for(int i=0;i<6;i++){}


            JSONObject tmp;
            for(int j=0;j<questArr.length();j++){

                tmp = questArr.getJSONObject(j);
                String s = tmp.getString("id");
                String s2 = tmp.getString("name");
                Log.d("Vi kom helt fram:" + s2, ":909:" + s + j);

            }



        }catch(JSONException e){

        }


    }


    public void changePage(View v){ //Ska bort sen


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
