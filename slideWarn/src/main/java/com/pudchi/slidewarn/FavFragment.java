package com.pudchi.slidewarn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Connection;

public class FavFragment extends Fragment implements UpdateableFragment{

	public static final String PAGE = "PAGE";
    public static final int posi = 4;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    TextView lbl;
    Button gotologin;

    static int i = 0;

	private int mPage;

	public static FavFragment newIns(int page)
	{
		Bundle args = new Bundle();
		args.putInt(PAGE, page);
        FavFragment fragment = new FavFragment();
        fragment.setArguments(args);
        return fragment;

	}


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        int s = MainActivity.get_login_state();
        i = s;
        super.onResume();

    }

    @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mPage = getArguments().getInt(PAGE);

	}

	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

        if (i >= 1)
        {
            View view = inflater.inflate(R.layout.fragment_favor, container, false);


            mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fav);
            mRecyclerView.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(getContext());

            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new CardAdapter();
            mRecyclerView.setAdapter(mAdapter);

            return view;
        }
        else
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
                    onStop();
                    onDestroyView();
                }
            });

            return view;
        }
	}


    @Override
    public void update(int data) {
        i = data;
    }
}
