package com.example.scubadive2;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {


    public static LogFragment newInstance() {
        return new LogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loginfragment, container, false);

        final EditText userIdEt = view.findViewById(R.id.userIdEt),
                userPwEt = view.findViewById(R.id.userPwEt);
        Button loginBtn = view.findViewById(R.id.loginBtn),
                joinBtn = view.findViewById(R.id.joinBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userIdEt.getText().toString();
                String userPW = encoder(userPwEt.getText().toString());

                Intent intent = new Intent(getActivity(), SendActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("userPw", userPW);
                startActivityForResult(intent, 2010);
            }
        });
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JoinActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2010) {
            if(data.getStringExtra("count").equals("1")) {
                ((MainActivity)getActivity()).replaceFragment(newInstance());
            }
            else {
                Log.d("kkk", "로그인실패");
            }
        }
    }

    public static String encoder(String data) {
        String retVal = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(data.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for(int i=0; i<byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }

            retVal = sb.toString();

        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return retVal;

    }
}
