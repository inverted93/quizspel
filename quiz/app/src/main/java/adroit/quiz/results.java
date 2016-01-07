package adroit.quiz;

import android.content.Intent;
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

        //Lägg in rating här, skall endast gå att rate:a en gång per användare.

    }

    public static void setJson(JSONObject j){
        jobj = j;

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

                    String ratingString = tmpObj.getString("Rating");
                    String numbOfRatingsString = tmpObj.getString("Rated");
                    double rating = Double.parseDouble(ratingString);
                    double numbOfRatings = Double.parseDouble(numbOfRatingsString);
                    double numbOfRatingsFinal = numbOfRatings +1;


                    Double sum = (rating * numbOfRatings + usersRate)/numbOfRatingsFinal;

                    Log.d("Rating", "tja lol" + sum);
                    Log.d("Rating", "He" + rating + numbOfRatings + usersRate);

                    JSONObject newQuizObj = new JSONObject();

                    newQuizObj.put("QID", id);
                    newQuizObj.put("Name", getIntent().getExtras().getString("QuizTitle"));
                    newQuizObj.put("Description", getIntent().getExtras().getString("QuizDesc"));
                    newQuizObj.put("Rating", sum);
                    newQuizObj.put("Rated", numbOfRatings +1);
                    newQuizObj.put("Played", getIntent().getExtras().getString("QuizPlayed"));
                    newQuizObj.put("UserID", getIntent().getExtras().getString("userID"));
                    newQuizObj.put("Creationdate", getIntent().getExtras().getString("creationDate"));

                    //Dessa ska in sen men intenterna funkar inte riktigt..



                    quizArr.put(i, newQuizObj);
                   

                }

               // if(){

                    String playedString = tmpObj.getString("Played");


                //}



            }

            createJson(quizArr);

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

            updateData.setJson(finalObj);
            MainActivity.runUpdate();
            MainActivity.runRetrieve();



        }catch(JSONException e){
            e.printStackTrace();
        }




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
