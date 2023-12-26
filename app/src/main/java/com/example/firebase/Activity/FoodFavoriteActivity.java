package com.example.firebase.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.firebase.Adapter.FavoriteFoodAdapter;
import com.example.firebase.R;

import java.util.ArrayList;

public class FoodFavoriteActivity extends AppCompatActivity {


    MyDatabaseHelper myDatabaseHelper;
    private RecyclerView foodRv;
    private ArrayList<String> id;
    private ArrayList<String> title;
    private ArrayList<String> category;
    private ArrayList<String> image;
    private FavoriteFoodAdapter favoriteFoodAdapter;
    private androidx.appcompat.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_favorite);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitleTextAppearance(this, R.style.CustomToolbarTitleStyle);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Food Favorite");
        actionBar.setDisplayHomeAsUpEnabled(true);

        myDatabaseHelper = new MyDatabaseHelper(FoodFavoriteActivity.this);

        id = new ArrayList<>();
        title = new ArrayList<>();
        category = new ArrayList<>();
        image = new ArrayList<>();
        foodRv = findViewById(R.id.foodRv);

        showData();

        favoriteFoodAdapter = new FavoriteFoodAdapter(FoodFavoriteActivity.this,this, id,title, category, image);
        foodRv.setAdapter(favoriteFoodAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void showData(){
        Cursor cursor = myDatabaseHelper.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(FoodFavoriteActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                category.add(cursor.getString(2));
                image.add(cursor.getString(3));
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}