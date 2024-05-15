package com.example.myapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Adapters.BookingAdapter;
import com.example.myapp.Adapters.CategoryAdapter;
import com.example.myapp.Adapters.PopularAdapter;
import com.example.myapp.Domains.PopularDomain;
import com.example.myapp.Domains.categoryDomain;
import com.example.myapp.R;
import com.example.myapp.SQLiteHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView.Adapter adapterPopular,adapterCat, adapterBook;
    RecyclerView recyclerViewPopular,recyclerViewCategory ,recyclerCatPopup, recyclerBookPopup;
    SQLiteHelper dbHelper;
    ConstraintLayout layout;
    ImageView profileBtn;
    ArrayList<PopularDomain>items=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.conslayout);
        profileBtn = findViewById(R.id.profile_btn);

        dbHelper = new SQLiteHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.close();

        initRecyclerView();

        SharedPreferences sharedPreferences = getSharedPreferences("Startup", MODE_PRIVATE);
        String checkStart = sharedPreferences.getString("isStarted","");

        if (!checkStart.equals("yes")){
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }

        String cat = getIntent().getStringExtra("title");

        if (cat != null && cat.equals("profile")){
            categoryProfile();
        } else if (cat != null && (cat.equals("Beach") || cat.equals("Camp") || cat.equals("Forest") || cat.equals("Desert") || cat.equals("Mountain"))) {
            categoryPopup(cat);
        }

        setupSearchListener();

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("title","profile");
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

    }

    private void categoryProfile() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpview = inflater.inflate(R.layout.popup_profile, null);

        recyclerBookPopup = popUpview.findViewById(R.id.booking_views);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpview, width, height, focusable);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerBookPopup.setLayoutManager(layoutManager);

        dbHelper = new SQLiteHelper(this);
        ArrayList<categoryDomain>items=new ArrayList<>();
        adapterBook = new BookingAdapter(items,MainActivity.this);
        recyclerBookPopup.setAdapter(adapterBook);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"id", "loc_id", "timestamp"};
        Cursor cursor = db.query("bookings", projection, null, null, null, null, "id DESC");

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int loc_id = cursor.getInt(cursor.getColumnIndexOrThrow("loc_id"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

                categoryDomain category = new categoryDomain(id, loc_id, time);
                items.add(category);
                count++;

            } while (cursor.moveToNext() && count < 8);

            cursor.close();
            adapterBook.notifyDataSetChanged();
        }

        db.close();
        layout.post(() -> popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0));
    }

    private void initRecyclerView() {


        recyclerViewPopular = findViewById(R.id.view_pop);
        dbHelper = new SQLiteHelper(this);
        adapterPopular = new PopularAdapter(items,MainActivity.this);

        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewPopular.setAdapter(adapterPopular);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"id", "title", "location", "description","category","bed","guide","score","pic","wifi","price"};
        Cursor cursor = db.query("locations", projection, null, null, null, null, "id DESC");

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                int bed = cursor.getInt(cursor.getColumnIndexOrThrow("bed"));
                int guideInt = cursor.getInt(cursor.getColumnIndexOrThrow("guide"));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
                String pic = cursor.getString(cursor.getColumnIndexOrThrow("pic"));
                int wifiInt = cursor.getInt(cursor.getColumnIndexOrThrow("wifi"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

                boolean guide = guideInt == 1;
                boolean wifi = wifiInt == 1;

                PopularDomain popularDomain = new PopularDomain(id, title, location, description, category, bed, guide, score, pic, wifi, price);
                items.add(popularDomain);
                count++;

            } while (cursor.moveToNext() && count < 8);

            cursor.close();
            adapterPopular.notifyDataSetChanged();
        }

        db.close();

        ArrayList<categoryDomain> catsList=new ArrayList<>();
        catsList.add(new categoryDomain("Beach","cat1"));
        catsList.add(new categoryDomain("Camp","cat2"));
        catsList.add(new categoryDomain("Forest","cat3"));
        catsList.add(new categoryDomain("Desert","cat4"));
        catsList.add(new categoryDomain("Mountain","cat5"));


        recyclerViewCategory = findViewById(R.id.view_cat);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapterCat = new CategoryAdapter(catsList);
        recyclerViewCategory.setAdapter(adapterCat);


    }

    private void categoryPopup(String cat) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUpview = inflater.inflate(R.layout.popup_category, null);

        recyclerCatPopup = popUpview.findViewById(R.id.container_views);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popUpview, width, height, focusable);

        int spanCount = 1;
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, spanCount);
        recyclerCatPopup.setLayoutManager(layoutManager);

        dbHelper = new SQLiteHelper(this);
        ArrayList<PopularDomain>items=new ArrayList<>();
        adapterCat = new PopularAdapter(items,MainActivity.this);
        recyclerCatPopup.setAdapter(adapterCat);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"id", "title", "location", "description","category","bed","guide","score","pic","wifi","price"};
        String selection = "category = ?";
        String[] selectionArgs = {cat};
        Cursor cursor = db.query("locations", projection, selection, selectionArgs, null, null, "id DESC");

        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                int bed = cursor.getInt(cursor.getColumnIndexOrThrow("bed"));
                int guideInt = cursor.getInt(cursor.getColumnIndexOrThrow("guide"));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
                String pic = cursor.getString(cursor.getColumnIndexOrThrow("pic"));
                int wifiInt = cursor.getInt(cursor.getColumnIndexOrThrow("wifi"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

                boolean guide = guideInt == 1;
                boolean wifi = wifiInt == 1;

                PopularDomain popularDomain = new PopularDomain(id, title, location, description, category, bed, guide, score, pic, wifi, price);
                items.add(popularDomain);
                count++;

            } while (cursor.moveToNext() && count < 8);

            cursor.close();
            adapterCat.notifyDataSetChanged();
        }

        db.close();
        layout.post(() -> popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0));

    }

    private void setupSearchListener() {

        EditText searchTxt = findViewById(R.id.editTextText3);

        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().trim();
                searchBooks(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchBooks(String searchText) {

        items.clear();

        if (searchText.isEmpty()) {
            initRecyclerView();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"id", "title", "location", "description","category","bed","guide","score","pic","wifi","price"};
        String selection = "title LIKE ? OR location LIKE ?";
        String[] selectionArgs = new String[]{"%" + searchText + "%", "%" + searchText + "%"};

        Cursor cursor = db.query("locations", projection, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                int bed = cursor.getInt(cursor.getColumnIndexOrThrow("bed"));
                int guideInt = cursor.getInt(cursor.getColumnIndexOrThrow("guide"));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
                String pic = cursor.getString(cursor.getColumnIndexOrThrow("pic"));
                int wifiInt = cursor.getInt(cursor.getColumnIndexOrThrow("wifi"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

                boolean guide = guideInt == 1;
                boolean wifi = wifiInt == 1;

                PopularDomain popularDomain = new PopularDomain(id, title, location, description, category, bed, guide, score, pic, wifi, price);
                items.add(popularDomain);

            } while (cursor.moveToNext());

            cursor.close();
            adapterPopular.notifyDataSetChanged();
        }else{
            items.clear();
            adapterPopular.notifyDataSetChanged();
        }

        db.close();
    }

}