package com.example.kartik.testpaging.DataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.kartik.testpaging.ModelClasses.Movies;


public class MovieDataSourceFactory extends DataSource.Factory<Long,Movies> {

    private MutableLiveData<MoviesDataSource> mutableLiveData = new MutableLiveData<>();


    //    public MovieDataSourceFactory() {
//        this.mutableLiveData = new MutableLiveData<MoviesDataSource>();
//    }

    @Override
    public DataSource<Long,Movies> create() {

        MoviesDataSource moviesDataSource = new MoviesDataSource();
        mutableLiveData.postValue(moviesDataSource);
        // Log.d(TAG, "create: "+moviesDataSource.);
        return moviesDataSource;
    }



    public MutableLiveData<MoviesDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}