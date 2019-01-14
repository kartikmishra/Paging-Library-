package com.example.kartik.testpaging.SavedDataBaseDataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.kartik.testpaging.SavedDataBaseDataSource.SavedMoviesDataSource;

public class SavedMoviesDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<SavedMoviesDataSource> mutableLiveData = new MutableLiveData<>();



    @Override
    public DataSource create() {

        SavedMoviesDataSource savedMoviesDataSource = new SavedMoviesDataSource();
        mutableLiveData.postValue(savedMoviesDataSource);
        return savedMoviesDataSource;
    }


    public MutableLiveData<SavedMoviesDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
