package com.uas.hudhasyururi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    // Inisialisasi komponen UI sesuai dengan ID XML activity_register.xml terbaru Anda
    private TextInputEditText etRegisterUsername, etRegisterPassword;
    private MaterialButton btnRegister;
    private TextView tvKembaliLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        // Menghubungkan ID Java dengan ID komponen di XML
        etRegisterUsername = findViewById(R.id.etRegisterUsername);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvKembaliLogin = findViewById(R.id.tvKembaliLogin);

        // FIX 1: Saat klik teks "Login di sini" -> Langsung arahkan ke halaman Login
        if (tvKembaliLogin != null) {
            tvKembaliLogin.setOnClickListener(v -> {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Menutup halaman register agar tidak menumpuk di memori
            });
        }

        // FIX 2: Saat klik tombol "DAFTAR SEKARANG"
        if (btnRegister != null) {
            btnRegister.setOnClickListener(v -> {
                String usernameBaru = etRegisterUsername.getText().toString().trim();
                String passwordBaru = etRegisterPassword.getText().toString().trim();

                // 1. Validasi: Memastikan semua kolom wajib diisi
                if (usernameBaru.isEmpty() || passwordBaru.isEmpty()) {
                    Toast.makeText(this, "Semua kolom form registrasi wajib diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. Proses simpan ke database SQLite melalui DatabaseHelper
                boolean isRegistered = dbHelper.addUser(usernameBaru, passwordBaru);

                if (isRegistered) {
                    Toast.makeText(this, "Registrasi Berhasil! Silakan Login.", Toast.LENGTH_LONG).show();

                    // 3. Sukses: Otomatis alihkan user ke halaman Login agar bisa masuk dengan akun baru
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Menutup halaman register
                } else {
                    // Jika gagal, biasanya karena username tersebut sudah terdaftar di database
                    Toast.makeText(this, "Registrasi Gagal! Username mungkin sudah digunakan.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}