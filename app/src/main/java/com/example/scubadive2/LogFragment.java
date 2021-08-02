package com.example.scubadive2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;

public class LogFragment extends Fragment {

    ListViewAdapter adapter;
    ListView logLv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logfragment, container, false);
        ArrayList<String> arr = new ArrayList<>();

        Button addLogBt = (Button)view.findViewById(R.id.addLogBt);

        adapter = new ListViewAdapter((MainActivity)getActivity());
        logLv = (ListView)view.findViewById(R.id.logLv);
        logLv.setAdapter(adapter);

        if(GetDataActivity.user != null) {
            GetDataActivity.activeMode = GetDataActivity.GET_DATA;
            Intent intent = new Intent(getActivity(), GetDataActivity.class);
            startActivityForResult(intent, 2050);
        }


        logLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent((MainActivity)getActivity(), ModifyLogActivity.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });

        addLogBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddLogActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2050) {
            adapter.notifyDataSetChanged();
        }
    }

}
