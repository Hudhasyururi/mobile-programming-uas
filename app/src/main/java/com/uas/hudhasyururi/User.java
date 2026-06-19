package com.uas.hudhasyururi;

public class User {
    private int id;
    private String nama, username, password;
    // Constructor, Getter, Setter
    public User(int id, String nama, String username, String password) {
        this.id = id; this.nama = nama; this.username = username; this.password = password;
    }
    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getUsername() { return username; }
}