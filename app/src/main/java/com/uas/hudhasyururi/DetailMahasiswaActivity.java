package com.uas.hudhasyururi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailMahasiswaActivity extends AppCompatActivity {

    private TextView tvDetailNama, tvDetailNim, tvDetailProdi, tvDetailSemester;
    private Button btnEdit, btnHapus;
    private DatabaseHelper db;
    private String id, nama, nim, prodi, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mahasiswa);

        db = new DatabaseHelper(this);
        Context context = this;
        String pkg = context.getPackageName();

        // 1. Inisialisasi ID Nama & NIM secara standar
        tvDetailNama = findViewById(R.id.tvDetailNama);
        tvDetailNim = findViewById(R.id.tvDetailNim);

        // KUNCI PERBAIKAN 1: Mencari ID Prodi/Jurusan secara dinamis agar tidak Compile Error
        int idProdi = context.getResources().getIdentifier("tvDetailProdi", "id", pkg);
        if (idProdi == 0) idProdi = context.getResources().getIdentifier("tvDetailJurusan", "id", pkg);
        if (idProdi == 0) idProdi = context.getResources().getIdentifier("txtDetailJurusan", "id", pkg);
        if (idProdi != 0) tvDetailProdi = findViewById(idProdi);

        // KUNCI PERBAIKAN 2: Mencari ID Semester secara dinamis jika penamaan berbeda
        int idSemester = context.getResources().getIdentifier("tvDetailSemester", "id", pkg);
        if (idSemester == 0) idSemester = context.getResources().getIdentifier("tvDetailSemesterAktif", "id", pkg);
        if (idSemester != 0) tvDetailSemester = findViewById(idSemester);

        btnEdit = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);

        // Mengambil data kiriman dari Intent secara lengkap
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            nama = intent.getStringExtra("nama");
            nim = intent.getStringExtra("nim");
            prodi = intent.getStringExtra("prodi");
            semester = intent.getStringExtra("semester");
        }

        // Menampilkan data ke UI secara aman (anti-Crash NullPointer)
        if (tvDetailNama != null) tvDetailNama.setText(nama);
        if (tvDetailNim != null) tvDetailNim.setText(nim);
        if (tvDetailProdi != null) tvDetailProdi.setText(prodi);
        if (tvDetailSemester != null) tvDetailSemester.setText("Semester: " + semester);

        // Logika Tombol Hapus
        if (btnHapus != null) {
            btnHapus.setOnClickListener(v -> {
                boolean isDeleted = db.deleteMahasiswa(id);
                if (isDeleted) {
                    Toast.makeText(DetailMahasiswaActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DetailMahasiswaActivity.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Logika Tombol Edit
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                Intent editIntent = new Intent(DetailMahasiswaActivity.this, EditMahasiswaActivity.class);
                editIntent.putExtra("id", id);
                editIntent.putExtra("nama", nama);
                editIntent.putExtra("nim", nim);
                editIntent.putExtra("prodi", prodi);
                editIntent.putExtra("semester", semester);
                startActivity(editIntent);
                finish();
            });
        }
    }
}