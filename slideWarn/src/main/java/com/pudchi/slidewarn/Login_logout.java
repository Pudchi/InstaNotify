package com.pudchi.slidewarn;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

public class Login_logout extends Activity {

    private static final String MD5 = "";
    static String server = "140.134.26.9:1433";
    static String root_username = "aip_group";
    static String root_password = "aipgroup1234";
    static String db_name = "AIP";
    static String correct_user_id;
    static String correct_user_pw;
    static int account_id = 0;
    static int member_count = 0;
    static String token_db;
    static String account_name = "";
    static String[] id_identity_final;
    static String[] history_detail_final;
    static Integer[] history_point_final;
    static String[] family_member_token_final;
    static java.sql.Date[] history_date_final;
    static Time[] history_time_final;
    static boolean sex;
    EditText account;
    EditText passwd;
    Button login;
    TextView error;
    ProgressBar login_progress;
    Connection conn;
    Statement st, st_2, st_3, st_4, st_5, st_6, st_7, st_8, st_9, st_10, st_11, st_12;
    int sql_login_case;
    tokenDB tDB_login;
    idDB idSQL;

    static public int get_account_id() {
        return account_id;
    }

    static public String get_account_name() {
        return account_name;
    }

    static public boolean get_sex() {
        return sex;
    }

    static public String[] get_id_identity() {
        return id_identity_final;
    }

    static public String[] get_history_detail() {
        return history_detail_final;
    }

    static public Integer[] get_history_point()
    {
        return history_point_final;
    }

    static public Time[] get_history_time() {
        return history_time_final;
    }

    static public java.sql.Date[] get_history_date() {
        return history_date_final;
    }

    static public String[] get_Family_member_token()
    {
        return family_member_token_final;
    }

    private void openDB() {
        tDB_login = new tokenDB(this);
    }

    private void openIDDB()
    {
        idSQL = new idDB(this);
    }

