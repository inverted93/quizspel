package adroit.quiz;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * Denna aktivitet innehåller speldelen av appen.
 * Användaren har ett textfält med en fråga, en progressbar som ökas när
 * en fråga besvaras/tiden går ut, en nedräknare, textfält med aktuellt frågenummer
 * och totala antalet frågor och en listview som alltid visar fyra stycken svarsalternativ.
 */

public class play extends AppCompatActivity {

    /**
     * Dessa stringar innehåller information om quizet som skickas som bundle till
     *  results aktiviteten som i sin tur skickar dessa till quizinfo
     *  när användaren väljer att byta aktivitet
    */
    String quizTitle;
    String quizDesc;
    String quizRating;
    String quizRated;
    String quizPlayed;
    String quizID;

    static boolean isVisible = false;   // Sätts false om appen är pausad(ej används), annars true

    private CountDownTimer CountDown;
    double correctAnswers = 0; // Lagrar antal rätta svar
    int answerNumber = 3; // Lagrar info om vilken plats i en array med svarsalternativ man skall hämta frågor
    int numberOfQuestions = 0;  // Hur många frågor har quizet
    int questionNr = 0; // Vilken fråga är aktuell för användaren
    int secondsLeft = 0;  // Används i countDownTimern för att lagra aktuell sekund
    TextView countDownText; // Visar antal sekunder kvar
    ArrayList<String> userAnswers = new ArrayList<>();  // Lagrar de svar användaren valt på frågorna
    List<String> questionsArr = new ArrayList<>(); // Lagrar alla svar som tillhör quizet
    List<String> answers = new ArrayList<>();   // Lagrar alla svarsalternativ
    List<String> guiAnswers = new ArrayList<>();    // Lagrar svarsalternativ för aktuell fråga
    List<String> rightAnswers = new ArrayList<>();  // Lagrar alla korrekta svar

    static JSONObject jsonResponse = new JSONObject();

