package com.uas.hudhasyururi;

public class Mahasiswa {
    private String id;
    private String nim;
    private String nama;
    private String jurusan;
    private String semester;

    // Constructor Utama (Sesuai dengan urutan pemanggilan di DaftarMahasiswaActivity)
    public Mahasiswa(String id, String nim, String nama, String jurusan, String semester) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.semester = semester;
    }

    // Getter dan Setter untuk mengakses data
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}