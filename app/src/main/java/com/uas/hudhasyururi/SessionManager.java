package com.uas.hudhasyururi;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    private static final String PREF_NAME = "AppSession";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAMA = "nama";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Fungsi untuk menyimpan sesi saat user berhasil login
    public void createLoginSession(String username, String nama) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_NAMA, nama);
        editor.apply();
    }

    // Fungsi untuk mengecek apakah user sudah login
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    // Fungsi untuk menghapus sesi (Logout)
    public void logoutUser() {
        editor.clear();
        editor.apply();
    }

    // Fungsi untuk mengambil Username user yang sedang login
    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    // Fungsi untuk mengambil Nama user (Versi Singkat)
    public String getNama() {
        return pref.getString(KEY_NAMA, "");
    }

    // ==========================================
    // --- TAMBAHAN BARU UNTUK WELCOME ACTIVITY ---
    // ==========================================

    // Fungsi untuk mengambil Nama user (Versi Lengkap, sesuai panggilan di WelcomeActivity)
    public String getNamaLengkap() {
        return pref.getString(KEY_NAMA, "");
    }
}