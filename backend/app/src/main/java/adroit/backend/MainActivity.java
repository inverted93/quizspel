package adroit.backend;

import android.content.Intent;

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

import java.util.concurrent.ExecutionException;




public class MainActivity extends AppCompatActivity {

    static JSONObject jobj;
    static String id;


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

            Button b = (Button)findViewById(R.id.loginButton);

            //Hämtar input när användaren skapar att nytt account

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String stringUsername = editUsername.getText().toString();
                    String stringPassword = editPassword.getText().toString();
                    String stringEmail = editEmail.getText().toString();

                    Log.d("Fiskmås1", stringEmail);
                    Log.d("Fiskmås2", stringPassword);
                    Log.d("Fiskmås3", stringUsername);

                    //Skapar JSONObjektet som alla put kommer att laggas i.
                    JSONObject updateMember = new JSONObject();

                    /*
                    JSONObject updateUserId = new JSONObject();
                    try {
                        updateUserId.put("UserID", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member UserID", "fel med id");
                    }

                    JSONObject updateEmail = new JSONObject();
                    try {
                        updateEmail.put("Email", stringEmail);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member Email", "Inget Email");
                    }

                    JSONObject updatePassword = new JSONObject();
                    try {
                        updatePassword.put("Password", stringPassword);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member password", "Inget password");
                    }

                    JSONObject updateUserName = new JSONObject();
                    try {
                        updateUserName.put("UserName", stringUsername);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member username", "Inget USername");
                    }

                    JSONObject updateQuestionsAnswered = new JSONObject();
                    try {
                        updateUserName.put("QuestionsAnswered", "234");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create QuestionsAnswer", "Frågor svarade");
                    }

                    JSONObject updateRightAnswers = new JSONObject();
                    try {
                        updateUserName.put("RightAnswers", "32");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create RightAnswers", "Rätt frågor");
                    }
                    */




                    //Nytt User ID
                    try {

                        JSONArray memberArr = jobj.getJSONArray("Members");

                        int length = memberArr.length() +1;






                        updateMember.put("UserID" , length);
                        updateMember.put("Email", stringEmail);
                        updateMember.put("Password", stringPassword);
                        updateMember.put("Username", stringUsername);
                        updateMember.put("QuestionsAnswered" , "0");
                        updateMember.put("RightAnswers" , "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member UserID", "fel med id");
                    }

                    //Email som användaren skickar in genom textfält
                    /*try {

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member Email", "Inget Email");
                    }

                    //Password som användaren själv skriver in
                    try {

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member password", "Inget password");
                    }*/

                    //Användarnamn(username) som användaren själv skriver in
                    /*try {

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create member username", "Inget USername");
                    }

                    //Antal svarade frågor. Hårdkodat
                    try {

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create QuestionsAnswer", "Frågor svarade");
                    }

                    //Antal rätta svar. Hårdkodat
                    try {

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Create RightAnswers", "Rätt frågor");
                    }*/

                    Log.d("TEstDronten", updateMember.toString());

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
                }
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
                    checkErrorMsg = true; //!!!
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














}








