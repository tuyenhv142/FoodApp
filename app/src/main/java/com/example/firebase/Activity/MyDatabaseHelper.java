package com.example.firebase.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FoodFavorite.db";
    private static final int DATABASE_VERSION = 1;

    private final String TABLE_NAME = "my_food";
    private final String FOOD_ID ="id";
    private final String TITLE = "title";
    private final String CATEGORY = "category";
    private final String IMAGE = "image";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + FOOD_ID +" TEXT PRIMARY KEY, " +
                TITLE + " TEXT, " +
                CATEGORY + " TEXT, " +
                IMAGE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addFoodFavorite(String id, String title, String category, String image ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FOOD_ID, id);
        cv.put(TITLE, title);
        cv.put(CATEGORY, category);
        cv.put(IMAGE, image);

        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Already exist",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Successfully",Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    void delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{id});

        if(result == -1){
            Toast.makeText(context,"Faild",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Successfully",Toast.LENGTH_SHORT).show();
        }
    }
}
