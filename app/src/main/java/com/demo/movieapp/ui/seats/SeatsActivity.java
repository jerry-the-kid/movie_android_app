package com.demo.movieapp.ui.seats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.SeatsAdapter;
import com.demo.movieapp.model.Seat;
import com.demo.movieapp.model.SeatStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeatsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Seat> seatList;
    SeatsAdapter seatsAdapter;

    public List<Seat> generateSeats(int numRows, int seatsPerRow) {
        List<Seat> seatList = new ArrayList<>();

        for (int row = 0; row < numRows; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                char rowLabel = (char) ('A' + row);  // Convert row index to corresponding character
                String seatLabel = String.format("%c%d", rowLabel, seatNum);
                Seat seat = new Seat(seatLabel, SeatStatus.AVAILABLE);
                seatList.add(seat);
            }
        }

        return seatList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);
        hideActionBar();
        recyclerView = findViewById(R.id.recyclerView);
        seatList = generateSeats(6, 4);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        seatsAdapter = new SeatsAdapter(seatList);

        recyclerView.setAdapter(seatsAdapter);
    }

    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}