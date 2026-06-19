package com.uas.hudhasyururi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class TambahMahasiswaActivity extends AppCompatActivity {

    private TextInputEditText etTambahNim, etTambahNama, etTambahJurusan, etTambahSemester;
    private MaterialButton btnTambahSimpan;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mahasiswa);

        dbHelper = new DatabaseHelper(this);

        etTambahNim = findViewById(R.id.etTambahNim);
        etTambahNama = findViewById(R.id.etTambahNama);
        etTambahJurusan = findViewById(R.id.etTambahJurusan);
        etTambahSemester = findViewById(R.id.etTambahSemester);
        btnTambahSimpan = findViewById(R.id.btnTambahSimpan);

        if (btnTambahSimpan != null) {
            btnTambahSimpan.setOnClickListener(v -> {
                String nim = etTambahNim.getText().toString().trim();
                String nama = etTambahNama.getText().toString().trim();
                String jurusan = etTambahJurusan.getText().toString().trim();
                String semester = etTambahSemester.getText().toString().trim();

                if (nim.isEmpty() || nama.isEmpty() || jurusan.isEmpty() || semester.isEmpty()) {
                    Toast.makeText(this, "Semua form wajib diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 🔥 BACA SESI: Cari tahu siapa nama akun yang sedang menginput data ini
                SharedPreferences sharedPref = getSharedPreferences("SesiUser", Context.MODE_PRIVATE);
                String userLogin = sharedPref.getString("USERNAME_LOGIN", "umum");

                // Simpan data mahasiswa ke SQLite bersama informasi pemiliknya
                boolean isInserted = dbHelper.addMahasiswa(nim, nama, jurusan, semester, userLogin);

                if (isInserted) {
                    Toast.makeText(this, "Data berhasil disimpan ke akun: " + userLogin, Toast.LENGTH_SHORT).show();
                    finish(); // Tutup halaman input dan kembali ke dashboard
                } else {
                    Toast.makeText(this, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}