package com.uas.hudhasyururi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;

public class DaftarMahasiswaActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvEmpty;
    private ExtendedFloatingActionButton fabTambah;
    private DatabaseHelper dbHelper;

    private ArrayList<String> listIdMhs, listNimMhs, listNamaMhs, listJurusanMhs, listSemesterMhs;
    private CustomMahasiswaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_mahasiswa);

        dbHelper = new DatabaseHelper(this);

        listView = findViewById(R.id.rvMahasiswa);
        tvEmpty = findViewById(R.id.tvEmpty);
        fabTambah = findViewById(R.id.fabTambah);

        listIdMhs = new ArrayList<>();
        listNimMhs = new ArrayList<>();
        listNamaMhs = new ArrayList<>();
        listJurusanMhs = new ArrayList<>();
        listSemesterMhs = new ArrayList<>();

        muatDataMahasiswa();

        if (listView != null) {
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(DaftarMahasiswaActivity.this, DetailMahasiswaActivity.class);
                intent.putExtra("MHS_ID", listIdMhs.get(position));
                intent.putExtra("MHS_NIM", listNimMhs.get(position));
                intent.putExtra("MHS_NAMA", listNamaMhs.get(position));
                intent.putExtra("MHS_JURUSAN", listJurusanMhs.get(position));
                intent.putExtra("MHS_SEMESTER", listSemesterMhs.get(position));
                startActivity(intent);
            });
        }

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
        muatDataMahasiswa(); // Segarkan data secara real-time
    }

    private void muatDataMahasiswa() {
        listIdMhs.clear();
        listNimMhs.clear();
        listNamaMhs.clear();
        listJurusanMhs.clear();
        listSemesterMhs.clear();

        // 🔥 AMBIL SESI: Ambil siapa yang login
        SharedPreferences sharedPref = getSharedPreferences("SesiUser", Context.MODE_PRIVATE);
        String usernameLogin = sharedPref.getString("USERNAME_LOGIN", "umum");

        // Ambil data dari SQLite yang HANYA milik userLogin ini
        Cursor res = dbHelper.getMahasiswaByUser(usernameLogin);

        if (res != null && res.getCount() > 0) {
            if (tvEmpty != null) tvEmpty.setVisibility(View.GONE);
            while (res.moveToNext()) {
                listIdMhs.add(res.getString(0));
                listNimMhs.add(res.getString(1));
                listNamaMhs.add(res.getString(2));
                listJurusanMhs.add(res.getString(3));
                listSemesterMhs.add(res.getString(4));
            }
            res.close();
        } else {
            if (tvEmpty != null) tvEmpty.setVisibility(View.VISIBLE);
        }

        // Hubungkan ke kelas Custom Adapter di bawah
        adapter = new CustomMahasiswaAdapter(this, listNamaMhs, listNimMhs, listJurusanMhs, listSemesterMhs);
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }

    // 🔥 CUSTOM ADAPTER: Menyambungkan data Java ke dalam layout Card View buatan Anda
    private class CustomMahasiswaAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<String> namaMhs;
        private final ArrayList<String> nimMhs;
        private final ArrayList<String> jurusanMhs;
        private final ArrayList<String> semesterMhs;

        public CustomMahasiswaAdapter(Context context, ArrayList<String> namaMhs, ArrayList<String> nimMhs,
                                      ArrayList<String> jurusanMhs, ArrayList<String> semesterMhs) {
            super(context, R.layout.item_mahasiswa, namaMhs);
            this.context = context;
            this.namaMhs = namaMhs;
            this.nimMhs = nimMhs;
            this.jurusanMhs = jurusanMhs;
            this.semesterMhs = semesterMhs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false);
            }

            // Menghubungkan ID komponen Card View XML Anda
            TextView txtNama = convertView.findViewById(R.id.txtItemNama);
            TextView txtNim = convertView.findViewById(R.id.txtItemNim);
            TextView txtDetail = convertView.findViewById(R.id.txtItemDetail);

            if (txtNama != null) txtNama.setText(namaMhs.get(position));
            if (txtNim != null) txtNim.setText("NIM: " + nimMhs.get(position));
            if (txtDetail != null) {
                txtDetail.setText(jurusanMhs.get(position) + " • Semester " + semesterMhs.get(position));
            }

            return convertView;
        }
    }
}