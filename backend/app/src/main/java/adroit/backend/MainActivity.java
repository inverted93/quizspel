package adroit.backend;

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import java.util.concurrent.ExecutionException;




public class MainActivity extends AppCompatActivity {

    static JSONObject jobj;
    static String id;
    static boolean errorOccured = false;
    private CharSequence toastText;


    public static void setJson(JSONObject j){

        jobj =j;

    }

    public static String getId(){
        return id;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runRetrieve();


    }

    public void networkCheck(){


        if(jobj==null){
            errorOccured =true;
            Context context = getApplicationContext();
            CharSequence msg = "Network Error";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }else{
            errorOccured =false;
        }

    }


    public static void runRetrieve(){
        new retrieveData().execute();  // VIktig rad
    }

    public static void runUpdate(){

        new updateData().execute();
    }





    EditText anvText;
    EditText passText;
    TextView felMed;
    EditText extraText;
    Button loginButton;

    boolean switcher;
    boolean checkErrorMsg; //Används för att fel meddelandet inte ska skrivas ut för tidigt.


    ArrayList<String> userList = new ArrayList<String>();
    ArrayList<String> passList = new ArrayList<String>();




    /*Deklaration av edittexterna(TExtfälten) till create user*/
    EditText editUsername;
    EditText editPassword;
    EditText editEmail;


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

            updateData.setJSON(jsonFinal);
            MainActivity.runUpdate();
            MainActivity.runRetrieve();


        }catch(JSONException e){

            e.printStackTrace();

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
        String alpha = ".*[A-Z].*";

        if(!password.matches(numb)){
            b = false;
            toastText="Password must contain atleast one number";
        }
        if(!password.matches(alpha)){
            b = false;
            toastText="Password must contain atleast one character";
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





    public void buttonSwitcher(View view) {


        EditText userName = (EditText) findViewById(R.id.usernameInput);
        Button newUser = (Button) findViewById(R.id.createUser);
        Button login = (Button) findViewById(R.id.loginButton);


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



    public void login(View view)throws JSONException {

        Context context = getApplicationContext();
        networkCheck();
        if(errorOccured==true){
            runRetrieve();
        }



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

                        if (anvTmp.equals(uName) && passTmp.equals(password)) {

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
                Log.d("BOOLEAN", "Piss" + b);
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

                    toastText = "The User: " + stringUsername + " was created";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                } else {
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, toastText, duration);
                    toast.show();
                }
            }

    }










}








