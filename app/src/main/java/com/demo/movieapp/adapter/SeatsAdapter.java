package com.demo.movieapp.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.Seat;

import org.w3c.dom.Text;

import java.util.List;

public class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.SeatsHolder> {
    List<Seat> seats;

    public SeatsAdapter(List<Seat> seats) {
        this.seats = seats;
    }

    @NonNull
    @Override
    public SeatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seat, parent,false);
        return new SeatsAdapter.SeatsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatsHolder holder, int position) {
        Seat seat = seats.get(position);
        holder.seatNumber.setText(seat.getSeatId());
        int reservedColor;
        switch (seat.getStatus()) {
            case RESERVED:
                reservedColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.white);
                holder.seatNumber.setTextColor(holder.itemView.getResources().getColor(R.color.black));
                break;
            case SELECTED:
                reservedColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.color_secondary);
                break;
            default:
                reservedColor = ContextCompat.getColor(holder.itemView.getContext(), R.color.color_gray);
                break;
        }
        ViewCompat.setBackgroundTintList(holder.seatNumber, ColorStateList.valueOf(reservedColor));
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    class SeatsHolder extends RecyclerView.ViewHolder {
        Button seatNumber;

        public SeatsHolder(@NonNull View itemView) {
            super(itemView);
            seatNumber = itemView.findViewById(R.id.seatButton);
        }
    }
}
