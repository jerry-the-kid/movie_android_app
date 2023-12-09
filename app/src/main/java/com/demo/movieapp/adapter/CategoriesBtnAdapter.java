package com.demo.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.model.CategoryButton;

import java.util.List;

public class CategoriesBtnAdapter extends RecyclerView.Adapter<CategoriesBtnAdapter.CategoriesBtnHolder> {
    List<CategoryButton> buttons;

    public CategoriesBtnAdapter(List<CategoryButton> buttons) {
        this.buttons = buttons;
    }

    @NonNull
    @Override
    public CategoriesBtnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.button_item, parent,false);
        return new CategoriesBtnHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesBtnHolder holder, int position) {
        CategoryButton button = buttons.get(position);
        holder.button.setText(button.getName());
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }

    class CategoriesBtnHolder extends RecyclerView.ViewHolder {
        Button button;

        public CategoriesBtnHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.category_btn);
        }
    }
}
