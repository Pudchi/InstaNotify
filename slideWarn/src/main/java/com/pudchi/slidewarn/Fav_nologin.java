package com.pudchi.slidewarn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fav_nologin extends Fragment {

    public static final String PAGE = "PAGE";
    protected int mPage;

    TextView lbl;
    Button gotologin;

    public static Fav_nologin newIns(int page)
    {
        Bundle args = new Bundle();
        args.putInt(PAGE, page);
        Fav_nologin fragment = new Fav_nologin();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_fav_nologin, container, false);
        lbl = (TextView) view.findViewById(R.id.nonlogin_lbl);
        gotologin = (Button) view.findViewById(R.id.gotologin);

        gotologin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent gotologin = new Intent();
                gotologin.setClass(getContext(), Login_logout.class);
                startActivity(gotologin);
            }
        });

        return view;
    }
}
