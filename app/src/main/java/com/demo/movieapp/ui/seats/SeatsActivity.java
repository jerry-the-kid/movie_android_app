package com.demo.movieapp.ui.seats;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.SeatsAdapter;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Hour;
import com.demo.movieapp.model.Movie;
import com.demo.movieapp.model.Seat;
import com.demo.movieapp.model.SeatStatus;
import com.demo.movieapp.model.Showtime;
import com.demo.movieapp.model.Ticket;
import com.demo.movieapp.ui.checkout.CheckoutActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SeatsActivity extends AppCompatActivity {
    Button buttonPrev, buttonSubmit;
    TextView seats, seatsPrice, seatRoom;
    RecyclerView recyclerView;
    List<Seat> seatList;
    SeatsAdapter seatsAdapter;
    ArrayList<String> seatsNumber;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference showsTimeReference = db.collection("ticket");

    private MutableLiveData<Showtime> showtime = new MutableLiveData<>();


    public LiveData<Showtime> getShowtimeLiveData() {
        return showtime;
    }


    public List<Seat> generateSeats(int numRows, int seatsPerRow, ArrayList<String> reservedSeat) {
        List<Seat> seatList = new ArrayList<>();

        for (int row = 0; row < numRows; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                char rowLabel = (char) ('A' + row);  // Convert row index to corresponding character
                String seatLabel = String.format("%c%d", rowLabel, seatNum);
                SeatStatus seatStatus = reservedSeat.contains(seatLabel) ? SeatStatus.RESERVED : SeatStatus.AVAILABLE;
                Seat seat = new Seat(seatLabel, seatStatus);
                seatList.add(seat);
            }
        }

        return seatList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        seatsNumber = new ArrayList<>();
        GlobalState globalState = GlobalState.getInstance();
        Hour currentHour = globalState.getHour();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);
        hideActionBar();

        buttonSubmit = findViewById(R.id.button3);
        seats = findViewById(R.id.seats);
        seatRoom = findViewById(R.id.seats_room);
        buttonPrev = findViewById(R.id.buttonPrev);

        buttonPrev.setOnClickListener(v -> {
            globalState.setHour(null);
            finish();
        });

        buttonSubmit.setOnClickListener(v -> {
            Movie currentMovie = globalState.getCurrentMovie();

            if (seatsNumber.isEmpty()) return;

            Ticket ticket = new Ticket(currentMovie.getTitle(), currentHour.getStartTime(),
                    currentMovie.getImageUrl(), seatsNumber, currentHour.getCinemaName(), currentHour.getRoomId(), new Date());

            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
//            createNewTicket(ticket);
        });


        recyclerView = findViewById(R.id.recyclerView);
        seatsPrice = findViewById(R.id.seats_price);
        seatList = generateSeats(6, 4, currentHour.getReservedSeats());
        seatRoom.setText("Room " + globalState.getHour().getRoomId());

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        seatsAdapter = new SeatsAdapter(seatList);
        seatsAdapter.setClickListener((seatNumber, seatStatus) -> {

            switch (seatStatus) {
                case AVAILABLE:
                    seatsNumber.removeIf(s -> s.equals(seatNumber));
                    break;
                case SELECTED:
                    seatsNumber.add(seatNumber);
                    break;
            }

            String result = seatsNumber
                    .stream()
                    .reduce("", (partialString, element) -> partialString + element + ", ");

            seats.setText(result.length() != 0 ? result.substring(0, result.length() - 2) : "");

            // Calculate total price
            int totalPrice = seatsNumber.size() * 50000;

            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            String formattedPrice = numberFormat.format(totalPrice);
            seatsPrice.setText("Total: " + formattedPrice + " VND");
        });

        recyclerView.setAdapter(seatsAdapter);
    }

    private void createNewTicket(Ticket ticket) {
        showsTimeReference.add(ticket).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SeatsActivity.this, "Ticket create successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Exception exception = task.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                }
            }
        });

    }

//    public void getShowTimeByHour(String showTimeId, Hour hour) {
//        showsTimeReference.document(showTimeId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    Showtime showtimeFromFirebase = task.getResult().toObject(Showtime.class);
//                    showtime.setValue(showtimeFromFirebase);
//                } else {
//
//                    Exception exception = task.getException();
//                    if (exception != null) {
//                        exception.printStackTrace();
//                    }
//                }
//            }
//        });
//    }

    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}