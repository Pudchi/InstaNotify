package com.pudchi.slidewarn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class personal_info extends Activity {

    static ImageView info_pic;
    static TextView user_id;
    static TextView user_name;
    static TextView user_sex;
    static TextView user_identity;

    String user_id_from_login;
    int id_from_login = Login_logout.get_account_id();
    String name_from_login = Login_logout.get_account_name();
    boolean sex = Login_logout.get_sex();

    String[] identity = Login_logout.get_id_identity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        info_pic = (ImageView) findViewById(R.id.person_pic);
        user_id = (TextView) findViewById(R.id.id_txt);
        user_name = (TextView) findViewById(R.id.name_txt);
        user_sex = (TextView) findViewById(R.id.sex_txt);
        user_identity = (TextView) findViewById(R.id.identity_txt);

        user_id_from_login = new String(""+id_from_login);

        user_id.setText(user_id_from_login);
        user_name.setText(name_from_login);

        if(sex == true)
        {
            user_sex.setText("男");
        }
        else if (sex == false)
        {
            user_sex.setText("女");
        }

        StringBuilder builder = new StringBuilder();
        for (String s : identity) {
            builder.append(s);
            builder.append("  ");
        }

        user_identity.setText(builder.substring(0, builder.length() - 2));

    }

}
