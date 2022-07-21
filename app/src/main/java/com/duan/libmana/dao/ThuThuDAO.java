package com.duan.libmana.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duan.libmana.database.DbHelper;
import com.duan.libmana.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO{

    private SQLiteDatabase db;

    public ThuThuDAO(Context context){
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThuThu obj){

        ContentValues values = new ContentValues();
        values.put("maTT",obj.maTT);
        values.put("hoTen", obj.hoTen);
        values.put("matKhau",obj.matKhau);

        return db.insert("ThuThu", null, values);
    }

    public int updatePass(ThuThu obj){
        ContentValues values = new ContentValues();

        values.put("hoTen",obj.hoTen);
        values.put("matKhau",obj.matKhau);

        return  db.update("ThuThu",values,"maTT=?", new String[]{obj.maTT});
    }

    public int delete(String id){
        return db.delete("ThuThu","maTT=?", new String[]{id});
    }

    // get data nhieu tham so
    @SuppressLint("Range")
    public List<ThuThu> getData(String sql, String...selectionArgs){

        List<ThuThu> list = new ArrayList<ThuThu>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThuThu obj = new ThuThu();
            obj.maTT = c.getString(c.getColumnIndex("maTT"));
            obj.hoTen = c.getString(c.getColumnIndex("hoTen"));
            obj.matKhau = c.getString(c.getColumnIndex("matKhau"));

            list.add(obj);
        }
        return list;
    }

    // get tất cả data
    public List<ThuThu> getAll(){
        String sql = "select * from ThuThu";

        return getData(sql);
    }

    // get data theo id
    public ThuThu getID(String id){
        String sql = "select * from ThuThu where maTT=?";

        List<ThuThu> list = getData(sql, id);

        return list.get(0);
    }

    // Check login
    public int checkLogin(String id, String password){
        String sql = "select * from ThuThu where maTT=? and matKhau=?";
        List<ThuThu> list = getData(sql, id, password);

        if (list.size() == 0){
            return -1;
        }
        return 1;
    }
}