    public static void setJson(JSONObject json){

        jsonResponse = json;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        /**
         * Hämtar bundels från quizinfo med infromation om valt quiz som lagras i variabler
         */

        quizDesc = getIntent().getExtras().getString("QuizDesc");
        quizRating = getIntent().getExtras().getString("QuizRating");
        quizRated = getIntent().getExtras().getString("QuizRated");
        quizPlayed = getIntent().getExtras().getString("QuizPlayed");
        quizID = getIntent().getExtras().getString("QuizID");
        quizTitle = getIntent().getExtras().getString("QuizTitle");


        /**
         * Hämtar två JSON arrayer som man sedan hämtar strängar med frågor
         * och svaralternativ ifrån och lagrar i olika arrayer
         */

        try {
            //  Hämtar två JSON arrayer, en med frågor, en med svar
            JSONArray jQuestions = jsonResponse.getJSONArray("Question");
            JSONArray jAnswers = jsonResponse.getJSONArray("Answer");

            for (int i = 0; i < jQuestions.length(); i++) // Loopar igenom fråge-arrayen
            {
                JSONObject info = jQuestions.getJSONObject(i);
                String check = info.getString("QID");   // Lagrar Quiz ID "QID" i check

                if (check.equals(quizID)) // kolla om check är samma som quizID, som kom med bundle från quizinfo
                {
                    String question = info.getString("qText");
                    String QueID = info.getString("QueID"); // lagrar fråge ID "QueID"
                    questionsArr.add(question); // lagrar frågan i en array med frågor

                    for (int i2 = 0; i2 < jAnswers.length(); i2++) // Kollar igenom array med frågor
                    {
                        JSONObject info2 = jAnswers.getJSONObject(i2);
                        String check2 = info2.getString("QueID"); //lagrar fråge ID "QueID"

                        if (QueID.equals(check2)) // kolla om check2 är samma som QueID, som kom med bundle från quizinfo
                        {
                            String answer = info2.getString("aText"); // Hämta ett svarsalternativ
                            answers.add(answer); // lagrar svarsalternativet i en array med svarsalternativ
                            String rAnswer = info2.getString("rightAnswer"); // Hämtar en sträng som visar om svarsalternativet är rätt eller fel

                            if (rAnswer.equals("true")) // Kollar om svarsalternativet är korrekt
                            {
                                rightAnswers.add(answer);   // lägger till svaralternativet i en lista med alla rätta svar
                            }
                        }
                    }
                }
            }
        }catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String firstQuestion = questionsArr.get(questionNr); // Hämtar första frågan i fråge arrayen
        ((TextView) findViewById(R.id.editText3)).setText(firstQuestion); // Skriver ut frågan i en textview
        numberOfQuestions = questionsArr.size();    // sätter variabel med antal frågor genom att kolla hur stor frågearrayen är
        ((TextView) findViewById(R.id.quizname)).setText(quizTitle);    // Skriver ut quiznamn i en textview
        ((ProgressBar)findViewById(R.id.progressBar)).setMax(numberOfQuestions);    // Sätter ett maxvärde på en progressbar baserat på antal frågor
        ((ProgressBar)findViewById(R.id.progressBar)).setProgress(1);  // Sätter progressen på 1
        ((TextView) findViewById(R.id.editText5)).setText((questionNr +1) + "/" + numberOfQuestions); // Skriver ut vilken fråga man är på och hur många det finns

        for(int i = 0 ; i <4 ; i++) {

            /**
             * Loopar går 4 gånger och lägger in i guianswers arrayen
             * guianswers är de frågor som skall visas för användaren
             */

            String itemAnswer = answers.get(i);
            guiAnswers.add(itemAnswer);

        }

        countDownText = (TextView) findViewById(R.id.editText4); // Kopplar countdowntext till en resurs

        final  ListView answersList = (ListView) findViewById(R.id.answersList); // Skapar en listview
        final ArrayAdapter<String> answersAdapter = new ArrayAdapter<String> // Skapar en adapter
                (this, android.R.layout.simple_list_item_1, guiAnswers){

            @Override // overridar den befintliga getView metoden för att modifiera den
            public View getView(int position, View convertView, ViewGroup parent){



                View view = super.getView(position,convertView,parent);
                /**
                 * Sätter höjden på varje listobjekt beroend på hur hög listview är
                 * eftersom det skall visas 4 objekt delas höjden på 4.
                 * -3 görs för att synliggöra sista objektet mer då listview har
                 * en shape background med padding som annars täcker lite av sista objektet
                 */
                view.setMinimumHeight(((parent.getMeasuredHeight()) / 4));

                // Sätter backgrundfärgen på listviewobjekten beroend på plats
                // blir varannan i respektive färg
                if(position %2 == 1)
                {
                    view.setBackgroundResource(R.drawable.listviewplay);
                    //view.setBackgroundColor(Color.parseColor("#FCF4D9"));
                }
                else
                {
                    view.setBackgroundResource(R.drawable.listview);
                    //view.setBackgroundColor(Color.parseColor("#8ED2C9"));
                }
                return view;
            }
        };

        answersList.setAdapter(answersAdapter); // kopplar listview till adapter

        CountDown = new CountDownTimer(20000, 100) { // ny timer på 8 sek


            public void onTick(long ms) { // ontick som sätter ett textfält med tid kvar varje sekund
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    countDownText.setText("Time left: " + secondsLeft); // ändrar textfält med aktuell sekund

                }
            }

            public void onFinish() {    // Om inget svar ges från användaren går tiden ut, då utförs följande


                if (questionNr >= (numberOfQuestions -1)){

                    /**
                     * Om aktuellt frågenummer är lika stor eller större än totalt antal frågor
                     * (-1 pga frågenummer börjar på 0) då skall den anropa metoden changepage som utför visa operationer
                     * som är nödvändiga för att gå vidare tilll nästa aktivitet, resultat.
                     * T.ex. räkna ihop poäng.
                     */

                    this.cancel();
                    changePage("");
                }
                else { // tiden gått ut

                    /**
                     * anropar metoden nextquestion som utför nödvändiga operationer som behövs när
                     * användaren har svarat på en fråga(eller i detta fall, tiden gått ut för frågan)
                     * Metoden tar vad man har svarat på frågan som argument, då tiden har gått ut blir
                     * svarat en tom sträng. I metoden fyller man answerslist med nya frågor
                     * därför tar vi bort och sätter answersadaptern igen för att uppdatera frågorna som visas.
                     * Sen startar vi timern igen
                     */

                    nextQuestion("");
                    countDownText.setText("Time left: 0");
                    answersList.setAdapter(null);
                    answersList.setAdapter(answersAdapter);
                    this.start();
                }
            }

        }.start();

