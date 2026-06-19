package com.uas.hudhasyururi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class MahasiswaAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> listId;
    private ArrayList<String> listNim;
    private ArrayList<String> listNama;
    private ArrayList<String> listJurusan;
    private ArrayList<String> listSemester;

    public MahasiswaAdapter(Context context, ArrayList<String> listId, ArrayList<String> listNim,
                            ArrayList<String> listNama, ArrayList<String> listJurusan, ArrayList<String> listSemester) {
        this.context = context;
        this.listId = listId;
        this.listNim = listNim;
        this.listNama = listNama;
        this.listJurusan = listJurusan;
        this.listSemester = listSemester;
    }

    @Override
    public int getCount() {
        return listNama.size();
    }

    @Override
    public Object getItem(int position) {
        return listNama.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false);
        }

        TextView txtNama = convertView.findViewById(R.id.txtItemNama);
        TextView txtNim = convertView.findViewById(R.id.txtItemNim);
        TextView txtDetail = convertView.findViewById(R.id.txtItemDetail);

        // Pasang data ke komponen teks agar terlihat rapi dan terpisah
        txtNama.setText(listNama.get(position));
        txtNim.setText("NIM: " + listNim.get(position));
        txtDetail.setText(listJurusan.get(position) + "  •  Semester " + listSemester.get(position));

        return convertView;
    }
}