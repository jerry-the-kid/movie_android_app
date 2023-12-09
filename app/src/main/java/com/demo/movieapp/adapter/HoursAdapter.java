package com.demo.movieapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;

import java.util.List;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HoursHolder> {
    List<String> hours;

    public HoursAdapter(List<String> hours) {
        this.hours = hours;
    }

    @NonNull
    @Override
    public HoursHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hour_item, parent,false);
        return new HoursAdapter.HoursHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursHolder holder, int position) {
        String buttonName = hours.get(position);
        holder.button.setText(buttonName);

    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    class HoursHolder extends RecyclerView.ViewHolder {
        Button button;
        public HoursHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);
        }
    }
}
