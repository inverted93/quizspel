package adroit.backend;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    jsonConnection jsonClass = new jsonConnection();
    TextView tv;
    String ab;
    JSONObject jobj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // tv= (TextView) findViewById(R.id.textView);
        new retrievedata().execute();

    }



    class retrievedata extends AsyncTask<String, String, String> {

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

    EditText anvText;
    EditText passText;
    TextView felMed;
    EditText extraText;
    Button loginButton;


    ArrayList<String> userList = new ArrayList<String>();
    ArrayList<String> passList = new ArrayList<String>();



    public void fillList(){

        userList.add("admin");
        userList.add("axel");
        userList.add("jonas");
        userList.add("samuel");
        userList.add("alex");

        passList.add("password");
        passList.add("password");
        passList.add("password");
        passList.add("password");
        passList.add("password");


    }




    public void login(View view){


        fillList();


        anvText = (EditText)findViewById(R.id.editText);
        passText = (EditText)findViewById(R.id.editText2);
        felMed = (TextView)findViewById(R.id.textView5);



        //String anv2Tmp = ((EditText) findViewById(R.id.editText)).getText().toString();


        String anvTmp = anvText.getText().toString();
        String passTmp = passText.getText().toString();


        felMed.setVisibility(View.INVISIBLE); // Kanske onodig..

        Log.d("Texxxt", ":" + anvTmp + passTmp );



        for(int i=0;i<userList.size();i++){    //For-loop som gar ingenom arrayen


            if(anvTmp.equals(userList.get(i)) && passTmp.equals(passList.get(i))){    //Jamfor ett namn och lösenord i listan med 

                Intent myIntent = new Intent(this, hub.class);


                startActivity(myIntent);





            }else{
                Log.d("Misslyckad ", "inloggning");



                felMed.setVisibility(View.VISIBLE);
                felMed.setText("Incorrect username or password");





            }





        }





        //mEdit   = (EditText)findViewById(R.id.edittext);


    }



    public void createUser(View v){




        Log.d("Hej vi kom hit iaf","trevligt ");



        felMed.setText("Fill out all the forms");

        extraText = (EditText)findViewById(R.id.editText10);

        extraText.setVisibility(View.VISIBLE);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setVisibility(View.INVISIBLE);







    }





}
