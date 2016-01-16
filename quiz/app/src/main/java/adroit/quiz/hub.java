package adroit.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class hub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        fillStats(); // metod som hämtar statistik om användaren och presenterar denna i ett texfält
        ((TextView) findViewById(R.id.textView7)).setVisibility(View.INVISIBLE); // Felmeddelande som görs osynligt varje gång aktiviten startar

    }

    String id = MainActivity.getId(); // Hämtar användarens id från förta aktiviteten

    static JSONObject jobj = new JSONObject(); // gör ett nytt jsonobjekt

    public static void setJson(JSONObject j){ //metod för att uppdatera jsonobjektet
        jobj= j;
    }


    public void changePage(View view){ // Byter till aktivitet quizMain

        Intent myIntent = new Intent(this, quizMain.class);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);

    }

    public void changeAppToBackend (View view) {
        // Metod för att starta upp backend appen. Om appen inte finns på enheten så
        // fångar catchen up detta och skriver ut ett meddelande till användaren
        try {
            Intent startBackend = getPackageManager().getLaunchIntentForPackage("adroit.backend");
            startActivity(startBackend);
        }
        catch (Exception e) {
            //Felmeddelande visas för användaren
            ((TextView) findViewById(R.id.textView7)).setText(getString(R.string.errorMessageHub));
            ((TextView) findViewById(R.id.textView7)).setVisibility(View.VISIBLE);
        }
    }



    public void changePageToMain(View view){ // Byter till aktivitet mainActivity

        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);

    }


    public void fillStats(){
        /*Statsen som visas fylls på*/
        //Texview för att visa användarnamnet Identifieras
        //TextViewn som ska visa statsen identifieras
        TextView userName = (TextView) findViewById(R.id.textView6);
        TextView corrView = (TextView)findViewById(R.id.textView3);

        try{
            // Json array med medlemar hämtas
            JSONArray membArr = jobj.getJSONArray("Members");
            // Temporärar hållare skapas
            String qA ="";
            String cA ="";

            //En loop snurrar igenom memberArr
            for(int i=0; i<membArr.length(); i++){
                //Json-objektet som ligger på pos i hämtas

                JSONObject tmpObj = membArr.getJSONObject(i);
                //Hämtar id och kollar om det är rätt id, om så är fallet så hämtas data ifrån det kontot.
                String userId = tmpObj.getString("UserID");
                if(userId.equals(id)){
                    userName.setText(tmpObj.getString("UserName"));
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
            String percent = formatter.format((cADouble/qADouble)*100);
            //I try-satsen nedan försöker vi göra om stringen till en double igen. Detta för att se om
            //uträkningen bildade ett tal. Om användaren inte har svarat på några frågor så kommer
            // uträkningen att bli error eftersom man försöker dela 0 med 0.
            try{
                double test = Double.parseDouble(percent);

            }catch(NumberFormatException e){
                //Om catchen fångar erroret så sätts stringen till "0"
                percent = "0";
            }

            //Sätter texten till det som ska visas
            corrView.setText("Questions answered\n" + qA + "\n\nRight answers percentage\n" + percent + "%");

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void onBackPressed()
    // Vid backPressed kommer en dialogruta som frågar om användaren vill logga ut
    {

        AlertDialog.Builder adbuilder = new AlertDialog.Builder(hub.this);
        adbuilder.setMessage("Are you sure you want to log out?");
        adbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // Om användaren trycker "yes" skickas denne vidare till aktivitet MainActivity
                Intent i = new Intent(hub.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0, 0);

            }
        });
        //Om användaren säger nej så stängs dialogrutan.
        adbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = adbuilder.create();
        alert.show();

    }




}
