package adroit.quiz;

import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {



    static JSONObject jobj;
    static String id; // Id som finns i användarens konto
    static boolean errorOccured = false;

    EditText editUsername;
    EditText editPassword;
    EditText editEmail;


    public static void setJson(JSONObject j){ //metod för att uppdatera jsonobjektet
        //Anropas av retreiveData för att uppdatera json i klassen
        jobj =j;

    }

    public static String getId(){
        return id;
    }
    // metod för returnera variabeln id används i andra
    // klasser för att ta reda på vilken användare som använder appen



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //JSON hämtas ner ifrån hosten
        runRetrieve();

    }

    public static void runRetrieve(){
        //En metod som används för att hämta ner en ny version av json ifrån hosten.
        new retrieveData().execute();

    }

    public static void runUpdate(){
        new updateData().execute();
        //En metod som används för att göra en update

    }


    EditText anvText;
    EditText passText;
    TextView felMed;

    //Ska användas för att man ska kunna skapa konto och logga in med samma knapp.
    //Beroende på switcher så kommer koden för att logga in eller skapa medlem köras.
    boolean switcher;
    boolean checkErrorMsg; //Används för att felmeddelandet inte ska skrivas ut för tidigt.


    public void buttonSwitcher(View view) {
        // Metod för att ändra text på två knappar (och i förlängningen dess funktionalitet)
        // Görs även ett textfält synligt när användaren trycker på "Create User"

        EditText userName = (EditText) findViewById(R.id.usernameInput); // Textfält för inmatning av användarnamn
        Button newUser = (Button) findViewById(R.id.newUserButton);// Knapp för att skapa ny anvndare
        Button login = (Button) findViewById(R.id.loginButton); // Knapp för att logga in


        if (switcher == false) {
            // Om användaren tryckt på "create user" knappen synliggörs ett texfält
            // där ombeds användaren skriva in önkskat användarnamn
            userName.setVisibility(View.VISIBLE);
            newUser.setText(R.string.loginButtonString);
            login.setText(R.string.userCreate);
            switcher = true;

        }else{
            // Om användaren senare trycker på knappen "log in" igen så
            // görs textfälltet med användarnamn osynligt och knapparna får ursprunglig text
            userName.setVisibility(View.INVISIBLE);
            newUser.setText(R.string.createButtonString);
            login.setText(R.string.loginButtonString);
            switcher = false;
        }
    }


    public void createJson(JSONArray memberArr){
        //Klassen tar emot en JSONArray som innehåller alla medlemmar inklusive den nyligen skapade medlemmen.
        //Skapar ett jsonobjekt där arrayerna ska placeras (det slutgiltiga jsonobjektet)

        JSONObject jsonFinal = new JSONObject();

        try{
            //Hämtar alla arrayer som inte har uppdaterats
            JSONArray quizArr = jobj.getJSONArray("Quiz");
            JSONArray questArr = jobj.getJSONArray("Question");
            JSONArray ansArr = jobj.getJSONArray("Answer");

            //Lägger till alla fyra arrayerna
            jsonFinal.put("Quiz", quizArr);
            jsonFinal.put("Question", questArr);
            jsonFinal.put("Answer", ansArr);
            jsonFinal.put("Members", memberArr);

            //Uppdaterar i klassen updateData, som sedan använder den nya json när den kommmunicerar med hosten.
            updateData.setJson(jsonFinal);
            MainActivity.runUpdate();
            MainActivity.runRetrieve();


        }catch(JSONException e){

            e.printStackTrace();

        }
    }

    public void networkCheck(){
        /*Metoden anropas för att verifiera att ett jsonobjekt har hämtats ifrån hosten.
        * Om så inte är fallet kommer användaren att få ett toastmeddelande som informerar
        * något är fel med nätverket.*/
        if(jobj==null){
            //En klass variabel sätts som true, när ett fel har inträffat
            errorOccured =true;
            Context context = getApplicationContext();
            CharSequence msg = "Network Error";
            int duration = Toast.LENGTH_SHORT;
            //Toasten visas
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }else{
            errorOccured =false;
        }

    }

    private CharSequence toastText;

    //Metoden är kopplad till den högra knappen i startrutan och loggar antingen in eller
    //skapar ett nytt konto baserat på switchern som har sats till true eller false när den vänstra knappen trycks på
    public void login(View view)throws JSONException {

        Context context = getApplicationContext();
        networkCheck();
        if(errorOccured==true){
            runRetrieve();
        }
        //logga in
        if (switcher == false) {

            try {

                checkErrorMsg = false;
                anvText = (EditText) findViewById(R.id.email);
                passText = (EditText) findViewById(R.id.password);
                felMed = (TextView) findViewById(R.id.textView5);
                String anvTmp = anvText.getText().toString();
                String passTmp = passText.getText().toString();

                felMed.setVisibility(View.INVISIBLE);

                JSONArray memberArr = jobj.getJSONArray("Members");
                String uName;
                String password;

                Log.d("1. ", "2" + memberArr.length());


                for (int i = 0; i < memberArr.length(); i++) {    //For-loop som gar ingenom arrayen


                    JSONObject tmpJ = memberArr.getJSONObject(i);
                    uName = tmpJ.getString("UserName");
                    password = tmpJ.getString("Password");

                    //Jamfor ett namn och lösenord i listan med respektive värde i databasen.
                    //om det stämmer så skickas användaren vidare till nästa aktivitet, annars visas ett errormeddelande
                    if (anvTmp.equals(uName) && passTmp.equals(password)) {

                        id = tmpJ.getString("UserID");

                        Intent myIntent = new Intent(this, hub.class);
                        startActivity(myIntent);
                        finish();
                        overridePendingTransition(0, 0);
                        checkErrorMsg = true;
                        break;

                    } else {
                        checkErrorMsg = false;
                    }

                }
            } catch (NullPointerException e) {

            }

            if (checkErrorMsg == false) { //!!!!!!
                felMed.setVisibility(View.VISIBLE);
                felMed.setText("Incorrect username or password");

            }

        } else {
            //Här är kod för att skapa användare

            //Edittexter knyts till var sitt täxtfält i gränssnittet
            editUsername = (EditText) findViewById(R.id.usernameInput);
            editPassword = (EditText) findViewById(R.id.password);
            editEmail = (EditText) findViewById(R.id.email);

            //Här deklareras Stringar som får det värde som användaren har skrivit i textfälten
            String stringUsername = editUsername.getText().toString();
            String stringPassword = editPassword.getText().toString();
            String stringEmail = editEmail.getText().toString();

            //Log som gör att utvecklaren kan se indatan lagras rätt i rätt String
            Log.d("Email", stringEmail);
            Log.d("Password", stringPassword);
            Log.d("Username", stringUsername);


            boolean b = getBool(stringUsername, stringEmail, stringPassword);
            //Om getBool returnerar true, så skapas en ny användare
            if (b == true) {
                //Skapar JSONObjektet som alla put kommer att laggas i.
                JSONObject updateMember = new JSONObject();

                try {

                    JSONArray memberArr = jobj.getJSONArray("Members");
                    //Denna int genererar vilkwr ID den nya användaren ska få
                    int length = memberArr.length() + 1;
                    //Under läggs all indata som användaren har genererat in i JSON filen.
                    updateMember.put("UserID", length);
                    updateMember.put("Email", stringEmail);
                    updateMember.put("Password", stringPassword);
                    updateMember.put("UserName", stringUsername);
                    updateMember.put("QuestionsAnswered", "0");
                    updateMember.put("RightAnswers", "0");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Create member UserID", "Error");
                }


                Log.d("Test", updateMember.toString());

                    /*Hämtar först ut alla Members ur JSON filen. Lägger sedan till all ny input i arrayen
                     Detta då en vanlig put skriver över dom existerade objekten i JSON filen */

                try {
                    JSONArray memberArrUpdateMember = jobj.getJSONArray("Members");
                    memberArrUpdateMember.put(updateMember);
                    Log.d("KollarTEst", memberArrUpdateMember.toString());
                    Log.d("KollarTEst2", jobj.toString());
                    createJson(memberArrUpdateMember);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CharSequence text = "The User: " + stringUsername + " was created";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, toastText, duration);
                toast.show();
            }
        }
    }

    public boolean getBool(String userName, String email, String password){
        /*Nedan är vår testkod för att användaren matar in korrekt information när en ny medlem ska skapas.
        metoden används för att se så att användarnamnet eller
        emailen inte redan finns i databasen. Den kollar också
         så att användaren har med ett @ i sin mailadress.*/
        Boolean b = true;

        if(!email.contains("@")){
            //Kollar så att det finns ett @, sätter variabeln till false om det inte finns.
            b = false;
            toastText="Not a valid Email";
        }
        if(email.length()<5){
            //Kollar så att mailen är tillräckligt lång
            b=false;
            toastText ="The Email is to short";
        }


        EditText emailField = (EditText) findViewById(R.id.email);
        EditText userNameField = (EditText) findViewById(R.id.usernameInput);
        EditText passwordField = (EditText) findViewById(R.id.password);


        if(email.equals("")){
            b = false;
            emailField.setHint("Fill this out - Email");
            toastText="You must fill out all forms";
        }
        if(userName.equals("")){
            b = false;
            userNameField.setHint("Fill this out - Username");
            toastText="You must fill out all forms";
        }
        if(password.equals("")){
            b = false;
            passwordField.setHint("Fill this out - Password");
            toastText="You must fill out all forms";
        }
        if(password.length()<4){
            b = false;
            toastText="Password must be atleast 5 character";
        }
        //Skapar två regex
        String numb   = ".*[0-9].*";
        String alphaLower = ".*[a-z].*";
        String alphaUpper = ".*[A-Z].*";

        if(!password.matches(numb)){
            //Om inte lösenordet innehåller minst ett nummer så kommer ett felmeddelande visas
            b = false;
            toastText="Password must contain atleast one number";
        }
        if(!password.matches(alphaLower)){
            //Om inte lösenordet innehåller minst en litenbokstav så kommer ett felmeddelande visas
            b = false;
            toastText="Password must contain atleast one lowercase letter";
        }
        if(!password.matches(alphaUpper)){
            //Om inte lösenordet innehåller minst en storbokstav så kommer ett felmeddelande visas
            b = false;
            toastText="Password must contain atleast one capital letter";
        }


        try{
            JSONArray membArr = jobj.getJSONArray("Members");
            /*hämtar ett Json objekt som används för att hämta användarnamn och email.
             * Loopen jobbar igenom alla objekt och ser om användarnamnet eller emailen
             * finns på något mer ställe */
            for(int i=0; i<membArr.length();i++){
                JSONObject tmp = membArr.getJSONObject(i);
                Log.d("Pop", "Andre" + tmp.toString());
                String uNameFromJson = tmp.getString("UserName");
                String emailFromJson = tmp.getString("Email");

                if(userName.equals(uNameFromJson)){
                    toastText ="Username is taken! ";
                    b= false;
                    //Sätter variabeln till false om det redan finns ett
                    // konto med det här användarnamnet eller emailen.
                }
                if(email.equals(emailFromJson)){
                    b= false;
                    toastText ="This Email already has an account";
                }
            }

        }catch(JSONException e){

            e.printStackTrace();
        }


        return b;
        //Returnerar boolean till anropande rad där de kan användas för varna användare
    }


        }



class retrieveData extends AsyncTask<String, String, String> {


    jsonConnection jsonClass = new jsonConnection();
    TextView tv;
    String ab;
    JSONObject jobj;

    JSONArray memberArr;


    @Override
    protected String doInBackground(String... arg0){

        try{
            jobj= jsonConnection.requestJson("https://api.myjson.com/bins/43reh");

        }catch(JSONException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            memberArr = jobj.getJSONArray("Members");
        }catch(JSONException e){
            e.printStackTrace();
        }
        return ab;
    }

    protected void onPostExecute(String ab){
        quizMain.setJson(jobj);
        play.setJson(jobj);
        MainActivity.setJson(jobj);
        results.setJson(jobj);
        GameAdapter.setJson(jobj);
        hub.setJson(jobj);

    }
}
