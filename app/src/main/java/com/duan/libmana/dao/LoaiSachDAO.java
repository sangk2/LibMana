package com.duan.libmana.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.libmana.database.DbHelper;
import com.duan.libmana.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {

    private SQLiteDatabase db;

    public LoaiSachDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSach obj){
        ContentValues values = new ContentValues();
        // maLoai tự tăng nên không cần đưa vào

        values.put("tenLoai", obj.tenLoai);

        return db.insert("LoaiSach", null, values);
    }

    public int update(LoaiSach obj){
        ContentValues values = new ContentValues();

        values.put("tenLoai", obj.tenLoai);

        return db.update("LoaiSach", values,"maLoai=?", new String[]{String.valueOf(obj.maLoai)});
    }

    public int delete(String id){
        return db.delete("LoaiSach","maLoai=?", new String[]{id});
    }

    // Get tất cả data
    public List<LoaiSach> getAll(){
        String sql = "select * from LoaiSach";
        return getData(sql);
    }

    // Get data theo id
    public LoaiSach getID(String id){
        String sql = "select * from LoaiSach where maLoai=?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
        // Vì đây là trả về list nên sẽ trả về 1 danh sách sẽ lỗi
        // nên chỉ trả về vị trí đầu tiên
    }

    // Get data nhiều tham số
    @SuppressLint("Range")
    private List<LoaiSach> getData(String sql, String...selectionArgs){

        List<LoaiSach> list = new ArrayList<LoaiSach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            LoaiSach obj = new LoaiSach();

            obj.maLoai = Integer.parseInt(c.getString(c.getColumnIndex("maLoai")));
            obj.tenLoai = c.getString(c.getColumnIndex("tenLoai"));
            list.add(obj);
        }
        return list;
    }

}
