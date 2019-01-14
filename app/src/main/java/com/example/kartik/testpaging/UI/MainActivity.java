package com.example.kartik.testpaging.UI;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kartik.testpaging.Adapters.MoviesAdapter;
import com.example.kartik.testpaging.DataSource.MoviesViewModel;
import com.example.kartik.testpaging.Database.AppDatabase;
import com.example.kartik.testpaging.ModelClasses.Movies;
import com.example.kartik.testpaging.Adapters.SavedMoviesAdapter;
import com.example.kartik.testpaging.R;
import com.example.kartik.testpaging.SavedDataBaseDataSource.SavedMoviesViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener
        , SharedPreferences.OnSharedPreferenceChangeListener, SavedMoviesAdapter.ListItemClickListener {


    private static final String TAG = "MainActivity";


    private MoviesViewModel viewModel;

    private MoviesAdapter adapter;
    private SavedMoviesAdapter moviesAdapter;
    public static List<Movies> movies = new ArrayList<>();

    public static String pref;
    public static AppDatabase mDB;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         recyclerView = findViewById(R.id.rv_movies);


        mDB = AppDatabase.getsInstance(getApplicationContext());

        int noOfCoilumns = Utility.calculateNoOfColumns(this);
        StaggeredGridLayoutManager layoutManager1 = new StaggeredGridLayoutManager(noOfCoilumns,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);

        setUpSharedPreferences();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            if(pref.equals("popular")){
                actionBar.setTitle("  Popular Movies");
            }
            if(pref.equals("top_rated")){
                actionBar.setTitle("Top Rated Movies");
            }
            if(pref.equals("favourite")){
                actionBar.setTitle("Favourite Movies");
            }

        }
        assert actionBar != null;


         viewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);


         if(!pref.equals("favourite")){

             adapter = new MoviesAdapter(this,this);
             viewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Movies>>() {
                 @Override
                 public void onChanged(@Nullable PagedList<Movies> movies) {
                     adapter.submitList(movies);

                     adapter.notifyItemRangeChanged(0,adapter.getItemCount());
                     adapter.notifyDataSetChanged();


                 }
             });

             recyclerView.setAdapter(adapter);
         }

         else if(pref.equals("favourite")){

             SavedMoviesViewModel savedMoviesViewModel = ViewModelProviders.of(this).get(SavedMoviesViewModel.class);
             moviesAdapter = new SavedMoviesAdapter(this,this);
             savedMoviesViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Movies>>() {
                 @Override
                 public void onChanged(@Nullable PagedList<Movies> movies) {
                     moviesAdapter.submitList(movies);
                 }
             });

             recyclerView.setAdapter(moviesAdapter);
             moviesAdapter.notifyDataSetChanged();
         }



    }



    public void setUpSharedPreferences(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);


        pref = preferences.getString("movie_pref","popular");

        preferences.registerOnSharedPreferenceChangeListener(this);
        Log.d(TAG, "setUpSharedPreferences: pref_value : "+pref);
    }



    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("moviePosition",clickedItemIndex);
        //Log.d(TAG, "onListItemClick: movie_position"+clickedItemIndex);
        startActivity(intent);

    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String s) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onListItemClicki(int clickedItemIndex) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra("movie_position",clickedItemIndex);
        //Log.d(TAG, "onListItemClick: movie_position"+clickedItemIndex);
        startActivity(intent);
    }

    public static class Utility {
        public static int calculateNoOfColumns(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (dpWidth / 180);
            return noOfColumns;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        if(id==R.id.action_search){
            if(pref.equals("favourite") && SavedMoviesAdapter.moviesList.isEmpty()){
                Snackbar snackbar = Snackbar.make(findViewById(R.id.mCoordinatorLayout)
                        ,"You have no favourite movies",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else {
                doSearch();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void doSearch() {

        if(pref.equals("favourite") && SavedMoviesAdapter.moviesList.size()>0){

            new SimpleSearchDialogCompat(MainActivity.this, "Search....",
                    "Search a Movie here", null, (ArrayList)
                    SavedMoviesAdapter.moviesList, new SearchResultListener<Movies>() {

                @Override
                public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Movies movies, int i) {
                    int p = MoviesAdapter.list.indexOf(movies);
                    recyclerView.scrollToPosition(p);
                    baseSearchDialogCompat.dismiss();
                }
            }).show();
            recyclerView.smoothScrollToPosition(moviesAdapter.getItemCount());
        }
        else {
            new SimpleSearchDialogCompat(MainActivity.this, "Search....",
                    "Search a Movie here", null, (ArrayList)
                    MoviesAdapter.list, new SearchResultListener<Movies>() {

                @Override
                public void onSelected(BaseSearchDialogCompat baseSearchDialogCompat, Movies movies, int i) {
                    int p = MoviesAdapter.list.indexOf(movies);
                    recyclerView.scrollToPosition(p);
                    baseSearchDialogCompat.dismiss();
                }
            }).show();

            recyclerView.smoothScrollToPosition(adapter.getItemCount());
        }


    }
}
