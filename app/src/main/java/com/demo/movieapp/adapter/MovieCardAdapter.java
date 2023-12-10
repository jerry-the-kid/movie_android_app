package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.demo.movieapp.R;
import com.demo.movieapp.model.Movie;

import java.util.List;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.MovieCardHolder> {
    public OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(Movie movie, int position);
    }

    public void setClickListener(OnItemClickListener myListener){
        this.clickListener = myListener;
    }

    List<Movie> movies;

    public MovieCardAdapter(List<Movie> movies) {
        this.movies = movies;
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
        Movie movie = movies.get(position);
        Glide.with(holder.imageView)
                .load(movie.getImageUrl())
                .into(holder.imageView);

        holder.title.setText(movie.getTitle());
        holder.rottenScore.setText(Integer.toString(movie.getTomatometer()));
        holder.audienceScore.setText(Integer.toString(movie.getAudienceScore()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieCardHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, rottenScore, audienceScore;

        public MovieCardHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.film_card_image);
            title = itemView.findViewById(R.id.film_card_title);
            rottenScore = itemView.findViewById(R.id.film_card_rotten_score);
            audienceScore = itemView.findViewById(R.id.film_card_audience_score);

            itemView.setOnClickListener(view -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Movie clickedMovie = movies.get(position);
                        clickListener.onItemClick(clickedMovie, position);
                    }
                }
            });
        }
    }
}
