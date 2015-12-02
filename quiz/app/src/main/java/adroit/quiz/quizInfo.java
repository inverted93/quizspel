package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class quizInfo extends AppCompatActivity {

    String quizTitle;
    String quizDesc;
    String quizRating;
    String quizRated;
    String quizPlayed;
    String quizID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_info);
        setInfo();
    }


    public void setInfo(){

        TextView quizTitel = (TextView) findViewById(R.id.quizTitel);
        TextView desc = (TextView) findViewById(R.id.quizDesc);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        TextView playedNumber = (TextView) findViewById(R.id.playedNumber);
        TextView votedNumber = (TextView) findViewById(R.id.votedNumber);

        quizTitle = getIntent().getExtras().getString("QuizTitle");
        quizDesc = getIntent().getExtras().getString("QuizDesc");
        quizRating = getIntent().getExtras().getString("QuizRating");
        quizRated = getIntent().getExtras().getString("QuizRated");
        quizPlayed = getIntent().getExtras().getString("QuizPlayed");
        quizID = getIntent().getExtras().getString("QuizID");

        quizTitel.setText(quizTitle);
        desc.setText(quizDesc);
        ratingBar.setRating(Float.parseFloat(quizRating));
        playedNumber.setText(quizPlayed);
        votedNumber.setText(quizRated);

    }


    public void changePage(View view){

        Bundle b = new Bundle();
        b.putString("QuizID", quizID);
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
