package com.demo.movieapp.ui.change_information;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.movieapp.MainActivity;
import com.demo.movieapp.R;
import com.demo.movieapp.model.User;
import com.demo.movieapp.ui.home.HomeFragment;
import com.demo.movieapp.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChangeInformationActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ImageView ivAvatar;
    private EditText edName;
    private EditText edAvatar;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPassword;
    private Button btnSave;
    private Button btnLogOut;
    private User user;

    protected void init()
    {
        ivAvatar = findViewById(R.id.ivAvatar);
        edName = findViewById(R.id.edName);
        edAvatar = findViewById(R.id.edAvatar);
        edPhone = findViewById(R.id.edPhone);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnSave = findViewById(R.id.btnSave);
        btnLogOut = findViewById(R.id.btnLogOut);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);
        init();
        getData();
        btnSave.setOnClickListener(view -> {
            String name = edName.getText().toString();
            String avatar = edAvatar.getText().toString();
            String phone = edPhone.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            updateUser(name, avatar, phone, email, password);
        });

        btnLogOut.setOnClickListener(view -> {
            mAuth.signOut();
            finish();
        });
    }

    private void getData() {
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("UUID");
        db.collection("user").
                whereEqualTo("id", uuid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    for (DocumentSnapshot doc : snapshot.getDocuments())
                    {
                        Picasso.get().load((String) doc.get("avatar")).into(ivAvatar);
                        edAvatar.setText((String) doc.get("avatar"));
                        edName.setText((String) doc.get("name"));
                        edPhone.setText((String) doc.get("phone"));
                        edEmail.setText((String) doc.get("email"));
                        edPassword.setText((String) doc.get("password"));
                    }
                });
    }

    private void updateUser(String name, String avatar, String phone, String email, String password) {
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("UUID");
        db.collection("user")
                .whereEqualTo("id", uuid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    for (QueryDocumentSnapshot doc : snapshot)
                    {
                        reAuthentication(email, password);
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("avatar", avatar);
                        updates.put("name", name);
                        updates.put("phone", phone);
                        updates.put("email", email);
                        updates.put("password", password);
                        db.collection("user")
                                .document(doc.getId())
                                .update(updates)
                                .addOnSuccessListener(result -> {
                                    Toast.makeText(ChangeInformationActivity.this, "Update User Successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Update User Error", e.toString());
                                    Toast.makeText(ChangeInformationActivity.this, "An unknown error occurred!", Toast.LENGTH_SHORT).show();
                                });
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(email)
                                .addOnSuccessListener(task -> {
                                });
                        user.updatePassword(password)
                                .addOnSuccessListener(task -> {
                        });
                    }
                });



    }

    private void reAuthentication(String email, String password)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });
    }
}
