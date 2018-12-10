package com.zzi.testdemo;

public class RecordRvData {
    private String Name;
    private String Phone;
    private int Background;
    private int Photo;

    public RecordRvData() {
    }

    public RecordRvData(String name, String phone, int background,int photo) {
        Name = name;
        Phone = phone;
        Background = background;
        Photo = photo;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setBackground(int background) {
        Background = background;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public String getPhone() {
        return Phone;
    }

    public int getBackground() {
        return Background;
    }

    public int getPhoto() {
        return Photo;
    }
}
