package adroit.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    boolean switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new retrievedata().execute();

    }

    public void userCreate(View view) {

        EditText userName = (EditText) findViewById(R.id.usernameInput);
        Button newUser = (Button) findViewById(R.id.newUserButton);
        Button login = (Button) findViewById(R.id.loginButton);
        if (switcher == false) {
            userName.setVisibility(View.VISIBLE);
            newUser.setText(R.string.loginButtonString);
            login.setText(R.string.userCreate);
            switcher = true;
        } else {
            userName.setVisibility(View.INVISIBLE);
            newUser.setText(R.string.createButtonString);
            login.setText(R.string.loginButtonString);
            switcher = false;
        }
    }


    public void changePage(View view) {

        Intent myIntent = new Intent(this, hub.class);

        startActivity(myIntent);

    }


    public void login(View view) {


        fillList();


        EditText anvText = (EditText) findViewById(R.id.email);
        EditText passText = (EditText) findViewById(R.id.password);
        TextView felMed = (TextView) findViewById(R.id.textView5);

        //String anv2Tmp = ((EditText) findViewById(R.id.editText)).getText().toString();

        String anvTmp = anvText.getText().toString();
        String passTmp = passText.getText().toString();

        felMed.setVisibility(View.INVISIBLE); // Kanske onodig..

        Log.d("Texxxt", ":" + anvTmp + passTmp);

        for (int i = 0; i < userList.size(); i++) {    //For-loop som gar ingenom arrayen

            if (anvTmp.equals(userList.get(i)) && passTmp.equals(passList.get(i))) {    //Jamfor ett namn och lösenord i listan med

                Intent myIntent = new Intent(this, hub.class);
                startActivity(myIntent);
                break;

            } else {
                Log.d("Misslyckad ", "inloggning");

                felMed.setVisibility(View.VISIBLE);
                felMed.setText("Incorrect username or password");
            }
        }
        //mEdit   = (EditText)findViewById(R.id.edittext);


    }


    ArrayList<String> userList = new ArrayList<String>();
    ArrayList<String> passList = new ArrayList<String>();

    public void fillList() {

        userList.add("");
        userList.add("admin");
        userList.add("axel");
        userList.add("jonas");
        userList.add("samuel");
        userList.add("alex");


        passList.add("");
        passList.add("password");
        passList.add("password");
        passList.add("password");
        passList.add("password");
        passList.add("password");



    }

}
    class retrievedata extends AsyncTask<String, String, String> {

        jsonConnection jsonClass = new jsonConnection();
        TextView tv;
        String ab;
        JSONObject jobj = null;

        @Override
        protected String doInBackground(String... arg0){

            Log.d("Hej vi kom hit", "1");

            try{
                jobj= jsonConnection.requestJson("https://api.myjson.com/bins/1p8pv");
                Log.d("Hej vi kom hit", "2");

            }catch(JSONException e){
                e.printStackTrace();
                Log.d("Catch", "1");

            }catch (IOException e){
                e.printStackTrace();
                Log.d("Catch", "2");
            }

            Log.d("Network test: ", /*jobj.toString()*/ "10");


            try{

                ab=jobj.getString("Name");
                Log.d("GOOOOOOOAL" , ab);
            }catch (JSONException e){
                e.printStackTrace();
            }

            return ab;

        }

        /*protected void onPostExecute(String ab){
           tv.setText(ab);
        }
*/



    }





















