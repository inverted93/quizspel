package adroit.backend;

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

        private static String readAll(Reader rd)throws IOException{
            StringBuilder sb = new StringBuilder();
            int count;
            while((count = rd.read()) != -1){
                sb.append((char) count);

            }
            return sb.toString();


        }





    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException{
        //InputStream input = new URL(url).openStream();
        URL website = new URL(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(website.openStream()));

        String inputLine;


        while((inputLine = reader.readLine())!= null){
            System.out.println("1: " + inputLine);
        }
        reader.close();



        JSONObject jTemp = new JSONObject();

       /* try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
            String jsonText = readAll(reader);
            JSONObject json = new JSONObject(jsonText);
            return json;


        }finally{
            input.close();
        }*/



        return jTemp;


    }

















}
