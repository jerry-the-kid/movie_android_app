package com.demo.movieapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.demo.movieapp.databinding.ActivityMainBinding;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ActivityMainBinding binding;
    NavController navController;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("user");
    private CollectionReference ticketReference = db.collection("ticket");
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private String documentUserId = "";

    private MutableLiveData<ArrayList<Ticket>> tickets = new MutableLiveData<>();


    public LiveData<ArrayList<Ticket>> getTicketsLiveData() {
        return tickets;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hideActionBar();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_ticket, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        firebaseAuth = FirebaseAuth.getInstance();

//        firebaseAuth.signOut();

        Intent intent = getIntent();
        boolean navigateToTicket = intent.getBooleanExtra("navigateToTicket", false);
        if (navigateToTicket) {
            navController.navigate(R.id.navigation_ticket);
        }

        GlobalState globalState = GlobalState.getInstance();

        authStateListener = firebaseAuth -> {
            user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Query query = usersCollection.whereEqualTo("id", user.getUid());

                query.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Query q = ticketReference.whereEqualTo("userId", document.getId());

                            q.get().addOnCompleteListener(t -> {
                                if (t.isSuccessful()) {
                                    if (t.getResult().isEmpty()) {
                                        globalState.usersTicket.setValue(new ArrayList<>());
                                        return;
                                    }
                                    ArrayList<Ticket> tickets_temp = new ArrayList<>();
                                    for (QueryDocumentSnapshot d : t.getResult()) {
                                        Ticket ticket = d.toObject(Ticket.class);
                                        tickets_temp.add(ticket);
                                    }
                                    globalState.usersTicket.setValue(tickets_temp);
                                }
                            });

                        }
                    }
                });
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }


    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}