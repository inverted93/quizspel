package adroit.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class quizInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_info);

        String s = getIntent().getExtras().getString("Some Key");
        TextView test2 = (TextView) findViewById(R.id.quizTitel);
        test2.setText(s);
    }




    public void setRating(){

        float f = 1.4f;



    }

    public void changePage(View view){



        Intent myIntent = new Intent(this, play.class);


        startActivity(myIntent);


    }


}
