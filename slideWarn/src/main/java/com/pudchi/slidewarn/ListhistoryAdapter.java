package com.pudchi.slidewarn;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListhistoryAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    private int count = 0;
    ArrayList<HistoryItem> historyItemArrayList = new ArrayList<>();
    Context context;


    public ListhistoryAdapter(Context context)
    {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*private class ViewHolder
    {
        ImageView imgRead;
        TextView txtTime;
        TextView txtHeader;
        TextView txtPoint;
    }*/


    @Override
    public int getCount() {
        return count;

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imgRead;
        TextView txtTime;
        TextView txtHeader;
        TextView txtPoint;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.historylist, null);
            holder = new ViewHolder();

            holder.imgRead = (ImageView) convertView.findViewById(R.id.read);
            holder.txtTime = (TextView) convertView.findViewById(R.id.time_txt);
            holder.txtHeader = (TextView) convertView.findViewById(R.id.header_txt);
            holder.txtPoint = (TextView) convertView.findViewById(R.id.point_txt);

            convertView.setTag(holder);
        } else
            {
                holder = (ViewHolder) convertView.getTag();
            }

        int color_back[] = {Color.TRANSPARENT,Color.LTGRAY, Color.MAGENTA};
        int emergencynum = historyItemArrayList.get(position).emergencylevel;

        holder.txtHeader.setBackgroundColor(color_back[emergencynum]);
        holder.txtTime.setText(historyItemArrayList.get(position).time.toString());
        holder.txtHeader.setText(historyItemArrayList.get(position).header);
        holder.txtPoint.setText(historyItemArrayList.get(position).point);
        //holder.imgRead.setImageDrawable(list.getDrawableId());


        return convertView;
    }


}
