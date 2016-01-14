package adroit.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class results extends AppCompatActivity {

    String quizTitle;
    String quizDesc;
    String quizRating;
    String quizRated;
    String quizPlayed;
    String quizID;

    static JSONObject jobj = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //moveTaskToBack(true);
        setContentView(R.layout.activity_results2);

        double score = getIntent().getExtras().getDouble("Score");

        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);

        TextView scoreView = (TextView) findViewById(R.id.editText2);
        scoreView.setText(nf.format(score));
        setScoreColor(score);

        Log.d("Today", "Is shit create");
        updateStats();
        //Lägg in rating här, skall endast gå att rate:a en gång per användare.

    }

    public static void setJson(JSONObject j){
        jobj = j;

    }

    String loginId = "1";
    String correctAnswersStringDouble;
    String answeredQuestionsString;
    String played = "1";

    public void createJsonMembers(JSONArray membArr){

        try{

            JSONArray questArr = jobj.getJSONArray("Question");
            JSONArray answerArr = jobj.getJSONArray("Answer");
            JSONArray quizArr = jobj.getJSONArray("Quiz");

            JSONObject finalObj = new JSONObject();
            finalObj.put("Quiz", quizArr);
            finalObj.put("Question", questArr);
            finalObj.put("Answer", answerArr);
            finalObj.put("Members", membArr);

            updateData.setJson(finalObj);
            MainActivity.runUpdate();
            MainActivity.runRetrieve();

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void updateStats(){

        loginId = MainActivity.getId();
        String quizId = getIntent().getExtras().getString("QuizID");
        correctAnswersStringDouble = getIntent().getExtras().getString("corrA");
        double correctAnswersDouble = Double.parseDouble(correctAnswersStringDouble);
        int correctAnswersInt = (int) correctAnswersDouble;
        answeredQuestionsString = getIntent().getExtras().getString("nrOQ");
        int answeredQuestions = Integer.parseInt(answeredQuestionsString);

        try{
            JSONArray membArr = jobj.getJSONArray("Members");
            JSONArray quizArr = jobj.getJSONArray("Quiz");

            Log.d("Today ", "is shit" + correctAnswersInt);
            Log.d("Today ", "is shit" + answeredQuestions);
            JSONObject newMemberObj = new JSONObject();

            for(int i=0; i < membArr.length(); i++){

                JSONObject tmpMembArr = membArr.getJSONObject(i);
                String id = tmpMembArr.getString("UserID");

                if(loginId.equals(id)){
                    Log.d("Miami", "is da shit Stats");

                    String qAOld = tmpMembArr.getString("QuestionsAnswered");
                    String cAOld = tmpMembArr.getString("RightAnswers");
                    int answeredQuestionOld = Integer.parseInt(qAOld);
                    int correctAnswersOld = Integer.parseInt(cAOld);

                    int qA = answeredQuestionOld + answeredQuestions;
                    int cA = correctAnswersInt + correctAnswersOld;

                    newMemberObj.put("UserID", id);
                    newMemberObj.put("Email", tmpMembArr.getString("Email"));
                    newMemberObj.put("Password", tmpMembArr.getString("Password"));
                    newMemberObj.put("UserName", tmpMembArr.getString("UserName"));
                    newMemberObj.put("QuestionsAnswered", ""+qA);
                    newMemberObj.put("RightAnswers", ""+cA);

                    membArr.put(i, newMemberObj);
                    createJsonMembers(membArr);

                }
                    JSONObject tmpQuizArr = quizArr.getJSONObject(i);
                    String quizIdFromJson = tmpQuizArr.getString("QID");

                if(quizId.equals(quizIdFromJson)){

                    JSONObject newQuizObj = new JSONObject();

                    String quizPlayed = getIntent().getExtras().getString("QuizPlayed");
                    int playedInt = Integer.parseInt(quizPlayed);
                    int playedIntFinal = playedInt +1;
                    played = ""+playedIntFinal;
                    Log.d("Miami", "den metoden" + playedIntFinal);

                    newQuizObj.put("QID", quizId);
                    newQuizObj.put("Name", getIntent().getExtras().getString("QuizTitle"));
                    newQuizObj.put("Description", getIntent().getExtras().getString("QuizDesc"));
                    newQuizObj.put("Rating", getIntent().getExtras().getString("QuizRating"));
                    newQuizObj.put("Rated", getIntent().getExtras().getString("QuizRated"));
                    newQuizObj.put("Played", ""+playedIntFinal);
                    newQuizObj.put("UserID", tmpQuizArr.getString("UserID"));
                    newQuizObj.put("Creationdate", tmpQuizArr.getString("Creationdate"));

                    quizArr.put(i, newQuizObj);
                    createJson(quizArr);

                    Log.d("Miami", "den metoden" + playedIntFinal + newQuizObj.toString());
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }


    public void rateQuiz(View view){

        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar);
        double usersRate = bar.getRating();

        String quizId = getIntent().getExtras().getString("QuizID");

        JSONObject tmpObj = new JSONObject();

        try{

            JSONArray quizArr = jobj.getJSONArray("Quiz");
            //quizArr

            for(int i=0;i<quizArr.length();i++){

                tmpObj = quizArr.getJSONObject(i);
                String id = tmpObj.getString("QID");
                if(id.equals(quizId)){

                    Log.d("Miami", "is da shit Rating");
                    String ratingString = tmpObj.getString("Rating");
                    String numbOfRatingsString = tmpObj.getString("Rated");
                    double rating = Double.parseDouble(ratingString);
                    double numbOfRatings = Double.parseDouble(numbOfRatingsString);
                    double numbOfRatingsFinal = numbOfRatings +1;
                    String numbOfRatingsFinalString = "" +numbOfRatingsFinal;

                    Double sum = (rating * numbOfRatings + usersRate)/numbOfRatingsFinal;
                    String summa = "" + sum;

                    Log.d("Rating", "tja lol" + sum);
                    Log.d("Rating", "He" + rating + numbOfRatings + usersRate);

                    JSONObject newQuizObj = new JSONObject();

                    newQuizObj.put("QID", id);
                    newQuizObj.put("Name", getIntent().getExtras().getString("QuizTitle"));
                    newQuizObj.put("Description", getIntent().getExtras().getString("QuizDesc"));
                    newQuizObj.put("Rating", summa);
                    newQuizObj.put("Rated", numbOfRatingsFinalString);
                    newQuizObj.put("Played", played);
                    newQuizObj.put("UserID", tmpObj.getString("UserID"));
                    newQuizObj.put("Creationdate", tmpObj.getString("Creationdate"));

                    quizArr.put(i, newQuizObj);
                }
                    String playedString = tmpObj.getString("Played");
            }
            createJson(quizArr);
            backToHub(view);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }


    public void createJson(JSONArray quizArr){

        try{

            JSONArray questArr = jobj.getJSONArray("Question");
            JSONArray answerArr = jobj.getJSONArray("Answer");
            JSONArray membersArr = jobj.getJSONArray("Members");

            JSONObject finalObj = new JSONObject();
            finalObj.put("Quiz", quizArr);
            finalObj.put("Question", questArr);
            finalObj.put("Answer", answerArr);
            finalObj.put("Members", membersArr);

            Log.d("Miami", "Sucks" + finalObj.toString());

            updateData.setJson(finalObj);
            MainActivity.runUpdate();
            MainActivity.runRetrieve();

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void setScoreColor (double score){
        if(score >= 90) {
            ((TextView) findViewById(R.id.editText2)).setTextColor(Color.GREEN);
        }
        if (score <= 30){
            ((TextView) findViewById(R.id.editText2)).setTextColor(Color.RED);
        }

    }



    public void changePageToQuizInfo(View view){

        quizTitle = getIntent().getExtras().getString("QuizTitle");
        quizDesc = getIntent().getExtras().getString("QuizDesc");
        quizRating = getIntent().getExtras().getString("QuizRating");
        quizRated = getIntent().getExtras().getString("QuizRated");
        quizPlayed = getIntent().getExtras().getString("QuizPlayed");
        quizID = getIntent().getExtras().getString("QuizID");

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

    public void backToHub(View v){

        Intent i = new Intent(this, hub.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);


    }




}
