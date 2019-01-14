package com.example.kartik.testpaging.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;

import com.example.kartik.testpaging.ModelClasses.Movies;

@Database(entities = {Movies.class},version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;
    private static String DATABASE_NAME = "movies";

    public static AppDatabase  getsInstance(Context context){
        if(sInstance==null){
            synchronized (LOCK){
                Log.d(TAG, "Creating new Database Instance ");
                sInstance = Room.databaseBuilder(context.getApplicationContext()
                        ,AppDatabase.class,AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(TAG, "Getting the Database Instance");
        return sInstance;
    }

    public abstract MoviesDao moviesDao();
}
