package com.pudchi.slidewarn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class News_coupon_store extends Activity {

    public String[] choices={"7-ELEVEN","全家便利商店","萊爾富","OK便利商店"};
    public boolean[] chsBool = {true,true,true,true};
    ImageButton seven;
    ImageButton family;
    ImageButton hilife;
    ImageButton oklogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_coupon_store);


        seven = (ImageButton) findViewById(R.id.sevenlogo);
        family = (ImageButton) findViewById(R.id.familylogo);
        hilife = (ImageButton) findViewById(R.id.hilifelogo);
        oklogo = (ImageButton) findViewById(R.id.oklogo);
        TextView header = (TextView) findViewById(R.id.malltext);

        /*add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new AlertDialog.Builder(News_coupon_store.this)
                        .setTitle("合作便利超商")
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
                                    if (chsBool[0] = false)
                                    {
                                        seven.setVisibility(View.GONE);
                                    }
                                    if (chsBool[1] = false)
                                    {
                                        family.setVisibility(View.GONE);
                                    }
                                    if (chsBool[2] = false)
                                    {
                                        hilife.setVisibility(View.GONE);
                                    }
                                    if (chsBool[3] = false)
                                    {
                                        oklogo.setVisibility(View.GONE);
                                    }
                                }
                                Toast.makeText(News_coupon_store.this, builder.toString(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(News_coupon_store.this, "取消選取...", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();

                dialog.show();
            }
        });*/

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_coupon_store.this, seven_coupon.class));
                overridePendingTransition(R.anim.abc_popup_enter, android.R.anim.fade_out);
            }
        });

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_coupon_store.this, family_coupon.class));
                overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_fade_out);
            }
        });

        hilife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_coupon_store.this, hi_life_coupon.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
            }
        });

        oklogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(News_coupon_store.this, ok_coupon.class));
                overridePendingTransition(R.anim.abc_popup_enter, android.R.anim.fade_out);
            }
        });

    }

    /*DialogInterface.OnMultiChoiceClickListener multiclick = new DialogInterface.OnMultiChoiceClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            chsBool[which]=isChecked;
        }
    };

    DialogInterface.OnClickListener onclick = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which)
            {
                case Dialog.BUTTON_NEGATIVE:
                    Toast.makeText(News_coupon_store.this, "取消選取",
                            Toast.LENGTH_LONG).show();
                    break;
                case Dialog.BUTTON_POSITIVE:
                    String selectedStr = "";
                    for(int i=0; i<chsBool.length; i++)
                    {
                        if(chsBool[i]=true)
                        {
                            selectedStr = selectedStr + " " + choices[i];
                        }
                    }
            }
        }
    };*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_coupon, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
