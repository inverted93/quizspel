package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class quizInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_info);

        String s = getIntent().getExtras().getString("Some Key");
        TextView quizTitel = (TextView) findViewById(R.id.quizTitel);
        quizTitel.setText(s);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        TextView infoText = (TextView) findViewById(R.id.infoText);
        if (s.equals("Star Wars")) {
            ratingBar.setRating(Float.parseFloat("5.0"));
            infoText.setText("It's a trap!");

        } else if (s.equals("Potato")) {
            ratingBar.setRating(Float.parseFloat("1.5"));
            infoText.setText("My life is potato");

        } else {}
    }




    public void changePage(View view){



        Intent myIntent = new Intent(this, play.class);


        startActivity(myIntent);


    }


}
