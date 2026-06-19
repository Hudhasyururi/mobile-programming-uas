package com.uas.hudhasyururi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvNamaDeveloper, tvNimDeveloper, tvProdiDeveloper;
    private Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil); // Menyambungkan ke activity_profil.xml

        // Menyembunyikan ActionBar bawaan agar tampilan bersih
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Menghubungkan ID dari XML
        tvNamaDeveloper = findViewById(R.id.tvNamaDeveloper);
        tvNimDeveloper = findViewById(R.id.tvNimDeveloper);
        tvProdiDeveloper = findViewById(R.id.tvProdiDeveloper);
        btnKembali = findViewById(R.id.btnKembali);

        // Identitas Developer (Silakan sesuaikan jika ada yang berbeda)
        if (tvNamaDeveloper != null) tvNamaDeveloper.setText("Hudha Syururi");
        if (tvNimDeveloper != null) tvNimDeveloper.setText("2411010035");
        if (tvProdiDeveloper != null) tvProdiDeveloper.setText("Teknik Informatika");

        // Fungsi tombol kembali ke Dashboard
        if (btnKembali != null) {
            btnKembali.setOnClickListener(v -> finish());
        }
    }
}