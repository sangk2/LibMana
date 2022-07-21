package com.duan.libmana.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.duan.libmana.R;
import com.duan.libmana.adapter.LoaiSachSpinnerAdapter;
import com.duan.libmana.adapter.SachAdapter;
import com.duan.libmana.dao.LoaiSachDAO;
import com.duan.libmana.dao.SachDAO;
import com.duan.libmana.model.LoaiSach;
import com.duan.libmana.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SachFragment extends Fragment {

    ListView lv;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaSach, edTenSach, edGiaThue;
    Spinner spLoaiSach;
    Button btnSave, btnCancel;

    ArrayList<Sach> list;

    static SachDAO dao;
    SachAdapter adapter;
    Sach sach;

    LoaiSachSpinnerAdapter loaiSachSpinnerAdapter;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachDAO loaiSachDAO;
    LoaiSach loaiSach;
    int maLoaiSach;

    int positonLoaiSach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sach, container, false);

        lv = v.findViewById(R.id.lvSach);
        fab = v.findViewById(R.id.fab);
        dao = new SachDAO(getActivity());

        capNhatLv();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialong(getActivity(), 0);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sach = list.get(position);
                openDialong(getActivity(),1); // 1 update
                return false;
            }
        });

        return v;
    }

    protected  void openDialong(final Context context, final int type){
        // custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.sach_dialog);
        edMaSach = dialog.findViewById(R.id.edMaSach);
        spLoaiSach = dialog.findViewById(R.id.spLoaiSach);
        edTenSach = dialog.findViewById(R.id.edTenSach);
        edGiaThue = dialog.findViewById(R.id.edGiaThue);
        btnCancel = dialog.findViewById(R.id.btnCancelSach);
        btnSave = dialog.findViewById(R.id.btnSaveSach);

        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        loaiSachSpinnerAdapter = new LoaiSachSpinnerAdapter(context, listLoaiSach);
        spLoaiSach.setAdapter(loaiSachSpinnerAdapter);
        // Lấy maLoaiSach
        spLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).maLoai;
                Toast.makeText(context,"Chọn "+listLoaiSach.get(position).tenLoai,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Kiểm tra type insert 0 hay Update 1
        edMaSach.setEnabled(false); // tắt nhập Mã sách
        if (type != 0){
            edMaSach.setText(String.valueOf(sach.maSach));
            edTenSach.setText(sach.tenSach);
            edGiaThue.setText(String.valueOf(sach.giaThue));
            for (int i = 0; i<listLoaiSach.size(); i++) {
                if (sach.maLoai == (listLoaiSach.get(i).maLoai)) {
                    positonLoaiSach = i;
                }
            }
            spLoaiSach.setSelection(positonLoaiSach);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sach = new Sach();
                sach.maLoai = maLoaiSach;
                sach.tenSach = edTenSach.getText().toString();
                sach.giaThue = Integer.parseInt(edGiaThue.getText().toString());

                if (kiemTra() > 0){
                    if (type == 0){ // 0 insert
                        if (dao.insert(sach) > 0){
                            Toast.makeText(context,"Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }else { // 1  update
                        sach.maSach = Integer.parseInt(edMaSach.getText().toString());
                        if (dao.update(sach) > 0){
                            Toast.makeText(context,"Sửa thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Sửa thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatLv();
                    dialog.dismiss(); // đóng dialog
                }
            }
        });
        dialog.show();
    }

//    public void xoa(final String id){
//        // Sử dụng Alert
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Xóa Sách");
//        builder.setMessage("Bạn có muốn xóa không?");
//        builder.setCancelable(true);
//
//        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dao.delete(id);
//                capNhatLv();
//                dialog.cancel();
//            }
//        });
//        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        builder.show();
//    }

    public void xoa(final String id){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_delete);

        // Khi chạm bên ngoài dialog sẽ ko đóng
        dialog.setCanceledOnTouchOutside(false);

        Button btnYes =dialog. findViewById(R.id.btnYesDel);
        Button btnNo = dialog.findViewById(R.id.btnNoDel);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.delete(id);
                capNhatLv();
                dialog.cancel();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    void capNhatLv(){
        list = (ArrayList<Sach>) dao.getAll();
        adapter = new SachAdapter(getActivity(), this, list);
        lv.setAdapter(adapter);
    }

    public int kiemTra(){
        int check = 1;
        if (edTenSach.getText().length() == 0 || edGiaThue.getText().length() == 0){
            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}