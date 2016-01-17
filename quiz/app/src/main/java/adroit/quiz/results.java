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

        double score = getIntent().getExtras().getDouble("Score"); // Hämtar användarens resultat på quizet


        // Gör om resultat double formatet och skriver ut det till en textview
        // anropar sedan metoden setScore() som sätter textfärgen beroende på resultat
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        TextView scoreView = (TextView) findViewById(R.id.editText2);
        scoreView.setText(nf.format(score));
        setScoreColor(score);
        //updateStats en metod som lägger på en etta på värdet i json hos quizen
        // (antal som spelat quizen)
        updateStats();

    }

    public static void setJson(JSONObject j){
        //Används för sätta json i den här klassen
        jobj = j;

    }

    String loginId = "1";
    String correctAnswersStringDouble;
    String answeredQuestionsString;
    String played = "1";

    public void createJsonMembers(JSONArray membArr){

        //Den här metoden används för att skapa det kompletta JSON obejktet när en medlem har uppdaterats
        try{
            //De orörda delarna av json hämtas.
            JSONArray questArr = jobj.getJSONArray("Question");
            JSONArray answerArr = jobj.getJSONArray("Answer");
            JSONArray quizArr = jobj.getJSONArray("Quiz");

            //Alla jsonarrayer läggs i ett gemensamt objekt
            JSONObject finalObj = new JSONObject();
            finalObj.put("Quiz", quizArr);
            finalObj.put("Question", questArr);
            finalObj.put("Answer", answerArr);
            finalObj.put("Members", membArr);

            //Det slutgiltiga objektet skickas till metoden setJson i updateData
            updateData.setJson(finalObj);
            //updateData klassen skapas på nytt och uppdateringarna sker
            MainActivity.runUpdate();
            //retrieveData körs igen för att hämta ner den nya datan och skickar ut den till klasserna
            MainActivity.runRetrieve();

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void updateStats(){
        //Metoden uppdaterar statsen för hur många frågor man svarat på och hur många man har
        //svarat rätt på.
        //Id på det inloggade kontot hämtas
        loginId = MainActivity.getId();

        //Används som id längre ner, när quiz ska uppdateras
        String quizId = getIntent().getExtras().getString("QuizID");

        //Antal korrekta svar hämtas ifrån föregående aktivitet och parsas till en int
        correctAnswersStringDouble = getIntent().getExtras().getString("corrA");
        double correctAnswersDouble = Double.parseDouble(correctAnswersStringDouble);
        int correctAnswersInt = (int) correctAnswersDouble;

        //Antal svarade frågor hämtas ifrån föregående aktivitet och parsas till en int
        answeredQuestionsString = getIntent().getExtras().getString("nrOQ");
        int answeredQuestions = Integer.parseInt(answeredQuestionsString);

        try{
            //De två arrayerna som innehåller alla medlemmar och alla quizar hämtas.
            JSONArray membArr = jobj.getJSONArray("Members");
            JSONArray quizArr = jobj.getJSONArray("Quiz");

            //Ett nytt medlemsobjekt skapas
            JSONObject newMemberObj = new JSONObject();

            //En loop snurrar lika många varv som det finns medlemmar
            for(int i=0; i < membArr.length(); i++) {

                //För varje varv tas ett objekt ut och sparas temporärt i en behållare.
                JSONObject tmpMembArr = membArr.getJSONObject(i);

                //Idt till den för loop-varvet aktuella medlemen tas ut.
                String id = tmpMembArr.getString("UserID");

                //If-satsen slår ut när idt ifrån det just nu inloggade kontot stämmer överrens
                // med det temporära idt ifrån loopen. När detta sker har man hittat rätt användare.
                if (loginId.equals(id)) {

                    //Antal frågor och antal korrekta svar som kontot sedan tidigare har presterat hämtas.
                    //Dessa parsas om från string till int
                    String qAOld = tmpMembArr.getString("QuestionsAnswered");
                    String cAOld = tmpMembArr.getString("RightAnswers");
                    int answeredQuestionOld = Integer.parseInt(qAOld);
                    int correctAnswersOld = Integer.parseInt(cAOld);

                    //De gamla resultaten och de nya läggs ihop för att få det totala statsen efter den nyligen
                    //besvarade quizen.
                    int qA = answeredQuestionOld + answeredQuestions;
                    int cA = correctAnswersInt + correctAnswersOld;

                    //Alla de attribut som krävs för att skapa en ny medlem läggs i det nya objektet.
                    newMemberObj.put("UserID", id);
                    newMemberObj.put("Email", tmpMembArr.getString("Email"));
                    newMemberObj.put("Password", tmpMembArr.getString("Password"));
                    newMemberObj.put("UserName", tmpMembArr.getString("UserName"));
                    newMemberObj.put("QuestionsAnswered", "" + qA);
                    newMemberObj.put("RightAnswers", "" + cA);

                    //Det nya objektet läggs till på samma plats som det tidigare låg.
                    //Man skriver alltså över det gamla med det nya.
                    membArr.put(i, newMemberObj);
                    //Metoden som skapar det slutgiltiga objektet anropas
                    createJsonMembers(membArr);

                }

            }

                //När en användare har spelat ska variabeln som innehåller hur många som spelat också uppdateras.
                //Detta görs nedan.
               for(int j =0; j<quizArr.length();j++){

                   // En quiz hämtas ner ifrån listan.
                   JSONObject tmpQuizArr = quizArr.getJSONObject(j);
                   //Id hämtas ifrån den aktuella quizen
                   String quizIdFromJson = tmpQuizArr.getString("QID");

                   //Om idt ifråga stämmer överens med det id som
                   if(quizId.equals(quizIdFromJson)){

                       //Ett nytt quizobjekt skapas
                       JSONObject newQuizObj = new JSONObject();

                       //Hur många gånger quizet har spelats hämtas ifrån föregående aktivitet.
                       //Det parsas om till en int
                       String quizPlayed = getIntent().getExtras().getString("QuizPlayed");
                       int playedInt = Integer.parseInt(quizPlayed);
                       //Antalet spelade för quizet adderas med ett
                       int playedIntFinal = playedInt +1;

                       //Inten görs om till en string och placeras i en behållare
                       played = ""+playedIntFinal;

                       //Ett nytt quiz objekt skapas Och alla de parametrarna som de hade läggs in
                       newQuizObj.put("QID", quizId);
                       newQuizObj.put("Name", getIntent().getExtras().getString("QuizTitle"));
                       newQuizObj.put("Description", getIntent().getExtras().getString("QuizDesc"));
                       newQuizObj.put("Rating", getIntent().getExtras().getString("QuizRating"));
                       newQuizObj.put("Rated", getIntent().getExtras().getString("QuizRated"));
                       newQuizObj.put("Played", ""+playedIntFinal);
                       newQuizObj.put("UserID", tmpQuizArr.getString("UserID"));
                       newQuizObj.put("Creationdate", tmpQuizArr.getString("Creationdate"));

                       //Det nya objektet läggs in på det gamla objektets plats
                       quizArr.put(j, newQuizObj);
                       //Objektet skickas till en metod som skapar det slutgiltiga jsonobjektet.
                       createJson(quizArr);

                   }
               }


        }catch(JSONException e){
            e.printStackTrace();
        }
    }


    public void rateQuiz(View view){

        //RateQuiz körs bara när användaren trycker på knappen Rate quiz.

        //Antalet skärnor hämtas ifrån ratingbaren
        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar);
        double usersRate = bar.getRating();

        //Quizens id hämtas.
        String quizId = getIntent().getExtras().getString("QuizID");

        JSONObject tmpObj;

        try{
            //Quiz arrayen hämtas
            JSONArray quizArr = jobj.getJSONArray("Quiz");

            //Alla quizar söks igenom efter korrekt quiz.
            for(int i=0;i<quizArr.length();i++){

                //En efter en tas objekten ut och idn jämförs för att hitta det korrekta.
                tmpObj = quizArr.getJSONObject(i);
                String id = tmpObj.getString("QID");
                if(id.equals(quizId)){

                    //Den tidigare ratingen hämtas och parsas
                    String ratingString = tmpObj.getString("Rating");
                    //Antal ratings hämtas och parsas
                    String numbOfRatingsString = tmpObj.getString("Rated");
                    double rating = Double.parseDouble(ratingString);
                    double numbOfRatings = Double.parseDouble(numbOfRatingsString);

                    //Antalet ratingar adderas med ett
                    double numbOfRatingsFinal = numbOfRatings +1;
                    String numbOfRatingsFinalString = "" +numbOfRatingsFinal;

                    //Den nya ratingen räknas ut genom att ta det tidigare betyget och multiplicera det
                    // med antal tidigare röster för att sedan addera på betyget som användaren har skickat in.
                    //Detta delas sedan på antalet röster (ink. den nya rösten).
                    Double sum = (rating * numbOfRatings + usersRate)/numbOfRatingsFinal;
                    //Parsas om till string
                    String summa = "" + sum;

                    //Ett nytt jsonobjekt skapas
                    JSONObject newQuizObj = new JSONObject();

                    //Alla variabler läggs in i det nya objektet.
                    newQuizObj.put("QID", id);
                    newQuizObj.put("Name", getIntent().getExtras().getString("QuizTitle"));
                    newQuizObj.put("Description", getIntent().getExtras().getString("QuizDesc"));
                    newQuizObj.put("Rating", summa);
                    newQuizObj.put("Rated", numbOfRatingsFinalString);
                    newQuizObj.put("Played", played);
                    newQuizObj.put("UserID", tmpObj.getString("UserID"));
                    newQuizObj.put("Creationdate", tmpObj.getString("Creationdate"));

                    //Det nya objektet sparas över på det gamla objektets plats
                    quizArr.put(i, newQuizObj);
                }

            }

            //Den uppdaterade arrayen skickas till create JSON
            createJson(quizArr);
            backToHub(view);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }


    public void createJson(JSONArray quizArr){
            //Metoden används för att skapa det slutgiltiga objketet när man uppdaterat quiz arrayen
        try{

            //De orörda arrayerna hämtas
            JSONArray questArr = jobj.getJSONArray("Question");
            JSONArray answerArr = jobj.getJSONArray("Answer");
            JSONArray membersArr = jobj.getJSONArray("Members");

            //Ett nytt json objekt skapas där alla arrayer läggs
            JSONObject finalObj = new JSONObject();
            finalObj.put("Quiz", quizArr);
            finalObj.put("Question", questArr);
            finalObj.put("Answer", answerArr);
            finalObj.put("Members", membersArr);

            //Json uppdateras i updateData
            updateData.setJson(finalObj);
            //Uppdatering skeer
            MainActivity.runUpdate();
            //Hämtar den nya datan
            MainActivity.runRetrieve();

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void setScoreColor (double score){ // Metod som ändrar färg på textfältet beroende på resultat (score)

        if(score >= 0.9) { // Om resultatet är (score är 90% eller högre görs färgen grön
            ((TextView) findViewById(R.id.editText2)).setTextColor(Color.GREEN);
        }
        else if (score <= 0.3){ // Om resultatet är (score är 90% eller högre görs färgen grön
            ((TextView) findViewById(R.id.editText2)).setTextColor(Color.RED);
        }

    }



    public void changePageToQuizInfo(View view){

        /**
         * Ändrar aktivitet till quizinfo och skickar med en
         * bundle med string för att informationen i quizinfo ska vara för samma
         * quiz som användaren precis har spelat
         */

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
        /**
         * Ändrar aktivitet till quizinfo och skickar med en
         * bundle med string för att informationen i quizinfo ska vara för samma
         * quiz som användaren precis har spelat
         */

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

        // Skickar användaren tillbaka till hubben
        // används då användaren har gett Quizet ett betyg (rating)

        Intent i = new Intent(this, hub.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);


    }




}
