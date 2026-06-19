package com.uas.hudhasyururi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Menghubungkan ID dari XML desain terbaru Anda
        CardView btnMenuMahasiswa = findViewById(R.id.btnMenuMahasiswa);
        CardView btnMenuProfil = findViewById(R.id.btnMenuProfil);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Aksi Klik Menu: Data Mahasiswa
        if (btnMenuMahasiswa != null) {
            btnMenuMahasiswa.setOnClickListener(v -> {
                // Mengarah ke halaman Daftar Mahasiswa
                Intent intent = new Intent(DashboardActivity.this, DaftarMahasiswaActivity.class);
                startActivity(intent);
            });
        }

        // Aksi Klik Menu: Profil Developer
        if (btnMenuProfil != null) {
            btnMenuProfil.setOnClickListener(v -> {
                // Mengarah ke halaman Profil yang sudah ada di Manifest Anda
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        }

        // Aksi Klik Menu: Keluar (Logout)
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Toast.makeText(DashboardActivity.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();

                // Kembali ke halaman Login dan hapus riwayat (agar tidak bisa di-back)
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}