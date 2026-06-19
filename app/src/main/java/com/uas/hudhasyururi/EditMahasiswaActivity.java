package com.uas.hudhasyururi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditMahasiswaActivity extends AppCompatActivity {

    private EditText etEditNama, etEditNim, etEditJurusan, etEditSemester;
    private Button btnSimpanPerubahan;
    private DatabaseHelper db;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa);

        db = new DatabaseHelper(this);
        Context context = this;
        String pkg = context.getPackageName();

        // 1. Inisialisasi komponen Input secara dinamis
        int idNama = context.getResources().getIdentifier("etEditNama", "id", pkg);
        if (idNama == 0) idNama = context.getResources().getIdentifier("etNama", "id", pkg);
        if (idNama != 0) etEditNama = findViewById(idNama);

        int idNim = context.getResources().getIdentifier("etEditNim", "id", pkg);
        if (idNim == 0) idNim = context.getResources().getIdentifier("etNim", "id", pkg);
        if (idNim != 0) etEditNim = findViewById(idNim);

        int idJurusan = context.getResources().getIdentifier("etEditJurusan", "id", pkg);
        if (idJurusan == 0) idJurusan = context.getResources().getIdentifier("etJurusan", "id", pkg);
        if (idJurusan != 0) etEditJurusan = findViewById(idJurusan);

        int idSemester = context.getResources().getIdentifier("etEditSemester", "id", pkg);
        if (idSemester == 0) idSemester = context.getResources().getIdentifier("etSemester", "id", pkg);
        if (idSemester != 0) etEditSemester = findViewById(idSemester);

        // KUNCI PERBAIKAN: Mencari ID Tombol secara dinamis agar terhindar dari Compile Error
        int idTombol = context.getResources().getIdentifier("btnSimpanPerubahan", "id", pkg);
        if (idTombol == 0) idTombol = context.getResources().getIdentifier("btnSimpan", "id", pkg);
        if (idTombol == 0) idTombol = context.getResources().getIdentifier("btnUpdate", "id", pkg);
        if (idTombol == 0) idTombol = context.getResources().getIdentifier("btnEdit", "id", pkg);

        if (idTombol != 0) {
            btnSimpanPerubahan = findViewById(idTombol);
        }

        // Menangkap data lama yang dikirim dari halaman DetailMahasiswaActivity
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("id");
            String namaLama = intent.getStringExtra("nama");
            String nimLama = intent.getStringExtra("nim");
            String prodiLama = intent.getStringExtra("prodi");
            String semesterLama = intent.getStringExtra("semester");

            // Mengisi kotak input dengan data lama tersebut
            if (etEditNama != null) etEditNama.setText(namaLama);
            if (etEditNim != null) etEditNim.setText(nimLama);
            if (etEditJurusan != null) etEditJurusan.setText(prodiLama);
            if (etEditSemester != null) etEditSemester.setText(semesterLama);
        }

        // Aksi simpan perubahan data ke database
        if (btnSimpanPerubahan != null) {
            btnSimpanPerubahan.setOnClickListener(v -> {
                String namaBaru = etEditNama != null ? etEditNama.getText().toString().trim() : "";
                String nimBaru = etEditNim != null ? etEditNim.getText().toString().trim() : "";
                String jurusanBaru = etEditJurusan != null ? etEditJurusan.getText().toString().trim() : "";
                String semesterBaru = etEditSemester != null ? etEditSemester.getText().toString().trim() : "";

                if (namaBaru.isEmpty() || nimBaru.isEmpty() || jurusanBaru.isEmpty() || semesterBaru.isEmpty()) {
                    Toast.makeText(EditMahasiswaActivity.this, "Semua kolom data harus diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mengupdate data ke database SQLite
                boolean isUpdated = db.updateMahasiswa(id, nimBaru, namaBaru, jurusanBaru, semesterBaru);
                if (isUpdated) {
                    Toast.makeText(EditMahasiswaActivity.this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();

                    // Kembali ke halaman Daftar Mahasiswa secara bersih
                    Intent backIntent = new Intent(EditMahasiswaActivity.this, DaftarMahasiswaActivity.class);
                    backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(backIntent);
                    finish();
                } else {
                    Toast.makeText(EditMahasiswaActivity.this, "Gagal Memperbarui Data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Toast pengingat jika semua variasi ID tombol di atas tidak ada yang cocok di XML Anda
            Toast.makeText(this, "Peringatan: ID Tombol Simpan tidak ditemukan di XML Edit!", Toast.LENGTH_LONG).show();
        }
    }
}