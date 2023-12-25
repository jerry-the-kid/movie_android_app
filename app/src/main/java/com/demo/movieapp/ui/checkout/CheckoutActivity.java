package com.demo.movieapp.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.movieapp.MainActivity;
import com.demo.movieapp.R;
import com.demo.movieapp.adapter.OnlineCardAdapter;
import com.demo.movieapp.databinding.ActivityCheckoutBinding;
import com.demo.movieapp.dialog.AddPayCardDialog;
import com.demo.movieapp.dialog.WarningDialog;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.OnlineCard;
import com.demo.movieapp.model.Room;
import com.demo.movieapp.model.Showtime;
import com.demo.movieapp.model.Ticket;
import com.demo.movieapp.model.User;
import com.demo.movieapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    RecyclerView cardsRecyclerview;
    OnlineCardAdapter onlineCardAdapter;
    ActivityCheckoutBinding binding;
    AddPayCardDialog addPayCardDialog = new AddPayCardDialog();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("user");
    CollectionReference cardsCollection = db.collection("card");
    private CollectionReference showsTimeReference = db.collection("showsTime");
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private String documentUserId = "";
    private CollectionReference ticketReference = db.collection("ticket");
    private MutableLiveData<User> userData = new MutableLiveData<>();


    public LiveData<User> getUserLiveData() {
        return userData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        hideActionBar();

        GlobalState globalState = GlobalState.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);


        authStateListener = firebaseAuth -> {
            user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Query query = usersCollection.whereEqualTo("id", user.getUid());

                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                        Toast.makeText(this, "" + task.getResult().size(), Toast.LENGTH_SHORT).show();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            documentUserId = document.getId();
                            User userFromDb = document.toObject(User.class);
                            userData.setValue(userFromDb);
                        }
                    } else {
                        // Handle errors
                        Toast.makeText(this, "Error getting user document: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        };

        Ticket ticket = globalState.getCurrentTicket();

        binding.ticketTitle.setText(ticket.getTitle());

        Glide.with(binding.ticketAvatar)
                .load(ticket.getImageUrl())
                .into(binding.ticketAvatar);
        String result = ticket.getReservedSeats()
                .stream()
                .reduce("", (partialString, element) -> partialString + element + ", ");

        binding.ticketCinameName.setText(globalState.getCurrentTicket().getCinema());
        binding.ticketInformation.setText("Cinema " + ticket.getRoomId() + ", Seats "
                + result.substring(0, result.length() - 2) + ", " + ticket.getTime());

        binding.ticketTotalPrice.setText(ticket.getTotalPriceString() + " VND");


        binding.buttonPrev.setOnClickListener(v -> finish());
        binding.btnModify.setOnClickListener(v -> finish());

        ArrayList<OnlineCard> cards = new ArrayList<>();


        cardsRecyclerview = binding.cardsRecyclerView;
        onlineCardAdapter = new OnlineCardAdapter(cards);

        onlineCardAdapter.setClickListener(v -> {

        });


        this.getUserLiveData().observe(this, user -> {
            binding.ticketUserEmail.setText(user.getEmail());
            binding.ticketUsername.setText(user.getName());
            binding.ticketPhoneNumber.setText(user.getPhone());

            cards.clear();
            cards.addAll(user.getOnlineCards());
            onlineCardAdapter.notifyDataSetChanged();
        });

        cardsRecyclerview.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        cardsRecyclerview.setAdapter(onlineCardAdapter);

        addPayCardDialog.setConfirmListener(card -> {
//            Toast.makeText(this, card.getCardNumber(), Toast.LENGTH_SHORT).show();
            Query query = cardsCollection.whereEqualTo("cardNumber", card.getCardNumber())
                    .whereEqualTo("cvv", card.getCvv())
                    .whereEqualTo("userName", card.getUserName());

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
//                    Toast.makeText(this, ""+task.getResult().size(), Toast.LENGTH_SHORT).show();
                    if (task.getResult().isEmpty()) {
                        Toast.makeText(this, "Card is invalid or not found", Toast.LENGTH_SHORT).show();
                        WarningDialog.showAddDialog(this, "Invalid Card",
                                "Card is invalid or not found", (dialog, which) -> {
                                    dialog.dismiss();
                                });
                        return;
                    }

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        OnlineCard cardFromDb = document.toObject(OnlineCard.class);

                        User user = getUserLiveData().getValue();
                        ArrayList<OnlineCard> cards_temp = user.getOnlineCards();
                        cards_temp.add(cardFromDb);
                        user.setOnlineCards(cards_temp);
                        userData.setValue(user);
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("onlineCards", user.getOnlineCards());
                        usersCollection.document(documentUserId).update(updates);

                        Toast.makeText(this, "Add Card Successfully !!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle errors
                    Toast.makeText(this, "Error getting user card: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });

        });
        binding.ticketAddCard.setOnClickListener((v) -> {
            addPayCardDialog.show(getSupportFragmentManager(), "add Dialog");
        });

        binding.payButton.setOnClickListener(v -> {
            OnlineCard card = cards.stream().filter(OnlineCard::isSelected).findFirst().orElse(null);
            if (card == null) {
                WarningDialog.showAddDialog(this, "Card is Required",
                        "Please choose a card or add new card", (dialog, which) -> {
                            dialog.dismiss();
                        });
                return;
            }

            if (!card.isCardValid((double) globalState.getCurrentTicket().getTotalPrice())) {
                WarningDialog.showAddDialog(this, "Insufficient Balance",
                        "Your card does not have sufficient funds to complete this transaction.", (dialog, which) -> {
                            dialog.dismiss();
                        });
                return;
            }
            ticket.setUserId(userData.getValue().getId());
            Query query = showsTimeReference.whereEqualTo("cinemaName", ticket.getCinema())
                    .whereEqualTo("movieName", ticket.getTitle());

            createNewTicket(ticket);

            query.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Showtime showtime = document.toObject(Showtime.class);
                        ArrayList<Room> rooms = showtime.getRooms();
                        ArrayList<Room> updatedRooms = new ArrayList<>();
                        for (Room room : rooms) {
                            if (room.getRoomId() == ticket.getRoomId()) {
                                room.getReservedSeats().addAll(ticket.getReservedSeats());
                            }
                            updatedRooms.add(room);
                        }

                        Map<String, Object> updates = new HashMap<>();
                        updates.put("rooms", updatedRooms);

                        showsTimeReference.document(document.getId()).update(updates);
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("navigateToTicket", true);
                        startActivity(intent);
                    }
                } else {
                    // Handle errors
                    Toast.makeText(this, "Error getting showstime: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }


    private void createNewTicket(Ticket ticket) {
        ticketReference.add(ticket).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CheckoutActivity.this, "Ticket create successfully", Toast.LENGTH_SHORT).show();
                } else {
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

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}