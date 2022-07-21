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
import com.duan.libmana.model.Sach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {

    private Context context;
    private ArrayList<Sach> list;
    TextView tvMaSach, tvTenSach;

    public SachSpinnerAdapter(@NonNull @NotNull Context context, ArrayList<Sach> list) {
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
            v = inflater.inflate(R.layout.sach_item_spinner, null);
        }
        final Sach sach = list.get(position);

        if (sach != null){
            try {
                tvMaSach = v.findViewById(R.id.tvMaSachSp);
                tvMaSach.setText(sach.maSach + ". ");

                tvTenSach = v.findViewById(R.id.tvTenSachSp);
                tvTenSach.setText(sach.tenSach);
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
            v = inflater.inflate(R.layout.sach_item_spinner,null);
        }

        final Sach sach = list.get(position);

        if (sach != null){
            try {
                tvMaSach = v.findViewById(R.id.tvMaSachSp);
                tvMaSach.setText(sach.maSach+ " - ");

                tvTenSach = v.findViewById(R.id.tvTenSachSp);
                tvTenSach.setText(sach.tenSach);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return v;
    }
}
