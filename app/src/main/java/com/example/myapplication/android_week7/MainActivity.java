package com.example.myapplication.android_week7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    ProgressBar myBar ;
    EditText editText;
    TextView textView;
    Button button;
    boolean isRunning = false; final int MAX_PROGRESS = 100;

    int inputValue = 0;
    int globalVar = 0;
    int accum  = 0; int progressStep = 1;
    Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBar = (ProgressBar)findViewById(R.id.myBar);
        editText = (EditText)findViewById(R.id.txtBox);
        textView = findViewById(R.id.txtPercent);
        button = findViewById(R.id.btnDoItAgain);
        myBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onStart();
                textView.setText(""); button.setEnabled(false);
                inputValue = Integer.parseInt(editText.getText().toString());
                accum = 0; myBar.setMax(inputValue);
                myBar.setProgress(0);
                myBar.setVisibility(View.VISIBLE);


                Thread myBackgroundThread = new Thread( backgroundTask, "backAlias1");
                myBackgroundThread.start();}// onClick
        });

    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    private Runnable foregroundRunnable = new Runnable() {
        @Override
        public void run() {
            try {


            //    myBar.incrementProgressBy(progressStep);
                myBar.setProgress(accum);
                int percent = (accum*100)/inputValue;
                textView.setText(   +percent+ " % "  );
                accum += (progressStep);

                if (accum >= inputValue) {
                    textView.setText("100%");
                   // myBar.setVisibility(View.INVISIBLE);
                    button.setEnabled(true);
                }
            }
            catch (Exception e) { Log.e("<<foregroundTask>>", e.getMessage()); }
        }
    };
    private Runnable backgroundTask = new Runnable() {
        @Override
        public void run() {
            try {
                for (int n = 0; n < inputValue; n++) {

                    Thread.sleep(1);

                    globalVar++;

                    myHandler.post(foregroundRunnable);
                }
            }
            catch (InterruptedException e) { Log.e("<<foregroundTask>>", e.getMessage()); }
        }
    };
}


