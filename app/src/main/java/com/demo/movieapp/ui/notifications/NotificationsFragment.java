package com.demo.movieapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.movieapp.adapter.NotificationAdapter;
import com.demo.movieapp.databinding.FragmentNotificationsBinding;
import com.demo.movieapp.model.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("user");
    NotificationAdapter adapter;
    private FragmentNotificationsBinding binding;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        ArrayList<Notification> notifications = new ArrayList<>();
        adapter = new NotificationAdapter(notifications);

        binding.rvNotifications.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        binding.rvNotifications.setAdapter(adapter);

        View root = binding.getRoot();


        authStateListener = firebaseAuth -> {
            user = mAuth.getCurrentUser();
            if (user == null) return;

            Query q = db.getInstance()
                    .collection("notifications").whereEqualTo("user_id", user.getUid());
//                    .orderBy("time", Query.Direction.DESCENDING);

            q.get().addOnCompleteListener(t -> {
                if (t.isSuccessful()) {
                    if (t.getResult().isEmpty()) return;

                    notifications.clear();
                    for (QueryDocumentSnapshot d : t.getResult()) {
                        Notification notification
                                = new Notification(d.getString("title"),
                                d.getDate("time"), d.getString("cinema"), d.getString("user_id"));

                        notifications.add(notification);
                    }

                    adapter.notifyDataSetChanged();
                }
            });


        };

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
}