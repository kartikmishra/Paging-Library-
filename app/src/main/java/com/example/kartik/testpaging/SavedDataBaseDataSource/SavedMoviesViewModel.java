package com.example.kartik.testpaging.SavedDataBaseDataSource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.kartik.testpaging.ModelClasses.Movies;
import com.example.kartik.testpaging.SavedDataBaseDataSource.SavedMoviesDataSource;
import com.example.kartik.testpaging.SavedDataBaseDataSource.SavedMoviesDataSourceFactory;


public class SavedMoviesViewModel extends ViewModel {

    LiveData<PagedList<Movies>> pagedListLiveData;
    LiveData<SavedMoviesDataSource> sourceLiveData;

    public SavedMoviesViewModel() {

        SavedMoviesDataSourceFactory savedMoviesDataSourceFactory = new SavedMoviesDataSourceFactory();
        sourceLiveData = savedMoviesDataSourceFactory.getMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setPageSize(20))
                        .build();


        pagedListLiveData = new LivePagedListBuilder(savedMoviesDataSourceFactory,config)
                .build();
    }

    public LiveData<PagedList<Movies>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}
