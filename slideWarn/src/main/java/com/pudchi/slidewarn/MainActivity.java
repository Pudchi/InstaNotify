package com.pudchi.slidewarn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class MainActivity extends FragmentActivity
{

    public static final String GCM_SENDER_ID = "xxxxxxxxxxx";
    public static final String SERVER_API_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1001;
    static public boolean flag_refresh = false;
    static int login_state = 0;
    static tokenDB tDB = null;
    static idDB iDB = null;
    ToggleButton switch_on_off;
    TextView t;
    String TAG;
    private PagerSlidingTabStrip tabs;

    public static SQLiteDatabase get_db() {
        return tDB.getReadableDatabase();
    }

    static public int get_login_state() {
        return login_state;
    }

    static public void set_login_state(int state) {
        login_state = state;
    }

    static public boolean get_flag_refresh() {
        return flag_refresh;
    }

    static public void set_flag_refresh(boolean b) {
        flag_refresh = b;
    }

    public void openDB() {
        tDB = new tokenDB(this);
    }

    public void openIDDB() {
        iDB = new idDB(this);
    }

    public void closeDB() {
        tDB.close();
    }

    private boolean checkPlay() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                Toast.makeText(this, "This device is not supported.",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();
        openIDDB();

        if (checkPlay()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        final ViewPager vP = (ViewPager) findViewById(R.id.viewpager);
        final MyPagerAdapter MAP = new MyPagerAdapter(getSupportFragmentManager());
        vP.setAdapter(MAP);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(vP);


        tabStrip.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
                // TODO Auto-generated method stub
                /*Toast.makeText(MainActivity.this, "Selected Page position: "
						+ position, Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub

                int p = arg0;

                if (p == 3 && flag_refresh) {
                    set_flag_refresh(false);
                    HistoryFragment.i = 1;

                } else if (login_state == 0) {
                    HistoryFragment.i = 0;
                }


            }


        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        /*t = (TextView) menu.findItem(R.id.switch_id)
                .getActionView().findViewById(R.id.actiontext);

        switch_on_off = (ToggleButton) menu.findItem(R.id.switch_id)
                .getActionView().findViewById(R.id.lost);
        switch_on_off.toggle();

        switch_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplication(), "走失通知開啟", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "走失通知關閉", Toast.LENGTH_SHORT).show();
                }

            }
        });*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.viewpager) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.about:
                showAbout();
                return true;
            case R.id.login_logout:
                if (login_state == 0)
                {
                    showloginlogout();
                }
                else
                {
                    AlertDialog.Builder alert_dialog = new AlertDialog.Builder(MainActivity.this);
                            alert_dialog.setIcon(R.drawable.ic_error_black_48dp);
                            alert_dialog.setTitle("確認登出");
                            alert_dialog.setMessage("登出你的幸福老化帳戶?");
                            alert_dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    set_login_state(0);
                                    set_flag_refresh(false);
                                }
                            });
                    alert_dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    alert_dialog.show();

                }
                return true;
            case R.id.personal_info:
                if (login_state == 0)
                {
                    Toast.makeText(getApplication(), "登入後, 才能查看資料", Toast.LENGTH_LONG).show();
                }
                else
                {
                    startActivity(new Intent(MainActivity.this, personal_info.class));
                }
                return true;
            case R.id.contactus:
                Uri uri = Uri.parse("mailto:pudchi330@gmail.com");
                Intent send_mail = new Intent(Intent.ACTION_SENDTO);
                send_mail.setData(uri);
                send_mail.putExtra(Intent.EXTRA_EMAIL, "");
                send_mail.putExtra(Intent.EXTRA_SUBJECT, "Test");
                send_mail.putExtra(Intent.EXTRA_TEXT, "你好, 請在此描述使用App所遇到的問題\n或是有任何需要改進的地方\n在此先謝謝你的建議");
                startActivity(send_mail);
                return true;
            /*case R.id.action_settings:
                return true;*/
            case R.id.commonqa:
                Intent go_to_qa = new Intent();
                go_to_qa.setClass(MainActivity.this, common_qa.class);
                startActivity(go_to_qa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    };

    public void showAbout()
    {
        Intent show_about = new Intent();
        show_about.setClass(MainActivity.this, About_app.class);
        startActivity(show_about);

    };

    public void showloginlogout()
    {
        Intent show_login_logout = new Intent();
        show_login_logout.setClass(MainActivity.this, Login_logout.class);
        startActivity(show_login_logout);

    }

    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int PAGE_COUNT = 4;
        private String tabtitles[] = new String[]{"緊急通知", "進階通知", "生活通知", "歷史紀錄"};


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {


            switch (position) {
                case 0: // Fragment # 0
                    return WarnFragment.newIns(0);
                case 1: // Fragment # 1
                    return AdvFragment.newIns(1);
                case 2: // Fragment # 2
                    return NewsFragment.newIns(2);
                case 3: // Fragment # 3
                    return HistoryFragment.newIns(3);
                    /*case 4: // Fragment # 4
                        return HistoryFragment.newIns(4);*/
                default:
                    return null;
            }
        }


        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }


        @Override
        public int getItemPosition(Object object) {


            return super.getItemPosition(object);


        }

    }
}
