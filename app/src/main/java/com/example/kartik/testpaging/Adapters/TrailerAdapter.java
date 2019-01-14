package com.example.kartik.testpaging.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kartik.testpaging.UI.DetailActivity;
import com.example.kartik.testpaging.R;
import com.example.kartik.testpaging.ModelClasses.TrailerModel;
import com.example.kartik.testpaging.UI.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter  extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {


    private List<TrailerModel> list = new ArrayList<>();
    private Context context;
    private ListItemClickListener itemClickListener;


    public TrailerAdapter(List<TrailerModel> list, Context context, ListItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public TrailerAdapter(Context context, ListItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }


    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutId = R.layout.trailer_recyclerview_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId,parent,false);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {

        if(list!=null){
            Picasso.with(context)
                    .load("http://i3.ytimg.com/vi/"+DetailActivity.trailerModelList.get(position)
                            .getKey()+"/maxresdefault.jpg").into(holder.imageView);
        }


    }

    public  void addAllItems(List<TrailerModel> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.trailer_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();

            itemClickListener.onListItemClick(id);
        }
    }
}
