package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        String formattedDate = dateFormat.format(ticket.getTime());
        String formattedTime = timeFormat.format(ticket.getTime());
        String seats = String.join(", ", ticket.getSeats());
        String information = "Cinema " + ticket.getCinema() +", Seat "+ seats + ", " + formattedTime;

        holder.title.setText(ticket.getTitle());
        holder.date.setText(formattedDate);
        holder.information.setText(information);
        holder.moviePic.setImageResource(ticket.getImageLink());
        holder.score.setText(Double.toString(ticket.getStars()));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    class TicketHolder extends  RecyclerView.ViewHolder{
        TextView title, information, score, date;
        ImageView moviePic;

        public TicketHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ticket_title);
            information = itemView.findViewById(R.id.ticket_information);
            score = itemView.findViewById(R.id.ticket_star);
            moviePic = itemView.findViewById(R.id.ticket_avatar);
            date = itemView.findViewById(R.id.ticket_date);
        }
    }
}
