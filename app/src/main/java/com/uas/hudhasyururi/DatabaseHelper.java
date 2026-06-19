package com.uas.hudhasyururi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UAS_Akademik.db";
    private static final int DATABASE_VERSION = 2; // Naikkan versi jika mengubah struktur tabel

    private static final String TABLE_USER = "tabel_user";
    private static final String COL_USER_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    private static final String TABLE_MAHASISWA = "tabel_mahasiswa";
    private static final String COL_MHS_ID = "id";
    private static final String COL_MHS_NIM = "nim";
    private static final String COL_MHS_NAMA = "nama";
    private static final String COL_MHS_JURUSAN = "jurusan";
    private static final String COL_MHS_SEMESTER = "semester";
    // 🔥 KUNCI: Kolom baru untuk menandai data ini milik siapa
    private static final String COL_USER_PEMILIK = "user_pemilik";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT)");

        // Tambahkan COL_USER_PEMILIK di baris paling bawah tabel mahasiswa
        db.execSQL("CREATE TABLE " + TABLE_MAHASISWA + " (" +
                COL_MHS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MHS_NIM + " TEXT, " +
                COL_MHS_NAMA + " TEXT, " +
                COL_MHS_JURUSAN + " TEXT, " +
                COL_MHS_SEMESTER + " TEXT, " +
                COL_USER_PEMILIK + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        onCreate(db);
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // 🔥 KUNCI TAMBAH: Sekarang harus memasukkan parameter usernamePemilik
    public boolean addMahasiswa(String nim, String nama, String jurusan, String semester, String usernamePemilik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MHS_NIM, nim);
        values.put(COL_MHS_NAMA, nama);
        values.put(COL_MHS_JURUSAN, jurusan);
        values.put(COL_MHS_SEMESTER, semester);
        values.put(COL_USER_PEMILIK, usernamePemilik); // Data disimpan sesuai user yang login

        long result = db.insert(TABLE_MAHASISWA, null, values);
        return result != -1;
    }

    // 🔥 KUNCI TAMPIL: Hanya mengambil data mahasiswa yang COCOK dengan user yang sedang login
    public Cursor getMahasiswaByUser(String usernamePemilik) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MAHASISWA + " WHERE " + COL_USER_PEMILIK + " = ?", new String[]{usernamePemilik});
    }

    public boolean updateMahasiswa(String id, String nim, String nama, String jurusan, String semester) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MHS_NIM, nim);
        values.put(COL_MHS_NAMA, nama);
        values.put(COL_MHS_JURUSAN, jurusan);
        values.put(COL_MHS_SEMESTER, semester);

        int result = db.update(TABLE_MAHASISWA, values, COL_MHS_ID + " = ?", new String[]{id});
        return result > 0;
    }

    public boolean deleteMahasiswa(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_MAHASISWA, COL_MHS_ID + " = ?", new String[]{id});
        return result > 0;
    }
}