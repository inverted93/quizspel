package adroit.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class hub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        ((TextView) findViewById(R.id.textView7)).setVisibility(4);

    }


    public void changePage(View view){

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
            ((TextView) findViewById(R.id.textView7)).setVisibility(0);
        }
    }

    public void changePageToMain(View view){

        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);

    }

    public void onBackPressed()
    {

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
        adbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = adbuilder.create();
        alert.show();

    }




}
