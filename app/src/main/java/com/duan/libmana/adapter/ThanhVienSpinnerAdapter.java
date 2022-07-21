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
import com.duan.libmana.model.ThanhVien;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {

    private Context context;
    private ArrayList<ThanhVien> list;
    TextView tvMaTV, tvTenTV;

    public ThanhVienSpinnerAdapter(@NonNull @NotNull Context context, ArrayList<ThanhVien> list) {
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
            v = inflater.inflate(R.layout.thanh_vien_item_spinner, null);
        }
        final ThanhVien thanhVien = list.get(position);

        if (thanhVien != null){
            try {
                tvMaTV = v.findViewById(R.id.tvMaTVSp);
                tvMaTV.setText(thanhVien.maTV+". ");

                tvTenTV = v.findViewById(R.id.tvTenTVSp);
                tvTenTV.setText(thanhVien.hoTen);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }

    // Hiển thị Spinner
    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.thanh_vien_item_spinner,null);
        }

        final ThanhVien thanhVien = list.get(position);

        if (thanhVien != null){
            try {
                tvMaTV = v.findViewById(R.id.tvMaTVSp);
                tvMaTV.setText(thanhVien.maTV+ " - ");

                tvTenTV = v.findViewById(R.id.tvTenTVSp);
                tvTenTV.setText(thanhVien.hoTen);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }
}
