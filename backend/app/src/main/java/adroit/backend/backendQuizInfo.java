package adroit.backend;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class backendQuizInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend_quiz_info);
        setQuizInfo();
    }


    String quizHeader;
    String quizDesc;
    String quizRating;
    String quizRated;
    String quizPlayed;
    String quizID;


    public void setQuizInfo(){

        TextView quizTitle = (TextView) findViewById(R.id.textView5);
        TextView description = (TextView) findViewById(R.id.textView7);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        TextView playedNumber = (TextView) findViewById(R.id.textView);
        TextView votedNumber = (TextView) findViewById(R.id.textView2);


        quizHeader = getIntent().getExtras().getString("QuizTitle");
        quizDesc = getIntent().getExtras().getString("QuizDesc");
        quizRating = getIntent().getExtras().getString("QuizRating");
        quizRated = getIntent().getExtras().getString("QuizRated");
        quizPlayed = getIntent().getExtras().getString("QuizPlayed");
        quizID = getIntent().getExtras().getString("QuizID");


        quizTitle.setText(quizHeader);
        description.setText(quizDesc);
        ratingBar.setRating(Float.parseFloat(quizRating));
        playedNumber.setText(quizPlayed);
        votedNumber.setText(quizRated);








    }
}
