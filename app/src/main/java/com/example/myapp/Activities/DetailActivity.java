package com.example.myapp.Activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.myapp.Domains.PopularDomain;
import com.example.myapp.R;
import com.example.myapp.SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTxt,locationTxt,bedTxt,guideTxt,wifiTxt,descriptionTxt,scoreTxt, priceTxt;

    private ImageView picImg,backBtn;
    int loc_id;

    private AppCompatButton bookingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        loc_id = getIntent().getIntExtra("id", -1);
        String title = getIntent().getStringExtra("title");
        String location = getIntent().getStringExtra("location");
        String desc = getIntent().getStringExtra("desc");
        int bed = getIntent().getIntExtra("bed", -1);
        boolean guide = getIntent().getBooleanExtra("guide", false);
        double score = getIntent().getDoubleExtra("score", -1.0);
        String pic = getIntent().getStringExtra("pic");
        boolean wifi = getIntent().getBooleanExtra("wifi", false);
        int price = getIntent().getIntExtra("price", -1);

        String convertedScore = String.valueOf(score);
        String convertedPrice = String.valueOf(price);
        String convertedBed = String.valueOf(bed);

        titleTxt =findViewById(R.id.titleTxt);
        locationTxt =findViewById(R.id.locationTxt);
        bedTxt = findViewById(R.id.bed_Txt);
        guideTxt =findViewById(R.id.guide_Txt);
        wifiTxt =findViewById(R.id.wifiTxt);
        descriptionTxt =findViewById(R.id.descriptionTxt);
        scoreTxt =findViewById(R.id.scoreTxt);
        picImg = findViewById(R.id.picImg);
        scoreTxt = findViewById(R.id.scoreTxt);
        priceTxt = findViewById(R.id.priceTxt);

        backBtn = findViewById(R.id.backBtn);
        bookingBtn = findViewById(R.id.book_btn);

        titleTxt.setText(title);
        scoreTxt.setText(convertedScore);
        locationTxt.setText(location);
        bedTxt.setText(convertedBed);
        descriptionTxt.setText(desc);
        priceTxt.setText("$ " + convertedPrice);

        if (guide){
            guideTxt.setText("Guide");
        }else{
            guideTxt.setText("No-Guide");
        }

        if (wifi){
            wifiTxt.setText("Wifi");
        }else{
            wifiTxt.setText("No-Wifi");
        }

        int drawableResId=getResources().getIdentifier(pic,"drawable",getPackageName());

        Glide.with(this)
                .load(drawableResId)
                .into(picImg);

        Glide.with(DetailActivity.this)
                .load(pic)
                .transform(new CenterCrop(),new GranularRoundedCorners(40,40,40,40))
                .placeholder(R.drawable.intro)
                .into(picImg);

        backBtn.setOnClickListener(v -> finish());

        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBooking();
            }
        });


    }

    private void addBooking() {
        SQLiteHelper dbHelper = new SQLiteHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String timestamp = getCurrentTimestamp();

        ContentValues values = new ContentValues();
        values.put("loc_id", loc_id);
        values.put("timestamp", timestamp);

        long newRowId = db.insert("bookings", null, values);
        db.close();

        if (newRowId != -1) {
            Toast.makeText(this, "Booking Success!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Booking Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentTimestamp() {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return sdf.format(new Date(currentTimeMillis));
    }


}