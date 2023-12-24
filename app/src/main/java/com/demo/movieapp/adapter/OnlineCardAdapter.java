package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.OnlineCard;

import java.util.ArrayList;

public class OnlineCardAdapter extends RecyclerView.Adapter<OnlineCardAdapter.OnlineCardHolder> {
    public OnItemClickListener clickListener;
    ArrayList<OnlineCard> cards;

    public OnlineCardAdapter(ArrayList<OnlineCard> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public OnlineCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pay_card, parent, false);
        return new OnlineCardAdapter.OnlineCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineCardHolder holder, int position) {
        OnlineCard onlineCard = cards.get(position);
        holder.textView.setText(onlineCard.getFormatCardNumber());
        holder.check.setVisibility(onlineCard.isSelected() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(OnlineCard onlineCard);
    }

    class OnlineCardHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView check;

        public OnlineCardHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.card_number);
            check = itemView.findViewById(R.id.check);
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        for (OnlineCard card : cards) {
                            card.setSelected(false);
                        }
                        notifyDataSetChanged();
                        cards.get(position).setSelected(!cards.get(position).isSelected());
                        notifyItemChanged(position);
                        clickListener.onItemClick(cards.get(position));
                    }
                }
            });
        }
    }
}
