package adroit.backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;


public class hub extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        fillStats();
    }

    String id = "1";
    static JSONObject jobj = new JSONObject();

    public static void setJson(JSONObject j){
        jobj= j;
    }


    public void fillStats(){

        Log.d("Rangers", "Conquers WestHam");
        TextView totView = (TextView)findViewById(R.id.textView10);
        TextView corrView = (TextView)findViewById(R.id.textView13);

        try{

            JSONArray membArr = jobj.getJSONArray("Members");
            String qA ="";
            String cA ="";

            for(int i=0; i<membArr.length(); i++){

                JSONObject tmpObj = membArr.getJSONObject(i);
                String userId = tmpObj.getString("UserID");
                if(userId.equals(id)){

                    qA = tmpObj.getString("QuestionsAnswered");
                    cA = tmpObj.getString("RightAnswers");

                }

            }

            totView.setText(qA);
            corrView.setText(cA + "%");


        }catch(JSONException e){
            e.printStackTrace();
        }






    }








    public void changePage(View v){

        Intent myIntent = new Intent(this, createQuiz.class);

        startActivity(myIntent);

    }

    public void changePageMyQuiz(View v) {

        Intent myIntent = new Intent(this, myQuiz.class);
        startActivity(myIntent);

    }












}
