package com.demo.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.Hour;
import com.demo.movieapp.model.Room;
import com.demo.movieapp.model.Showtime;

import java.util.ArrayList;
import java.util.List;

public class TimePickerAdapter extends RecyclerView.Adapter<TimePickerAdapter.TimePickerHolder> {

    public HoursAdapter.OnItemClickListener clickListener;
    List<Showtime> showtimes;
    Context parentContext;

    public TimePickerAdapter(List<Showtime> showtimes, HoursAdapter.OnItemClickListener clickListener) {
        this.showtimes = showtimes;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TimePickerAdapter.TimePickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.theatre_item_schedule, parent, false);
        return new TimePickerAdapter.TimePickerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimePickerAdapter.TimePickerHolder holder, int position) {
        Showtime showtime = showtimes.get(position);
        holder.cinemaName.setText(showtime.getCinemaName());
        ArrayList<Hour> hours = new ArrayList<>();
        for (Room room : showtime.getRooms()) {
            hours.add(new Hour(showtime.getId(), showtime.getMovieId(), showtime.getDay(), room.getStartTime(),
                    showtime.getCinemaName(), room.getRoomId(), room.getReservedSeats()));
        }
        HoursAdapter hoursAdapter = new HoursAdapter(hours);
        holder.recyclerView.setAdapter(hoursAdapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        if (clickListener != null) {
            hoursAdapter.setClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return showtimes.size();
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
