package com.example.kartik.testpaging.DataSource;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.kartik.testpaging.ModelClasses.MovieResponse;
import com.example.kartik.testpaging.ModelClasses.Movies;
import com.example.kartik.testpaging.Retrofit.ApiClient;
import com.example.kartik.testpaging.UI.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDataSource extends PageKeyedDataSource<Long,Movies> {





    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movies> callback) {

        if(!MainActivity.pref.equals("favourite")){
            ApiClient.getmInstance().getApi()
                    .getPopularMovies(MainActivity.pref,"5cf2ad47540f022c1822cc45594e268b",1)
                    .enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            if(response.body()!=null){
                                callback.onResult(response.body().getResults(),null, Long.parseLong("2"));
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });


        }

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movies> callback) {

        if(!MainActivity.pref.equals("favourite")) {
            ApiClient.getmInstance()
                    .getApi().getPopularMovies(MainActivity.pref, "5cf2ad47540f022c1822cc45594e268b", params.key)
                    .enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            long key = (params.key > 1) ? params.key - 1 : null;
                            if (response.body() != null) {
                                callback.onResult(response.body().getResults(), key);
                            }

                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });

        }
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movies> callback) {

        if(!MainActivity.pref.equals("favourite")) {
            ApiClient.getmInstance()
                    .getApi().getPopularMovies(MainActivity.pref, "5cf2ad47540f022c1822cc45594e268b", params.key)
                    .enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            long nextKey = (params.key == response.body().getTotalResults()) ? null : params.key + 1;
                            if (response.body() != null) {
                                callback.onResult(response.body().getResults(), nextKey);
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });

        }
    }
}
