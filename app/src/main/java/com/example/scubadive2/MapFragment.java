package com.example.scubadive2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MapFragment extends Fragment {
    Button goMapBt;
    TextView lettOutputTv, longOutputTv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapfragment, container, false);

        goMapBt = (Button)view.findViewById(R.id.goMapBt);
        lettOutputTv = (TextView)view.findViewById(R.id.lettOutputTv);
        longOutputTv = (TextView)view.findViewById(R.id.longOutputTv);

        goMapBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((MainActivity)getActivity(), com.example.scubadive2.MapActivity.class);
                startActivityForResult(intent, 2000);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2000) {
            double lati = data.getDoubleExtra("lat", 0);
            double longi = data.getDoubleExtra("long", 0);
            double lati1 = lati, longi1 = longi;
            lati = Math.abs(lati); longi = Math.abs(longi);
            String latitude = "", longitude = "";
            latitude += (String.valueOf((int)lati)+"°");
            lati = (lati-(int)lati)*60;
            latitude += (String.valueOf((int)lati)+"\'");
            lati = (lati-(int)lati)*60;
            latitude += (String.valueOf(lati)+"\"");
            if(lati1 > 0) latitude += "N";
            else latitude += "S";

            longitude += (String.valueOf((int)longi)+"°");
            longi = (longi - (int)longi)*60;
            longitude += (String.valueOf((int)longi)+"\'");
            longi = (longi - (int)longi)*60;
            longitude += (String.valueOf(longi)+"\"");
            if(longi1 > 0) longitude += "E";
            else longitude += "W";
            lettOutputTv.setText(latitude);
            longOutputTv.setText(longitude);
            Toast.makeText(getActivity(), "좌표가 입력되었습니다", Toast.LENGTH_LONG).show();
        }
    }


}
