package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class hub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
    }


    public void changePage(View view){

        Intent myIntent = new Intent(this, play.class);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);

    }


    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);

    }




}
