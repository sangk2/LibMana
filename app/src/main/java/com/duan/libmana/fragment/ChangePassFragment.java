package com.duan.libmana.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.duan.libmana.R;
import com.duan.libmana.dao.ThuThuDAO;
import com.duan.libmana.model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePassFragment extends Fragment {

    TextInputEditText edPassOld, edPassNew, edRePass;
    Button btnSave, btnCancel;
    ThuThuDAO thuThuDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_pass, container, false);

        edPassOld = v.findViewById(R.id.edPassOld);
        edPassNew = v.findViewById(R.id.edPassNew);
        edRePass = v.findViewById(R.id.edRePass);
        btnSave = v.findViewById(R.id.btnSave);
        btnCancel = v.findViewById(R.id.btnCancel);

        thuThuDAO = new ThuThuDAO(getActivity());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOld.setText("");
                edPassNew.setText("");
                edRePass.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // đọc user, pass trong SharedPreferences
                SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = preferences.getString("USERNAME","");
                if(kiemTra() > 0){
                    ThuThu thuThu = thuThuDAO.getID(user);
                    thuThu.matKhau = edPassNew.getText().toString();

                    //thuThuDAO.updatePass(thuThu);

                    if (thuThuDAO.updatePass(thuThu) > 0){
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thành công!",
                                Toast.LENGTH_SHORT).show();

                        btnCancel.isFocusable();
                    }else {
                        Toast.makeText(getActivity(),"Thay đổi mật khẩu thất bại",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }
    public int kiemTra(){
        int check = 1;
        if (edPassOld.getText().length() == 0 || edPassNew.getText().length() == 0 ||
                edRePass.getText().length() == 0){
            Toast.makeText(getContext(),"Bạn hãy nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            // đọc user, pass trong SharedPreferences
            SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE",Context.MODE_PRIVATE);
            String pass = preferences.getString("PASSWORD","");

            String passOld = edPassOld.getText().toString();
            String passNew = edPassNew.getText().toString();
            String rePass = edRePass.getText().toString();

            if (!pass.equals(passOld)){
                Toast.makeText(getContext(),"Mật khẩu cũ sai",Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!passNew.equals(rePass)){
                Toast.makeText(getContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}