package com.duan.libmana.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.libmana.database.DbHelper;
import com.duan.libmana.model.PhieuMuon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {

    private SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


    public PhieuMuonDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon obj){
        ContentValues values = new ContentValues();
        // maPM tự tăng nên không cần đưa vào
        values.put("maTT", obj.getMaTT());
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());
        values.put("tienThue", obj.getTienThue());
        values.put("ngay", sdf.format(obj.getNgay()));
        values.put("traSach", obj.getTraSach());

        return db.insert("PhieuMuon", null, values);
    }

    public int update(PhieuMuon obj){
        ContentValues values = new ContentValues();

        values.put("maTT", obj.getMaTT());
        values.put("maTV", obj.getMaTV());
        values.put("maSach", obj.getMaSach());
        values.put("tienThue", obj.getTienThue());
        values.put("ngay", sdf.format(obj.getNgay()));
        values.put("traSach", obj.getTraSach());

        return db.update("PhieuMuon", values,"maPM=?", new String[]{String.valueOf(obj.getMaPM())});
    }

    public int delete(String id){
        return db.delete("PhieuMuon","maPM=?", new String[]{id});
    }

    // Get tất cả data
    public List<PhieuMuon> getAll(){
        String sql = "select * from PhieuMuon";
        return getData(sql);
    }

    // Get data theo id
    public PhieuMuon getID(String id){
        String sql = "select * from PhieuMuon where maPM=?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
        // Vì đây là trả về list nên sẽ trả về 1 danh sách sẽ lỗi
        // nên chỉ trả về vị trí đầu tiên
    }

    // Get data nhiều tham số
    @SuppressLint("Range")
    private List<PhieuMuon> getData(String sql, String...selectionArgs){

        List<PhieuMuon> list = new ArrayList<PhieuMuon>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            PhieuMuon obj = new PhieuMuon();

            obj.setMaPM(Integer.parseInt(c.getString(c.getColumnIndex("maPM"))));
            obj.setMaTT(c.getString(c.getColumnIndex("maTT")));
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            obj.setTienThue(Integer.parseInt(c.getString(c.getColumnIndex("tienThue"))));
            try {
                obj.setNgay(sdf.parse(c.getString(c.getColumnIndex("ngay"))));
            }catch (ParseException e){
                e.printStackTrace();
            }
            obj.setTraSach(Integer.parseInt(c.getString(c.getColumnIndex("traSach"))));
            list.add(obj);
        }
        return list;
    }

}
