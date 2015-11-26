package adroit.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    jsonConnection jsonClass = new jsonConnection();
    TextView tv;
    String ab;
    JSONObject jobj = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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












    public void changePage(View view){



        Intent myIntent = new Intent(this, hub.class);


        startActivity(myIntent);


    }







}