    void add(String t) {

        SQLiteDatabase db = tDB_login.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_token", t.toString());
        db.insertWithOnConflict("Notify_Token", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    void add_id(int i) {
        SQLiteDatabase id_db = idSQL.getWritableDatabase();
        ContentValues id_v = new ContentValues();
        id_v.put("_id", i);
        id_db.insertWithOnConflict("Login_Id", null, id_v, SQLiteDatabase.CONFLICT_ABORT);

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

    public boolean is_Connecting_to_internet_login() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_logout);
        account = (EditText) findViewById(R.id.accountinput);
        passwd = (EditText) findViewById(R.id.passwdinput);
        login = (Button) findViewById(R.id.loginbutton);
        error = (TextView) findViewById(R.id.error);
        login_progress = (ProgressBar) findViewById(R.id.login_progressBar);
        login_progress.setVisibility(View.GONE);

        token_db = RegistrationIntentService.get_In_app_token();
        openDB();
        openIDDB();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_Connecting_to_internet_login()) {
                    Start_Login begin_login = new Start_Login();
                    begin_login.execute("");
                } else {
                    error.setText("請檢查網路連線");
                }
            }
        });

    }

    public class Start_Login extends AsyncTask<String, String, String> {
        String mark = "";
        Boolean is_Success = false;
        String user_id = account.getText().toString();
        String user_pw = passwd.getText().toString();
        String origin_pw = user_id + user_pw;
        String md5_pw;

        @Override
        protected void onPreExecute() {
            login_progress.setVisibility(View.VISIBLE);
            Toast.makeText(Login_logout.this, "透過加密技術登入, 請耐心等候", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String s) {
            login_progress.setVisibility(View.GONE);
            error.setText(mark);
            Toast.makeText(Login_logout.this, s, Toast.LENGTH_SHORT).show();
            if (is_Success) {
                MainActivity.set_login_state(1);
                MainActivity.set_flag_refresh(true);
                correct_user_id = account.getText().toString();
                correct_user_pw = passwd.getText().toString();
                finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (user_id.trim().equals("") || user_pw.trim().equals("")) {
                mark = "請輸入帳號 & 密碼!";
            } else {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    byte[] coded = messageDigest.digest(origin_pw.getBytes());

                    md5_pw = Base64.encodeToString(coded, Base64.NO_WRAP);
                    Log.d("MD5", "MD5_PW: " + md5_pw);

                } catch (NoSuchAlgorithmException ne) {
                    mark = ne.getMessage().toString();
                    is_Success = false;

                } /*catch (UnsupportedEncodingException us_e)
                            {
                                mark = us_e.getMessage().toString();
                                is_Success = false;
                            }*/

                try {

                    conn = ConnectionHelper(root_username, root_password, db_name, server);

                    String account_Query =
                            "SELECT id FROM G1_User_Account WHERE email='" + user_id + "' AND passwd='" + md5_pw + "'";


                    st = conn.createStatement();
                    ResultSet rs = st.executeQuery(account_Query);

                    if (rs.next()) {
                        account_id = rs.getInt(1);
                        add_id(account_id);

                        String identity_Query =
                                "SELECT TypeName FROM G1_User_Identity WHERE id=" + account_id;

                        String name_Query =
                                "SELECT LName, FName, Sex FROM G1_User_Detail WHERE id=" + account_id;

                        String regis_Query =
                                "select * from G7_Gcm_Token where user_id=" + account_id + " AND regis_id='" + token_db + "'";

                        String token_Query =
                                "select * from G7_Gcm_Token where user_id=" + account_id;

                        String user_id_Query =
                                "select * from G7_Gcm_Token where regis_id='" + token_db + "'";

                        String token_insert =
                                "insert into G7_Gcm_Token values (" + account_id + ", '" + token_db + "')";

                        String pull_history =
                                "SELECT header, point, date, time FROM G7_Notify_Detail WHERE id=" + account_id;

                        String get_Family_member_id =
                                "SELECT id FROM G1_User_FamilyMember WHERE id_relatives=" + account_id;

                        st_3 = conn.createStatement();
                        ResultSet name_rs = st_3.executeQuery(name_Query);

                        if (name_rs.next()) {

                            account_name = name_rs.getString(2) + " " + name_rs.getString(1);
                            sex = name_rs.getBoolean(3);

                            st_11 = conn.createStatement();
                            ResultSet rs_identity = st_11.executeQuery(identity_Query);
                            ArrayList<String> identity_Data = new ArrayList<>();
                            while (rs_identity.next()) {
                                identity_Data.add(rs_identity.getString("TypeName"));
                            }

                            String[] id_identity = new String[identity_Data.size()];
                            id_identity = identity_Data.toArray(id_identity);

                            id_identity_final = id_identity;
                        }

                        st_12 = conn.createStatement();
                        ResultSet pull_his = st_12.executeQuery(pull_history);
                        ArrayList<String> his_detail = new ArrayList<>();
                        ArrayList<Integer> his_point = new ArrayList<>();
                        ArrayList<java.sql.Date> his_date = new ArrayList<>();
                        ArrayList<Time> his_time = new ArrayList<>();
                        while (pull_his.next()) {
                            his_detail.add(pull_his.getString("header"));
                            his_point.add(pull_his.getInt("point"));
                            his_date.add(pull_his.getDate("date"));
                            his_time.add(pull_his.getTime("time"));
                        }

                        String[] history_detail = new String[his_detail.size()];
                        history_detail = his_detail.toArray(history_detail);
                        Integer[] history_point = new Integer[his_point.size()];
                        history_point = his_point.toArray(history_point);
                        java.sql.Date[] history_date = new java.sql.Date[his_date.size()];
                        history_date = his_date.toArray(history_date);
                        Time[] history_time = new Time[his_time.size()];
                        history_time = his_time.toArray(history_time);

                        history_detail_final = history_detail;
                        history_point_final = history_point;
                        history_date_final = history_date;
                        history_time_final = history_time;

                        Statement st_13 = conn.createStatement();
                        ResultSet family_id = st_13.executeQuery(get_Family_member_id);
                        ArrayList<Integer> family_mid = new ArrayList<>();
                        while (family_id.next()) {
                            family_mid.add(family_id.getInt("id"));
                        }
                        Integer[] fm_id = new Integer[family_mid.size()];
                        fm_id = family_mid.toArray(fm_id);
                        member_count = fm_id.length;

                        ArrayList<String> family_token_tmp = new ArrayList<>();
                        Statement st_14 = conn.createStatement();
                        //"select * from G7_Gcm_Token where user_id=" + account_id;
                        for (int y = 0; y < member_count; y++) {
                            ResultSet get_fam_token = st_14.executeQuery("SELECT regis_id from G7_Gcm_Token where user_id=" + fm_id[y]);
                            if (get_fam_token.next()) {
                                family_token_tmp.add(get_fam_token.getString("regis_id"));
                            }
                        }
                        String[] tmp = new String[family_token_tmp.size()];
                        tmp = family_token_tmp.toArray(tmp);
                        family_member_token_final = tmp;
                        int token_num = family_member_token_final.length;
                        for (int m = 0; m < token_num; m++) {
                            add(family_member_token_final[m]);
                        }


                        st_2 = conn.createStatement();
                        ResultSet regis_rs = st_2.executeQuery(regis_Query);
                        if (regis_rs.next()) {
                            sql_login_case = 3;
                        } else {
                            sql_login_case = 0;
                        }

                        st_5 = conn.createStatement();
                        ResultSet rs_user_id = st_5.executeQuery(user_id_Query);
                        if (rs_user_id.next()) {
                            int tmp_id = rs_user_id.getInt(1);
                            if (tmp_id != account_id) {
                                sql_login_case = 1;
                            }
                        }


                        st_6 = conn.createStatement();
                        ResultSet rs_token = st_6.executeQuery(token_Query);
                        if (rs_token.next()) {
                            String tmp_token = rs_token.getString(2);
                            if (tmp_token.equals(token_db) == false) {
                                sql_login_case = 2;
                            }
                        }


                        switch (sql_login_case) {
                            case 1:
                                String delete_data_id =
                                        "DELETE FROM G7_Gcm_Token WHERE regis_id='" + token_db + "'";
                                st_7 = conn.createStatement();
                                st_7.executeUpdate(delete_data_id);
                                st_8 = conn.createStatement();
                                st_8.executeUpdate(token_insert);

                                mark = "登入成功!";
                                conn.close();
                                is_Success = true;

                                break;

                            case 2:
                                String delete_data_token =
                                        "DELETE FROM G7_Gcm_Token WHERE user_id=" + account_id;

                                st_9 = conn.createStatement();
                                st_9.executeUpdate(delete_data_token);

                                st_10 = conn.createStatement();
                                st_10.executeUpdate(token_insert);
                                mark = "登入成功!";
                                conn.close();
                                is_Success = true;

                                break;
                            case 3:
                                mark = "登入成功!";
                                conn.close();
                                is_Success = true;
                                break;
                            case 0:
                                st_4 = conn.createStatement();
                                st_4.executeUpdate(token_insert);

                                mark = "登入成功!";
                                conn.close();
                                is_Success = true;
                                break;
                        }
                    } else {
                        mark = "錯誤的帳號 or 密碼!";
                        conn.close();
                        is_Success = false;

                    }


                } catch (SQLException ex) {
                    mark = ex.getMessage().toString();
                    is_Success = false;
                }
            }
            return mark;
        }
    }
}

/*String regis_Query =
        "select * from G7_Gcm_Token where regis_id=" + token_db + "'";*/

/*st_2 = conn.createStatement();
                        ResultSet rs_regis = st_2.executeQuery(regis_Query);

                        if(rs_regis.next()) {

                            String update = "update G7_Gcm_Token set user_id=" + account_id + "where regis_id='" + token_db + "'";

                            st_3 = conn.createStatement();
                            st_3.executeUpdate(update);
                            mark = "登入成功!";
                            is_Success = true;
                        }*/

                            /*String token_insert =
                                    "insert into G7_Gcm_Token values (" + account_id + ", '" + token_db + "')";
                            st_4 = conn.createStatement();
                            st_4.executeUpdate(token_insert);*/