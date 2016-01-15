package adroit.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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


    //Denna metoden hämtar ut värden från Bundle som skickades med från den tidigare aktivitetet (quizMain)
    //Sedan sätts dessa värden i separata textrutor för att visa relevant information om spelet
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

            //Nedan parsas Stringen om till en double för att den sedan
            // ska kunna omvandlas till en int. Detta eftersom den kommer i formatet
            // ##.# och det inte ser snyggt ut i layouten om den förblir i det formatet.
            Double quizRatedTmp = Double.parseDouble(quizRated);
            int quizRatedInt = quizRatedTmp.intValue();

            quizTitel.setText(quizTitle);
            desc.setText(quizDesc);
            ratingBar.setRating(Float.parseFloat(quizRating));
            playedNumber.setText(quizPlayed);
            votedNumber.setText(""+quizRatedInt);
    }

    //Skickar vidare värdena till nästa aktivitet (play)
    public void changePage(View view){

        Bundle b = new Bundle();
        b.putString("QuizTitle", quizTitle);
        b.putString("QuizDesc", quizDesc);
        b.putString("QuizRating", quizRating);
        b.putString("QuizRated", quizRated);
        b.putString("QuizPlayed", quizPlayed);
        b.putString("QuizID", quizID);

        Intent myIntent = new Intent(this, play.class);
        myIntent.putExtras(b);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);

    }


    public void onBackPressed() // Skickar en vidare till föregående aktivitet
    {
        super.onBackPressed();

        Intent i = new Intent(this, quizMain.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

}
