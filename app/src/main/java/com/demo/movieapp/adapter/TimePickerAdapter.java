package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.CinemaFilmSchedule;

import java.util.List;

public class TimePickerAdapter extends RecyclerView.Adapter<TimePickerAdapter.TimePickerHolder> {

    List<CinemaFilmSchedule> cinemaSchedule;

    public TimePickerAdapter(List<CinemaFilmSchedule> cinemaSchedule) {
        this.cinemaSchedule = cinemaSchedule;
    }

    @NonNull
    @Override
    public TimePickerAdapter.TimePickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.theatre_item_schedule, parent,false);
        return new TimePickerAdapter.TimePickerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimePickerAdapter.TimePickerHolder holder, int position) {
        CinemaFilmSchedule schedule = cinemaSchedule.get(position);
        holder.cinemaName.setText(schedule.getCinemaName());
        HoursAdapter hoursAdapter = new HoursAdapter(schedule.getHours());
        holder.recyclerView.setAdapter(hoursAdapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        return cinemaSchedule.size();
    }

    class TimePickerHolder extends RecyclerView.ViewHolder {
        TextView cinemaName;
        RecyclerView recyclerView;


        public TimePickerHolder(@NonNull View itemView) {
            super(itemView);
            cinemaName = itemView.findViewById(R.id.cinema_name);
            recyclerView = itemView.findViewById(R.id.cinema_schedule);
        }
    }
}
