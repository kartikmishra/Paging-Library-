package com.example.kartik.testpaging.UI;


import android.content.Intent;

import android.net.ParseException;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kartik.testpaging.Adapters.MoviesAdapter;
import com.example.kartik.testpaging.Adapters.ReviewAdapter;
import com.example.kartik.testpaging.Adapters.TrailerAdapter;
import com.example.kartik.testpaging.AppExecutors;
import com.example.kartik.testpaging.Database.AppDatabase;
import com.example.kartik.testpaging.ModelClasses.Movies;
import com.example.kartik.testpaging.Adapters.SavedMoviesAdapter;
import com.example.kartik.testpaging.R;
import com.example.kartik.testpaging.Retrofit.ApiClient;
import com.example.kartik.testpaging.ModelClasses.ReviewModel;
import com.example.kartik.testpaging.ModelClasses.ReviewResponse;
import com.example.kartik.testpaging.ModelClasses.TrailerModel;
import com.example.kartik.testpaging.ModelClasses.TrailerResponse;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListener{

    private ImageView largeImage;
    private TextView movieName,releaseDate,overView;
    private ImageView smallImage;
    private RatingBar ratingBar;


    public static List<TrailerModel> trailerModelList = new ArrayList<>();
    public static   List<String> key = new ArrayList<>();
    public  static  List<ReviewModel> reviewModels = new ArrayList<>();


    private static final String TAG = "DetailActivity";
    public static int position;
    public static int mPosition;
    private Movies movies;
    private Movies savedMovies;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private AppDatabase mDB;
    private ImageView saveImageView;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        Intent intent  = getIntent();
         position = intent.getIntExtra("moviePosition",0);
         mPosition = intent.getIntExtra("movie_position",0);

        largeImage = findViewById(R.id.detail_activity__iv);
        movieName = findViewById(R.id.tv_movieName_Label);
        releaseDate = findViewById(R.id.tv_releaseDateValue);
        smallImage = findViewById(R.id.detail_activity_small_iv);
        ratingBar = findViewById(R.id.tv_vote_average);
        overView = findViewById(R.id.tv_overView);
        saveImageView = findViewById(R.id.fav_btn);

        movies = MoviesAdapter.list.get(position);

        if(SavedMoviesAdapter.moviesList!=null && SavedMoviesAdapter.moviesList.size()>0){
            savedMovies = SavedMoviesAdapter.moviesList.get(mPosition);
        }
        mDB = AppDatabase.getsInstance(getApplicationContext());
        RecyclerView trailerRecyclerView = findViewById(R.id.recyclerView_trailers);
        RecyclerView reviewRecyclerView = findViewById(R.id.recyclerView_reviews);



        trailerAdapter = new TrailerAdapter(this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);
        trailerRecyclerView.setHasFixedSize(true);
        trailerRecyclerView.setAdapter(trailerAdapter);
        //trailerAdapter.notifyDataSetChanged();

        reviewAdapter = new ReviewAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        reviewRecyclerView.setLayoutManager(layoutManager);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.setAdapter(reviewAdapter);

        if(!MainActivity.pref.equals("favourite")){


            getTrailers();
            getReviews();

            assert actionBar != null;
            actionBar.setTitle(movies.getTitle());

            String movie_poster_url_big = "http://image.tmdb.org/t/p/" + "w780"+"/"+movies.getPoster_path();
            Picasso.with(getApplicationContext()).load(movie_poster_url_big).into(largeImage);


            Picasso.with(getApplicationContext()).load(movie_poster_url_big).into(smallImage);

            movieName.setText(movies.getTitle());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


            try {
                String date = DateUtils.formatDateTime(this,
                        formatter.parse(movies.getRelease_date()).getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
                releaseDate.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


            Double average =movies.getVote_average();
            String finalAverage = Double.toString(average/2);

            ratingBar.setNumStars(5);
            ratingBar.setRating(Float.parseFloat(finalAverage));

            if(movies.getOverview()!=null){
                overView.setText(MoviesAdapter.list.get(position).getOverview());
            }else {
                overView.setText("No overview available");
            }
        }

        else if(MainActivity.pref.equals("favourite")){

            getTrailers();
            getReviews();
            String movie_poster_url_big = "http://image.tmdb.org/t/p/" + "w780"+"/"+
                    SavedMoviesAdapter.moviesList.get(mPosition).getPoster_path();
            Picasso.with(getApplicationContext()).load(movie_poster_url_big).into(largeImage);


            Picasso.with(getApplicationContext()).load(movie_poster_url_big).into(smallImage);

            movieName.setText(SavedMoviesAdapter.moviesList.get(mPosition).getTitle());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


            try {
                String date = DateUtils.formatDateTime(this,
                        formatter.parse(SavedMoviesAdapter.moviesList.get(mPosition).getRelease_date()).getTime(),
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);
                releaseDate.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


            Double average =SavedMoviesAdapter.moviesList.get(mPosition).getVote_average();
            String finalAverage = Double.toString(average/2);

            ratingBar.setNumStars(5);
            ratingBar.setRating(Float.parseFloat(finalAverage));

            if(SavedMoviesAdapter.moviesList.get(mPosition).getOverview()!=null){
                overView.setText(SavedMoviesAdapter.moviesList.get(mPosition).getOverview());
            }else {
                overView.setText("No overview available");
            }

        }





        saveFavMovies();
        setUpFavBtnState();

    }



    public void setUpFavBtnState(){
                      id = mDB.moviesDao().loadAllByIds(movies.getId());

                      if(id == movies.getId()){
                           saveImageView.setImageResource(R.drawable.favsymbolred);
                       }
                       else {
                           saveImageView.setImageResource(R.drawable.favsymboldark);
                       }

    }



    @Override
    public void onBackPressed() {
        if(MainActivity.pref.equals("favourite")){
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        super.onBackPressed();
    }

    public void saveFavMovies(){


        saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id==movies.getId()){
                    final Movies movies1 = new Movies(movies.getId(),movies.getVote_average()
                            ,movies.getTitle(),movies.getPoster_path(),movies.getOverview(),movies.getRelease_date());
                    mDB.moviesDao().deleteMovies(movies1);

                    saveImageView.setImageResource(R.drawable.favsymboldark);

                }
                else {
                    final Movies movies1 = new Movies(movies.getId(),movies.getVote_average()
                        ,movies.getTitle(),movies.getPoster_path(),movies.getOverview(),movies.getRelease_date());
                    mDB.moviesDao().insertMovies(movies1);

                    saveImageView.setImageResource(R.drawable.favsymbolred);
                }

            }
        });
    }

    public void getTrailers() {


        if(MainActivity.pref.equals("favourite")){
            ApiClient.getmInstance().getApi().getMoviesTrailers(SavedMoviesAdapter.moviesList.get(mPosition).getId(),"5cf2ad47540f022c1822cc45594e268b")
                    .enqueue(new Callback<TrailerResponse>() {
                        @Override
                        public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                            trailerModelList= response.body().getResults();
                            trailerAdapter.addAllItems(trailerModelList);

                        }
                        @Override
                        public void onFailure(Call<TrailerResponse> call, Throwable t) {

                        }
                    });
        }
        else {

            ApiClient.getmInstance().getApi().getMoviesTrailers(movies.getId(),"5cf2ad47540f022c1822cc45594e268b")
                    .enqueue(new Callback<TrailerResponse>() {
                        @Override
                        public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                            trailerModelList= response.body().getResults();
                            trailerAdapter.addAllItems(trailerModelList);

                        }

                        @Override
                        public void onFailure(Call<TrailerResponse> call, Throwable t) {

                        }
                    });
        }



    }


    public void getReviews() {


        if(MainActivity.pref.equals("favourite")){

            ApiClient.getmInstance().getApi()
                    .getMoviesReviews(savedMovies.getId(),"5cf2ad47540f022c1822cc45594e268b")
                    .enqueue(new Callback<ReviewResponse>() {
                        @Override
                        public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                            reviewModels =  response.body().getResults();
                            reviewAdapter.addAllItems(reviewModels);

                        }

                        @Override
                        public void onFailure(Call<ReviewResponse> call, Throwable t) {

                        }
                    });
        }
        else {
            ApiClient.getmInstance().getApi()
                    .getMoviesReviews(movies.getId(),"5cf2ad47540f022c1822cc45594e268b")
                    .enqueue(new Callback<ReviewResponse>() {
                        @Override
                        public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                            reviewModels =  response.body().getResults();
                            reviewAdapter.addAllItems(reviewModels);

                        }

                        @Override
                        public void onFailure(Call<ReviewResponse> call, Throwable t) {

                        }
                    });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.home){
            if(MainActivity.pref.equals("favourite")){
                Intent intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else {
                NavUtils.navigateUpFromSameTask(this);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.youtube.com/watch?v="+trailerModelList.get(clickedItemIndex).getKey()));
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);

    }
}
