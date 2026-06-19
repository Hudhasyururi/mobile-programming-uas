package com.uas.hudhasyururi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DetailMahasiswaActivity extends AppCompatActivity {

    private TextView tvNama, tvNim, tvJurusan, tvSemester;
    private MaterialButton btnEdit, btnHapus;
    private DatabaseHelper dbHelper;

    private String idMahasiswa, nim, nama, jurusan, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mahasiswa);

        dbHelper = new DatabaseHelper(this);

        // 1. Inisialisasi Komponen UI
        tvNama = findViewById(R.id.tvDetailNama);
        tvNim = findViewById(R.id.tvDetailNim);
        tvJurusan = findViewById(R.id.tvDetailJurusan);
        tvSemester = findViewById(R.id.tvDetailSemester);
        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);

        // 2. Ambil data kiriman awal dari Dashboard
        idMahasiswa = getIntent().getStringExtra("MHS_ID");
        nim = getIntent().getStringExtra("MHS_NIM");
        nama = getIntent().getStringExtra("MHS_NAMA");
        jurusan = getIntent().getStringExtra("MHS_JURUSAN");
        semester = getIntent().getStringExtra("MHS_SEMESTER");

        // 3. Tampilkan data ke layar
        tampilkanDataKeLayar();

        // 4. Aksi Tombol EDIT (SUDAH DIPERBAIKI KE EDITMAHASISWAACTIVITY)
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                // 🔥 PERBAIKAN: Diubah dari TambahMahasiswaActivity ke EditMahasiswaActivity
                Intent intent = new Intent(DetailMahasiswaActivity.this, EditMahasiswaActivity.class);

                // Kirim data lama ke halaman edit agar form langsung terisi
                intent.putExtra("MHS_ID", idMahasiswa);
                intent.putExtra("MHS_NIM", nim);
                intent.putExtra("MHS_NAMA", nama);
                intent.putExtra("MHS_JURUSAN", jurusan);
                intent.putExtra("MHS_SEMESTER", semester);

                startActivity(intent);
                finish(); // Tutup halaman detail agar data langsung ter-refresh di dashboard utama
            });
        }

        // 5. Aksi Tombol HAPUS
        if (btnHapus != null) {
            btnHapus.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Apakah Anda yakin ingin menghapus data dari " + nama + "?")
                        .setPositiveButton("Ya, Hapus", (dialog, which) -> {
                            boolean isDeleted = dbHelper.deleteMahasiswa(idMahasiswa);
                            if (isDeleted) {
                                Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(this, "Gagal menghapus data!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Batal", null)
                        .show();
            });
        }
    }

    private void tampilkanDataKeLayar() {
        if (tvNama != null) tvNama.setText(nama);
        if (tvNim != null) tvNim.setText(nim);
        if (tvJurusan != null) tvJurusan.setText(jurusan);
        if (tvSemester != null) tvSemester.setText("Semester " + semester);
    }
}