package com.demo.movieapp.ui.checkout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.OnlineCardAdapter;
import com.demo.movieapp.databinding.ActivityCheckoutBinding;
import com.demo.movieapp.dialog.AddPayCardDialog;
import com.demo.movieapp.model.OnlineCard;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckoutActivity extends AppCompatActivity {
    RecyclerView cardsRecyclerview;
    OnlineCardAdapter onlineCardAdapter;
    ActivityCheckoutBinding binding;
    AddPayCardDialog addPayCardDialog = new AddPayCardDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        hideActionBar();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);

        binding.buttonPrev.setOnClickListener(v -> finish());

        cardsRecyclerview = binding.cardsRecyclerView;
        onlineCardAdapter = new OnlineCardAdapter(
                new ArrayList<>(Arrays.asList(new OnlineCard("1234432112344321", "321", "PHAM QUANG HAO"),
                        new OnlineCard("4567765445677654", "556", "TRAN VAN VINH")
                ))
        );

        onlineCardAdapter.setClickListener(v -> {

        });

        cardsRecyclerview.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        cardsRecyclerview.setAdapter(onlineCardAdapter);

        binding.ticketAddCard.setOnClickListener((v) -> {
            addPayCardDialog.show(getSupportFragmentManager(), "add Dialog");
        });
    }

    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}