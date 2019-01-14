package com.example.kartik.testpaging.SavedDataBaseDataSource;

import android.arch.lifecycle.LiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.kartik.testpaging.Database.AppDatabase;
import com.example.kartik.testpaging.ModelClasses.Movies;
import com.example.kartik.testpaging.UI.DetailActivity;
import com.example.kartik.testpaging.UI.MainActivity;

import java.util.List;

public class SavedMoviesDataSource extends ItemKeyedDataSource<Long,Movies> {


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Movies> callback) {
        if(MainActivity.pref.equals("favourite")){


          List<Movies> items = MainActivity.mDB.moviesDao().loadAllMovies();
            callback.onResult(items);
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Movies> callback) {
        if(params.key==params.key-1){
            callback.onResult(MainActivity.movies);
        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Movies> callback) {


    }

    @NonNull
    @Override
    public Long getKey(@NonNull Movies item) {
        return item.getId();
    }
}
