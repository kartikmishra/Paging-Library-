package com.example.kartik.testpaging.DataSource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.kartik.testpaging.ModelClasses.Movies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MoviesViewModel extends ViewModel {

    private static final String TAG = "MoviesViewModel";

    private Executor executor;
     LiveData<PagedList<Movies>> moviesPagedList;
     LiveData<MoviesDataSource> pageKeyedDataSourceLiveData;

     private PageKeyedDataSource<Long,Movies> pageKeyedDataSource;



    public MoviesViewModel(){

        executor = Executors.newFixedThreadPool(5);
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory();
        //dataSource = movieDataSourceFactory.create();
         //pageKeyedDataSource = (PageKeyedDataSource<Long, Movies>) movieDataSourceFactory.create();
        pageKeyedDataSourceLiveData =  movieDataSourceFactory.getMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20))
                .build();

        //moviesPagedList = (new LivePagedListBuilder(movieDataSourceFactory,config))
          //      .setFetchExecutor(executor).build();

         moviesPagedList = (new LivePagedListBuilder(movieDataSourceFactory,config))
                 .setFetchExecutor(executor)
                 .build();
    }

    public LiveData<PagedList<Movies>> getMoviesPagedList() {
        return moviesPagedList;
    }



}
