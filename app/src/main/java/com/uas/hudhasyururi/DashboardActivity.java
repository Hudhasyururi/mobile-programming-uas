package com.uas.hudhasyururi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    private CardView btnMenuMahasiswa, btnMenuProfil;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        btnMenuMahasiswa = findViewById(R.id.btnMenuMahasiswa);
        btnMenuProfil = findViewById(R.id.btnMenuProfil);
        btnLogout = findViewById(R.id.btnLogout);

        if (btnMenuMahasiswa != null) {
            btnMenuMahasiswa.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, DaftarMahasiswaActivity.class);
                startActivity(intent);
            });
        }

        if (btnMenuProfil != null) {
            btnMenuProfil.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        }

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                // KUNCI: Hapus data session login agar tidak nyangkut
                SharedPreferences prefs = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();

                Toast.makeText(DashboardActivity.this, "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}