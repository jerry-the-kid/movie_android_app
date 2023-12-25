package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.movieapp.R;
import com.demo.movieapp.model.Ticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketHolder> {
    ArrayList<Ticket> tickets;

    public TicketAdapter(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket, parent,false);
        return new TicketAdapter.TicketHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        Ticket ticket = tickets.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");

        String formattedDate = dateFormat.format(ticket.getDate());

        holder.title.setText(ticket.getTitle());

        Glide.with(holder.moviePic)
                .load(ticket.getImageUrl())
                .into(holder.moviePic);
        String result = ticket.getReservedSeats()
                .stream()
                .reduce("", (partialString, element) -> partialString + element + ", ");

        holder.information.setText("Cinema " + ticket.getRoomId() + ", Seats "
                + result.substring(0, result.length() - 2) + ", " + ticket.getTime());
        holder.cinema.setText(ticket.getCinema());
        holder.date.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    class TicketHolder extends RecyclerView.ViewHolder {
        TextView title, information, date, cinema;
        ImageView moviePic;

        public TicketHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ticket_title);
            information = itemView.findViewById(R.id.ticket_information);
            cinema = itemView.findViewById(R.id.ticket_title);
            moviePic = itemView.findViewById(R.id.ticket_avatar);
            date = itemView.findViewById(R.id.ticket_date);
        }
    }
}
