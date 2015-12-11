package adroit.quiz;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class play extends AppCompatActivity {

    // Quiz information som skickas med bundle till resultat som
    // bla skickar dessa till quizinfo igen om OnBacknuttonpressed
    String quizTitle;
    String quizDesc;
    String quizRating;
    String quizRated;
    String quizPlayed;
    String quizID;

    static boolean isVisible = false;

    private CountDownTimer CountDown;
    double correctAnswers = 0;
    int answerNumber = 3;
    int numberOfQuestions = 0;
    int questionNr = 0;
    int secondsLeft = 0;
    TextView countDownText;
    ArrayList<String> userAnswers = new ArrayList<>();
    List<String> questionsArr = new ArrayList<>();
    List<String> answers = new ArrayList<>();
    List<String> guiAnswers = new ArrayList<>();
    List<String> rightAnswers = new ArrayList<>();

    static JSONObject jsonResponse = new JSONObject();

    public static void setJson(JSONObject json){

        jsonResponse = json;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        quizDesc = getIntent().getExtras().getString("QuizDesc");
        quizRating = getIntent().getExtras().getString("QuizRating");
        quizRated = getIntent().getExtras().getString("QuizRated");
        quizPlayed = getIntent().getExtras().getString("QuizPlayed");
        quizID = getIntent().getExtras().getString("QuizID");
        quizTitle = getIntent().getExtras().getString("QuizTitle");

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
                }
            }
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String firstQuestion = questionsArr.get(questionNr);
        ((TextView) findViewById(R.id.editText3)).setText(firstQuestion);
        numberOfQuestions = questionsArr.size();
        ((ProgressBar)findViewById(R.id.progressBar)).setMax(numberOfQuestions);

        for(int i = 0 ; i <4 ; i++) {
            String itemAnswer = answers.get(i);
            guiAnswers.add(itemAnswer);

        }

        countDownText = (TextView) findViewById(R.id.editText4);

        final ListAdapter answersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, guiAnswers);
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
                if (questionNr >= (numberOfQuestions -1)){
                    this.cancel();
                    changePage();
                }
                else {
                    countDownText.setText("Time left: 0");
                    nextQuestion("");
                    answersList.setAdapter(null);
                    answersList.setAdapter(answersAdapter);
                    this.start();
                }
            }

        }.start();

        answersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(questionNr >= (numberOfQuestions-1)) {
                    CountDown.cancel();
                    changePage();
                } else {
                    String choosenAnswer = String.valueOf(parent.getItemAtPosition(position));

                    nextQuestion(choosenAnswer);

                    answersList.setAdapter(null);
                    answersList.setAdapter(answersAdapter);

                    CountDown.cancel();
                    CountDown.start();
                }

            }
        });

    }

    public void nextQuestion(String questionAnswer) {
        Log.d("nextQueston", "10");
        userAnswers.add(questionAnswer);

        int nrOfAnswers = answerNumber + 4; // gör 4 till class variabel typ nrOfQuestionsVisible eller guiAnswers.size();
        guiAnswers.clear();
        for(int i = answerNumber ; i < nrOfAnswers ; i++) {
            Log.d("innan get", "1");
            String guiAnswer = answers.get(i);
            Log.d("Efter get", "2");
            guiAnswers.add(guiAnswer);
            Log.d("efter add", "3");
        }
        answerNumber = answerNumber + 4;

        questionNr++;

        String questionText = questionsArr.get(questionNr);
        ((TextView) findViewById(R.id.editText3)).setText(questionText);
        ((ProgressBar)findViewById(R.id.progressBar)).setProgress(questionNr);

    }

    public void changePage() {
        Log.d("changePage", "1");
        if(isVisible){
            userAnswers.add("");

        for(int i = 0; i < numberOfQuestions; i++) {
            if ((userAnswers.get(i)).equals(rightAnswers.get(i))){
                correctAnswers++;
            }
        }

            Log.d("changePage", "2");
            double scorePercentage = correctAnswers / (double) numberOfQuestions;

            Bundle b = new Bundle();
            b.putDouble("Score", scorePercentage);
            b.putString("QuizTitle", quizTitle);
            b.putString("QuizDesc", quizDesc);
            b.putString("QuizRating", quizRating);
            b.putString("QuizRated", quizRated);
            b.putString("QuizPlayed", quizPlayed);
            b.putString("QuizID", quizID);

            Intent myIntent = new Intent(this, results.class);
            myIntent.putExtras(b);
            startActivity(myIntent);
            finish();
            overridePendingTransition(0, 0);
            Log.d("changePage", "3");
        }
    }

   @Override
   public void onBackPressed()
   {
       AlertDialog.Builder abuilder = new AlertDialog.Builder(play.this);
       abuilder.setMessage("Do you want to end this quiz?");
       abuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

           public void onClick(DialogInterface dialog, int which) {
               CountDown.cancel();
               Bundle b = new Bundle();
               b.putString("QuizTitle", quizTitle);
               b.putString("QuizDesc", quizDesc);
               b.putString("QuizRating", quizRating);
               b.putString("QuizRated", quizRated);
               b.putString("QuizPlayed", quizPlayed);
               b.putString("QuizID", quizID);
               Intent i = new Intent(play.this, quizInfo.class);
               i.putExtras(b);
               startActivity(i);
               finish();
               overridePendingTransition(0, 0);
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


    @Override
    public void onResume()
    {
        super.onResume();
        isVisible = true;

        if(questionNr >= (numberOfQuestions-1)) {
            changePage();
        }
    }


    @Override
    public void onPause()
    {
        super.onPause();
        isVisible = false;
    }


}

