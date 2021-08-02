package com.example.scubadive2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LicenceAdapter extends BaseAdapter {
    LayoutInflater lnf;

    public LicenceAdapter(Activity context) {
        lnf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return GetDataActivity.licenceArr.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return GetDataActivity.licenceArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RowDataViewHolder viewHolder;
        if(convertView == null) {
            convertView = lnf.inflate(R.layout.row1, parent, false);
            viewHolder = new RowDataViewHolder();
            viewHolder.licBelongHolder = convertView.findViewById(R.id.licBelongTv);
            viewHolder.licCodeHolder = convertView.findViewById(R.id.licCodeTv);
            viewHolder.licNoHolder = convertView.findViewById(R.id.licNoTv);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RowDataViewHolder)convertView.getTag();
        }
        char belong = GetDataActivity.licenceArr.get(position).getLicenceCode().charAt(0);
        String belong1 = "";
        if(belong == 'P') belong1 = "PADI";
        else if(belong == 'N') belong1 = "NAUI";
        else if(belong == 'S') belong1 = "SSI";

        char level = GetDataActivity.licenceArr.get(position).getLicenceCode().charAt(1);
        String level1 = "";
        if(level == '1') level1 = "오픈워터";
        else if(level == '2') level1 = "어드벤스트 오픈워터";
        else if(level == '3') level1 = "레스큐";

        viewHolder.licBelongHolder.setText(belong1);
        viewHolder.licCodeHolder.setText(level1);
        viewHolder.licNoHolder.setText(GetDataActivity.licenceArr.get(position).getLicenceNo());

        return convertView;
    }

    public class RowDataViewHolder {
        public TextView licBelongHolder;
        public TextView licCodeHolder;
        public TextView licNoHolder;
    }

}
