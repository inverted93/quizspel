package adroit.quiz;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    // Jonas 130-131, 188-190. Plus import på alla paket från Mainactivity och retrievedata klasserna i backend.

    static JSONObject jobj;
    static String id;


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

    boolean switcher;
    boolean checkErrorMsg; //Används för att felmeddelandet inte ska skrivas ut för tidigt.


    ArrayList<String> userList = new ArrayList<String>();
    ArrayList<String> passList = new ArrayList<String>();



    public void userCreate(View view) {


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
        Log.d("Heeeej", "1");
    }

    //Temporär onClick för emulatorn är så jävla långsam
    public void changePage(View view){

        Intent myIntent = new Intent(this, hub.class);
        startActivity(myIntent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void login(View view)throws JSONException{

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

        }else{
            //Här ska vara kod för att skapa användare
        }
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
            jobj= jsonConnection.requestJson("https://api.myjson.com/bins/1otb5");

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

    }
}
