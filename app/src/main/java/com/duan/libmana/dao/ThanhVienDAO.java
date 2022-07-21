package com.duan.libmana.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.libmana.database.DbHelper;
import com.duan.libmana.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {

    private SQLiteDatabase db;

    public ThanhVienDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien obj){
        ContentValues values = new ContentValues();
        // maTV tự tăng nên không cần đưa vào
        values.put("hoTen", obj.hoTen);
        values.put("namSinh", obj.namSinh);

        return db.insert("ThanhVien", null, values);
    }

    public int update(ThanhVien obj){
        ContentValues values = new ContentValues();

        values.put("hoTen", obj.hoTen);
        values.put("namSinh", obj.namSinh);

        return db.update("ThanhVien", values,"maTV=?", new String[]{String.valueOf(obj.maTV)});
    }

    public int delete(String id){
        return db.delete("ThanhVien","maTV=?", new String[]{id});
    }

    // Get tất cả data
    public List<ThanhVien> getAll(){
        String sql = "select * from ThanhVien";
        return getData(sql);
    }

    // Get data theo id
    public ThanhVien getID(String id){
        String sql = "select * from ThanhVien where maTV=?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);
        // Vì đây là trả về list nên sẽ trả về 1 danh sách sẽ lỗi
        // nên chỉ trả về vị trí đầu tiên
    }

    // Get data nhiều tham số
    @SuppressLint("Range")
    private List<ThanhVien> getData(String sql, String...selectionArgs){

        List<ThanhVien> list = new ArrayList<ThanhVien>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThanhVien obj = new ThanhVien();

            obj.maTV = Integer.parseInt(c.getString(c.getColumnIndex("maTV")));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.namSinh = c.getString(c.getColumnIndex("namSinh"));
            list.add(obj);
        }
        return list;
    }

}
