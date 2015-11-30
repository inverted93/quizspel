package adroit.quiz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class play extends AppCompatActivity {

    String quizTitle;
    private CountDownTimer CountDown;
    double correctAnswers = 3;
    int numberOfQuestions = 8;
    int questionNr = 0;
    int secondsLeft = 0;
    TextView countDownText;
    ArrayList<String> userAnswers = new ArrayList<>();
    //List<String> answers = Arrays.asList("Ett jävla pack", "Rimliga", "Höger", "Kompetenta", "Häftiga", "Snygga", "Intellektuella");
    List<String> answers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        quizTitle = getIntent().getExtras().getString("QuizTitle");

        answers.add("Ett jävla pack");
        answers.add("Rimliga");
        answers.add("Höger");
        answers.add("Kompetenta");
        answers.add("Häftiga");
        answers.add("Snygga");
        answers.add("Intellektuella");

        ((ProgressBar)findViewById(R.id.progressBar)).setMax(numberOfQuestions);

        countDownText = (TextView) findViewById(R.id.editText4);

        final ListAdapter answersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answers);
        final  ListView answersList = (ListView) findViewById(R.id.answersList);
        answersList.setAdapter(answersAdapter);

        CountDown = new CountDownTimer(8000, 100) {


            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    countDownText.setText("Time left: " + secondsLeft);

                }
            }

            public void onFinish() {
                countDownText.setText("Time left: 0");
                nextQuestion("apa");
                answersList.setAdapter(null);
                answersList.setAdapter(answersAdapter);
                if (questionNr >= numberOfQuestions){
                    this.cancel();
                    changePage();
                }
                else {
                    this.start();
                }
            }

        }.start();

        answersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String choosenAnswer = String.valueOf(parent.getItemAtPosition(position));

                nextQuestion(choosenAnswer);

                answersList.setAdapter(null);
                answersList.setAdapter(answersAdapter);

                CountDown.cancel();

                if(questionNr >= numberOfQuestions) {
                    changePage();
                }
                else {
                    CountDown.start();
                }

            }
        });

    }

    public void nextQuestion(String questionAnswer) {

        userAnswers.add(questionAnswer);

        answers.clear();
        answers.add("Jonas Mamma");

        ((TextView) findViewById(R.id.editText3)).setText("Bytt fråga");

        questionNr++;
        ((ProgressBar)findViewById(R.id.progressBar)).setProgress(questionNr);



    }

    public void changePage(){

        double scorePercentage = correctAnswers / (double)numberOfQuestions;

        Bundle b = new Bundle();
        b.putDouble("Score", scorePercentage);
        b.putString("QuizTitle", quizTitle);

        Intent myIntent = new Intent(this, results.class);
        myIntent.putExtras(b);

        startActivity(myIntent);


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        CountDown.cancel();
        finish();

    }

}