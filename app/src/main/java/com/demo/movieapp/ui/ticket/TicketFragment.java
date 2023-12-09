package com.demo.movieapp.ui.ticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.movieapp.R;
import com.demo.movieapp.adapter.TicketAdapter;
import com.demo.movieapp.databinding.FragmentTicketBinding;
import com.demo.movieapp.model.Ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class TicketFragment extends Fragment {
    TicketAdapter adapter;

    private FragmentTicketBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TicketViewModel dashboardViewModel =
                new ViewModelProvider(this).get(TicketViewModel.class);

        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Ticket> tickets = new ArrayList<Ticket>(Arrays.asList(
                new Ticket("Bleach thousand year blood", new Date(), R.drawable.pic,
                        4.5, new String[]{"A1", "A2", "A3"}, "01"),
                new Ticket("Bleach thousand year blood", new Date(), R.drawable.pic,
                        4.5, new String[]{"A1", "A2"}, "02")
        ));

        adapter = new TicketAdapter(tickets);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        binding.recyclerView.setAdapter(adapter);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}