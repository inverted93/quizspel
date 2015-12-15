package adroit.backend;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by axelholm on 2015-12-14.
 */



public class updateData {



    public static void updateShit(String u){

        JSONObject jobj;
        String json;
        try{

            InputStream stream = new URL(u).openStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            json = readAll(reader);   //anropar metoden neranför som bugger en string av BufferedReadern
            jobj = new JSONObject(json); //skapar ett nytt JSON-objekt av stringen.

            /*URLConnection urlConnection = url.openConnection();

            urlConnection.setAllowUserInteraction(true);
            Boolean b = urlConnection.getAllowUserInteraction();
*/
            Log.d("Sant eller", "inte? " + jobj.getString("firstName"));




        }catch (MalformedURLException e){

            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();

        }catch(JSONException e){

            e.printStackTrace();

        }




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
