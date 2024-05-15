package com.example.myapp.Domains;

import java.io.Serializable;

public class PopularDomain implements Serializable {

    private int id;
    private String title;
    private String location;
    private String description;
    private String category;
    private int bed;
    private boolean guide;
    private double score;
    private String pic;
    private boolean wifi;
    private int price;

    public PopularDomain(int id, String title, String location, String description, String category, int bed, boolean guide, double score, String pic, boolean wifi, int price) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.description = description;
        this.category = category;
        this.bed = bed;
        this.guide = guide;
        this.score = score;
        this.pic = pic;
        this.wifi = wifi;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public boolean isGuide() {
        return guide;
    }

    public void setGuide(boolean guide) {
        this.guide = guide;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

     /*items.add(new PopularDomain("Mar caible, avendia lago","Miami Beach","This 2 bed/1bath home boats an enormous,open-living plan,accented by striking" +
                " architectural features and high-end finishes." +
                "Feel inspired by open sight lines that" +
                " embrace the outdoors,crowned by stunning coffered ceilings.","Beach",2,true,4.8,"pic1",true,1000));
        items.add(new PopularDomain("Passo Rolle,TN","Hawaii Beach","This 1 bed/1bath home boats an enormous,open-living plan,accented by striking" +
                " architectural features and high-end finishes." +
                "Feel inspired by open sight lines that" +
                " embrace the outdoors,crowned by stunning coffered ceilings.","Beach",1,false,4.8,"pic1",false,2500));
        items.add(new PopularDomain("Mar caible, avendia lago","Miami Beach","This 3 bed/1bath home boats an enormous,open-living plan,accented by striking" +
                " architectural features and high-end finishes." +
                "Feel inspired by open sight lines that" +
                " embrace the outdoors,crowned by stunning coffered ceilings.","Beach",3,true,4.8,"pic1",true,3000));
*/
}
