package com.example.myapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "travelapp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "password TEXT)";

    private static final String CREATE_TABLE_LOCATIONS = "CREATE TABLE locations (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT," +
            "location TEXT," +
            "description TEXT ," +
            "category TEXT," +
            "bed INTEGER," +
            "guide BOOLEAN," +
            "score DOUBLE," +
            "pic TEXT," +
            "wifi BOOLEAN," +
            "price INTEGER)";

    private static final String CREATE_TABLE_BOOKINGS = "CREATE TABLE bookings (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "loc_id INTEGER," +
            "timestamp TEXT)";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_LOCATIONS);
        db.execSQL(CREATE_TABLE_BOOKINGS);
        insertDummyBookData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS books");
        db.execSQL("DROP TABLE IF EXISTS userlendings");
        onCreate(db);
    }

    private void insertDummyBookData(SQLiteDatabase db) {

        String[] title = {
                "Magic Sands Beach Park",
                "Mission Beach",
                "Bryce Glamp And Camp",
                "Tarantula Ranch Campground & Vineyard near Death Valley National Park",
                "White Mountain National Forest",
                "Coconino National Forest",
                "Red Desert",
                "Patagonian Desert",
                "Mount Saint Elias",
                "Mount Bona"


        };

        String[] loc = {
                "Hawaii",
                "San Diego",
                "Cannonville",
                "Amargosa Valley",
                "New Hampshire",
                "Northern Arizona",
                "Wyoming",
                "Argentina",
                "Alaska border",
                "Eastern Alaska"


        };

        String[] desc = {
                "Magic Sands, or officially La'aloa Beach, is a popular white-sand beach in Kailua-Kona. It has come to be known as Magic Sands, or sometimes Disappearing Sands, because several times a year the sand gets washed out in storms, exposing the lava rock underneath.",
                "Mission Beach is a laid-back neighborhood whose namesake sands attract surfers, sunbathers and volleyball players. A paved path curls around Mission Bay, a popular spot for water sports. Belmont Park is a small amusement park with a wooden roller coaster built in 1925, while SeaWorld San Diego has marine shows and up-close views of orcas and dolphins",
                "Fitted with a patio, the units offer air conditioning and feature a flat-screen TV and a private bathroom with shower and a hair dryer. There is also a well-fitted kitchen in some of the units equipped with a dishwasher, an oven, and a stovetop. At the campground, each unit includes bed linen and towels.",
                "Located in Amargosa Valley, Tarantula Ranch Campground & Vineyard near Death Valley National Park provides accommodation with seating area. Featuring a shared kitchen, this property also provides guests with a picnic area. There is an outdoor fireplace and guests can make use of free WiFi and free private parking.",
                "White Mountain National Forest lies within the White Mountains in the U.S. states of New Hampshire and Maine. It's crossed by the White Mountain Trail and Kancamagus scenic drives, plus part of the Appalachian Trail. Dating to the 1860s, the Mt. Washington Cog Railway climbs to the summit of Mt. Washington, the highest peak in the northeastern U.S.",
                "The Coconino National Forest is a 1.856-million acre United States National Forest located in northern Arizona in the vicinity of Flagstaff, with elevations ranging from 2,600 feet to the highest point in Arizona at 12,633 feet.",
                "The Red Desert is a high-altitude desert and sagebrush steppe located in the south-central portion of the U.S. state of Wyoming, comprising approximately 9,320 square miles.",
                "The Patagonian Desert, also known as the Patagonian Steppe, is the largest desert in Argentina and is the 8th largest desert in the world by area, occupying 673,000 square kilometers",
                "Mount Saint Elias, the second-highest mountain in both Canada and the United States, stands on the Yukon and Alaska border about 26 miles southwest of Mount Logan, the highest mountain in Canada. ",
                "Mount Bona is one of the major mountains of the Saint Elias Mountains in eastern Alaska, and is the fifth-highest independent peak in the United States. It is either the tenth- or eleventh-highest peak in North America. Mount Bona and its adjacent neighbor Mount Churchill are both large ice-covered stratovolcanoes"

        };

        String[] cat = {
                "Beach",
                "Beach",
                "Camp",
                "Camp",
                "Forest",
                "Forest",
                "Desert",
                "Desert",
                "Mountain",
                "Mountain"

        };

        int[] bed = {
                3,
                1,
                2,
                4,
                1,
                2,
                0,
                1,
                3,
                0

        };

        boolean[] guide = {
                true,
                false,
                true,
                false,
                true,
                false,
                true,
                true,
                false,
                true


        };

        double[] score = {
                4.8,
                4.5,
                3.2,
                4.0,
                5.0,
                4.8,
                3.2,
                3.2,
                4.0,
                5.0

        };

        String[] path = {
                "https://lh3.googleusercontent.com/p/AF1QipPHQ4K39p7dWK5s_igkPpNsyATNTcy68IBkFGlA=s680-w680-h510",
                "https://a.cdn-hotels.com/gdcs/production138/d328/81e706c5-5023-4082-9764-f0e3e71f13cc.jpg",
                "https://cf.bstatic.com/xdata/images/hotel/max500/394307314.webp?k=c8e0ff0d33a82ca9f34150ad92d22f34793279b2ac4a6690350840d71b748a0f&o=",
                "https://cf.bstatic.com/xdata/images/hotel/max500/534918781.webp?k=9eeb2197c62945b1725a6fab7fe61781965eb05c24816c9de8abb363935bc426&o=",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2u5joo7QWxY4XpaY7ljZLrvQFxBxDPxLAtHdR1i_3kg&s",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1f-K-K7CW750lFY51WXnM1SihHAQe0oK8E3FfJLhefA&s",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Killpecker.jpg/300px-Killpecker.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Patagonian_Steppe_%283260842962%29.jpg/1200px-Patagonian_Steppe_%283260842962%29.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/4/4b/Mt_Saint_Elias%2C_South_Central_Alaska.jpg",
                "https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcT80ateYGO-u1nkYoeHddCSmEdEhvcl1bnKLJVtqCOJRS3uj-CnYfemdVOz6A-kowPZ"

        };

        boolean[] wifi = {
                true,
                true,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                false
        };

        int[] price = {
                2000,
                2500,
                1200,
                1800,
                1000,
                1200,
                500,
                800,
                1200,
                2500
        };

        ContentValues values = new ContentValues();
        int i = 0;
        while (i < title.length) {
            values.clear();
            values.put("title", title[i]);
            values.put("location", loc[i]);
            values.put("description", desc[i]);
            values.put("category", cat[i]);
            values.put("bed", bed[i]);
            values.put("guide", guide[i]);
            values.put("score", score[i]);
            values.put("pic", path[i]);
            values.put("wifi", wifi[i]);
            values.put("price", price[i]);
            db.insert("locations", null, values);
            i++;
        }

    }

}

