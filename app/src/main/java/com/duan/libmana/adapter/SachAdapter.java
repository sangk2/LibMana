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
import com.duan.libmana.dao.LoaiSachDAO;
import com.duan.libmana.fragment.SachFragment;
import com.duan.libmana.model.LoaiSach;
import com.duan.libmana.model.Sach;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SachAdapter extends ArrayAdapter<Sach> {

    private Context context;
    SachFragment fragment;
    private ArrayList<Sach> list;

    TextView tvMaSach, tvTenSach, tvGiaThue, tvLoaiSach;
    ImageView imgDel;

    LoaiSachDAO loaiSachDAO;

    public SachAdapter(@NonNull @NotNull Context context, SachFragment fragment, ArrayList<Sach> list) {
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
            v = inflater.inflate(R.layout.sach_item,null);
        }

        final Sach sach = list.get(position);

        if (sach != null){
            try {
                tvMaSach = v.findViewById(R.id.tvMaSach);
                tvMaSach.setText("Mã sách: "+sach.maSach);

                tvTenSach =  v.findViewById(R.id.tvTenSach);
                tvTenSach.setText("Tên sách: "+sach.tenSach);

                tvGiaThue = v.findViewById(R.id.tvGiaThue);
                tvGiaThue.setText("Giá thuê: "+sach.giaThue);

                loaiSachDAO = new LoaiSachDAO(context);
                LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(sach.maLoai));
                tvLoaiSach = v.findViewById(R.id.tvLoaiSach);
                tvLoaiSach.setText("Loại sách: "+loaiSach.tenLoai);

                imgDel = v.findViewById(R.id.imgDeleteSach);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment.xoa(String.valueOf(sach.maSach));
            }
        });

        return v;
    }
}
