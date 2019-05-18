package com.example.progressbar;

import android.os.Handler;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int progressStatus=0;
    TextView textView;
    Button stop;
    Handler handler =new Handler();//hander is class is used to handle threads
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.textView);
        stop=findViewById(R.id.stop_button);
        //start long running operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus<100) {
                    progressStatus += 1;
                    //update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            textView.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });
                    try {
                        //sleep for 200 millisecond
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        stop.setOnClickListener(new View.OnClickListener() {
            private Runnable runnable;

            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                progressBar.setProgress(0);
                setTitle(String.valueOf(0));
            }
        });
    }
}