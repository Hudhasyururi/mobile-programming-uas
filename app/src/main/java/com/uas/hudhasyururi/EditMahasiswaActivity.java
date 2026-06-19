package com.uas.hudhasyururi;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditMahasiswaActivity extends AppCompatActivity {

    // Komponen UI yang sesuai dengan ID XML Anda
    private TextInputEditText etNim, etNama, etJurusan, etSemester;
    private MaterialButton btnSimpan;
    private DatabaseHelper dbHelper;
    private String idMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Menghubungkan secara eksplisit ke XML edit data yang Anda kirim
        setContentView(R.layout.activity_edit_mahasiswa);

        dbHelper = new DatabaseHelper(this);

        // Inisialisasi ID komponen sesuai dengan isi XML Anda
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etJurusan = findViewById(R.id.etJurusan);
        etSemester = findViewById(R.id.etSemester);
        btnSimpan = findViewById(R.id.btnSimpan);

        // Menerima data lama dari intent yang dikirim oleh DetailMahasiswaActivity
        idMahasiswa = getIntent().getStringExtra("MHS_ID");
        String nimLama = getIntent().getStringExtra("MHS_NIM");
        String namaLama = getIntent().getStringExtra("MHS_NAMA");
        String jurusanLama = getIntent().getStringExtra("MHS_JURUSAN");
        String semesterLama = getIntent().getStringExtra("MHS_SEMESTER");

        // Menampilkan data lama ke dalam form input edit data
        if (etNim != null) etNim.setText(nimLama);
        if (etNama != null) etNama.setText(namaLama);
        if (etJurusan != null) etJurusan.setText(jurusanLama);
        if (etSemester != null) etSemester.setText(semesterLama);

        // Aksi tombol "Simpan Data"
        if (btnSimpan != null) {
            btnSimpan.setOnClickListener(v -> {
                String txtNimBaru = etNim.getText().toString().trim();
                String txtNamaBaru = etNama.getText().toString().trim();
                String txtJurusanBaru = etJurusan.getText().toString().trim();
                String txtSemesterBaru = etSemester.getText().toString().trim();

                // Validasi agar data tidak kosong
                if (txtNimBaru.isEmpty() || txtNamaBaru.isEmpty() || txtJurusanBaru.isEmpty() || txtSemesterBaru.isEmpty()) {
                    Toast.makeText(this, "Semua data form wajib diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Menjalankan fungsi update data lama berdasarkan ID (TIDAK MEMBUAT DATA BARU)
                boolean isUpdated = dbHelper.updateMahasiswa(idMahasiswa, txtNimBaru, txtNamaBaru, txtJurusanBaru, txtSemesterBaru);

                if (isUpdated) {
                    Toast.makeText(this, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                    finish(); // Menutup halaman edit dan kembali ke dashboard utama
                } else {
                    Toast.makeText(this, "Gagal memperbarui data!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}