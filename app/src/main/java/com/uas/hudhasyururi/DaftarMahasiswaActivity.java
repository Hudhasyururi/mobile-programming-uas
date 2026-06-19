package com.uas.hudhasyururi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class DaftarMahasiswaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MahasiswaAdapter adapter;
    private ArrayList<Mahasiswa> listMahasiswa;
    private DatabaseHelper db;
    private FloatingActionButton fabTambah;
    private String activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_mahasiswa);

        db = new DatabaseHelper(this);

        // Mengambil sesi user yang sedang aktif login
        SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        activeUser = prefs.getString("session_username", "");

        // KUNCI PERBAIKAN: Hanya memanggil ID yang pasti ada di XML Anda
        recyclerView = findViewById(R.id.rvMahasiswa);

        listMahasiswa = new ArrayList<>();
        adapter = new MahasiswaAdapter(listMahasiswa);

        // Pasang adapter secara aman jika RecyclerView berhasil terikat
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Peringatan: Komponen rvMahasiswa tidak ditemukan di layout!", Toast.LENGTH_LONG).show();
        }

        // Inisialisasi Tombol Floating Action Button (FAB) Tambah
        fabTambah = findViewById(R.id.fabTambah);
        if (fabTambah != null) {
            fabTambah.setOnClickListener(v -> {
                Intent intent = new Intent(DaftarMahasiswaActivity.this, TambahMahasiswaActivity.class);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Menyegarkan data secara otomatis saat kembali ke halaman ini
    }

    private void loadData() {
        try {
            if (listMahasiswa != null) {
                listMahasiswa.clear();
            }

            // Mengambil data dari database helper berdasarkan user aktif
            ArrayList<Mahasiswa> dataBaru = db.getAllMahasiswa(activeUser);

            if (dataBaru != null && listMahasiswa != null) {
                listMahasiswa.addAll(dataBaru);
            }

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal memuat data: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}