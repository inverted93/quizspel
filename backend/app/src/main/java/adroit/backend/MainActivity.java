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




public class MainActivity extends AppCompatActivity{

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

        new retrieveData().execute();  // VIktig rad





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








    public void userCreate(View view) {


        EditText userName = (EditText) findViewById(R.id.usernameInput);
        Button newUser = (Button) findViewById(R.id.newUserButton);
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








