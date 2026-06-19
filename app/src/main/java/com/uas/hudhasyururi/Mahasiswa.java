package com.uas.hudhasyururi;

public class Mahasiswa {
    private String id;
    private String nama;
    private String nim;
    private String jurusan;
    private String semester;
    private String owner;

    public Mahasiswa(String id, String nama, String nim, String jurusan, String semester, String owner) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
        this.semester = semester;
        this.owner = owner;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public String getNim() { return nim; }
    public String getJurusan() { return jurusan; }
    public String getSemester() { return semester; }
    public String getOwner() { return owner; }
}