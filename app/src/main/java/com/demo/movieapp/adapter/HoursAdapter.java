package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.Hour;

import java.util.List;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HoursHolder> {
    public OnItemClickListener clickListener;
    List<Hour> hours;

    public HoursAdapter(List<Hour> hours) {
        this.hours = hours;
    }

    public void setClickListener(OnItemClickListener myListener) {
        this.clickListener = myListener;
    }

    @NonNull
    @Override
    public HoursHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hour_item, parent, false);
        return new HoursAdapter.HoursHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HoursHolder holder, int position) {
        Hour hour = hours.get(position);
        holder.button.setText(hour.getStartTime());
    }

    public interface OnItemClickListener {
        void onItemClick(Hour hour);
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
            button.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(hours.get(position));
                    }
                }
            });
        }
    }
}
