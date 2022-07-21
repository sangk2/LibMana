package com.duan.libmana.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.libmana.database.DbHelper;
import com.duan.libmana.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    private SQLiteDatabase db;

    public SachDAO(Context context){
        try {

            DbHelper dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public long insert(Sach obj){
        ContentValues values = new ContentValues();
        // maTT tự tăng nên không cần đưa vào
        values.put("tenSach", obj.tenSach);
        values.put("giaThue", obj.giaThue);
        values.put("maLoai", obj.maLoai);

        return db.insert("Sach", null, values);
    }

    public int update(Sach obj){
        ContentValues values = new ContentValues();

        values.put("tenSach", obj.tenSach);
        values.put("giaThue", obj.giaThue);
        values.put("maLoai", obj.maLoai);

        return db.update("Sach", values,"maSach=?", new String[]{String.valueOf(obj.maSach)});
    }

    public int delete(String id){
        return db.delete("Sach","maSach=?", new String[]{id});
    }

    // Get tất cả data
    public List<Sach> getAll(){
        String sql = "select * from Sach";
        return getData(sql);
    }

    // Get data theo id
    public Sach getID(String id){
        String sql = "select * from Sach where maSach=?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
        // Vì đây là trả về list nên sẽ trả về 1 danh sách sẽ lỗi
        // nên chỉ trả về vị trí đầu tiên
    }

    // Get data nhiều tham số
    @SuppressLint("Range")
    private List<Sach> getData(String sql, String...selectionArgs){

        List<Sach> list = new ArrayList<Sach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Sach obj = new Sach();

            obj.maSach = Integer.parseInt(c.getString(c.getColumnIndex("maSach")));
            obj.tenSach = c.getString(c.getColumnIndex("tenSach"));
            obj.giaThue = Integer.parseInt(c.getString(c.getColumnIndex("giaThue")));
            obj.maLoai = Integer.parseInt(c.getString(c.getColumnIndex("maLoai")));
            list.add(obj);
        }
        return list;
    }

}
