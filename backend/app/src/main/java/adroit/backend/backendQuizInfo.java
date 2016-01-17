package adroit.backend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class backendQuizInfo extends Activity {

   // @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend_quiz_info);
        setQuizInfo();
    }

    //Behållare av typen string
    String quizHeader;
    String quizDesc;
    String quizRating;
    String quizRated;
    String quizPlayed;
    String quizID;


    public void setQuizInfo() {
        //Hämtar de olika elementen i aktiviteten
        TextView quizTitle = (TextView) findViewById(R.id.textView5);
        TextView description = (TextView) findViewById(R.id.textView7);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        TextView playedNumber = (TextView) findViewById(R.id.textView);
        TextView votedNumber = (TextView) findViewById(R.id.textView2);

        //Hämtar datan som ska in på sidan ifrån tidigare sida.
        quizHeader = getIntent().getExtras().getString("QuizTitle");
        quizDesc = getIntent().getExtras().getString("QuizDesc");
        quizRating = getIntent().getExtras().getString("QuizRating");
        quizRated = getIntent().getExtras().getString("QuizRated");
        quizPlayed = getIntent().getExtras().getString("QuizPlayed");
        quizID = getIntent().getExtras().getString("QuizID");

        double quizRatedDouble = Double.parseDouble(quizRating);
        NumberFormat formatter = new DecimalFormat("#0");
        String quizRatedFinal = formatter.format(quizRatedDouble);


        //Sätter texten i elementen
        quizTitle.setText(quizHeader);
        description.setText(quizDesc);
        ratingBar.setRating(Float.parseFloat(quizRating));
        playedNumber.setText(quizPlayed);
        votedNumber.setText(quizRatedFinal);


    }

    @Override
    public void onBackPressed()
    {
        //När användaren trycker på tillbaka knappen så skickas han eller hon
        //tillbaka till myQuiz
        super.onBackPressed();

        Intent i = new Intent(this, myQuiz.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);

    }

}
