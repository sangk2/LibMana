package com.duan.libmana.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.libmana.database.DbHelper;
import com.duan.libmana.model.Sach;
import com.duan.libmana.model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public ThongKeDAO(Context context){
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thống kê top 10
    @SuppressLint("Range")
    public List<Top> getTop(){
        String sqlTop = "select maSach, count(maSach) as soLuong from PhieuMuon " +
                "group by maSach order by soLuong desc limit 10";
        List<Top> list = new ArrayList<Top>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);
        while (c.moveToNext()){
            Top top = new Top();
            Sach sach = sachDAO.getID(c.getString(c.getColumnIndex("maSach")));
            top.tenSach = sach.tenSach;
            top.soLuong = Integer.parseInt(c.getString(c.getColumnIndex("soLuong")));
            
            list.add(top);
        }
        return list;
    }

    // Thống kê doanh thu
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "select sum(tienThue) as doanhThu from PhieuMuon where ngay between ? and ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay,denNgay});

        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
// doanh thu chỉ in ra 1 dòng
// nếu trong khoảng thời gian mà có thì nó sẽ in ra tổng tiền thuê
// còn nếu không có thì nó sẽ in ra 0
        }
        return list.get(0);

    }
}
