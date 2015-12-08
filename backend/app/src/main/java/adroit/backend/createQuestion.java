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


    public void getText(View v){

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
            questArr.put(questTmp);

            ansTmp.put("ans1", "" + ans1String);
            ansTmp.put("ans2", "" + ans2String);
            ansTmp.put("ans3", "" + ans3String);
            ansTmp.put("ans4", "" + ans4String);
            ansArr.put(pc, ansTmp);


            JSONObject tmp;
//
            tmp = ansArr.getJSONObject(1);


            Log.d("HEJSANSVEJSAN", "1:" + tmp.getString("ans1") + tmp.getString("ans2") + tmp.getString("ans3") + tmp.getString("ans4") + tmp.getString("ans5"));






            if(questionString.isEmpty()||ans1String.isEmpty()||ans2String.isEmpty()||ans3String.isEmpty()){  //Borjan pa en checkIfEmptyMetod
                Log.d("True", "1");
            }else{
                Log.d("false", "1");
            }





        }catch(JSONException e){

        }


    }



    public void fillFields(){



    }


    public void Create(){



    }
























    public void changePage(View v){ //Ska bort sen


        Intent myIntent = new Intent(this, hub.class);


        startActivity(myIntent);
    }









}
