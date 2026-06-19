package com.uas.hudhasyururi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TambahMahasiswaActivity extends AppCompatActivity {

    private EditText etNama, etNim, etJurusan, etSemester;
    private Button btnSimpan;
    private DatabaseHelper db;
    private String activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_mahasiswa);

        db = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        activeUser = prefs.getString("session_username", "");

        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        etJurusan = findViewById(R.id.etJurusan);
        etSemester = findViewById(R.id.etSemester);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(v -> {
            String nama = etNama.getText().toString().trim();
            String nim = etNim.getText().toString().trim();
            String jurusan = etJurusan.getText().toString().trim();
            String semester = etSemester.getText().toString().trim();

            if (nama.isEmpty() || nim.isEmpty() || jurusan.isEmpty() || semester.isEmpty()) {
                Toast.makeText(TambahMahasiswaActivity.this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            // UPDATE: Memasukkan parameter semester ke fungsi insert
            boolean isInserted = db.insertMahasiswa(nama, nim, jurusan, semester, activeUser);
            if (isInserted) {
                Toast.makeText(TambahMahasiswaActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(TambahMahasiswaActivity.this, "Gagal Menyimpan Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}