package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class results extends AppCompatActivity {

    String quizTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results2);

        quizTitle = getIntent().getExtras().getString("QuizTitle");

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

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Bundle b = new Bundle();
        b.putString("QuizTitle", quizTitle);

        Intent i = new Intent(this, quizInfo.class);
        i.putExtras(b);

        startActivity(i);
        finish();

    }

}
