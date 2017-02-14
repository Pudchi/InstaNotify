package com.pudchi.slidewarn;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class dbloginpage extends Activity{

    Button add;
    TextView errorlbl;
    EditText name, address, pincode;
    Connection connect;
    PreparedStatement preparedStatement;
    Statement st;
    String ipaddress, db, username, password;

    private Connection ConnectionHelper(String user, String password,
                                        String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://140.134.26.9:1433//lear_db;instance=SQLEXPRESS;"
                    + "databaseName=" + database + ";user=" + user
                    + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return connection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbloginpage);

        add = (Button) findViewById(R.id.btnadd);

        errorlbl = (TextView) findViewById(R.id.lblerror);

        name = (EditText) findViewById(R.id.txtname);
        address = (EditText) findViewById(R.id.txtaddress);
        pincode = (EditText) findViewById(R.id.txtpincode);

        ipaddress = "140.134.26.9";
        db = "AIP";
        username = "aip_group";
        password = "aipgroup1234";
        connect = ConnectionHelper(username, password, db, ipaddress);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    st = connect.createStatement();

                    preparedStatement = connect
                            .prepareStatement("insert into studentRecord(Name,Address,Pincode) values ('"
                                    + name.getText().toString()
                                    + "','"
                                    + address.getText().toString()
                                    + "','"
                                    + pincode.getText().toString() + "')");
                    preparedStatement.executeUpdate();
                    errorlbl.setText("Data Added successfully");
                } catch (SQLException e) {
                    errorlbl.setText(e.getMessage().toString());
                }

            }
        });

    }



}
