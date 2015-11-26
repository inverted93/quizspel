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
        }else{
            userName.setVisibility(View.INVISIBLE);
            newUser.setText(R.string.createButtonString);
            login.setText(R.string.loginButtonString);
            switcher = false;
        }
    }


    public void changePage(View view){

        Intent myIntent = new Intent(this, hub.class);

        startActivity(myIntent);

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
                jobj= jsonConnection.requestJson("https://api.myjson.com/bins/1o5l9");
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

                ab=jobj.getString("employees");
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





















