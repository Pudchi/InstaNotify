package com.pudchi.slidewarn;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

public class common_qa extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_qa);

        ScrollView sc_qa = (ScrollView) findViewById(R.id.scrollView_q_a);
        ImageView qa_pic = (ImageView) findViewById(R.id.qa_pic);

        qa_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
