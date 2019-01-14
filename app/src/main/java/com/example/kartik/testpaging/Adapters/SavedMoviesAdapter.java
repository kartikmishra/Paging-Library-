package com.example.kartik.testpaging.Adapters;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kartik.testpaging.ModelClasses.Movies;
import com.example.kartik.testpaging.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SavedMoviesAdapter extends PagedListAdapter<Movies,SavedMoviesAdapter.NewAdapterViewHolder> {


    private Context context;
    private ListItemClickListener itemClickListener;

    public static List<Movies> moviesList = new ArrayList<>();

    public SavedMoviesAdapter(Context context, ListItemClickListener itemClickListener){
        super(DIFF_CALLBACK);
        this.context = context;
        this.itemClickListener = itemClickListener;
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

    public interface ListItemClickListener{
        void onListItemClicki(int clickedItemIndex);
    }
    @NonNull
    @Override
    public NewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
        return new NewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAdapterViewHolder holder, int position) {

        Movies movies = getItem(position);
        if(movies!=null){
            moviesList.add(movies);
            Set<Movies> moviesSet = new LinkedHashSet<>(moviesList);
            moviesList.clear();
            moviesList.addAll(moviesSet);

            Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+movies.getPoster_path()).into(holder.imageView);
            holder.textView.setText(movies.getTitle());
        }

    }

    class NewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        public NewAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movies_iv);
            textView = itemView.findViewById(R.id.movie_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            itemClickListener.onListItemClicki(id);
        }
    }

    @Override
    public void onCurrentListChanged(@Nullable PagedList<Movies> currentList) {
        moviesList = new ArrayList<>();
        super.onCurrentListChanged(currentList);
    }
}
