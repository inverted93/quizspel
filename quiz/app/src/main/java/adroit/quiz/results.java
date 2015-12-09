package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class results extends AppCompatActivity {

    String quizTitle;
    String quizDesc;
    String quizRating;
    String quizRated;
    String quizPlayed;
    String quizID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //moveTaskToBack(true);
        setContentView(R.layout.activity_results2);

        double score = getIntent().getExtras().getDouble("Score");

        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(1);

        TextView scoreView = (TextView) findViewById(R.id.editText2);
        scoreView.setText("Your score: " + nf.format(score));

        //Lägg in rating här, skall endast gå att rate:a en gång per användare.

    }

    public void changePageToQuizInfo(View view){

        Bundle b = new Bundle();
        b.putString("QuizTitle", quizTitle);

        Intent i = new Intent(this, quizInfo.class);
        i.putExtras(b);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);

    }


    @Override
    public void onBackPressed()
    {

        quizTitle = getIntent().getExtras().getString("QuizTitle");
        quizDesc = getIntent().getExtras().getString("QuizDesc");
        quizRating = getIntent().getExtras().getString("QuizRating");
        quizRated = getIntent().getExtras().getString("QuizRated");
        quizPlayed = getIntent().getExtras().getString("QuizPlayed");
        quizID = getIntent().getExtras().getString("QuizID");

        super.onBackPressed();
        Bundle b = new Bundle();
        b.putString("QuizTitle", quizTitle);
        b.putString("QuizDesc", quizDesc);
        b.putString("QuizRating", quizRating);
        b.putString("QuizRated", quizRated);
        b.putString("QuizPlayed", quizPlayed);
        b.putString("QuizID", quizID);

        Intent i = new Intent(this, quizInfo.class);
        i.putExtras(b);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);

    }

}
