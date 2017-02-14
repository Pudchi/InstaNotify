package com.pudchi.slidewarn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pudchi.slidewarn.R;

public class News_coupon_mall extends Activity {

    public String[] choices={"全聯福利中心","家樂福","愛買"};
    public boolean[] chsBool = {true,true,true};

    ImageButton allconnect;
    ImageButton a_mart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_coupon_mall);

        allconnect = (ImageButton) findViewById(R.id.allconnect);
        a_mart = (ImageButton) findViewById(R.id.a_mart);
        TextView header = (TextView) findViewById(R.id.malltext);

        allconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_coupon_mall.this, all_connect_coupon.class));
                overridePendingTransition(R.anim.abc_popup_enter, android.R.anim.fade_out);
            }
        });

        /*carrefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_coupon_mall.this, carrefour_coupon.class));
                overridePendingTransition(R.anim.abc_popup_enter, android.R.anim.fade_out);
            }
        });*/

        a_mart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_coupon_mall.this, a_mart_coupon.class));
                overridePendingTransition(R.anim.abc_popup_enter, android.R.anim.fade_out);
            }
        });

        /*add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new AlertDialog.Builder(News_coupon_mall.this)
                        .setTitle("合作量販店")
                        .setIcon(android.R.drawable.ic_menu_view)
                        .setMultiChoiceItems(choices, chsBool, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                chsBool[which] = isChecked;
                            }
                        })
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder builder = new StringBuilder();
                                for(int j=0; j<chsBool.length; j++)
                                {
                                    if(chsBool[j] == true)
                                    {
                                        builder.append(choices[j]+". ");
                                    }
                                }
                                Toast.makeText(News_coupon_mall.this, builder.toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(News_coupon_mall.this, "取消選取...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();

                dialog.show();


            }
        });*/
    }


}
