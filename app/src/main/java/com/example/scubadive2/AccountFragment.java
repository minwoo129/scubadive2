package com.example.scubadive2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AccountFragment extends Fragment {

    LicenceAdapter adapter;
    ListView licLv;
    TextView belongTv, maxDepthTv;
    int selectItem1 = 0, selectItem2 = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accountfragment, container, false);
        TextView diverNameTv = view.findViewById(R.id.diverNameTv),
                birthTv1 = view.findViewById(R.id.birthTv1),
                emailTv = view.findViewById(R.id.emailTv);
        belongTv = view.findViewById(R.id.belongTv);
        maxDepthTv = view.findViewById(R.id.maxDepthTv);
        Button modAccBtn = view.findViewById(R.id.modAccBtn),
                addLicBtn = view.findViewById(R.id.addLicBtn);


        diverNameTv.setText(GetDataActivity.user.getUserName());
        birthTv1.setText(GetDataActivity.user.getBirthD8());
        emailTv.setText(GetDataActivity.user.getEmail());
        char belong = GetDataActivity.user.getLicenceCode().charAt(0);
        if(belong == 'P') belongTv.setText("PADI");
        else if(belong == 'N') belongTv.setText("NAUI");
        else if(belong == 'S') belongTv.setText("SSI");

        char level = GetDataActivity.user.getLicenceCode().charAt(1);
        if(level == '1') maxDepthTv.setText("오픈워터");
        else if(level == '2') maxDepthTv.setText("어드벤스트 오픈워터");
        else if(level == '3') maxDepthTv.setText("레스큐");

        adapter = new LicenceAdapter((MainActivity)getActivity());
        licLv = (ListView)view.findViewById(R.id.licLv);
        licLv.setAdapter(adapter);

        modAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setTitle("개인정보수정");
                LayoutInflater inflater1 = getLayoutInflater();
                View v1 = inflater1.inflate(R.layout.mod_account_dialog, null);
                final EditText modPwEt = v1.findViewById(R.id.modPwEt);
                final EditText modPwEt1 = v1.findViewById(R.id.modPwEt1);
                final EditText modEmailEt = v1.findViewById(R.id.modEmailEt);
                final EditText modNameEt = v1.findViewById(R.id.modNameEt);

                modEmailEt.setText(GetDataActivity.user.getEmail(), TextView.BufferType.EDITABLE);
                modNameEt.setText(GetDataActivity.user.getUserName(), TextView.BufferType.EDITABLE);
                ab.setView(v1);
                ab.setPositiveButton("변경하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(modPwEt.getText().toString() != null && modPwEt1.getText().toString() != null) {
                            if(modPwEt.getText().toString().equals(modPwEt1.getText().toString())) {
                                GetDataActivity.user.setUserPw(encoder(modPwEt.getText().toString()));
                                GetDataActivity.user.setEmail(modEmailEt.getText().toString());
                                GetDataActivity.user.setUserName(modNameEt.getText().toString());
                                GetDataActivity.activeMode = GetDataActivity.MODIFY_ACCOUNT;
                                Intent intent = new Intent(getActivity(), GetDataActivity.class);
                                startActivityForResult(intent, 2060);
                            }
                        }
                    }
                });
                ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ab.show();

            }
        });

        addLicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setTitle("자격증 추가");
                LayoutInflater inflater2 = getLayoutInflater();
                View v2 = inflater2.inflate(R.layout.add_licence_dialog, null);
                final RadioGroup liRG1 = v2.findViewById(R.id.liRG1);
                final RadioGroup liRG2 = v2.findViewById(R.id.liRG2);
                final EditText liNumEt1 = v2.findViewById(R.id.liNumEt1);
                final String[] strs = new String[2];
                ab.setView(v2);
                liRG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.liRb11:
                                strs[0] = "P";
                                break;
                            case R.id.liRb12:
                                strs[0] = "N";
                                break;
                            case R.id.liRb13:
                                strs[0] = "S";
                                break;
                        }
                    }
                });
                liRG2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.liRb21:
                                strs[1] = "1";
                                break;
                            case R.id.liRb22:
                                strs[1] = "2";
                                break;
                            case R.id.liRb23:
                                strs[1] = "3";
                                break;
                        }
                    }
                });

                ab.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Licence li = new Licence(strs[0]+strs[1], liNumEt1.getText().toString());
                        GetDataActivity.licenceArr.add(li);
                        GetDataActivity.activeMode = GetDataActivity.ADD_LICENCE;
                        Intent intent = new Intent(getActivity(), GetDataActivity.class);
                        startActivityForResult(intent, 2060);

                    }
                });
                ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ab.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2060) {
            adapter.notifyDataSetChanged();

            char belong = GetDataActivity.user.getLicenceCode().charAt(0);
            if(belong == 'P') belongTv.setText("PADI");
            else if(belong == 'N') belongTv.setText("NAUI");
            else if(belong == 'S') belongTv.setText("SSI");

            char level = GetDataActivity.user.getLicenceCode().charAt(1);
            if(level == '1') maxDepthTv.setText("오픈워터");
            else if(level == '2') maxDepthTv.setText("어드벤스트 오픈워터");
            else if(level == '3') maxDepthTv.setText("레스큐");
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
