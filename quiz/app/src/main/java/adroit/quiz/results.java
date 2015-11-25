package adroit.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;

public class results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results2);

        double score = getIntent().getExtras().getDouble("The Key");

        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(1);

        TextView scoreView = (TextView) findViewById(R.id.editText2);
        scoreView.setText("Your score: " + nf.format(score));


    }
}