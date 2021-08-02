package com.example.scubadive2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
    LayoutInflater lnf;

    public ListViewAdapter(Activity context) {
        lnf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return GetDataActivity.logArr.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return GetDataActivity.logArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RowDataViewHolder viewHolder;
        if(convertView == null) {
            convertView = lnf.inflate(R.layout.row, parent, false);
            viewHolder = new RowDataViewHolder();
            viewHolder.dateHolder = convertView.findViewById(R.id.dateTv);
            viewHolder.timeHolder = convertView.findViewById(R.id.timeTv);
            viewHolder.locationHolder = convertView.findViewById(R.id.locationTv);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RowDataViewHolder)convertView.getTag();
        }

        viewHolder.dateHolder.setText(GetDataActivity.logArr.get(position).getDate());
        viewHolder.timeHolder.setText(GetDataActivity.logArr.get(position).getTime());
        viewHolder.locationHolder.setText(GetDataActivity.logArr.get(position).getPoint());

        return convertView;
    }

    public class RowDataViewHolder {
        public TextView dateHolder;
        public TextView timeHolder;
        public TextView locationHolder;
    }

}
