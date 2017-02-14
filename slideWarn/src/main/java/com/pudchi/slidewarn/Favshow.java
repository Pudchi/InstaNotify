package com.pudchi.slidewarn;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pudchi.slidewarn.R;

public class Favshow extends Activity {

    String[] name = {"Lollipop", "Android", "海綿寶寶"};
    int[] pics = new int[]{R.drawable.l_image, R.drawable.android_icon, R.drawable.sponge};
    //MyAdapter adapter = null;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favshow);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_fav);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mAdapter);

        /*adapter = new MyAdapter(this);
        favlist.setAdapter(adapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favshow, menu);
        return true;
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

    /*public class MyAdapter extends BaseAdapter
    {
        private LayoutInflater myInflater;

        public MyAdapter(Context c)
        {
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = myInflater.inflate(R.layout.favlist, null);

            ImageView pic = (ImageView) convertView.findViewById(R.id.infopic);
            TextView t = (TextView) convertView.findViewById(R.id.name);

            pic.setImageResource(pics[position]);
            t.setText(name[position]);

            return convertView;
        }
    }*/
