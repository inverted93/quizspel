package adroit.backend;

import android.content.Context;
import android.content.Intent;

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



    public void userCreate(View view) throws JSONException{

        networkCheck();
        if(errorOccured==true){
            runRetrieve();
        }
        /*Edit texterna knyts til vissa textfält*/
        editUsername = (EditText)findViewById(R.id.usernameInput);
        editPassword = (EditText)findViewById(R.id.password);
        editEmail = (EditText)findViewById(R.id.email);

        //createQuestion.createJSON();

        EditText userName = (EditText) findViewById(R.id.usernameInput);
        Button newUser = (Button) findViewById(R.id.creatUser);
        Button login = (Button) findViewById(R.id.loginButton);


        if (switcher == false) {



            userName.setVisibility(View.VISIBLE);
            newUser.setText(R.string.loginButtonString);
            login.setText(R.string.userCreate);
            switcher = true;
            //Knappen som ska anändas till onklick deklareras
            Button b = (Button)findViewById(R.id.loginButton);

            //Hämtar input när användaren skapar att nytt account
            /*Onklick deklareras*/
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*Stringar deklareras för att kunna ta emot input
                    * från användaren när denna trycke rpå create member kanppen.*/
                    String stringUsername = editUsername.getText().toString();
                    String stringPassword = editPassword.getText().toString();
                    String stringEmail = editEmail.getText().toString();

                    Boolean boolTmp = getBool(stringUsername, stringEmail, stringPassword);
                    if(boolTmp==true){



                    /*Loggar där programmeraren kan se om inputen kom till stringarna*/
                    Log.d("Fiskmås1", stringEmail);
                    Log.d("Fiskmås2", stringPassword);
                    Log.d("Fiskmås3", stringUsername);

                    /*Skapar JSONObjektet som alla put kommer att laggas i.*/
                    JSONObject updateMember = new JSONObject();

                    try {

                        JSONArray memberArr = jobj.getJSONArray("Members");

                        /*Int length som får datan av hur många objekt som finns i JSONdatan.
                        *DEnna int används sedan för att ge den nya membern sin UserID
                        */
                        int length = memberArr.length() +1;

                        /*Här läggs den nya användar datan
                        *in i JSONObjektet
                        *updateMember.*/
                        updateMember.put("UserID" , length);
                        updateMember.put("Email", stringEmail);
                        updateMember.put("Password", stringPassword);
                        updateMember.put("Username", stringUsername);
                        updateMember.put("QuestionsAnswered" , "0");
                        updateMember.put("RightAnswers" , "0");
                        /*Catch för JSONobjekten*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member UserID", "fel med id");
                    }


                    Log.d("TEstDronten", updateMember.toString());

                    /*Hämtar först ut alla Members ur JSON filen. Lägger sedan till all ny input i arrayen
                     *Detta då en vanlig put skriver över dom existerade objekten i JSON filen */

                    try {
                        JSONArray memberArrUpdateMember = jobj.getJSONArray("Members");
                        memberArrUpdateMember.put(updateMember);

                        /*Loggar där utvecklaren kan se om inputen kom in i
                        * member arrayen inom JSON datan och se om hela JSON
                        * datan uppdaterades.*/
                        Log.d("KollarTEst", memberArrUpdateMember.toString());
                        Log.d("KollarTEst2", jobj.toString());
                        createJson(memberArrUpdateMember);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            /*Onklick slutar*/
            });

        }else{
            userName.setVisibility(View.INVISIBLE);
            newUser.setText(R.string.createButtonString);
            login.setText(R.string.loginButtonString);
            switcher = false;
        }


        Log.d("Heeeej", "1");

    }



    public void login(View view)throws JSONException{
        networkCheck();
        if(errorOccured==true){
            runRetrieve();
        }
        Log.d("Heeeeeej", "1 vi ar i login ");

        try {

        checkErrorMsg = false; //!!!!
        anvText = (EditText)findViewById(R.id.email);
        passText = (EditText)findViewById(R.id.password);
        felMed = (TextView)findViewById(R.id.textView5);
        String anvTmp = anvText.getText().toString();
        String passTmp = passText.getText().toString();

        felMed.setVisibility(View.INVISIBLE); // Kanske onodig..

        JSONArray memberArr = jobj.getJSONArray("Members");
        String uName;
        String email;
        String password;

        Log.d("1. ", "2" + memberArr.length());

            for (int i = 0; i < memberArr.length(); i++) {    //For-loop som gar ingenom arrayen


                JSONObject tmpJ = memberArr.getJSONObject(i);
                uName = tmpJ.getString("UserName");
                email = tmpJ.getString("Email");
                password = tmpJ.getString("Password");

                //Jamfor ett namn och lösenord i listan med  TEMPFIX FOR ATT SLIPPA LOGGA IN
                if (anvTmp.equals(uName) && passTmp.equals(password) || anvTmp.equals(email) && passTmp.equals(password) || i == 0) {// Vi ska ta bort funktionen som bara loggar in sen..

                    id = tmpJ.getString("UserID");
                    Intent myIntent = new Intent(this, hub.class);
                    /*Bundle b = new Bundle();
                    b.putString("id",id);
                    myIntent.putExtras(b);*/
                    startActivity(myIntent);
                    checkErrorMsg = true;
                    break;

                } else {
                    checkErrorMsg = false;
                }

            }
        }catch(NullPointerException e){

        }

        if(checkErrorMsg==false){ //!!!!!!
            felMed.setVisibility(View.VISIBLE);
            felMed.setText("Incorrect username or password");

        }

    }

    String tText="";



    public boolean getBool(String userName, String email, String password){
        /*metoden används för attt se så att användarnamnet eller
        emailen inte redan finns i databasen. Den kollar också
         så att användaren har med ett @ i sin mailadress.*/
        Boolean b = true;

        b = checkIfEmpty();

        if(!email.contains("@")){
            //Kollar så att det finns ett @, sätter variabeln till false om det inte finns.
            b = false;
            String tText="";
        }

        try{
            JSONArray membArr = jobj.getJSONArray("Members");
            /*hämtar ett Json objekt som används för att hämta användarnamn och email.
             * Loopen jobbar igenom alla objekt och ser om användarnamnet eller emailen
             * finns på något mer ställe */
            for(int i=0; i<membArr.length();i++){
                JSONObject tmp = membArr.getJSONObject(i);
                Log.d("Pop", "Andre");
                String uNameFromJson = tmp.getString("UserName");
                String emailFromJson = tmp.getString("Email");

                if(userName.equals(uNameFromJson)){
                    tText ="Username is taken! ";
                    b= false;
                    //Sätter variabeln till false om det redan finns ett
                    // konto med det här användarnamnet eller emailen.
                }
                if(email.equals(emailFromJson)){
                    b= false;
                    tText ="This Email already has an account";
                }
            }

        }catch(JSONException e){

            e.printStackTrace();
        }
        return b;
        //Returnerar boolean till anropande rad där de kan användas för varna användare
    }


    public boolean checkIfEmpty(){

        boolean b =true;

        EditText emailField = (EditText) findViewById(R.id.email);
        EditText userNameField = (EditText) findViewById(R.id.usernameInput);
        EditText passwordField = (EditText) findViewById(R.id.password);

        String emailFieldString = emailField.getText().toString();
        String userNameFieldString = emailField.getText().toString();
        String passwordFieldString = emailField.getText().toString();

        if(emailFieldString.equals("")){
            b = false;
            emailField.setHint("Fill this out");
        }
        if(userNameFieldString.equals("")){
            b = false;
            userNameField.setHint("Fill this out");
        }
        if(passwordFieldString.equals("")){
            b = false;
            passwordField.setHint("Fill this out");
        }
        if(passwordFieldString.length()<4){
            b = false;
            tText="Password must be atleast 4 character";
        }

        return b;
    }











}








