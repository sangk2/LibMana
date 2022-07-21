package com.duan.libmana.database;

public class Data_SQL {
    public static final String insert_ThuThu =
            "insert into ThuThu (maTT, hoTen, matKhau) values " +
                    "('admin', 'Tấn Sang', '123')";

    public static final String insert_ThanhVien =
            "insert into ThanhVien (hoTen, namSinh) values " +
                    "('Tấn Sang', '2002')," +
                    "('Trần Văn Tân', '2004')," +
                    "('Lê Thị Thanh', '1993')," +
                    "('Lâm Quốc Chí', '1997')," +
                    "('Hồ Xuân Hương', '1994')";

    public static final String insert_LoaiSach =
            "insert into LoaiSach (tenLoai) values" +
                    "('Android')," +
                    "('Java')," +
                    "('Website')," +
                    "('Kinh Doanh')," +
                    "('C')," +
                    "('Game')";
    public static final String insert_Sach =
            "insert into Sach(tenSach, giaThue, maLoai) values" +
                    "('Java cơ bản', '12000', '2')," +
                    "('C# & C++', '10000', '5')," +
                    "('Kỹ năng bán hàng', '5400', '4')," +
                    "('Java nâng cao', '25000', '2')," +
                    "('Android cơ bản', '16000', '1')," +
                    "('Android nâng cao', '32000', '1')," +
                    "('HTML & CSS3', '20000', '3')," +
                    "('JavaScrip', '26000', '3')," +
                    "('Game 2D', '19000', '6')," +
                    "('Game 3D', '25000', '6')";

    public static final String insert_PhieuMuon =
            "insert into PhieuMuon(maTT, maTV, maSach, tienThue, ngay, traSach) values " +
                    "('admin','1','10','25000','2021/10/5','1')," +
                    "('admin','2','9','19000','2021/10/12','0')," +
                    "('admin','4','2','10000','2021/10/15','0')," +
                    "('admin','5','4','25000','2021/10/17','1')," +
                    "('admin','2','1','12000','2021/10/19','1')," +
                    "('admin','3','10','25000','2021/10/20','0')," +
                    "('admin','1','5','16000','2021/10/21','0')," +
                    "('admin','5','3','5400','2021/10/24','1')," +
                    "('admin','3','7','20000','2021/10/26','0')," +
                    "('admin','4','6','32000','2021/10/28','1')";

}
