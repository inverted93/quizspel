package adroit.quiz;

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.PorterDuff;
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
import java.util.ArrayList;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // Jonas 130-131, 188-190. Plus import på alla paket från Mainactivity och retrievedata klasserna i backend.

    static JSONObject jobj;
    static String id;

    EditText editUsername;
    EditText editPassword;
    EditText editEmail;


    public static void setJson(JSONObject j){

        jobj =j;

    }

    public static String getId(){
        return id;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new retrieveData().execute();
        runRetrieve();

    }

    public static void runRetrieve(){

        new retrieveData().execute();

    }

    public static void runUpdate(){
        new updateData().execute();
    }


    EditText anvText;
    EditText passText;
    TextView felMed;
    EditText extraText;
    Button loginButton;
    EditText userName;

    boolean switcher;
    boolean checkErrorMsg; //Används för att felmeddelandet inte ska skrivas ut för tidigt.


    //ArrayList<String> userList = new ArrayList<String>();
    //ArrayList<String> passList = new ArrayList<String>();



    public void buttonSwitcher(View view) {


        EditText userName = (EditText) findViewById(R.id.usernameInput);
        Button newUser = (Button) findViewById(R.id.newUserButton);
        Button login = (Button) findViewById(R.id.loginButton);
        login.getBackground().setColorFilter(Color.parseColor("#FCF4D9"), PorterDuff.Mode.MULTIPLY);


        if (switcher == false) {

            userName.setVisibility(View.VISIBLE);
            newUser.setText(R.string.loginButtonString);
            login.setText(R.string.userCreate);
            switcher = true;

        }else{
            userName.setVisibility(View.INVISIBLE);
            newUser.setText(R.string.createButtonString);
            login.setText(R.string.loginButtonString);
            switcher = false;
        }
    }


    public void createJson(JSONArray memberArr){

        JSONObject jsonFinal = new JSONObject();

        try{

            JSONArray quizArr = jobj.getJSONArray("Quiz");
            JSONArray questArr = jobj.getJSONArray("Question");
            JSONArray ansArr = jobj.getJSONArray("Answer");

            jsonFinal.put("Quiz", quizArr);
            jsonFinal.put("Question", questArr);
            jsonFinal.put("Answer", ansArr);
            jsonFinal.put("Members", memberArr);

            updateData.setJson(jsonFinal);
            MainActivity.runUpdate();
            MainActivity.runRetrieve();


        }catch(JSONException e){

            e.printStackTrace();

        }
    }

    private CharSequence toastText;

    public void login(View view)throws JSONException {

        Context context = getApplicationContext();

        if (switcher == false) {

            try {

                checkErrorMsg = false; //!!!!
                anvText = (EditText) findViewById(R.id.email);
                passText = (EditText) findViewById(R.id.password);
                felMed = (TextView) findViewById(R.id.textView5);
                String anvTmp = anvText.getText().toString();
                String passTmp = passText.getText().toString();

                felMed.setVisibility(View.INVISIBLE); // Kanske onodig..

                JSONArray memberArr = jobj.getJSONArray("Members");
                String uName;
                String password;

                Log.d("1. ", "2" + memberArr.length());


                for (int i = 0; i < memberArr.length(); i++) {    //For-loop som gar ingenom arrayen


                    JSONObject tmpJ = memberArr.getJSONObject(i);
                    uName = tmpJ.getString("UserName");
                    password = tmpJ.getString("Password");

                    if (anvTmp.equals(uName) && passTmp.equals(password) || i == 0) {    //Jamfor ett namn och lösenord i listan med  TEMPFIX FOR ATT SLIPPA LOGGA IN

                        id = tmpJ.getString("UserID");

                        Intent myIntent = new Intent(this, hub.class);
                        startActivity(myIntent);
                        finish(); //Jonas
                        overridePendingTransition(0, 0); //Jonas
                        checkErrorMsg = true; //!!!
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

            editUsername = (EditText) findViewById(R.id.usernameInput);
            editPassword = (EditText) findViewById(R.id.password);
            editEmail = (EditText) findViewById(R.id.email);


            String stringUsername = editUsername.getText().toString();
            String stringPassword = editPassword.getText().toString();
            String stringEmail = editEmail.getText().toString();

            Log.d("Email", stringEmail);
            Log.d("Password", stringPassword);
            Log.d("Username", stringUsername);

            //if axels metod stämmer

            boolean b = getBool(stringUsername, stringEmail, stringPassword);
            if (b == true) {
                //Skapar JSONObjektet som alla put kommer att laggas i.
                JSONObject updateMember = new JSONObject();

                try {

                    JSONArray memberArr = jobj.getJSONArray("Members");

                    int length = memberArr.length() + 1;

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
        /*metoden används för attt se så att användarnamnet eller
        emailen inte redan finns i databasen. Den kollar också
         så att användaren har med ett @ i sin mailadress.*/
        Boolean b = true;

        if(!email.contains("@")){
            //Kollar så att det finns ett @, sätter variabeln till false om det inte finns.
            b = false;
            toastText = "Incorrect Email";
        }

        try{
            JSONArray membArr = jobj.getJSONArray("Members");
            /*hämtar ett Json objekt som används för att hämta användarnamn och email.
             * Loopen jobbar igenom alla objekt och ser om användarnamnet eller emailen
              finns på något mer ställe */
            for(int i=0; i<membArr.length();i++){
                JSONObject tmp = membArr.getJSONObject(i);
                String uNameFromJson = tmp.getString("UserName");
                String emailFromJson = tmp.getString("Email");

                if(userName.equals(uNameFromJson)||email.equals(emailFromJson)){

                    b= false;
                    //Sätter variabeln till false om det redan finns ett
                    // konto med det här användarnamnet eller emailen.
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
            jobj= jsonConnection.requestJson("https://api.myjson.com/bins/4xf5d");

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
        quizMain.setJson(jobj);//Jonas
        play.setJson(jobj);//Jonas
        MainActivity.setJson(jobj);//Jonas
        results.setJson(jobj);
        GameAdapter.setJson(jobj);
        hub.setJson(jobj);

    }
}
