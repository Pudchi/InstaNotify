package com.pudchi.slidewarn;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class warn_pop extends Activity {


    public static final String MESSAGE_KEY = "message";
    final static String my_device_token = RegistrationIntentService.get_In_app_token();
    static String server = "140.134.26.9:1433";
    static String root_username = "aip_group";
    static String root_password = "aipgroup1234";
    static String db_name = "AIP";
    final String SENDER_ID = "141975393120";
    final String GCM_API_KEY = "AIzaSyAu7ObVTIrW6wfHslnvQ9TT1uzMMyTvvKQ";
    final String GCM_TOKEN_yujun = "e2ZxGnR5Wt0:APA91bFD-5MKHmSbPPoa84eUeXw1Jq_0EZXTH0nhme9yUtQcAF5KG4U1mZ9uFnIVW9YPoDR0UiX3LmQjuQ0rJ6px3TqrlKII1NQru5VnR8N48bYymkLrmwp1LpCrSjwnDu9O8L6axlvw";
    final String notify_token = "doGvdYGtNog:APA91bGjv6yaqzbi1Gig0M01DTjvNieQpwKf1zi_JOoKYUgCn6KtMABLEmRvgPta3p7nDhNtPqbzbKRwrLugs9zZ5N1bPe5YYTHv2D6frOfwYTHHwU05A3gHcsf_TLHKnsKj7pmxGmP8";
    String[] fam_token;
    int token_count = 0;
    tokenDB tDB_warn;
    idDB iDB;
    Calendar c = Calendar.getInstance();
    String curDate;
    String curTime, time_tmp;
    Connection conn;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1001:
                    String s = msg.getData().getString("Result");
                    Toast.makeText(warn_pop.this, s, Toast.LENGTH_LONG);
                    break;

                default:
                    break;

            }
        }
    };

    private static String getPushMessage(String targetToken, String message) {
        String KEY_REGISTRATION_IDS = "registration_ids";
        String KEY_DATA = "data";

        JSONObject jObj = new JSONObject();
        JSONArray idsArray = new JSONArray();
        idsArray.put(targetToken);

        JSONObject jObjData = new JSONObject();
        try {
            jObjData.put(MESSAGE_KEY, message);

            jObj.put(KEY_REGISTRATION_IDS, idsArray);
            jObj.put(KEY_DATA, jObjData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj.toString();
    }

    private Connection ConnectionHelper(String user, String password,
                                        String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://140.134.26.9:1433//lear_db;instance=SQLEXPRESS;"
                    + "databaseName=" + database + ";user=" + user
                    + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return connection;
    }

    public String getCurrentTime() {
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm:ss");
        String tmptime = simpleTime.format(c.getTime());

        return tmptime;
    }

    public Date getCurrentDate() {
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date();
        String tmpdate = simpleDate.format(date);
        Date date_tmp = new Date();
        try {
            date_tmp = simpleDate.parse(tmpdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date_tmp;
    }

    private void insert_History(final String header) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                String result = "";
                Message msg = new Message();
                msg.what = 1001;
                Bundle data = new Bundle();

                try {
                    curDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
                    curTime = getCurrentTime();
                    int id_t = get_login_id();


                    String history_insert =
                            "INSERT INTO G7_Notify_Detail (id, header, [read], emergenlevel, date, time) VALUES (" + id_t + ", '" + header + "', 0, 2, '" + curDate + "', '" + curTime + "')";

                    conn = ConnectionHelper(root_username, root_password, db_name, server);
                    Statement st = conn.createStatement();
                    st.executeUpdate(history_insert);
                    conn.close();

                } catch (SQLException se) {
                    se.getMessage().toString();
                    data.putString("Result", se.getMessage().toString());
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
            }
        };
        new Thread(runnable).start();
    }

    public void openDB() {
        tDB_warn = new tokenDB(this);

    }

    public void openIDDB() {
        iDB = new idDB(this);
    }

    private void closeDB() {
        tDB_warn.close();
    }

    public int get_login_id() {
        openIDDB();
        SQLiteDatabase id_database = iDB.getReadableDatabase();
        Cursor id_c = id_database.rawQuery("SELECT * FROM Login_Id ORDER BY _serial DESC", null);

        id_c.moveToFirst();
        int id_tmp = id_c.getInt(1);
        id_c.close();
        return id_tmp;
    }

    public String[] getdb_token() {
        openDB();
        SQLiteDatabase database = tDB_warn.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT _token FROM Notify_Token", null);
        String[] token_tmp = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            for (int a = 0; a < cursor.getCount(); a++) {
                token_tmp[a] = cursor.getString(0);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return token_tmp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warn_pop);
        openDB();

        if (MainActivity.login_state == 1) {
            fam_token = Login_logout.get_Family_member_token();
            token_count = fam_token.length;
        } else {
            token_count = 0;
        }

        Button outwound = (Button) findViewById(R.id.outwound);
        Button heartp = (Button) findViewById(R.id.heartp);
        Button insidebody = (Button) findViewById(R.id.insidebody);
        Button other = (Button) findViewById(R.id.other);

        outwound.getBackground().setAlpha(255);
        heartp.getBackground().setAlpha(255);
        insidebody.getBackground().setAlpha(255);
        other.getBackground().setAlpha(255);
        outwound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.login_state == 0) {
                    fam_token = getdb_token();
                    if (fam_token.length > 0) {
                        for (int e = 0; e < fam_token.length; e++) {
                            new SendGCM(warn_pop.this).execute(getPushMessage(fam_token[e], "緊急通知! 家屬發生外傷!"));
                        }
                    } else {
                        Toast.makeText(warn_pop.this, "此裝置未曾用幸福老化帳戶登入過\n!手機離線資料庫無可通知對象!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    for (int w = 0; w < fam_token.length; w++) {
                        new SendGCM(warn_pop.this).execute(getPushMessage(fam_token[w], "緊急通知! 家屬發生外傷!"));
                    }

                }
                //new SendGCM(warn_pop.this).execute(getPushMessage(notify_token, "緊急通知! 家屬發生外傷"));
                insert_History("外傷");
            }
        });
        heartp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.login_state == 0) {
                    fam_token = getdb_token();
                    if (fam_token.length > 0) {
                        for (int h = 0; h < fam_token.length; h++) {
                            new SendGCM(warn_pop.this).execute(getPushMessage(fam_token[h], "緊急通知! 家屬發生心血管疾病!"));
                        }
                        //insert_History("心血管疾病");
                    } else {
                        Toast.makeText(warn_pop.this, "此裝置未曾用幸福老化帳戶登入過\n!手機離線資料庫無可通知對象!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    for (int t = 0; t < fam_token.length; t++) {
                        new SendGCM(warn_pop.this).execute(getPushMessage(fam_token[t], "緊急通知! 家屬發生心血管疾病!"));
                    }


                }
                insert_History("心血管疾病");
                //new SendGCM(warn_pop.this).execute(getPushMessage(notify_token, "緊急通知! 家屬發生心血管疾病"));
            }
        });
        insidebody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.login_state == 0) {
                    fam_token = getdb_token();
                    if (fam_token.length > 0) {
                        for (int i = 0; i < fam_token.length; i++) {
                            new SendGCM(warn_pop.this).execute(getPushMessage(fam_token[i], "緊急通知! 家屬發生器官問題!"));
                        }
                        //insert_History("器官問題");
                    } else {
                        Toast.makeText(warn_pop.this, "此裝置未曾用幸福老化帳戶登入過\n!手機離線資料庫無可通知對象!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    for (int b = 0; b < fam_token.length; b++) {
                        new SendGCM(warn_pop.this).execute(getPushMessage(fam_token[b], "緊急通知! 家屬發生器官問題!"));
                    }


                }
                insert_History("器官問題");
                //new SendGCM(warn_pop.this).execute(getPushMessage(notify_token, "緊急通知! 家屬發生器官問題"));
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.login_state == 0) {
                    fam_token = getdb_token();
                    if (fam_token.length > 0) {
                        for (int r = 0; r < fam_token.length; r++) {
                            new SendGCM(warn_pop.this).execute(getPushMessage(fam_token[r], "緊急通知! 家屬緊急疾病通知!"));
                        }
                        //insert_History("其他疾病");
                    } else {
                        Toast.makeText(warn_pop.this, "此裝置未曾用幸福老化帳戶登入過\n!手機離線資料庫無可通知對象!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    for (int s = 0; s < fam_token.length; s++) {
                        new SendGCM(warn_pop.this).execute(getPushMessage(fam_token[s], "緊急通知! 家屬緊急疾病通知!"));
                    }


                }
                insert_History("其他疾病");
                //new SendGCM(warn_pop.this).execute(getPushMessage(notify_token, "緊急通知! 家屬發生緊急事件"));
            }
        });

        DisplayMetrics dm_warn = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_warn);

        int width = dm_warn.widthPixels;
        int height = dm_warn.heightPixels;


        getWindow().setLayout((int) (width * .9), (int) (height * .7));

    }
}


    /*View.OnClickListener mHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.outwound:
                    new AsyncTask<Void, Void, String>(){

                        @Override
                        protected String doInBackground(Void... params) {
                            int status = -1;
                            String m = "";
                            try {
                                URL url = new URL("https://android.googleapis.com/gcm/send");


                                Bundle data_1 = new Bundle();
                                data_1.putString("message", "緊急通知! - 外傷");
                                String id = Integer.toString(msgId.incrementAndGet());

                                gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data_1);
                                m = "Sent message";

                            } catch (IOException e) {
                                m = "Error: " + e.getMessage();
                            }
                            return m;
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            Toast.makeText(getApplication(), "已發送緊急通知!", Toast.LENGTH_LONG).show();
                        }


                    };

                    break;

                case R.id.heartp:
                    break;

                case R.id.insidebody:
                    break;

                case R.id.other:
                    break;
            }

        }
    };*/

