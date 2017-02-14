package com.pudchi.slidewarn;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class consql{
        private static boolean _isOpened = false;
        String userid, password, ipaddress;

        static String ifopen = "nega";

        public Connection connect;
        Statement st;

        String db = "AIP";

        public boolean isOpened()
        {
            return _isOpened;
        }

        @SuppressLint("NewApi")
        private static Connection ConnectionHelper(String user, String password, String database, String server)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            Connection connection = null;
            String ConnectionURL = null;

            try
            {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://140.134.26.9:1433;"
                        + "databaseName=AIP;user=" + user
                        + ";password=" + password + ";";

                connection = DriverManager.getConnection(ConnectionURL);
            } catch (SQLException se)
            {
                Log.e("ERROR", se.getMessage());
            } catch (ClassNotFoundException e)
            {
                Log.e("ERROR", e.getMessage());
            } catch (Exception e)
            {
                Log.e("ERROR", e.getMessage());
            }

            return connection;
        }

        public static String msql()
        {
            //設定連結字串

            try
            {


                Connection connect = ConnectionHelper("aip_group", "aipgroup1234", "AIP", "140.134.26.9");

                if (connect.isClosed() == false)
                {
                    _isOpened = true;
                    ifopen = "Success!";
                    System.out.println("Connection oklogo");
                }
                else
                {
                    _isOpened = false;
                    System.out.println("Connect Fail");
                }

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return ifopen;
        }

        public void main(String[] args)
        {
            msql();
        }
}
