package com.demo.movieapp.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.Seat;
import com.demo.movieapp.model.SeatStatus;

import java.util.List;

public class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.SeatsHolder> {
    List<Seat> seats;

    public SeatsAdapter(List<Seat> seats) {
        this.seats = seats;
    }

    public OnItemClickListener clickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SeatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seat, parent, false);
        return new SeatsAdapter.SeatsHolder(itemView);
    }

    public interface OnItemClickListener {
        void onItemClick(String seatId, SeatStatus seatStatus);
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

            seatNumber.setOnClickListener(v -> {

                int position = getAdapterPosition();
                Seat seat = seats.get(position);
                switch (seat.getStatus()) {
                    case RESERVED:
                        return;
                    case AVAILABLE:
                        seat.setStatus(SeatStatus.SELECTED);
                        notifyItemChanged(position);
                        break;
                    case SELECTED:
                        seat.setStatus(SeatStatus.AVAILABLE);
                        notifyItemChanged(position);
                        break;
                }


                if (clickListener != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(seat.getSeatId(), seat.getStatus());
                    }
                }
            });
        }
    }
}
