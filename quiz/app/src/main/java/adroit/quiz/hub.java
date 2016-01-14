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

        TextView userName = (TextView) findViewById(R.id.textView6);
        TextView corrView = (TextView)findViewById(R.id.textView3);

        try{

            JSONArray membArr = jobj.getJSONArray("Members");
            String qA ="";
            String cA ="";

            for(int i=0; i<membArr.length(); i++){

                JSONObject tmpObj = membArr.getJSONObject(i);
                String userId = tmpObj.getString("UserID");
                if(userId.equals(id)){
                    userName.setText(tmpObj.getString("UserName"));
                    qA = tmpObj.getString("QuestionsAnswered");
                    cA = tmpObj.getString("RightAnswers");
                }
            }

            double qADouble = Double.parseDouble(qA);
            double cADouble = Double.parseDouble(cA);

            NumberFormat formatter = new DecimalFormat("#0");

            String percent = formatter.format((cADouble/qADouble)*100);

            corrView.setText("Questions answered\n" + qA + "\n\nRight answers percentage\n" + percent + "%");

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void onBackPressed() // Vid backPressed kommer en dialogruta som frågar om användaren vill logga ut
    {

        AlertDialog.Builder adbuilder = new AlertDialog.Builder(hub.this);
        adbuilder.setMessage("Are you sure you want to log out?");
        adbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                // Om användaren tryck "yes" skickas denne vidare till aktivitet MainActivity
                Intent i = new Intent(hub.this, MainActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0, 0);

            }
        });
        adbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = adbuilder.create();
        alert.show();

    }




}
