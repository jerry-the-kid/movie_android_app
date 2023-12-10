package com.demo.movieapp.ui.movie_detail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.CategoriesBtnAdapter;
import com.demo.movieapp.databinding.ActivityMovieDetailBinding;
import com.demo.movieapp.model.CategoryButton;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Movie;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.ArrayList;
import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity {
    GlobalState globalState = GlobalState.getInstance();
    ActivityMovieDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        hideActionBar();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        binding.buttonPrev.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        int position = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("position")));

        Movie movie = globalState.getMovieList().get(position);
        binding.setMovie(movie);
        binding.filmCardActors.setText(String.join(", ", movie.getActors()));
        binding.textView8.setText(Double.toString(movie.getStarPoint()));
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = movie.getYoutubeID();
                youTubePlayer.cueVideo(videoId, 0);

            }
        });


        binding.detailRecycledView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoryButton> categoriesButton = new ArrayList<>();

        for (String category : movie.getCategories()) {
            categoriesButton.add(new CategoryButton(category, false));
        }

        CategoriesBtnAdapter categoriesBtnAdapter = new CategoriesBtnAdapter(categoriesButton);
        binding.detailRecycledView.setAdapter(categoriesBtnAdapter);
    }


    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}