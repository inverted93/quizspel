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








    JSONArray questArr = new JSONArray();
    JSONArray ansArr = new JSONArray();

    int pc=1; // PageCount


    public void getText(){

        EditText question = (EditText) findViewById(R.id.editText5);

        EditText ans1 = (EditText) findViewById(R.id.editText6);
        EditText ans2 = (EditText) findViewById(R.id.editText7);
        EditText ans3 = (EditText) findViewById(R.id.editText8);
        EditText ans4 = (EditText) findViewById(R.id.editText9);

        try{


        String questionString = question.getText().toString();
        String ans1String = ans1.getText().toString();
        String ans2String = ans2.getText().toString();
        String ans3String = ans3.getText().toString();
        String ans4String = ans4.getText().toString();

            Log.d("1:" +questionString, ":2:" + ans1String + ans2String + ans3String + ans4String);





            JSONObject questTmp = new JSONObject();
            JSONObject ansTmp = new JSONObject();

            questTmp.put("question", "" + questionString);
            questArr.put(pc, questTmp);



               ansTmp.put("ans1", "" + ans1String);
               ansTmp.put("ans2", "" + ans2String);
               ansTmp.put("ans3", "" + ans3String);
               ansTmp.put("ans4", "" + ans4String);
               ansArr.put(pc, ansTmp);





            Log.d("1", "909");



            question.getText().clear();
            ans1.getText().clear();
            ans2.getText().clear();
            ans3.getText().clear();
            ans4.getText().clear();



        }catch(JSONException e){

            e.printStackTrace();
        }


    }



    public void fillFields(){

        EditText question = (EditText) findViewById(R.id.editText5);

        EditText ans1 = (EditText) findViewById(R.id.editText6);
        EditText ans2 = (EditText) findViewById(R.id.editText7);
        EditText ans3 = (EditText) findViewById(R.id.editText8);
        EditText ans4 = (EditText) findViewById(R.id.editText9);


        try{

            JSONObject tmpAns;
            JSONObject tmpQuest;

            tmpAns = ansArr.getJSONObject(pc);
            tmpQuest = questArr.getJSONObject(pc);

            String s = tmpQuest.getString("question");
            String a1 = tmpAns.getString("ans1");
            String a2 = tmpAns.getString("ans2");
            String a3 = tmpAns.getString("ans3");
            String a4 = tmpAns.getString("ans4");





            question.setText(s);
            ans1.setText(a1);
            ans2.setText(a2);
            ans3.setText(a3);
            ans4.setText(a4);



            Log.d("909" ,  " 808" + a1 + a2 + a3 + a4);

        }catch(JSONException e){
            e.printStackTrace();
        }





    }


    public void Create(){



    }







    public void next(View v){

        //if(questArr.length()==pc){

            getText();



        //}else{}

        pc=pc+1; //Addar 1 till pagecount

    }


    public void prev(View v){

        getText();
        pc= pc-1; //tar bort 1 fran pagecount
        fillFields();

    }





    public void done(View v){


        Intent myIntent = new Intent(this, hub.class);
        startActivity(myIntent);
    }









}
