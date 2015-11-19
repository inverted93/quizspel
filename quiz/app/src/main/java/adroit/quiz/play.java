package adroit.quiz;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class play extends AppCompatActivity {


    TextView countDownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        countDownText=(TextView)findViewById(R.id.editText4);

        new CountDownTimer(11000, 1000) {

            public void onTick(long millisUntilFinished) {

                countDownText.setText("Time left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                countDownText.setText("Time left: 0");
            }
        }.start();



        String[] answers = {"Ett jävla pack", "Rimliga", "Höger", "Kompetenta", "Häftiga", "Snygga", "Intellektuella"};
        ListAdapter answersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, answers);
        ListView answersList = (ListView) findViewById(R.id.answersList);
        answersList.setAdapter(answersAdapter);


    }

}
