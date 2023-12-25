package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.Notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {

    ArrayList<Notification> notifications;

    public NotificationAdapter(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.NotificationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.title.setText(notification.getTitle());
        holder.cinema.setText(notification.getCinema());

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");

        String formattedDate = dateFormat.format(notification.getTime());

        holder.date.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    class NotificationHolder extends RecyclerView.ViewHolder {
        TextView title, date, cinema;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvMovieName);
            date = itemView.findViewById(R.id.tvMovieDate);
            cinema = itemView.findViewById(R.id.tvMovieCinema);
        }
    }
}
