package com.duan.libmana.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    static final String dbName = "PNLIB";
    static final int dbVersion = 1;

    public DbHelper(Context context){
        super(context,dbName,null,dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tạo bảng Thu Thu
        String createTableThuThu =
                "create table ThuThu (" +
                        "maTT text primary key," +
                        "hoTen text not null," +
                        "matKhau TEXT not null)";
        db.execSQL(createTableThuThu);

        // Tạo bảng Thanh Vien
        String createTableThanhVien =
                "create table ThanhVien (" +
                        "maTV integer primary key autoincrement," +
                        "hoTen text not null," +
                        "namSinh text not null)";
        db.execSQL(createTableThanhVien);

        // Tạo bảng Loai Sach
        String createTableLoaiSach =
                "create table LoaiSach (" +
                        "maLoai integer primary key autoincrement," +
                        "tenLoai text not null)";
        db.execSQL(createTableLoaiSach);

        // Tao bang Sach
        String createTableSach =
                "create table Sach (" +
                        "maSach integer primary key autoincrement," +
                        "tenSach text not null," +
                        "giaThue integer not null," +
                        "maLoai integer references LoaiSach(maLoai))";
        db.execSQL(createTableSach);

        // Tạo bảng Phieu Muon
        String createTablePhieuMuon =
                "create table PhieuMuon (" +
                        "maPM integer primary key autoincrement," +
                        "maTT text references ThuThu(maTT)," +
                        "maTV integer references ThanhVien(maTV)," +
                        "maSach integer references Sach(maSach)," +
                        "tienThue integer not null," +
                        "ngay date not null," +
                        "traSach integer not null)";
        db.execSQL(createTablePhieuMuon);

        // Thêm dữ liệu DataBase
        db.execSQL(Data_SQL.insert_ThuThu);
        db.execSQL(Data_SQL.insert_ThanhVien);
        db.execSQL(Data_SQL.insert_LoaiSach);
        db.execSQL(Data_SQL.insert_Sach);
        db.execSQL(Data_SQL.insert_PhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists ThuThu");
        db.execSQL("drop table if exists ThanhVien");
        db.execSQL("drop table if exists LoaiSach");
        db.execSQL("drop table if exists Sach");
        db.execSQL("drop table if exists PhieuMuon");
        onCreate(db);
    }
}
