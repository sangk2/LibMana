package com.duan.libmana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.duan.libmana.dao.ThuThuDAO;
import com.duan.libmana.fragment.AddUserFragment;
import com.duan.libmana.fragment.ChangePassFragment;
import com.duan.libmana.fragment.DoanhThuFragment;
import com.duan.libmana.fragment.LoaiSachFragment;
import com.duan.libmana.fragment.PhieuMuonFragment;
import com.duan.libmana.fragment.SachFragment;
import com.duan.libmana.fragment.ThanhVienFragment;
import com.duan.libmana.fragment.TopFragment;
import com.duan.libmana.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderview;
    TextView tvUser;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);

        //set Toolbar thay cho Actionbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // dùng fragment PhieuMuon làm home
        FragmentManager fragmentManager = getSupportFragmentManager();
        PhieuMuonFragment phieuMuon = new PhieuMuonFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent,phieuMuon)
                .commit();

        NavigationView nav = findViewById(R.id.navView);
        // Hiển thị User trên Header

        mHeaderview = nav.getHeaderView(0);
        tvUser = mHeaderview.findViewById(R.id.tvUser);

        Intent intent = getIntent();
        String user = intent.getStringExtra("user");

        // Hiển thị Họ tên
        thuThuDAO  = new ThuThuDAO(this);

        try {
            ThuThu thuThu = thuThuDAO.getID(String.valueOf(user));
            String username = thuThu.hoTen;
            tvUser.setText("Xin chào "+username+"!");

        }catch (Exception e){
            e.printStackTrace();
        }
        // Hiển thị user
//        tvUser.setText("Xin chào "+user+"!");
//        tvUser.setText("Xin chào !");

        // admin có quyền add user
        if (user.equalsIgnoreCase("admin")){
            nav.getMenu().findItem(R.id.sub_AddUser).setVisible(true);
        }

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();

                switch (item.getItemId()){
                    case R.id.nav_PhieuMuon:
                        setTitle("Quản lý phiếu mượn");
                        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,phieuMuonFragment)
                                .commit();
                        break;
                    case R.id.nav_LoaiSach:
                        setTitle("Quản lý Loại sách");
                        LoaiSachFragment loaiSachFragment = new LoaiSachFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,loaiSachFragment)
                                .commit();
                        break;
                    case R.id.nav_Sach:
                        setTitle("Quản lý Sách");
                        SachFragment sachFragment = new SachFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,sachFragment)
                                .commit();
                        break;
                    case R.id.nav_ThanhVien:
                        setTitle("Quản lý thành viên");
                        ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,thanhVienFragment)
                                .commit();
                        break;
                    case R.id.sub_Top:
                        setTitle("Top 10 sách cho thuê nhiều nhất");
                        TopFragment topFragment = new TopFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,topFragment)
                                .commit();
                        break;
                    case R.id.sub_DoanhThu:
                        setTitle("Thống kê doanh thu");
                        DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,doanhThuFragment)
                                .commit();
                        break;
                    case R.id.sub_AddUser:
                        setTitle("Thêm người dùng");
                        AddUserFragment addUserFragment = new AddUserFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,addUserFragment)
                                .commit();
                        break;
                    case R.id.sub_Pass:
                        setTitle("Thay đổi mật khẩu");
                        ChangePassFragment changePassFragment = new ChangePassFragment();
                        manager.beginTransaction()
                                .replace(R.id.flContent,changePassFragment)
                                .commit();
                        break;
                    case R.id.sub_Logout:
                        dangXuat();
                        break;
                }

                drawer.closeDrawers();
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    public void dangXuat(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_dangxuat);

        // Khi chạm bên ngoài dialog sẽ ko đóng
        dialog.setCanceledOnTouchOutside(false);

        Button btnYes =dialog. findViewById(R.id.btnYesExit);
        Button btnNo = dialog.findViewById(R.id.btnNoExit);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                dialog.cancel();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}