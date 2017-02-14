package com.pudchi.slidewarn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AdvFragment extends Fragment{

	public static final String PAGE = "PAGE";
	
	private int mPage;
	
	public static AdvFragment newIns(int page)
	{
		Bundle args = new Bundle();
		args.putInt(PAGE, page);
        AdvFragment fragment = new AdvFragment();
        fragment.setArguments(args);
        return fragment;

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
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_adv, container, false);
		TextView tvhis = (TextView) view.findViewById(R.id.tvhis);
		Button tohepler = (Button) view.findViewById(R.id.warn);
		Button tocar = (Button) view.findViewById(R.id.contact);
		Button toresource = (Button) view.findViewById(R.id.resource);

		tohepler.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), advhelper.class);
				startActivity(intent);

			}
		});

        tocar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), advcar.class);
                startActivity(intent2);
            }
        });

		toresource.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent i3 = new Intent();
				i3.setClass(getActivity(), advresource.class);
				startActivity(i3);
			}
		});

		return view;
	}
	
	

}
