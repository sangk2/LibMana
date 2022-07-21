package com.duan.libmana.fragment;

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

public class AddUserFragment extends Fragment {

    TextInputEditText edUser, edHoTen, edPass, edRePass;
    Button btnSave, btnCancel;

    ThuThuDAO dao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_add_user, container, false);

        edUser = v.findViewById(R.id.edUser);
        edHoTen = v.findViewById(R.id.edHoTen);
        edPass = v.findViewById(R.id.edPass);
        edRePass = v.findViewById(R.id.edRePass);
        btnSave = v.findViewById(R.id.btnSave);
        btnCancel = v.findViewById(R.id.btnCancel);

        dao = new ThuThuDAO(getActivity());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuThu thuThu = new ThuThu();
                thuThu.maTT = edUser.getText().toString();
                thuThu.hoTen = edHoTen.getText().toString();
                thuThu.matKhau = edPass.getText().toString();

                if (kiemTra() > 0){
                    if (dao.insert(thuThu) > 0){
                        Toast.makeText(getActivity(),"Thêm thành viên thành công",Toast.LENGTH_SHORT).show();
                        reset();
                    }else {
                        Toast.makeText(getActivity(),"Thêm thành viên thất bại",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return v;
    }

    private void reset(){
        edUser.setText("");
        edHoTen.setText("");
        edPass.setText("");
        edRePass.setText("");
    }

    private int kiemTra(){
        int check = 1;
        if (edUser.getText().length() == 0 || edHoTen.getText().length() == 0 ||
                edPass.getText().length() == 0 || edRePass.getText().length() == 0){
            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            String pass = edPass.getText().toString();
            String rePass = edRePass.getText().toString();
            if (!pass.equals(rePass)){
                Toast.makeText(getContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}