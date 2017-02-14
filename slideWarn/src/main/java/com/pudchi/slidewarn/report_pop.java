package com.pudchi.slidewarn;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import com.pudchi.slidewarn.R;

public class report_pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pop);

        Button sendback = (Button) findViewById(R.id.foundreport);
        TextView name_txt = (TextView) findViewById(R.id.name_txt);
        TextView live_txt = (TextView) findViewById(R.id.live_txt);
        TextView time_txt = (TextView) findViewById(R.id.time_txt);

        sendback.getBackground().setAlpha(255);


        DisplayMetrics dm_report = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_report);

        int width = dm_report.widthPixels;
        int height = dm_report.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .7));
    }

}
