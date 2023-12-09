package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;

import java.util.List;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.MovieCardHolder> {
    List<Integer> images;

    public MovieCardAdapter(List<Integer> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public MovieCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_card_item, parent,false);
        return new MovieCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCardHolder holder, int position) {
        holder.imageView.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class MovieCardHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MovieCardHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.film_card_1);
        }
    }
}
