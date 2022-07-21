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
import com.duan.libmana.fragment.LoaiSachFragment;
import com.duan.libmana.model.LoaiSach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {

    TextView tvMaLoaiSach, tvTenLoaiSach;
    ImageView imgDelete;
    LoaiSachFragment fragment;
    private Context context;
    private ArrayList<LoaiSach> listLS;

    public LoaiSachAdapter(@NonNull @NotNull Context context, LoaiSachFragment fragment, ArrayList<LoaiSach> listLS) {
        super(context, 0, listLS);
        this.fragment = fragment;
        this.context = context;
        this.listLS = listLS;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.loai_sach_item, null);
        }
        final LoaiSach LoaiSach = listLS.get(position);

        if (LoaiSach != null){
            try {
                tvMaLoaiSach = v.findViewById(R.id.tvMaLoaiSach);
                tvMaLoaiSach.setText("Mã sách: "+LoaiSach.maLoai);

                tvTenLoaiSach = v.findViewById(R.id.tvTenLoaiSach);
                tvTenLoaiSach.setText("Tên sách: "+LoaiSach.tenLoai);

                imgDelete = v.findViewById(R.id.imgDeleteLS);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gọi function Xóa trong LoaiSachFragment
                fragment.xoa(String.valueOf(LoaiSach.maLoai));
            }
        });

        return v;
    }
}
