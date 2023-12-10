package com.demo.movieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.demo.movieapp.R;
import com.demo.movieapp.adapter.CategoriesBtnAdapter;
import com.demo.movieapp.adapter.MovieCardAdapter;
import com.demo.movieapp.databinding.FragmentHomeBinding;
import com.demo.movieapp.model.CategoryButton;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Movie;
import com.demo.movieapp.ui.movie_detail.MovieDetailActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {
    GlobalState globalState = GlobalState.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("movies");
    private FragmentHomeBinding binding;
    ArrayList<Movie> movies;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Glide.with(binding.avatar)
                .load(R.drawable.avatar)
                .transform(new CenterCrop(), new RoundedCorners(200))
                .into(binding.avatar);


        // Category
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        CategoriesBtnAdapter categoriesBtnAdapter = new CategoriesBtnAdapter(new ArrayList<>(
                Arrays.asList(new CategoryButton("Horror", false),
                        new CategoryButton("Romance", false),
                        new CategoryButton("Comedy", false),
                        new CategoryButton("Crime", false))

        ));
        binding.categoryRecyclerView.setAdapter(categoriesBtnAdapter);

        movies = new ArrayList<>();

        binding.cardFilm1RecycledView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        MovieCardAdapter movieCardAdapter = new MovieCardAdapter(movies);
        movieCardAdapter.setClickListener((movie, position) -> {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra("id", movie.getId());
            startActivity(intent);
        });
        binding.cardFilm1RecycledView.setAdapter(movieCardAdapter);


//        Movie movie01 = new Movie();
//        movie01.setTitle("Daryl Dixon");
//        movie01.setSummary("Daryl's journey across a broken but resilient France as he hopes to find a way back home.");
//        movie01.setDirector("David Zabel");
//        movie01.setImageUrl("https://m.media-amazon.com/images/M/MV5BMTllZDU4ZjgtYzEyOC00OTVkLWE3MDctMzk0NTM2MjUzNDkwXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_.jpg");
//        movie01.setReleaseYear(2023);
//        movie01.setYoutubeID("iTOaFootkSk");
//        movie01.setTomatometer(77);
//        movie01.setAudienceScore(70);


        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Movie movieTemp = document.toObject(Movie.class);
                    movieTemp.setId(document.getId());
                    movies.add(movieTemp);
                }

                movieCardAdapter.notifyDataSetChanged();
                globalState.setMovieList(movies);

            } else {
                // Handle errors
                Exception exception = task.getException();
                if (exception != null) {
                    // Handle the exception
                    Log.v("test", "Failed");
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    @Override
//    public void onClick(View v, int pos) {
//        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
//        intent.putExtra("id", movies.get(pos).getId());
//        startActivity(intent);
//    }
}