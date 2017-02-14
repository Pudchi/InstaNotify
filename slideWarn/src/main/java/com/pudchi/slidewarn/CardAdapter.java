package com.pudchi.slidewarn;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>
{

    String[] name = {"黃品綸","呂昀儒","王裕鈞","海綿寶寶","IRON MAN"};

    java.sql.Date[] date = Login_logout.get_history_date();
    Time[] time = Login_logout.get_history_time();
    String[] header = Login_logout.get_history_detail();
    Integer[] point_count = Login_logout.get_history_point();
    int[] pics = new int[]{R.drawable.ic_error_black_48dp, R.drawable.ic_check_circle_black_48dp};

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    //df.format();


    ArrayList<HistoryItem> hisItems;
    int num = point_count.length;
    String tmp_time = "";
    private ViewHolder.ClickHandler clickHandler;

    public CardAdapter()
    {
        super();
        hisItems = new ArrayList<HistoryItem>();
        HistoryItem historyItem = new HistoryItem();

        if (num >= 1) {
            for (int x = 0; x < num; x++) {
                historyItem = new HistoryItem();
                historyItem.setDrawable(pics[0]);
                //tmp_time = date[x].toString() + " " + time[x].toString().substring(0, 4);
                historyItem.setTime(date[x].toString() + " " + time[x].toString().substring(0, 5));
                historyItem.setHeader(header[x]);
                historyItem.setPoint(point_count[x].toString());
                hisItems.add(historyItem);
                //tmp_time = "";
            }
        } else {
            historyItem.setDrawable(pics[0]);
            historyItem.setHeader("帳號中沒有通知紀錄");
            historyItem.setPoint("帳號中沒有通知紀錄");
            hisItems.add(historyItem);
        }


        /*historyItem.setDrawable(pics[0]);
        historyItem.setTime(timelist[0]);
        historyItem.setHeader(header[0]);
        historyItem.setPoint(point_count[0]);
        hisItems.add(historyItem);

        historyItem = new HistoryItem();
        historyItem.setDrawable(pics[0]);
        historyItem.setTime(timelist[1]);
        historyItem.setHeader(header[1]);
        historyItem.setPoint(point_count[1]);
        hisItems.add(historyItem);

        historyItem = new HistoryItem();
        historyItem.setDrawable(pics[0]);
        historyItem.setTime(timelist[2]);
        historyItem.setHeader(header[2]);
        historyItem.setPoint(point_count[2]);
        hisItems.add(historyItem);

        historyItem = new HistoryItem();
        historyItem.setDrawable(pics[0]);
        historyItem.setTime(timelist[3]);
        historyItem.setHeader(header[3]);
        historyItem.setPoint(point_count[3]);
        hisItems.add(historyItem);

        historyItem = new HistoryItem();
        historyItem.setDrawable(pics[0]);
        historyItem.setTime(timelist[4]);
        historyItem.setHeader(header[4]);
        historyItem.setPoint(point_count[4]);
        hisItems.add(historyItem);*/

    }

    public CardAdapter(ArrayList<HistoryItem> historyItems, ViewHolder.ClickHandler handler) {
        this.hisItems = historyItems;
        this.clickHandler = handler;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.historylist, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v, clickHandler);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position)
    {
        HistoryItem his = hisItems.get(position);

        if (his == null) {
            return;
        }

        viewHolder.img_read.setImageResource(his.getDrawable());
        viewHolder.txt_time.setText(his.getTime());
        viewHolder.txt_header.setText(his.getHeader());
        viewHolder.txt_point.setText(his.getPoint());

        /*viewHolder.img_read.setImageResource(pics[0]);
        viewHolder.txt_time.setText(date[position].toString() + " " + time[position].toString().substring(0, 4));
        viewHolder.txt_header.setText(header[position]);
        viewHolder.txt_point.setText(point_count[position]);*/

    }


    @Override
    public int getItemCount() {
        return hisItems.size();
    }

    public HistoryItem getItem(int position)
    {
        return hisItems.get(position);
    }

    public void addItem(HistoryItem h) {

        hisItems.add(h);
    }

    public void clear() {
        this.notifyItemRangeChanged(0, hisItems.size());
        hisItems.clear();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img_read;
        public TextView txt_time;
        public TextView txt_header;
        public TextView txt_point;

        public ClickHandler click_handler;


        public ViewHolder(View itemView, ClickHandler handler) {
            super(itemView);
            img_read = (ImageView) itemView.findViewById(R.id.read);
            txt_time = (TextView) itemView.findViewById(R.id.time_txt);
            txt_header = (TextView) itemView.findViewById(R.id.header_txt);
            txt_point = (TextView) itemView.findViewById(R.id.point_txt);

            click_handler = handler;
        }

        public void onClick(View view) {
            int position = getPosition();

            click_handler.onClick(view, position);
        }

        public interface ClickHandler {
            void onClick(View caller, int position);
        }




    }
}

/*String[] h = {"擦傷", "跌倒", "手腕扭傷", "骨折", "車禍"};
String[] p = {"5", "15", "10", "25", "30"};
String[] t = {"2015-08-04 8:24", "2015-08-05 10:35", "2015-08-06 21:24", "2015-11-08 10:24", "2015-11-09 10:36"};
*/