package com.example.myapp.Domains;

public class categoryDomain {

    private int id;
    private int loc_id;
    private String title;
    private String picPath;

    public categoryDomain(String title, String picPath) {
        this.title = title;
        this.picPath = picPath;
    }

    public categoryDomain(int id, int loc_id, String picPath) {
        this.id = id;
        this.loc_id = loc_id;
        this.picPath = picPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }
}
