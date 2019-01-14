package com.example.kartik.testpaging.Adapters;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kartik.testpaging.ModelClasses.Movies;
import com.example.kartik.testpaging.R;
import com.example.kartik.testpaging.UI.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Random;
import java.util.Set;


public class MoviesAdapter extends PagedListAdapter<Movies,MoviesAdapter.MoviesViewHolder> {

    private static final String TAG = "MoviesAdapter";

    private Context context;
    private ImageView imageView;
    public static TextView textView;
    private ListItemClickListener itemClickListener;
    public static Movies movies;
    public static List<String> poster_path = new ArrayList<>();
    public static List<Movies> list = new ArrayList<>();
    public static List<Long> ids = new ArrayList<>();

    public MoviesAdapter(Context context,ListItemClickListener listItemClickListener){
        super(DIFF_CALLBACK);
        this.context = context;
        this.itemClickListener = listItemClickListener;

    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        movies = getItem(position);
        if(movies!=null && !MainActivity.pref.equals("favourite")){


           list.add(movies);
           Set<Movies> moviesSet = new LinkedHashSet<>(list);
           list.clear();
           list.addAll(moviesSet);


            Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+movies.getPoster_path()).into(imageView);
            textView.setText(movies.getTitle(),TextView.BufferType.SPANNABLE);
            int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            holder.relativeLayout.setBackgroundColor(randomAndroidColor);
            holder.relativeLayout.animate();

        }
        else if(MainActivity.pref.equals("favourite")){
            Log.d(TAG, "onBindViewHolder: "+MainActivity.movies.get(position).getPoster_path());
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w342"+MainActivity.movies.get(position)
                            .getPoster_path()).into(imageView);
        }


        holder.setIsRecyclable(false);

    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    private static DiffUtil.ItemCallback<Movies> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movies>() {
        @Override
        public boolean areItemsTheSame(Movies oldItem, Movies newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Movies oldItem, Movies newItem) {
            return oldItem.equals(newItem);
        }
    };

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RelativeLayout relativeLayout;
        public MoviesViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.movies_iv);
            textView = itemView.findViewById(R.id.movie_name);
            relativeLayout = itemView.findViewById(R.id.relLayout_text_main);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            itemClickListener.onListItemClick(position);

        }
    }

    @Override
    public void onCurrentListChanged(@Nullable PagedList<Movies> currentList) {
        list = new ArrayList<>();
        super.onCurrentListChanged(currentList);
    }
}
