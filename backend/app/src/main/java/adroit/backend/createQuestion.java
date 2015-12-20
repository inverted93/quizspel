package adroit.backend;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class createQuestion extends AppCompatActivity {


    String quizTitle;
    String quizDesc;
    static JSONObject jobj;

    RadioGroup radioGruppen;
    RadioButton radioKnappen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        addListener();
        getStrings();
    }

    public void addListener(){

        radioGruppen = (RadioGroup) findViewById(R.id.radioFacit);

        /*Button prevButton = (Button) findViewById(R.id.button8);
        Button doneButton = (Button) findViewById(R.id.button4);
        Button newButton = (Button) findViewById(R.id.button5);*/

        radioGruppen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGruppen, int checkedId) {
                Log.d("DET TRYCKTES EN KNAPP", "23    " + checkedId);

            }
        });
    }


    public int getRadio(){

        int id=0;

        RadioButton r1 = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton r2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton r3 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton r4 = (RadioButton) findViewById(R.id.radioButton4);

        if(r1.isPressed()){
            id = R.id.radioButton1;
        }
        if(r2.isPressed()){
            id = R.id.radioButton2;
        }
        if(r3.isPressed()){
            id = R.id.radioButton3;
        }
        if(r4.isPressed()){
            id = R.id.radioButton4;
        }






        return id;
    }





    public static void setJSON(JSONObject j){

        jobj= j;
    }


    EditText question;
    EditText ans1;
    EditText ans2;
    EditText ans3;
    EditText ans4;


    String questionString;
    String ans1String;
    String ans2String;
    String ans3String;
    String ans4String;


    JSONArray questArr = new JSONArray();
    JSONArray ansArr = new JSONArray();

    int pc=1; // PageCount


    public void getText(){

            try {

                getStrings();


                Log.d("1:" + questionString, ":2:" + ans1String + ans2String + ans3String + ans4String);

                JSONObject questTmp = new JSONObject();
                JSONObject ansTmp = new JSONObject();

                question.getText().clear();
                ans1.getText().clear();
                ans2.getText().clear();
                ans3.getText().clear();
                ans4.getText().clear();

                questTmp.put("question", "" + questionString);
                questArr.put(pc, questTmp);

                ansTmp.put("ans1", "" + ans1String);
                ansTmp.put("ans2", "" + ans2String);
                ansTmp.put("ans3", "" + ans3String);
                ansTmp.put("ans4", "" + ans4String);
                ansArr.put(pc, ansTmp);

            } catch (JSONException e) {

                e.printStackTrace();
            }
    }






    public void fillFields(){

      getStrings();


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



            Log.d("909", " 808" + a1 + a2 + a3 + a4);

        }catch(JSONException e){
            e.printStackTrace();
        }





    }


    public void Create(){



    }




    public void getStrings(){

        quizTitle = getIntent().getExtras().getString("title");  //Hamtar titeln ifran sidan innan
        quizDesc = getIntent().getExtras().getString("description"); //Hamtar description ifran sidan innan
        Log.d("1", "3" + quizTitle + quizDesc);

        question = (EditText) findViewById(R.id.editText5);
        ans1 = (EditText) findViewById(R.id.editText6);
        ans2 = (EditText) findViewById(R.id.editText7);
        ans3 = (EditText) findViewById(R.id.editText8);
        ans4 = (EditText) findViewById(R.id.editText9);

        questionString = question.getText().toString();
        ans1String = ans1.getText().toString();
        ans2String = ans2.getText().toString();
        ans3String = ans3.getText().toString();
        ans4String = ans4.getText().toString();


    }

    public void clearHint(){

        question.setHint("");
        ans1.setHint("");
        ans2.setHint("");
        ans3.setHint("");
        ans4.setHint("");


    }


    public Boolean checkIfEmpty(){


        getStrings();
        Boolean answer = false;

        if(questionString.equals("")||ans1String.equals("")||ans2String.equals("")||ans3String.equals("")||ans4String.equals("")){

            Log.d("Fyll i alla ", "falt");

            if(questionString.equals("")){
                question.setHint("Fill this out");
            }
            if(ans1String.equals("")){
                ans1.setHint("Fill this out");
            }
            if(ans2String.equals("")){
                ans2.setHint("Fill this out");
            }
            if(ans3String.equals("")){
                ans3.setHint("Fill this out");
            }
            if(ans4String.equals("")){
                ans4.setHint("Fill this out");
            }

        }else {
            answer = true;
        }
        return answer;
    }




    public void next(View v){


        int i = getRadio();
        Log.d("Whebpiufabjnjkads", "Okej" + i);
        getStrings();
        Boolean ok = checkIfEmpty();

        if(ok==true){

            clearHint();
            getText();
            pc=pc+1;//Addar 1 till pagecount
            fillFields();

            Button prevButton = (Button) findViewById(R.id.button8);
            prevButton.setVisibility(View.VISIBLE);


        }

    }



    public void prev(View v){
        clearHint();
        getStrings();
        getText();
        pc = pc - 1; //tar bort 1 fran pagecount
        fillFields();


        if(pc==1){
            Button prevButton = (Button) findViewById(R.id.button8);
            prevButton.setVisibility(View.INVISIBLE);
        }


    }





    public void done(View v){

        next(v);

        /*try{
            JSONArray quizArr = jobj.getJSONArray("Quiz");
            JSONArray questionArr = jobj.getJSONArray("Question");
            JSONArray answerArr = jobj.getJSONArray("Answer");


        }catch(JSONException e){
            e.printStackTrace();

        }*/





        Intent myIntent = new Intent(this, hub.class);
        startActivity(myIntent);
    }









}
