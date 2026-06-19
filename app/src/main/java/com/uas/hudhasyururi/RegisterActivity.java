package com.uas.hudhasyururi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    private Button btnRegister;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Pastikan sesuai nama layout XML Anda

        db = new DatabaseHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword); // Kolom konfirmasi password
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            // Validasi input kosong
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validasi kesamaan password dan konfirmasinya
            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Password tidak cocok!", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- SEKARANG DIJAMIN AMAN ---
            // Memeriksa apakah username sudah terdaftar sebelumnya
            if (db.checkUserExists(username)) {
                Toast.makeText(RegisterActivity.this, "Username sudah digunakan! Gunakan nama lain.", Toast.LENGTH_SHORT).show();
            } else {
                // Jika belum ada, langsung masukkan user baru ke database
                boolean isInserted = db.insertUser(username, password);
                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "Pendaftaran Berhasil! Silakan Login.", Toast.LENGTH_SHORT).show();
                    finish(); // Menutup halaman register dan kembali ke halaman login
                } else {
                    Toast.makeText(RegisterActivity.this, "Pendaftaran Gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}