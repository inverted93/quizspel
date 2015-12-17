package adroit.backend;

import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by axelholm on 2015-12-14.
 */



public class updateData {


    public static HttpResponse updateShit(String u){

        Log.d("1", "2 Vi kom in i metoden igen");

        ArrayList <String> url = new ArrayList<String>();

        url.add("https://api.myjson.com/bins/3lyn9");
        url.add("https://api.myjson.com/put/bins/3lyn9");
        url.add("https://api.myjson.com/bins/3lyn9");
        url.add("https://api.myjson.com/bins/:3lyn9");
        url.add("https://api.myjson.com/bins/3lyn9");
        url.add("https://api.myjson.com/bins/3lyn9");

        //String url = "https://api.myjson.com/PUT/bins/3lyn9";



        StringEntity se=null;


            for(int i=0; i<url.size();i++){

                try {

                //HttpClient httpClient = new DefaultHttpClient();

                    Log.d("Kommer in i metoden iaf", + url.size() + "okej" + url.get(i));

                //HttpPost httpost = new HttpPost(url.get(i));

                    HttpPut httput = new HttpPut(url.get(i));



                    //HashMap<String, String> data = new HashMap<>();
                //data.put("firstName", "Axel");
                //data.put("lastName", "Holm");

                StringEntity se2 = new StringEntity("'{\"firstName\":\"Axel\", \"lastName\":\"Holm\"}'");

                httput.setEntity(se2);
                httput.setHeader("Accept", "application/json");
                    httput.setHeader("Content-type", "application/json");


                    Log.d("1337133713371337", "HELLO    " + httput.getURI());


                    HttpResponse client = new DefaultHttpClient().execute(httput);

                    Log.d("13371337", "dicken" + httput);
                    Log.d("13371337", "HELLO" + client.getStatusLine().toString());


                    if(client == null){

                        return client;

                    }





            }catch(IOException e){

                e.printStackTrace();

            }

        }

        return null;


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
