package com.example.kartik.testpaging.Database;



import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;

import com.example.kartik.testpaging.ModelClasses.Movies;

import java.util.List;



@Dao
public interface MoviesDao {

    @Query("SELECT * FROM moviesTable")
    List<Movies> loadAllMovies();

//    @Query("SELECT * FROM moviesTable WHERE id IN (:userIds)")
//    List<Movies> loadAllByIds(long userIds);


    @Query("SELECT * FROM moviesTable WHERE id = :movesID")
    long loadAllByIds(long movesID);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(Movies movies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovies(Movies movies);

    @Delete
    void deleteMovies(Movies movies);
}
