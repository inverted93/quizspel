package adroit.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class play extends AppCompatActivity {

    String quizTitle;
    String quizID;
    private CountDownTimer CountDown;
    double correctAnswers = 3;
    int numberOfQuestions = 8;
    int questionNr = 0;
    int secondsLeft = 0;
    TextView countDownText;
    ArrayList<String> userAnswers = new ArrayList<>();
    //List<String> answers = Arrays.asList("Ett jävla pack", "Rimliga", "Höger", "Kompetenta", "Häftiga", "Snygga", "Intellektuella");
    List<String> questionsArr = new ArrayList<>();
    List<String> answers = new ArrayList<>();
    List<String> rightAnswers = new ArrayList<>();

    static JSONObject jsonResponse = new JSONObject();

    public static void setJson(JSONObject json){

        jsonResponse = json;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        quizID = getIntent().getExtras().getString("QuizID");
        quizTitle = getIntent().getExtras().getString("QuizTitle");

        //(NY JSONArray "Question")if(QID == "1") getString("qText") questions.add; getString("QueID") QueIDArr.add;
        //(NY JSONArray "Answers") getString("QueID") tempAnswers.add; if(tempAnswers == QueIdArr) AnswersID.add;
        // if(AnswersID == QueID) Answers.add(); MÅSTE ÄVEN KOLL VILKEN SOM ÄR RÄTT!!! //lagra rätt var i annan array för jämförelse
        // lägg in try/catch

        try {
            JSONArray jQuestions = jsonResponse.getJSONArray("Question");
            JSONArray jAnswers = jsonResponse.getJSONArray("Answer");
            for (int i = 0; i < jQuestions.length(); i++) {
                JSONObject info = jQuestions.getJSONObject(i);
                String check = info.getString("QID");
                if (check.equals(quizID)) {
                    String question = info.getString("qText");
                    String QueID = info.getString("QueID");
                    questionsArr.add(question);
                    for (int i2 = 0; i2 < jAnswers.length(); i2++) {
                        JSONObject info2 = jAnswers.getJSONObject(i2);
                        String check2 = info2.getString("QueID");
                        if (QueID.equals(check2)) {
                            String answer = info2.getString("aText");
                            answers.add(answer);
                            String rAnswer = info2.getString("rightAnswer");
                            if (rAnswer.equals("true")) {
                                rightAnswers.add(answer);
                            }
                        }
                    }

                    //questionsArr.add(question);
                }
            }
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String firstQuestion = questionsArr.get(0);
        ((TextView) findViewById(R.id.editText3)).setText(firstQuestion);
        //answers.add("Ett jävla pack");
        //answers.add("Rimliga");
        //answers.add("Höger");
        //answers.add("Kompetenta");
        //answers.add("Häftiga");
        //answers.add("Snygga");
        //answers.add("Intellektuella");

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

        //answers.clear();
        //answers.add("Jonas Mamma");

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
        AlertDialog.Builder abuilder = new AlertDialog.Builder(play.this);
        abuilder.setMessage("Do you want to end this quiz?");
        abuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                CountDown.cancel();
                //Bundle b = new Bundle();
                //b.putString("QuizTitle", quizTitle);
                Intent i = new Intent(play.this, quizInfo.class);
                //i.putExtras(b);
                startActivity(i);
                finish();
            }
        });
        abuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = abuilder.create();
        alert.show();
        
    }

   }