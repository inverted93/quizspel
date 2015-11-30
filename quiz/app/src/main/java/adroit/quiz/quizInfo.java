package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class quizInfo extends AppCompatActivity {

    String quizTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_info);

        quizTitle = getIntent().getExtras().getString("QuizTitle");
        TextView quizTitel = (TextView) findViewById(R.id.quizTitel);
        quizTitel.setText(quizTitle);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        TextView infoText = (TextView) findViewById(R.id.infoText);
        //endast temporära, skapa inte strings
        if (quizTitle.equals("Star Wars")) {
            ratingBar.setRating(Float.parseFloat("5.0"));
            infoText.setText("It's a trap!");

        } else if (quizTitle.equals("Potato")) {
            ratingBar.setRating(Float.parseFloat("1.5"));
            infoText.setText("My life is potato");

        } else {}
    }




    public void changePage(View view){

        Bundle b = new Bundle();
        b.putString("QuizTitle", quizTitle);

        Intent myIntent = new Intent(this, play.class);
        myIntent.putExtras(b);


        startActivity(myIntent);


    }

    public void onBackPressed()
    {
        super.onBackPressed();

        Intent i = new Intent(this, quizMain.class);
        startActivity(i);
        finish();

    }


}
