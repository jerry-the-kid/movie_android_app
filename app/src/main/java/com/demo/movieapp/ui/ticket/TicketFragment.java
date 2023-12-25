package com.demo.movieapp.ui.ticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.movieapp.adapter.TicketAdapter;
import com.demo.movieapp.databinding.FragmentTicketBinding;
import com.demo.movieapp.model.GlobalState;
import com.demo.movieapp.model.Ticket;

import java.util.ArrayList;


public class TicketFragment extends Fragment {
    TicketAdapter adapter;

    private FragmentTicketBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        GlobalState globalState = GlobalState.getInstance();

        TicketViewModel dashboardViewModel =
                new ViewModelProvider(this).get(TicketViewModel.class);

        binding = FragmentTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Ticket> tickets = new ArrayList<>();

        adapter = new TicketAdapter(tickets);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        binding.recyclerView.setAdapter(adapter);

        globalState.getUsersTicket().observe(getViewLifecycleOwner(), ticketList -> {
            tickets.clear();
            tickets.addAll(ticketList);
            adapter.notifyDataSetChanged();
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}