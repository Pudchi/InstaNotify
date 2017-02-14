package com.pudchi.slidewarn;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class dbhistorylist extends Activity
{

    String[] timelist = {"2015-08-04 8:24", "2015-08-05 10:35", "2015-08-06 21:24"};
    String[] header = {"擦傷", "跌倒", "手腕扭傷"};
    String[] pointcount = {"5", "15", "10"};
    int[] pic = {R.drawable.ic_error_black_48dp, R.drawable.ic_check_circle_black_48dp};

    HistoryAdapter hisadapter = null;


    private ListView listHistory;

    public class HistoryAdapter extends BaseAdapter
    {
        private LayoutInflater hisInflater;

        public HistoryAdapter(Context c)
        {
            hisInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return header.length;
        }

        @Override
        public Object getItem(int position) {
            return header[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = hisInflater.inflate(R.layout.historylist, null);

            ImageView p = (ImageView) convertView.findViewById(R.id.read);
            TextView time_lbl = (TextView) convertView.findViewById(R.id.time_lbl);
            TextView header_lbl = (TextView) convertView.findViewById(R.id.header_lbl);
            TextView point_lbl = (TextView) convertView.findViewById(R.id.point_lbl);
            TextView time_txt = (TextView) convertView.findViewById(R.id.time_txt);
            TextView header_txt = (TextView) convertView.findViewById(R.id.header_txt);
            TextView point_txt = (TextView) convertView.findViewById(R.id.point_txt);

            p.setImageResource(pic[1]);
            time_txt.setText(timelist[position]);
            header_txt.setText(header[position]);
            point_txt.setText(pointcount[position]);

            return convertView;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbhistorylist);

        listHistory = (ListView) findViewById(R.id.list_history);

        hisadapter = new HistoryAdapter(this);

        listHistory.setAdapter(hisadapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dbhistorylist, menu);
        return true;
    }

    /*public class HistoryAdapter extends BaseAdapter {
        private LayoutInflater hisInflater;

        public HistoryAdapter(Context c) {
            hisInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return header.length;
        }

        @Override
        public Object getItem(int position) {
            return header[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = hisInflater.inflate(R.layout.historylist, null);

            ImageView p = (ImageView) convertView.findViewById(R.id.read);
            TextView time_lbl = (TextView) convertView.findViewById(R.id.time_lbl);
            TextView header_lbl = (TextView) convertView.findViewById(R.id.header_lbl);
            TextView point_lbl = (TextView) convertView.findViewById(R.id.point_lbl);
            TextView time_txt = (TextView) convertView.findViewById(R.id.time_txt);
            TextView header_txt = (TextView) convertView.findViewById(R.id.header_txt);
            TextView point_txt = (TextView) convertView.findViewById(R.id.point_txt);

            p.setImageResource(pic[1]);
            time_txt.setText(timelist[position]);
            header_txt.setText(header[position]);
            point_txt.setText(pointcount[position]);

            return convertView;
        }
    }*/

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
};

    /*private  Runnable addlist = new Runnable() {
        @Override
        public void run() {
            listhistoryAdapter.add(hlist.get(0));
            hlist.remove(0);
        }
    };*/

    /*private void setView()
    {
        listHistory = (ListView) findViewById(R.id.list_history);
        hlist.add(new HistoryItem(iconnot, 0, 0, "2015-08-04 8:24", "小擦傷", 10));
        hlist.add(new HistoryItem(iconnot, 0, 1, "2015-08-05 10:35", "手腕扭到", 20));
        hlist.add(new HistoryItem(iconnot, 0, 2, "2015-08-06 21:24", "車禍", 30));

        listHistory.setAdapter(listhistoryAdapter);

    }*/







