package com.demo.movieapp.ui.cinema_schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.MovieDateItemAdapter;
import com.demo.movieapp.adapter.TimePickerAdapter;
import com.demo.movieapp.model.CinemaFilmSchedule;

import java.util.ArrayList;
import java.util.Arrays;

public class CinemaList extends AppCompatActivity {
    RecyclerView datePickerRecycler, cinemaScheduleRecycler;
    MovieDateItemAdapter movieDateItemAdapter;
    TimePickerAdapter timepickerAdapter;
    Spinner spinnerCinema, spinnerPlace;
    String[] cinemas = { "All Cinema", "Cinema 01", "Cinema 02",
            "Cinema 03", "Cinema 04",
            "Cinema 05", "Cinema 06" };

    String[] cities = {"All Cities","Ha Noi", "Ho Chi Minh", "Vung Tau", "Da Nang"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_list);
        hideActionBar();

        movieDateItemAdapter = new MovieDateItemAdapter();
        timepickerAdapter = new TimePickerAdapter(
                Arrays.asList(
                        new CinemaFilmSchedule("Movie 1", "Cinema 01",   Arrays.asList("16:30", "19:00", "20:00", "21:35", "22:15")),
                        new CinemaFilmSchedule("Movie 1", "Cinema 02",   Arrays.asList("16:30", "19:00", "20:00", "21:35", "22:15")),
                        new CinemaFilmSchedule("Movie 1", "Cinema 03",   Arrays.asList("16:30", "19:00", "20:00", "21:35", "22:15")),
                        new CinemaFilmSchedule("Movie 1", "Cinema 04",   Arrays.asList("16:30", "19:00", "20:00", "21:35", "22:15"))
                )
        );

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


    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}