package com.example.kartik.testpaging.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartik.testpaging.R;
import com.example.kartik.testpaging.ModelClasses.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {


    private List<ReviewModel> list = new ArrayList<>();
    private Context context;

    public ReviewAdapter(List<ReviewModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.review_recyclerview_item,parent,false);
        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {

        if(list.size()>0){
            String firstName = (String) list.get(position).getAuthor().substring(0,2);
            if(!firstName.equals(firstName.toUpperCase())){
                firstName = firstName.toUpperCase();
                holder.firstName.setText(firstName);
            }
            else {
                holder.firstName.setText(list.get(position).getAuthor());
            }

            holder.fullName.setText("A Review by "+list.get(position).getAuthor());
            holder.content.setText(list.get(position).getContent());

        }
    }

    public  void addAllItems(List<ReviewModel> items) {
        list.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView firstName,fullName,content;

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.tv_review_author_name);
            fullName = itemView.findViewById(R.id.textView_review_big_label);
            content = itemView.findViewById(R.id.content_review);
        }
    }
}