        answersList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // sätter en klick lyssnare till svarslistan

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(questionNr >= (numberOfQuestions-1)) {

                    /**
                     * Om aktuellt frågenummer är lika stor eller större än totalt antal frågor
                     * (-1 pga frågenummer börjar på 0) då skall den anropa metoden changepage som utför visa operationer
                     * som är nödvändiga för att gå vidare tilll nästa aktivitet, resultat.
                     * T.ex. räkna ihop poäng.
                     */

                    String choosenAnswer = String.valueOf(parent.getItemAtPosition(position));

                    CountDown.cancel();
                    changePage(choosenAnswer);
                } else {

                    String choosenAnswer = String.valueOf(parent.getItemAtPosition(position));
                    // Spara strängen som det listview-objektet användaren tryckt på innehöll

                    nextQuestion(choosenAnswer); // Lägger in föregående sträng i nextquestion metoden

                    answersList.setAdapter(null);   // Sätter adaptern till null
                    answersList.setAdapter(answersAdapter); // Sätter den till answersadapter igen, så den visar uppdaterad data

                    CountDown.cancel(); // Stänger av countdown
                    CountDown.start();  // Sätter på countdown
                }

            }
        });

    }

    public void nextQuestion(String questionAnswer) { // Metoden används när användaren svarat på fråga eller tiden gått ut

        userAnswers.add(questionAnswer); // Lägger in vad användaren avarat i en array

        /**
         *  följande for loop kollar på platsen(nummer) efter det sista svarsalternativet i föregånde quiz, därför
         * adderas det en 1:a på första platsen i loopen, och eftersom det är fyra svarlternativ som skall
         * visas varje gång ökas int nrOfAnswers med answersnumber
         * (platsen på sista svarsalternativet i föregående fråga) + 5
         */

        int nrOfAnswers = answerNumber + 5;
        guiAnswers.clear();
        for(int i = (answerNumber +1) ; i < nrOfAnswers ; i++) {

            String guiAnswer = answers.get(i); // hämtar ett svarsalternativ från svarsalternativ-arrayen

            guiAnswers.add(guiAnswer);  // Lägger in den i array med svarsalternativ som skall visas för användaren

        }
        answerNumber = answerNumber + 4; // Ökar värdet på answernumber med fyra, då det är fyra frågor

        questionNr++; // plussar på frågenummer

        String questionText = questionsArr.get(questionNr); // Hämtar den aktuella frågan från frågearrayen
        ((TextView) findViewById(R.id.editText3)).setText(questionText); // Skriver den nya frågan i en textview
        ((ProgressBar) findViewById(R.id.progressBar)).setProgress(questionNr +1); // Ökar progressbaren med 1
        ((TextView) findViewById(R.id.editText5)).setText((questionNr +1) + "/" + numberOfQuestions); // Uppdaterar med nya värden

    }

    public void changePage(String questionAnswer) { // Används när sista frågan är besvarard eller när dess tid gått ut

        if(isVisible){  // Görs bara om isVisible är true, vilket den är om appen inte är i backgrounden

            userAnswers.add(questionAnswer);  // lägger in sista svaret i array med användarsvar

        for(int i = 0; i < numberOfQuestions; i++) { // jämför användarsvaren med de rätta svaren
            if ((userAnswers.get(i)).equals(rightAnswers.get(i))){ //
                correctAnswers++; // plussas på om användarens svar stämmer överns med det rätta svaret
            }
        }

            double scorePercentage = correctAnswers / (double) numberOfQuestions; // räknar ut procent rätta svar

            /**
             * Koden nedan skickar vidare bundels till nästa aktivitet och startar den
             */

            Bundle b = new Bundle();
            b.putDouble("Score", scorePercentage);
            b.putString("QuizTitle", quizTitle);
            b.putString("QuizDesc", quizDesc);
            b.putString("QuizRating", quizRating);
            b.putString("QuizRated", quizRated);
            b.putString("QuizPlayed", quizPlayed);
            b.putString("QuizID", quizID);
            b.putString("corrA", "" + correctAnswers);
            b.putString("nrOQ", "" + numberOfQuestions);

            Intent myIntent = new Intent(this, results.class);
            myIntent.putExtras(b);
            startActivity(myIntent);
            finish();
            overridePendingTransition(0, 0);

        }
    }

   @Override
   public void onBackPressed() {

       // Overridar onBackpressed skapar en dialogruta som frågar användaren som denne vill avlsuta aktiviteten

       AlertDialog.Builder abuilder = new AlertDialog.Builder(play.this);
       abuilder.setMessage("Do you want to end this quiz?");
       abuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

           public void onClick(DialogInterface dialog, int which) {
               CountDown.cancel(); // avlutar coundown
               // skickar vidare bundles så att informationen i quizinfo skall vara korrekt
               // dvs det skall vara information om aktuella quizet
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

        /**
         * När användaren använder appen sätts isVisible true
         * om den är false så görs inget i changepage() och då
         * byts inte aktivitet, detta för att nästa aktivitet inte skall
         * skall starta upp appen för en användare som inte är inne i den
         * för tillfället. Vilket annars hade skett om tiden hade gått ut för
         * quizet.
         *
         * Om tiden har gått ut för alla frågor när användare
         * öppnar appen igen kommer nästa aktivitet starta direkt
         * pga av if statementet
         */

        super.onResume();
        isVisible = true;

        if(questionNr >= (numberOfQuestions-1)) {
            changePage(""); // Sätter sista svaret tomt
        }
    }


    @Override
    public void onPause()
    {
        /**
         * Sätter isVisible false när användaren är utanför appen
         * se förklaring onResume
         */
        super.onPause();
        isVisible = false;
    }




}


