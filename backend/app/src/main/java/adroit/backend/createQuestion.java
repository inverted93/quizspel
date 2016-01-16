package adroit.backend;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class createQuestion extends AppCompatActivity {
    /*Klassen används för att skapa frågor till quizen. I den
    finns det ett gäng metoder som haterar grafiska förändringar, datahantering och felsökning*/

    static String quizTitle;
    static String quizDesc;
    static JSONObject jobj;

    RadioGroup radioGruppen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        addListener();
        getStrings();
    }

    public void addListener(){
        /*Metoden lägger till de fyra radioknapparna i samma
        radiogrupp och sätter en lyssnare på dessa*/

        radioGruppen = (RadioGroup) findViewById(R.id.radioFacit);

        radioGruppen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGruppen, int checkedId) {


            }
        });
    }
    /*Deklarerar radiobuttons som ska användas för att
    visa vilket svar som är den rätta*/
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    RadioButton r4;

    public int getRadio(){
        /*Metoden används för att få reda på vilken av de fyra
        radioknapparna som har tryckts på, idt returneras till metodanrop*/

        int id=0;
        //Används för att spara id

        /*RadioButton behållare en RadioButton genom det id dom har*/
        r1 = (RadioButton) findViewById(R.id.radioButton1);
        r2 = (RadioButton) findViewById(R.id.radioButton2);
        r3 = (RadioButton) findViewById(R.id.radioButton3);
        r4 = (RadioButton) findViewById(R.id.radioButton4);

        if(r1.isChecked()){
            //om knappen är tryckt så läggs id:t i int id
            Log.d("RADIO ", "1   ");
            id = R.id.radioButton1;
        }
        if(r2.isChecked()){
            Log.d("RADIO ", "2   ");
            id = R.id.radioButton2;
        }
        if(r3.isChecked()){
            Log.d("RADIO ", "3   ");
            id = R.id.radioButton3;
        }
        if(r4.isChecked()){
            Log.d("RADIO ", "4   ");
            id = R.id.radioButton4;
        }

        //Returnerar idt till den valda knappen
        return id;
    }



    public void setRadio(int id){

        /*När en användare har gjort tidigare frågor så sparas idt
        till den radiobutton som ska vara intryckt för varje fråga.
        När den här metoden anropas så får den id till den radioknapp
        som ska vara intryckt som argument. Metoden sätter sedan knappen som intryckt.
         */

        RadioButton rx = (RadioButton) findViewById(id);
        rx.setChecked(true);
    }





    public static void setJson(JSONObject j){
        /*Metod som anropas i retrieveData klassen. Detta när en ny
        kopia av JSON har hämtats ner ifrån hosten. Detta så att
        klassen ska få ett uppdaterat JSON objekt*/
        jobj= j;
    }
    //Textfält-behållare deklareras, (de gör det här för att de ska kunna användas i flera metoder.)
    EditText question;
    EditText ans1;
    EditText ans2;
    EditText ans3;
    EditText ans4;
    //String variablar som används för att mellanlagra stringar som kommer ifrån textfälten.
    String questionString;
    String ans1String;
    String ans2String;
    String ans3String;
    String ans4String;

    int radioId;

    //Temporära jsonobjekt som används för att mellanlagra frågan och svaren.
    static JSONArray questArr = new JSONArray();
    static JSONArray ansArr = new JSONArray();
    //Sparar id till de radioknappar som blivit tryckta på frågorna.

    int pc=1;
    /* PageCount, Håller reda på vilken "sida" användaren för tillfället är på.
    // Detta så att man ska kunna använda arrayerna ovan för att spara ner informationen
    // temporärt. Om en användare har gjort 3 frågor och trycker på knappen "previous question"
    // så sparas frågan och svaren ifrån fråga 3 på position 3 i arrayerna och efter de
    //hämtas datan ner ifrån position 2 och läggs i fälten */


    public void getText(){
        /*getText är en metod som rensar text fälten ifrån texten som är skriven i dessa.
        * Sedan sparas texten som ligger i klassvariablarna ner i Arrayerna*/

            try {
                //Stringarna uppdateras med den senaste texten.
                getStrings();

                //Två Json objekt skapas
                 JSONObject questTmp = new JSONObject();
                JSONObject ansTmp = new JSONObject();
                //Textfälten rensas
                question.getText().clear();
                ans1.getText().clear();
                ans2.getText().clear();
                ans3.getText().clear();
                ans4.getText().clear();

                /*r1.setChecked(false);          //kan fixas senare, sa att radioknappen inte är i tryckt när man går till nästa
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);*/

                //Fråge-stringen läggs i jsonobjektet
                questTmp.put("question", questionString);
                //Jsonobjektet läggs i questArr som finns deklarerad i klassen.
                questArr.put(pc, questTmp);

                //Svars-stringen läggs i json objektet
                ansTmp.put("ans1", ans1String);
                ansTmp.put("ans2", ans2String);
                ansTmp.put("ans3", ans3String);
                ansTmp.put("ans4", ans4String);
                ansTmp.put("facit", radioId);
                //Jsonobjektet läggs i ansArr
                ansArr.put(pc, ansTmp);

            } catch (JSONException e) {

                e.printStackTrace();
            }
    }


    public void fillFields(){
        /*fillFields används för att fylla formuläret med korrekt information när användaren använder
        * knapparna next och previous.*/
        //Stringarna uppdateras
      getStrings();

        try{

            JSONObject tmpAns;
            JSONObject tmpQuest;
            //Två jsonobjekt deklareras och initieras nedan genom att hämta ut de jsonobjekt som
            //finns på position pc
            tmpAns = ansArr.getJSONObject(pc);
            tmpQuest = questArr.getJSONObject(pc);
            //Stringarna hämtas ifrån objekten och läggs i temporära behållare.
            String s = tmpQuest.getString("question");
            String a1 = tmpAns.getString("ans1");
            String a2 = tmpAns.getString("ans2");
            String a3 = tmpAns.getString("ans3");
            String a4 = tmpAns.getString("ans4");
            String facitString = tmpAns.getString("facit");
            //id till den radiobutton som ska vara nertryckt hämtas
            int facitInt = Integer.parseInt(facitString);
            //Texten förs in i de olika fälten.
            question.setText(s);
            ans1.setText(a1);
            ans2.setText(a2);
            ans3.setText(a3);
            ans4.setText(a4);
            //metoden setRadio körs med argumentet som innehåller id till en radioknapp.
            setRadio(facitInt);

        }catch(JSONException e){
            e.printStackTrace();
        }

    }

    public void getStrings(){
        /*getStrings är en metod som anropas flera gånger i klassen,
        detta för att hämta texten ifrån fälten och radiobutton-id. Eftersom värdena
         varierar beroende på var användaren är i processen så behöver klassen anropas flera gånger.*/

        //Titeln och Beskrivningen till quizen hämtas med hjäp av intent ifrån föregående klass
        quizTitle = getIntent().getExtras().getString("title");
        quizDesc = getIntent().getExtras().getString("description");

        //Identifierar textfälten och lägger dessa i behållare
        question = (EditText) findViewById(R.id.editText5);
        ans1 = (EditText) findViewById(R.id.editText6);
        ans2 = (EditText) findViewById(R.id.editText7);
        ans3 = (EditText) findViewById(R.id.editText8);
        ans4 = (EditText) findViewById(R.id.editText9);

        //Texten hämtas ifrån fälten och sparas i de temporära variablerna
        questionString = question.getText().toString();
        ans1String = ans1.getText().toString();
        ans2String = ans2.getText().toString();
        ans3String = ans3.getText().toString();
        ans4String = ans4.getText().toString();

        //Radiobutton id hämtas och sparas i id behållaren
        radioId=getRadio();

    }


    TextView radioInfo;
    public Boolean checkIfEmpty() {
        /*När användaren är klar med en fråga så är det viktigt att man kollar så att
        * användaren har fyllt i alla fält, så att de inte finns några tomma variablar.
        * checkIfEmpty metoden gör detta.*/
        //Innan detta så uppdateras stringarna så att de som jämförs nedan ser ut som de som användaren ser.
        getStrings();
        // Answer variabeln är true och om alla fält är ifyllda så kommer den inte att förändras.
        Boolean answer = true;

            Log.d("Fyll i alla ", "falt");

            if (questionString.equals("")) {
                //Om ett fält är tomt sätts det fältets hint till "Fill this out".
                question.setHint("Fill this out");
                //answer sätts till false.
                answer = false;
            }
            if (ans1String.equals("")) {
                ans1.setHint("Fill this out");
                answer = false;
            }
            if (ans2String.equals("")) {
                ans2.setHint("Fill this out");
                answer = false;
            }
            if (ans3String.equals("")) {
                ans3.setHint("Fill this out");
                answer = false;
            }
            if (ans4String.equals("")) {
                ans4.setHint("Fill this out");
                answer = false;
            }

            //Om ingen av radioknapparna är valda så kommer answer att sättas till false
            //Sammtidigt sätts färgen på texten som förklarar att man måste välja ett alternativ som de rätta,
            // till röd, för att uppmärksamma användaren på informationen.
            if(!r1.isChecked()&&!r2.isChecked()&&!r3.isChecked()&&!r4.isChecked()){
                answer = false;
                radioInfo = (TextView) findViewById(R.id.textView9);
                radioInfo.setTextColor(Color.RED);

            }else{
                //Om användaren valt en knapp så är det inga problem och färgen sätts till svart igen.
                radioInfo = (TextView) findViewById(R.id.textView9);
                radioInfo.setTextColor(Color.BLACK);
            }
        //Answer som nu atningen är true eller false returneras.
        return answer;
    }


    public void clearHint(){
        //Hintarna som eventuellt sattes i föregående metod återställs nedan.
        question.setHint("");
        ans1.setHint("");
        ans2.setHint("");
        ans3.setHint("");
        ans4.setHint("");

    }


    public void next(View v){
        /*Metoden används när användaren trycker på knappen "next question"*/
        //Stringarna uppdateras
        getStrings();
        //checkIfEmpty kollar så att inget fällt är tomt.
        Boolean ok = checkIfEmpty();

        //Om alla fält är fyllda så körs metoden.
        if(ok==true){

            //De ev. hintarna tas bort och
            clearHint();
            getText();
            //Addar 1 till pagecount
            pc=pc+1;
            fillFields();
            //Sätter eventuell röd text till svart.
            radioInfo = (TextView) findViewById(R.id.textView9);
            radioInfo.setTextColor(Color.BLACK);
            //previous knappen är inte synlig ifrån början men blir det är next trycks.
            Button prevButton = (Button) findViewById(R.id.button8);
            prevButton.setVisibility(View.VISIBLE);
        }
    }

    public void prev(View v){
        /*Previous knappen har tryckts*/
        //Kollar om fält är tomma
        Boolean ok = checkIfEmpty();

        if(ok==true){
            clearHint();
            getStrings();
            getText();
            //tar bort 1 fran pagecount
            pc = pc - 1;
            fillFields();
            radioInfo = (TextView) findViewById(R.id.textView9);
            radioInfo.setTextColor(Color.BLACK);
        }

        /*Om det är så att användaren har tryck tillbaka till första frågan så görs previous
        * knappen osynlig*/
        if(pc==1){
            Button prevButton = (Button) findViewById(R.id.button8);
            prevButton.setVisibility(View.INVISIBLE);
        }
    }

    public void createJSON(){
        /*createJSON är en metod som anropas när användaren har gjort klart quizet och vill spara det.
        Här skapas de Json-filer som kommer att skickas tillbaka till hosten. */
        try{
            //Dagens datum hämtas och formateras så att det passa formateringen i json
            Date d = new Date();
            String date = new SimpleDateFormat("yyyyMMdd").format(d);
            //Den gamla jsonarrayen quiz hämtas
            JSONArray oldQuizArray= jobj.getJSONArray("Quiz");
            //QuizIDt skapas genom att ta längden på arrayen (redan befintliga konton)
            // och lägga till +1
            int quizId = oldQuizArray.length()+1;
            String quizIdString = ""+quizId;
            //Ett nytt jsonobjekt skapas
            JSONObject nyQuiz = new JSONObject();
            //I det läggs all den data som quiz behöver, skapas efter json-syntaxen
            nyQuiz.put("QID", ""+quizIdString);
            nyQuiz.put("Name", quizTitle);
            nyQuiz.put("Description", quizDesc);
            nyQuiz.put("Rating", "0");
            nyQuiz.put("Rated", "0");
            nyQuiz.put("Played", "0");
            //Hämtar idt på den som är inloggad ifrån MainActivity
            nyQuiz.put("UserID", MainActivity.getId());
            nyQuiz.put("Creationdate", date);


            // Question..
            /*Samma sak görs med frågan och svaret. De gamla arrayerna hämtas ifrån det befintliga json*/
            JSONArray oldQuestArr = jobj.getJSONArray("Question");
            JSONArray oldAnsArr = jobj.getJSONArray("Answer");
            JSONObject temporaryAns = new JSONObject();
            JSONObject temporaryQuest = new JSONObject();
            ArrayList <String> ansList = new ArrayList<String>();

            //Arrayerna som frågorna ligger i loopas igenom, Loopen snurrar lika många varv
            //som antal frågor.
            for(int i=0; i<questArr.length()-1;i++){
                //Jsonobjekten hämtas ifrån de temporära arrayerna
                temporaryAns = ansArr.getJSONObject(i+1);
                temporaryQuest = questArr.getJSONObject(i+1);
                int quesId = oldQuestArr.length()+1;
                //Ett nytt json objekt skapas, används för att kunna spara ner frågan i samma syntax
                //som används hos jsonobjektet som finns hos hosten.
                JSONObject nyQuesJson = new JSONObject();
                nyQuesJson.put("QueID", ""+quesId);
                nyQuesJson.put("QID", ""+quizId);
                nyQuesJson.put("qText", temporaryQuest.getString("question"));
                //Lägger till de nyligen skapade objektet längst ner i arrayen
                oldQuestArr.put(nyQuesJson);

                //De fyra svaren som finns i varje fråga placeras i temporära behållare och addas sedan
                // till en ArrayList.
                String a1 = temporaryAns.getString("ans1");
                String a2 = temporaryAns.getString("ans2");
                String a3 = temporaryAns.getString("ans3");
                String a4 = temporaryAns.getString("ans4");
                ansList.add(0, a1);
                ansList.add(1, a2);
                ansList.add(2, a3);
                ansList.add(3, a4);

                //En forloop snurrar 4a varv, eftersom det finns fyra svar.
                for(int j=0; j<4;j++){

                    //Hämtar id till den radiobutton som är tryckt för frågan
                    String facitString = temporaryAns.getString("facit");
                    int radioID = Integer.parseInt(facitString);
                    String rightAnswerString ="false";
                    //Om det är den radioknapp som är intrykt så ska rightAnswerString sättas
                    // till true, förutsat att det är rätt varv på loopen. Detta är här kopplingen mellan
                    // radioknapparna och svarsfälten är.
                    if(radioID == r1.getId()&&j==0){
                        rightAnswerString ="true";
                    }else if(radioID == r2.getId()&&j==1){
                        rightAnswerString ="true";
                    }else if(radioID == r3.getId()&&j==2){
                        rightAnswerString ="true";
                    }else if(radioID == r4.getId()&&j==3){
                        rightAnswerString ="true";
                    }else{
                        rightAnswerString ="false";
                    }
                    int answerId = oldAnsArr.length()+1;


                    //Ett nytt jsonobjekt skapas för att spara svar efter korrekt syntax
                    JSONObject nyAnsJson = new JSONObject();
                    nyAnsJson.put("AID", ""+answerId);
                    nyAnsJson.put("QueID", ""+quesId);
                    nyAnsJson.put("aText", ansList.get(j));
                    nyAnsJson.put("rightAnswer", rightAnswerString);
                    //Läggs i slutet av den gamla arrayen.
                    oldAnsArr.put(nyAnsJson);

                }
            }
            //De modifierade Arrayerna och quizobjektet skickas till addJsonObj
            addJsonObj(nyQuiz, oldQuestArr, oldAnsArr);

        }catch(JSONException e){

            e.printStackTrace();

        }catch( NullPointerException e){

            e.printStackTrace();
        }
    }



    public void done(View v){

        /*När done knappen har tryckts och quizen ska skapas.
        * om alla fällt är ifyllda och quizet består av minst två frågor*/
        Boolean b = checkIfEmpty();

        if(b==true){

            if(questArr.length()>=2){
                //Texten ifrån den nuvarande sidan hämtas
                getText();
                //Ovanstående metod anropas
                createJSON();
                //Rensar de temporära arrayerna ifrån data, så att man kan skapa en till
                //quiz utan att fälten fylls i med text ifrån föregående quiz.
                ansArr = new JSONArray();
                questArr = new JSONArray();

                //Skickar användaren till hub
                Intent myIntent = new Intent(this, hub.class);
                startActivity(myIntent);
                finish();
                overridePendingTransition(0,0);

            }else{
                TextView info = (TextView) findViewById(R.id.textView10);
                info.setVisibility(View.VISIBLE);
                /*Sätter informationsfältet till synligt informerar
                användaren om att man myste göra fler än två frågor*/
            }
        }





    }






    public void addJsonObj(JSONObject quizObj, JSONArray questArr, JSONArray ansArr){

        try{
            //Hamtar de gamla jsonArrayerna av de som inte anvands och lagger till de nya arrayerna av de som anvands.
            JSONArray oldQuizArr = jobj.getJSONArray("Quiz");
            JSONArray oldQuestArr = questArr;
            JSONArray oldAnsArr = ansArr;
            JSONArray oldMemberArr = jobj.getJSONArray("Members");


            oldQuizArr.put(quizObj);

            //Skapar ett nytt json objekt
            JSONObject completeJson = new JSONObject();
            //Lägger de fyra arrayerna i objektet.
            completeJson.put("Quiz",  oldQuizArr);
            completeJson.put("Question", oldQuestArr);
            completeJson.put("Answer", oldAnsArr);
            completeJson.put("Members", oldMemberArr);
            //Skickar det kompletta jsonobjektet till updateData och sedan hämtas ett nytt jsonobjekt
            //så att resten av programmet kan använda den nya datan.
            updateData.setJSON(completeJson);
            MainActivity.runUpdate();
            MainActivity.runRetrieve();


        }catch(JSONException e){

            e.printStackTrace();

        }

    }

    @Override
    public void onBackPressed()
    {

        super.onBackPressed();

        Intent i = new Intent(this, createQuiz.class);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);

    }









}
