package busyboy.activity.countdownview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       TimeCountDownTextView countDownView = (TimeCountDownTextView) findViewById(R.id.countdown);
        countDownView.start();
    }
}
