package com.duan.libmana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duan.libmana.R;
import com.duan.libmana.model.LoaiSach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {

    private Context context;
    private ArrayList<LoaiSach> list;
    TextView tvMaLoaiSach, tvTenLoaiSach;

    public LoaiSachSpinnerAdapter(@NonNull @NotNull Context context, ArrayList<LoaiSach> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    // Hiển thị item của Spiner lên Dialog
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_sach_item_spinner, null);
        }
        final LoaiSach loaiSach = list.get(position);

        if (loaiSach != null){
            try {
                tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSachSp);
                tvMaLoaiSach.setText(loaiSach.maLoai + ". ");

                tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSachSp);
                tvTenLoaiSach.setText(loaiSach.tenLoai);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }

    // Hiển thị Spinner

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_sach_item_spinner,null);
        }
        final LoaiSach loaiSach = list.get(position);

        if (loaiSach != null){
            try {
                tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSachSp);
                tvMaLoaiSach.setText(loaiSach.maLoai+ " - ");

                tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSachSp);
                tvTenLoaiSach.setText(loaiSach.tenLoai);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }
}

