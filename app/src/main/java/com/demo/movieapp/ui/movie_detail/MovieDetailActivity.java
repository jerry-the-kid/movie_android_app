package com.demo.movieapp.ui.movie_detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Transition;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.demo.movieapp.R;
import com.demo.movieapp.adapter.CategoriesBtnAdapter;
import com.demo.movieapp.databinding.ActivityMovieDetailBinding;
import com.demo.movieapp.model.CategoryButton;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Movie;
import com.demo.movieapp.ui.cinema_schedule.CinemaList;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class MovieDetailActivity extends AppCompatActivity {
    GlobalState globalState = GlobalState.getInstance();
    ActivityMovieDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        hideActionBar();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);

        binding.buttonPrev.setOnClickListener(v -> {
            globalState.setCurrentMovie(null);
            finish();
        });

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        int position = Integer.parseInt(Objects.requireNonNull(intent.get().getStringExtra("position")));

        Movie movie = globalState.getMovieList().get(position);
        globalState.setCurrentMovie(movie);

        binding.fixedButton.setOnClickListener(v -> {
            intent.set(new Intent(this, CinemaList.class));
            intent.get().putExtra("movieId", movie.getId());
            intent.get().putExtra("movieTitle", movie.getTitle());
            startActivity(intent.get());
        });


        binding.setMovie(movie);
        binding.filmCardActors.setText(String.join(", ", movie.getActors()));
//        binding.textView8.setText(Double.toString(movie.getStarPoint()));
        Glide.with(this).load(movie.getImageUrl()).into(binding.ivPoster);

        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = movie.getYoutubeID();
                youTubePlayer.cueVideo(videoId, 0);

            }
        });


        binding.detailRecycledView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<CategoryButton> categoriesButton = new ArrayList<>();

        for (String category : movie.getCategories()) {
            categoriesButton.add(new CategoryButton(category, false));
        }

        CategoriesBtnAdapter categoriesBtnAdapter = new CategoriesBtnAdapter(categoriesButton);
        binding.detailRecycledView.setAdapter(categoriesBtnAdapter);

        binding.btnShare.setOnClickListener(v -> {
            shareToMedia(movie);
        });
    }


    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void shareToMedia(Movie movie) {
        String description = movie.getSummary();
        String movieName = movie.getTitle();

        String invitedMessage = "Name: " + movieName + "\n" + "Summary: " + description;


        binding.ivPoster.setDrawingCacheEnabled(true);
        binding.ivPoster.buildDrawingCache();
        Bitmap bitmap = binding.ivPoster.getDrawingCache();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        File file = new File(getExternalCacheDir() + "/image.jpg");
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, invitedMessage);

        startActivity(Intent.createChooser(intent, "Chia sẻ qua"));
    }
}