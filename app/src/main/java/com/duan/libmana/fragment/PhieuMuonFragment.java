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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.libmana.R;
import com.duan.libmana.adapter.PhieuMuonAdapter;
import com.duan.libmana.adapter.SachSpinnerAdapter;
import com.duan.libmana.adapter.ThanhVienSpinnerAdapter;
import com.duan.libmana.dao.PhieuMuonDAO;
import com.duan.libmana.dao.SachDAO;
import com.duan.libmana.dao.ThanhVienDAO;
import com.duan.libmana.model.PhieuMuon;
import com.duan.libmana.model.Sach;
import com.duan.libmana.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonFragment extends Fragment {

    ListView lv;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaPM;
    Spinner spTV, spSach;
    TextView tvNgay, tvTienThue;
    CheckBox chkTraSach;
    Button btnSave, btnCancel;

    ArrayList<PhieuMuon> list;

    static PhieuMuonDAO dao;
    PhieuMuonAdapter adapter;
    PhieuMuon phieuMuon;

    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDAO thanhVienDAO;
    int maThanhVien;

    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    Sach sach;
    int maSach, tienThue;

    int positionTV, positonSach;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_phieu_muon, container, false);

        lv = v.findViewById(R.id.lvPhieuMuon);
        fab = v.findViewById(R.id.fab);
        dao = new PhieuMuonDAO(getActivity());

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
                phieuMuon = list.get(position);
                openDialong(getActivity(),1); // 1 update
                return false;
            }
        });

        return v;
    }

    protected  void openDialong(final Context context, final int type){
        // custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.phieu_muon_dialog);
        edMaPM = dialog.findViewById(R.id.edMaPM);
        spTV = dialog.findViewById(R.id.spMaTV);
        spSach = dialog.findViewById(R.id.spMaSach);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvTienThue = dialog.findViewById(R.id.tvTienThue);
        chkTraSach = dialog.findViewById(R.id.chkTraSach);
        btnCancel = dialog.findViewById(R.id.btnCancelPM);
        btnSave = dialog.findViewById(R.id.btnSavePM);

        // Lấy ngày thuê mặc định này hiện hành
        tvNgay.setText("Ngày thuê: "+sdf.format(new Date()));

        thanhVienDAO = new ThanhVienDAO(context);
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context, listThanhVien);
        spTV.setAdapter(thanhVienSpinnerAdapter);
        // Lấy mã Thành viên
        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).maTV;
                Toast.makeText(context,"Chọn "+listThanhVien.get(position).hoTen,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDAO = new SachDAO(context);
        listSach = (ArrayList<Sach>) sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context,listSach);
        spSach.setAdapter(sachSpinnerAdapter);
        // Lấy maSach và tienThue
        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).maSach;
                tienThue = listSach.get(position).giaThue;

                tvTienThue.setText("Tiền thuê: "+tienThue);
                Toast.makeText(context,"Chọn "+listSach.get(position).tenSach,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edMaPM.setEnabled(false);
        // Kiểm tra type insert 0 hay Update 1
        if (type != 0){
            edMaPM.setText(String.valueOf(phieuMuon.getMaPM()));

            for (int i = 0; i < listThanhVien.size(); i++){
                if (phieuMuon.getMaTV() == (listThanhVien.get(i).maTV)){
                    positionTV = i;
                }
            }
            spTV.setSelection(positionTV);

            for (int i = 0; i<listSach.size(); i++) {
                if (phieuMuon.getMaSach() == (listSach.get(i).maSach)) {
                    positonSach = i;
                }
            }
            spSach.setSelection(positonSach);

            tvNgay.setText("Ngày thuê: "+sdf.format(phieuMuon.getNgay()));
            tvTienThue.setText("Tiền thuê: "+phieuMuon.getTienThue());

            if (phieuMuon.getTraSach() == 1){
                chkTraSach.setChecked(true);
            }else {
                chkTraSach.setChecked(false);
            }
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
                phieuMuon = new PhieuMuon();
                phieuMuon.setMaSach(maSach);
                phieuMuon.setMaTV(maThanhVien);
                phieuMuon.setNgay(new Date());
                phieuMuon.setTienThue(tienThue);
                if (chkTraSach.isChecked()){
                    phieuMuon.setTraSach(1);
                }else {
                    phieuMuon.setTraSach(0);
                }
                if (kiemTra() > 0){
                    if (type == 0){ // 0 insert
                        if (dao.insert(phieuMuon) > 0){
                            Toast.makeText(context,"Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }else { // 1  update
                        phieuMuon.setMaPM(Integer.parseInt(edMaPM.getText().toString()));
                        if (dao.update(phieuMuon) > 0){
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
//        builder.setTitle("Xóa Phiếu Mượn");
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
        list = (ArrayList<PhieuMuon>) dao.getAll();
        adapter = new PhieuMuonAdapter(getActivity(), this, list);
        lv.setAdapter(adapter);
    }

    public int kiemTra(){
        int check = 1;
//        if (edTenTV.getText().length() == 0 || edNamSinh.getText().length() == 0){
//            Toast.makeText(getContext(),"Bạn phải nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
//            check = -1;
//        }
        return check;
    }
}