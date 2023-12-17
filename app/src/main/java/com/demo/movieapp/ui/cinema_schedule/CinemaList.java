package com.demo.movieapp.ui.cinema_schedule;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.MovieDateItemAdapter;
import com.demo.movieapp.adapter.TimePickerAdapter;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Showtime;
import com.demo.movieapp.ui.seats.SeatsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class CinemaList extends AppCompatActivity {
    TextView movieTitle;
    RecyclerView datePickerRecycler, cinemaScheduleRecycler;
    MovieDateItemAdapter movieDateItemAdapter;
    TimePickerAdapter timepickerAdapter;
    Spinner spinnerCinema, spinnerPlace;
    Button buttonPrevious;
    String[] cinemas = {"All Cinema", "Cinema 01", "Cinema 02",
            "Cinema 03", "Cinema 04",
            "Cinema 05", "Cinema 06"};

    String[] cities = {"All Cities", "Ha Noi", "Ho Chi Minh", "Vung Tau", "Da Nang"};

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference showsTimeReference = db.collection("showsTime");

    private MutableLiveData<ArrayList<Showtime>> showtimesLiveData = new MutableLiveData<>();


    public LiveData<ArrayList<Showtime>> getShowtimesLiveData() {
        return showtimesLiveData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GlobalState globalState = GlobalState.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_list);
        hideActionBar();

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        String movieId = intent.get().getStringExtra("movieId");
        ArrayList<Showtime> showtimesLocal = new ArrayList<>();
//        String movieTitle = intent.getStringExtra("movieTitle");
        getShowTimesByMovieId(movieId);


        movieDateItemAdapter = new MovieDateItemAdapter();
        timepickerAdapter = new TimePickerAdapter(showtimesLocal, hour -> {
            intent.set(new Intent(this, SeatsActivity.class));
            globalState.setHour(hour);
            startActivity(intent.get());
        });

        movieTitle = findViewById(R.id.movie_title);

        this.getShowtimesLiveData().observe(this, showtimes -> {
            movieTitle.setText(showtimes.get(0).getMovieName());
            showtimesLocal.clear();
            showtimesLocal.addAll(showtimes);
            timepickerAdapter.notifyDataSetChanged();
        });


        cinemaScheduleRecycler = findViewById(R.id.time_picker_recycler_view);
        spinnerCinema = findViewById(R.id.spinner_cinema);
        spinnerPlace = findViewById(R.id.spinner_place);
        datePickerRecycler = findViewById(R.id.date_picker_recycler_view);
        datePickerRecycler.setAdapter(movieDateItemAdapter);

        datePickerRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        cinemaScheduleRecycler.setAdapter(timepickerAdapter);
        LinearLayoutManager cinemaScheduleLayOutManager =new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        cinemaScheduleRecycler.setLayoutManager(cinemaScheduleLayOutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(cinemaScheduleRecycler.getContext(),
                cinemaScheduleLayOutManager.getOrientation());
        cinemaScheduleRecycler.addItemDecoration(dividerItemDecoration);


        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cinemas);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter ad2
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cities);
        ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPlace.setAdapter(ad2);
        spinnerCinema.setAdapter(ad);
    }

    private void getShowTimesByMovieId(String movieId) {
        ArrayList<Showtime> showtimes = new ArrayList<>();


        // Create a query to get documents where the field "id" is equal to "01" and "status" is equal to "active"
        Query query = showsTimeReference
                .whereEqualTo("movieId", movieId)
                .whereEqualTo("day", 1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        Showtime showtime = document.toObject(Showtime.class);
                        assert showtime != null;
                        showtime.setId(document.getId());
                        showtimes.add(showtime);
                    }
                    showtimesLiveData.setValue(showtimes);
                } else {
                    // Handle the error
                    Exception exception = task.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                }
            }
        });
    }


    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}