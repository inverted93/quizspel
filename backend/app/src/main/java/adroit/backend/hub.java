package adroit.backend;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class hub extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        //Ett infomeddelande sätts till osynligt
        ((TextView) findViewById(R.id.textView7)).setVisibility(View.INVISIBLE);
        //En metod som fyller statsen i hub anropas
        fillStats();
    }

    //id till den som är inloggad hämtas ifrån MainActivity
    String id = MainActivity.getId();

    static JSONObject jobj = new JSONObject();
    // Json objektet skickas ifrån retreiveData.
    public static void setJson(JSONObject j){
        jobj= j;
    }

    public void changeAppToTheQuiz (View view) {
        // Metod för att starta upp backend appen. Om appen inte finns på enheten så
        // fångar catchen up detta och skriver ut ett meddelande till användaren
        try {
            Intent startBackend = getPackageManager().getLaunchIntentForPackage("adroit.quiz");
            startActivity(startBackend);
        }
        catch (Exception e) {
            //Felmeddelande visas
            ((TextView) findViewById(R.id.textView7)).setText(getString(R.string.errorMessageHub));
            ((TextView) findViewById(R.id.textView7)).setVisibility(View.VISIBLE);

        }
    }


    public void fillStats(){
        /*Statsen som visas fylls på*/
        //TextViewn som ska visa statsen identifieras
        TextView corrView = (TextView)findViewById(R.id.textView3);

        try{
            //Json arrayen members hämtas
            JSONArray membArr = jobj.getJSONArray("Members");
            //Temporära behållare skapas
            String qA ="";
            String cA ="";

            //En loop snurrar igenom memberArr
            for(int i=0; i<membArr.length(); i++){
                //Json-objektet som ligger på pos i hämtas
                JSONObject tmpObj = membArr.getJSONObject(i);
                //Hämtar id och kollar om det är rätt id, om så är fallet så hämtas data ifrån det kontot.
                String userId = tmpObj.getString("UserID");
                if(userId.equals(id)){
                    qA = tmpObj.getString("QuestionsAnswered");
                    cA = tmpObj.getString("RightAnswers");
                }
            }
            //Statsen parsas om till doubles
            double qADouble = Double.parseDouble(qA);
            double cADouble = Double.parseDouble(cA);

            //Skapar en formaterare som inte visar några decimaler
            NumberFormat formatter = new DecimalFormat("#0");
            //Räknar ut hur många procent rätt användaren har svarat
            String percent = formatter.format((cADouble / qADouble) * 100);

            try{
                double test = Double.parseDouble(percent);
            }catch(NumberFormatException e){
                percent = "0";
            }

            //Sätter texten till det som ska visas
            corrView.setText("Questions answered\n" + qA + "\n\nRight answers percentage\n" + percent + "%");

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void changePage(View v){
        //Byter aktivitet till createQuiz
        Intent myIntent = new Intent(this, createQuiz.class);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void changePageMyQuiz(View v) {
        //Byter aktivitet till myQuiz
        Intent myIntent = new Intent(this, myQuiz.class);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);
    }


    public void changePageToMain(View view){
        //Byter aktivitet till MainActivity
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);

    }

    public void onBackPressed()
    {
        //Om användaren trycker på tillbaka knappen så ställs frågan "Vill du logga ut?"
        //Om användaren trycker ja så skickas användaren tillbaka till MailActivity
        AlertDialog.Builder adbuilder = new AlertDialog.Builder(hub.this);
        adbuilder.setMessage("Are you sure you want to log out?");
        adbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                Intent i = new Intent(hub.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0, 0);

            }
        });
        //Om användaren säger nej så avslutas dialogrutan.
        adbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = adbuilder.create();
        alert.show();

    }





}
