package adroit.quiz;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by axelholm on 2015-11-18.
 */


public class jsonConnection {

static InputStream stream = null;
    static JSONObject jobj=null;
    static String json = "";

    jsonConnection(){


    }


    public static JSONObject requestJson(String url)throws IOException, JSONException{

        stream = new URL(url).openStream();



            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            json = readAll(reader);
            jobj = new JSONObject(json);




        Log.d("Vi kom in i metoden", "1" + json.toString());

        return jobj;

    }






        private static String readAll(Reader rd)throws IOException{
            StringBuilder sb = new StringBuilder();
            int count;
            while((count = rd.read()) != -1){
                sb.append((char) count);

            }
            return sb.toString();


        }












    }


















