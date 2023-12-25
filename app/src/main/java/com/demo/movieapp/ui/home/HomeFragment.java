package com.demo.movieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.demo.movieapp.MainActivity;
import com.demo.movieapp.R;
import com.demo.movieapp.adapter.CategoriesBtnAdapter;
import com.demo.movieapp.adapter.MovieCardAdapter;
import com.demo.movieapp.databinding.FragmentHomeBinding;
import com.demo.movieapp.model.CategoryButton;
import com.demo.movieapp.model.Cinema;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Movie;
import com.demo.movieapp.model.Room;
import com.demo.movieapp.model.Showtime;
import com.demo.movieapp.ui.change_information.ChangeInformationActivity;
import com.demo.movieapp.ui.login.LoginActivity;
import com.demo.movieapp.ui.movie_detail.MovieDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    GlobalState globalState = GlobalState.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("movies");
    private CollectionReference cinemaReference = db.collection("cinema");
    private CollectionReference showsTimeReference = db.collection("showsTime");
    private FragmentHomeBinding binding;
    ArrayList<Movie> movies;

    public static boolean isSearchTextIncluded(String searchString, String searchText) {
        // Convert both the search string and search text to lowercase for case-insensitive comparison
        searchString = searchString.toLowerCase();
        searchText = searchText.toLowerCase();

        return searchString.contains(searchText);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        movies.clear();
        binding = null;
    }

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
        movieCardAdapter.setClickListener((position) -> {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra("position", String.valueOf(position));
            startActivity(intent);
        });
        binding.cardFilm1RecycledView.setAdapter(movieCardAdapter);


        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                movies.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Movie movieTemp = document.toObject(Movie.class);
                    assert movieTemp != null;
                    movieTemp.setId(document.getId());
                    movies.add(movieTemp);
                }

                movieCardAdapter.notifyDataSetChanged();
                globalState.setMovieList((List<Movie>) movies.clone());

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


        binding.searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().trim();
                movies.clear();
                if (searchText.isEmpty()) {
                    movies.addAll(globalState.getMovieList());
                } else {
                    movies.addAll(globalState.getMovieList().stream().filter(movie -> isSearchTextIncluded(movie.getSearchString(), searchText))
                            .collect(Collectors.toList()));
                }
                movieCardAdapter.notifyDataSetChanged();
            }
        });

        binding.avatar.setOnClickListener(view -> {
            if(mAuth.getCurrentUser() != null)
            {
                Intent intent = new Intent(requireActivity(), ChangeInformationActivity.class);
                intent.putExtra("UUID", mAuth.getCurrentUser().getUid());
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

//        createCinemaAndShowsTime();

        return root;
    }

    public void createCinemaAndShowsTime() {
        Cinema cinema = new Cinema();
        cinema.setName("Cinema Lapen Center Vũng Tàu");
        cinema.setCity("Vũng Tàu");

        cinemaReference.add(cinema).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentReference document = task.getResult();
                Showtime showtime = new Showtime();
                showtime.setCinemaName(cinema.getName());
                showtime.setCinemaId(document.getId());
                showtime.setMovieId("N8zNjvWyZDzCvXfEoPGt");
                ArrayList<Room> rooms = new ArrayList<>(Arrays.asList(
                        new Room(2, "9:00", "11:04", new ArrayList<>()),
                        new Room(1, "12:00", "14:04", new ArrayList<>()),
                        new Room(2, "14:30", "16:34", new ArrayList<>()),
                        new Room(3, "16:45", "18:49", new ArrayList<>())
                ));
                showtime.setRooms(rooms);
                showtime.setDay(1);
                showsTimeReference.add(showtime);
            }
        });
    }

    public void createMovie() {
        Movie movie01 = new Movie();
        movie01.setTitle("Daryl Dixon");
        movie01.setSummary("Daryl's journey across a broken but resilient France as he hopes to find a way back home.");
        movie01.setDirector("David Zabel");
        movie01.setImageUrl("https://m.media-amazon.com/images/M/MV5BMTllZDU4ZjgtYzEyOC00OTVkLWE3MDctMzk0NTM2MjUzNDkwXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_.jpg");
        movie01.setReleaseYear(2023);
        movie01.setYoutubeID("iTOaFootkSk");
        movie01.setTomatometer(77);
        movie01.setAudienceScore(70);

        collectionReference.add(movie01);
    }
}