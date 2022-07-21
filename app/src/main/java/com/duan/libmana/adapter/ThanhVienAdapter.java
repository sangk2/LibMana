package com.duan.libmana.adapter;

import android.content.Context;
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
import com.duan.libmana.fragment.ThanhVienFragment;
import com.duan.libmana.model.ThanhVien;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {

    TextView tvMaTV, tvTenTV, tvNamSinh;
    ImageView imgDelete;
    ThanhVienFragment fragment;
    private Context context;
    private ArrayList<ThanhVien> listTV;

    public ThanhVienAdapter(@NonNull @NotNull Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> listTV) {
        super(context, 0, listTV);
        this.context = context;
        this.listTV = listTV;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.thanh_vien_item, null);
        }
        final ThanhVien thanhVien = listTV.get(position);

        if (thanhVien != null){
            try {
                tvMaTV = v.findViewById(R.id.tvMaTV);
                tvMaTV.setText("Mã thành viên: "+thanhVien.maTV);

                tvTenTV = v.findViewById(R.id.tvTenTV);
                tvTenTV.setText("Tên thành viên: "+thanhVien.hoTen);

                tvNamSinh = v.findViewById(R.id.tvNamSinh);
                tvNamSinh.setText("Năm sinh: "+thanhVien.namSinh);

                imgDelete = v.findViewById(R.id.imgDeleteTV);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gọi function Xóa trong ThanhVienFragment
                fragment.xoa(String.valueOf(thanhVien.maTV));
            }
        });

        return v;
    }
}
