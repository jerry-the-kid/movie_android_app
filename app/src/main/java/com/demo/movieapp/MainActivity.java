package com.demo.movieapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.demo.movieapp.databinding.ActivityMainBinding;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ActivityMainBinding binding;
    NavController navController;

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    });

    @SuppressLint("ScheduleExactAlarm")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hideActionBar();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_ticket, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        askNotificationPermission();

        if (mAuth.getCurrentUser() != null) {
            db.collection("ticket")
                    .whereEqualTo("userId", mAuth.getCurrentUser().getUid())
                    .orderBy("time", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        int requestCode = 0;
                        for (DocumentSnapshot doc : snapshot.getDocuments()
                        ) {

                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                Date time = sdf.parse((String) doc.get("time"));
                                String title = (String) doc.get("title");
                                String cinema = (String) doc.get("cinema");
                                String user_id = (String) doc.get("userId");

                                Date newDate = addTime(time, 60);
                                
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    requestCode++;
                                    // setup time for notification
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTimeInMillis(System.currentTimeMillis());
                                    calendar.setTime(newDate);
                                    calendar.set(Calendar.SECOND, 0);
//                                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));


                                    // get notification
                                    Intent intent = new Intent(this, NotificationReceiver.class);
                                    intent.putExtra("title", title);
                                    intent.putExtra("time", newDate);
                                    intent.putExtra("cinema", cinema);
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);


                                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                                    // Add notification to firebase
                                    addNotificationToFirestore(title, newDate, cinema, user_id);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Error: formatted incorrect!", Toast.LENGTH_SHORT).show();
                            }


                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.d("FIREBASE000", e.toString());
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    });
        }

    }


    public void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }


    public static Date addTime(Date current, int extraMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, current.getHours());
        calendar.set(Calendar.MINUTE, current.getMinutes());

        calendar.add(Calendar.MINUTE, -extraMinutes);

        return calendar.getTime();

    }

    private void addNotificationToFirestore(String title, Date newDate, String cinema, String user_id) {
        Map<String, Object> notification = new HashMap<>();
        notification.put("title", title);
        notification.put("time", newDate);
        notification.put("cinema", cinema);
        notification.put("userId", user_id);

        db.getInstance()
                .collection("notifications")
                .add(notification)
                .addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "Add notification successfully! " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error!", e);
                });
    }


}