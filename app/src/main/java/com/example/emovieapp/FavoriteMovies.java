package com.example.emovieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.emovieapp.Classes.MoviesClass;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovies extends SQLiteOpenHelper {
    private static String databasename="Movies";
    SQLiteDatabase moviesDB;
    int id;

    public FavoriteMovies (Context context){
        super(context,databasename,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table movies(id integer Primary key not null,name text not null,poster text not null," +
                "rating integer not null,back text,overView text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if Exists movies");
        onCreate(db);
    }

    public boolean addNewMovie(int id,String title,String poster_path,int rating,String back,String overView){
        moviesDB = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("id",id);
        row.put("name",title);
        row.put("poster",poster_path);
        row.put("rating",rating);
        row.put("back",back);
        row.put("overView",overView);

        long result = moviesDB.insert("movies",null, row);
        if(result == -1)
            return false;
        else
            return true;
    }

    public List<MoviesClass.ResultsBean> getAllMovies(){
        moviesDB = this.getReadableDatabase();
        String s="Select * from movies";
        Cursor cursor = moviesDB.rawQuery(s,null);
        List<MoviesClass.ResultsBean> list = new ArrayList<>();
        while(cursor.moveToNext()){
            list.add(new MoviesClass.ResultsBean(cursor.getInt(0),cursor.getString(1)
                    ,cursor.getString(2),cursor.getDouble(3)/10,cursor.getString(4)
                    ,cursor.getString(5)));
        }
        return list;
    }

    public boolean isLiked(int id){
        moviesDB = this.getReadableDatabase();
        String s="Select * from movies where id = " + id;
        Cursor cursor = moviesDB.rawQuery(s,null);
        while(cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public void removeMovie(String name){
        moviesDB = this.getWritableDatabase();
        moviesDB.delete("movies", "name = ?", new String[]{name});
    }

}
