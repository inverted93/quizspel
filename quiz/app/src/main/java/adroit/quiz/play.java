package adroit.quiz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class play extends AppCompatActivity {

    int secondsLeft = 0;
    TextView countDownText;
    ArrayList<String> userAnswers = new ArrayList<>();
    List<String> answers = Arrays.asList("Ett jävla pack", "Rimliga", "Höger", "Kompetenta", "Häftiga", "Snygga", "Intellektuella");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        countDownText=(TextView)findViewById(R.id.editText4);

        final CountDownTimer CountDown = new CountDownTimer(10000, 100) {

            public void onTick(long ms) {
                if (Math.round((float)ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    countDownText.setText("Time left: " + secondsLeft);
                }
            }

            public void onFinish() {
                countDownText.setText("Time left: 0");
                ((TextView)findViewById(R.id.editText3)).setText("Bytt sida");
            }
        }.start();


        //String[] answers = {"Ett jävla pack", "Rimliga", "Höger", "Kompetenta", "Häftiga", "Snygga", "Intellektuella"};
        ListAdapter answersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answers);
        ListView answersList = (ListView) findViewById(R.id.answersList);
        answersList.setAdapter(answersAdapter);

        answersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // GLÖM EJ att if elsa, om tiden går ut skall svaaret i arrayen vara null
            //adapter.clear(), and adapter.addAll(Array<T>) before calling notifyDataSetChanged()
            // ((TextView)findViewById(R.id.editText3)).setText(choosenAnswer);
            //Arrays.asList(yourArray).contains(yourValue)


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String choosenAnswer = String.valueOf(parent.getItemAtPosition(position));
                userAnswers.add(choosenAnswer);
                answers = null; // repainta för att se om det funerar va?
                CountDown.cancel();
                CountDown.start();
                //answersAdapter.notifyDataSetChanged
                ((TextView)findViewById(R.id.editText3)).setText("Bytt sida");

            }
        });

    }

}
