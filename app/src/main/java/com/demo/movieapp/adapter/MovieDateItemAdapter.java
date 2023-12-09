package com.demo.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.MovieTime;

import java.util.ArrayList;
import java.util.List;

public class MovieDateItemAdapter extends RecyclerView.Adapter<MovieDateItemAdapter.MovieDateItemHolder> {

    List<MovieTime> list = MovieTime.getScheduleFromToday();

    @NonNull
    @Override
    public MovieDateItemAdapter.MovieDateItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_item, parent,false);
        return new MovieDateItemAdapter.MovieDateItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDateItemAdapter.MovieDateItemHolder holder, int position) {
         MovieTime movieTime = list.get(position);
         holder.dayOfWeek.setText(movieTime.getWeekDay());
         holder.dayOfMonth.setText("" + movieTime.getDayOfMonth() + " / " + movieTime.getMonth());
        Context context = holder.cardView.getContext();

        int backgroundColor = movieTime.isDateSelected() ?
                ContextCompat.getColor(context, R.color.color_gray) : ContextCompat.getColor(context, R.color.white);

        int textColor = movieTime.isDateSelected() ?
                ContextCompat.getColor(context, R.color.white) : ContextCompat.getColor(context, R.color.black);

        holder.cardView.setCardBackgroundColor(backgroundColor);
        holder.dayOfWeek.setTextColor(textColor);
        holder.dayOfMonth.setTextColor(textColor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MovieDateItemHolder extends RecyclerView.ViewHolder {
        TextView dayOfWeek, dayOfMonth;
        CardView cardView;

        public MovieDateItemHolder(@NonNull View itemView) {
            super(itemView);
            this.dayOfWeek = itemView.findViewById(R.id.text_date_of_week);
            this.dayOfMonth = itemView.findViewById(R.id.text_date_of_month);
            this.cardView = itemView.findViewById(R.id.card_view);
        }
    }
}


