package com.uas.hudhasyururi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;

public class MahasiswaActivity extends AppCompatActivity {

    TextInputEditText etNim, etNama, etJurusan;
    AutoCompleteTextView actvSemester; // Komponen baru dropdown
    Button btnSimpan, btnLihatData;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        db = new DatabaseHelper(this);

        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etJurusan = findViewById(R.id.etJurusan);
        actvSemester = findViewById(R.id.actvSemester);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnLihatData = findViewById(R.id.btnLihatData);

        // Membuat pilihan list semester untuk dropdown (Semester 1 sampai 8)
        String[] listSemester = {"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listSemester);
        actvSemester.setAdapter(adapter);

        // Aksi Tombol Simpan
        btnSimpan.setOnClickListener(v -> {
            String nim = etNim.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String jrs = etJurusan.getText().toString().trim();
            String smt = actvSemester.getText().toString().trim(); // Ambil semester

            if (nim.isEmpty() || nama.isEmpty() || jrs.isEmpty() || smt.isEmpty()) {
                Toast.makeText(this, "Semua data wajib diisi!", Toast.LENGTH_SHORT).show();
            } else {
                // Di sini nanti dimasukkan ke SQLite Anda
                // Contoh jika fungsi di DatabaseHelper sudah siap:
                // db.insertMahasiswa(nim, nama, jrs, smt);

                Toast.makeText(this, "Data Mahasiswa Berhasil Disimpan!", Toast.LENGTH_SHORT).show();

                // Reset form setelah simpan
                etNim.setText("");
                etNama.setText("");
                etJurusan.setText("");
                actvSemester.setText("", false);
            }
        });

        // PERBAIKAN: Tombol untuk Membuka Halaman Tampilkan/Daftar Data Mahasiswa
        btnLihatData.setOnClickListener(v -> {
            Intent intent = new Intent(MahasiswaActivity.this, DaftarMahasiswaActivity.class);
            startActivity(intent);
        });
    }
}