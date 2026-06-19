package com.uas.hudhasyururi;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {

    private ArrayList<Mahasiswa> listMahasiswa;

    public MahasiswaAdapter(ArrayList<Mahasiswa> listMahasiswa) {
        this.listMahasiswa = listMahasiswa;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Menyambungkan ke layout modern yang baru dibuat
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        Mahasiswa mhs = listMahasiswa.get(position);

        // Memastikan data terikat ke kolom yang 100% benar
        holder.tvNama.setText(mhs.getNama());
        holder.tvNim.setText("NIM: " + mhs.getNim());
        holder.tvJurusan.setText(mhs.getJurusan());
        holder.tvSemester.setText("Semester " + mhs.getSemester());

        // Aksi klik untuk masuk ke halaman detail
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailMahasiswaActivity.class);
            intent.putExtra("id", mhs.getId());
            intent.putExtra("nama", mhs.getNama());
            intent.putExtra("nim", mhs.getNim());
            intent.putExtra("prodi", mhs.getJurusan());
            intent.putExtra("semester", mhs.getSemester());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listMahasiswa != null ? listMahasiswa.size() : 0;
    }

    public static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNim, tvJurusan, tvSemester;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            // Mengikat ke ID statis yang baru di item_mahasiswa.xml
            tvNama = itemView.findViewById(R.id.tvItemNama);
            tvNim = itemView.findViewById(R.id.tvItemNim);
            tvJurusan = itemView.findViewById(R.id.tvItemJurusan);
            tvSemester = itemView.findViewById(R.id.tvItemSemester);
        }
    }
}