package com.demo.movieapp.ui.checkout;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.movieapp.R;
import com.demo.movieapp.adapter.OnlineCardAdapter;
import com.demo.movieapp.databinding.ActivityCheckoutBinding;
import com.demo.movieapp.dialog.AddPayCardDialog;
import com.demo.movieapp.dialog.WarningDialog;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.OnlineCard;
import com.demo.movieapp.model.Ticket;

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

        GlobalState globalState = GlobalState.getInstance();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);

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

        ArrayList<OnlineCard> cards = new ArrayList<>(Arrays.asList(new OnlineCard("1234432112344321", "321", "PHAM QUANG HAO", 30000.0),
                new OnlineCard("4567765445677654", "556", "TRAN VAN VINH", 500000.0)
        ));


        cardsRecyclerview = binding.cardsRecyclerView;
        onlineCardAdapter = new OnlineCardAdapter(cards);

        onlineCardAdapter.setClickListener(v -> {

        });

        cardsRecyclerview.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        cardsRecyclerview.setAdapter(onlineCardAdapter);

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
            ;

            Toast.makeText(this, card.getCardNumber(), Toast.LENGTH_SHORT).show();
        });
    }

    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}