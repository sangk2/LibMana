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
import com.duan.libmana.fragment.TopFragment;
import com.duan.libmana.model.Top;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TopAdapter extends ArrayAdapter<Top> {

    private Context context;
    TopFragment fragment;
    private ArrayList<Top> list;
    TextView tvSach, tvSl;

    public TopAdapter(@NonNull @NotNull Context context, TopFragment fragment, ArrayList<Top> list) {
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
            v = inflater.inflate(R.layout.top_item, null);
        }

        final  Top top = list.get(position);
        if (top != null){
            try {
                tvSach = v.findViewById(R.id.tvSach);
                tvSach.setText("Sách: " + top.tenSach);

                tvSl = v.findViewById(R.id.tvSL);
                tvSl.setText("Số lượng: " + top.soLuong);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return v;
    }
}
