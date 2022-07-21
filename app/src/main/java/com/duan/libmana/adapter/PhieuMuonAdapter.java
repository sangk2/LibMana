package com.duan.libmana.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duan.libmana.R;
import com.duan.libmana.dao.SachDAO;
import com.duan.libmana.dao.ThanhVienDAO;
import com.duan.libmana.fragment.PhieuMuonFragment;
import com.duan.libmana.model.PhieuMuon;
import com.duan.libmana.model.Sach;
import com.duan.libmana.model.ThanhVien;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {

    private Context context;
    PhieuMuonFragment fragment;
    private ArrayList<PhieuMuon> list;

    TextView tvMaPM, tvTenTV, tvTenSach, tvTienThue, tvNgay, tvTraSach;
    ImageView imgDel;

    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PhieuMuonAdapter(@NonNull @NotNull Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.phieu_muon_item,null);
        }

        final PhieuMuon phieuMuon = list.get(position);

        if (phieuMuon != null){
            try {
                tvMaPM = v.findViewById(R.id.tvMaPM);
                tvMaPM.setText("Mã phiếu: " + phieuMuon.getMaPM());

                sachDAO = new SachDAO(context);
                Sach sach = sachDAO.getID(String.valueOf(phieuMuon.getMaSach()));
                tvTenSach = v.findViewById(R.id.tvTenSach);
                tvTenSach.setText("Tên sách: " + sach.tenSach);

                thanhVienDAO = new ThanhVienDAO(context);
                ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(phieuMuon.getMaTV()));
                tvTenTV = v.findViewById(R.id.tvTenTV);
                tvTenTV.setText("Tên thành viên: " + thanhVien.hoTen);

                tvTienThue = v.findViewById(R.id.tvTienThue);
                tvTienThue.setText("Tiền thuê: " + phieuMuon.getTienThue());

                tvNgay = v.findViewById(R.id.tvNgayPM);
                tvNgay.setText("Ngày thuê: " + sdf.format(phieuMuon.getNgay()));

                tvTraSach = v.findViewById(R.id.tvTraSach);
                if (phieuMuon.getTraSach() == 1) {
                    tvTraSach.setTextColor(Color.BLUE);
                    tvTraSach.setText("Đã trả sách");
                } else {
                    tvTraSach.setTextColor(Color.RED);
                    tvTraSach.setText("Chưa trả sách");
                }

                imgDel = v.findViewById(R.id.imgDeletePM);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment.xoa(String.valueOf(phieuMuon.getMaPM()));
            }
        });


        return v;
    }
}
