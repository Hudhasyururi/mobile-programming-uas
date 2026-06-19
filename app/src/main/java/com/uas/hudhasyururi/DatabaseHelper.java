package com.uas.hudhasyururi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Kampus.db";
    // Naikkan ke versi 4 untuk reset struktur tabel baru
    private static final int DATABASE_VERSION = 4;

    // Tabel User
    private static final String TABLE_USER = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    // Tabel Mahasiswa
    private static final String TABLE_MAHASISWA = "mahasiswa";
    private static final String COL_MHS_ID = "id";
    private static final String COL_MHS_NAMA = "nama";
    private static final String COL_MHS_NIM = "nim";
    private static final String COL_MHS_PRODI = "prodi";
    private static final String COL_MHS_SEMESTER = "semester"; // KOLOM BARU
    private static final String COL_MHS_OWNER = "username_owner";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE " + TABLE_USER + " (" +
                COL_USERNAME + " TEXT PRIMARY KEY, " +
                COL_PASSWORD + " TEXT)";
        db.execSQL(createTableUser);

        String createTableMahasiswa = "CREATE TABLE " + TABLE_MAHASISWA + " (" +
                COL_MHS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MHS_NAMA + " TEXT, " +
                COL_MHS_NIM + " TEXT, " +
                COL_MHS_PRODI + " TEXT, " +
                COL_MHS_SEMESTER + " TEXT, " + // MENAMBAHKAN SEMESTER KE TABLE
                COL_MHS_OWNER + " TEXT)";
        db.execSQL(createTableMahasiswa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAHASISWA);
        onCreate(db);
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USER, null, values);
        return result != -1;
    }

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    // UPDATE: Menambahkan parameter semester untuk disimpan ke DB
    public boolean insertMahasiswa(String nama, String nim, String prodi, String semester, String ownerUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MHS_NAMA, nama);
        values.put(COL_MHS_NIM, nim);
        values.put(COL_MHS_PRODI, prodi);
        values.put(COL_MHS_SEMESTER, semester);
        values.put(COL_MHS_OWNER, ownerUsername);

        long result = db.insert(TABLE_MAHASISWA, null, values);
        return result != -1;
    }

    public ArrayList<Mahasiswa> getAllMahasiswa(String ownerUsername) {
        ArrayList<Mahasiswa> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MAHASISWA + " WHERE " + COL_MHS_OWNER + "=?", new String[]{ownerUsername});

        if (cursor.moveToFirst()) {
            do {
                String id = String.valueOf(cursor.getInt(0));
                String nama = cursor.getString(1);
                String nim = cursor.getString(2);
                String prodi = cursor.getString(3);
                String semester = cursor.getString(4); // Ambil data semester dari DB
                String owner = cursor.getString(5);

                Mahasiswa mhs = new Mahasiswa(id, nama, nim, prodi, semester, owner);
                list.add(mhs);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean deleteMahasiswa(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_MAHASISWA, COL_MHS_ID + "=?", new String[]{id});
        return result > 0;
    }

    public boolean updateMahasiswa(String id, String nim, String nama, String jurusan, String semester) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MHS_NIM, nim);
        values.put(COL_MHS_NAMA, nama);
        values.put(COL_MHS_PRODI, jurusan);
        values.put(COL_MHS_SEMESTER, semester);

        int result = db.update(TABLE_MAHASISWA, values, COL_MHS_ID + "=?", new String[]{id});
        return result > 0;
    }
}