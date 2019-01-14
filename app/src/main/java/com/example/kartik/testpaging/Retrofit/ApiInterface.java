package com.example.kartik.testpaging.Retrofit;

import com.example.kartik.testpaging.ModelClasses.MovieResponse;
import com.example.kartik.testpaging.ModelClasses.ReviewResponse;
import com.example.kartik.testpaging.ModelClasses.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("movie/{movieType}")
    Call<MovieResponse> getPopularMovies(@Path ("movieType")String movieType, @Query("api_key") String api_key,
                                         @Query("page") long page);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getMoviesTrailers(@Path("id") long movieId, @Query("api_key") String api_key);


    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getMoviesReviews(@Path("id") long movieId, @Query("api_key") String api_key);

}