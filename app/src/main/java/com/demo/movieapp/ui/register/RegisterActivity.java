package com.demo.movieapp.ui.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.movieapp.R;
import com.demo.movieapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private  FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private CircleImageView civAvatar;
    private ImageView ivCamera;
    private EditText edFullName;
    private RadioButton cbMale;
    private RadioButton cbFemale;
    private DatePicker dpBirthdate;
    private EditText edEmail;
    private EditText edPhone;
    private EditText edPassword;
    private EditText edConfirmPassword;
    private Button btnSignUp;
    private TextView tvBack;

    private User user;

    public void init() {
        civAvatar = findViewById(R.id.civAvatar);
        ivCamera = findViewById(R.id.ivCamera);
        edFullName = findViewById(R.id.edFullName);
        cbMale = findViewById(R.id.cbMale);
        cbFemale = findViewById(R.id.cbFemale);
        dpBirthdate = findViewById(R.id.dpBirthdate);
        edEmail = findViewById(R.id.edEmail);
        edPhone = findViewById(R.id.edPhone);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvBack = findViewById(R.id.tvBack);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        tvBack.setOnClickListener(v -> {
            finish();
        });
        ivCamera.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });

        btnSignUp.setOnClickListener(v -> action());
    }

    private void action() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(dpBirthdate.getYear(), dpBirthdate.getMonth(), dpBirthdate.getDayOfMonth());

        String name = edFullName.getText().toString();
        boolean gender = !cbFemale.isActivated();
        Date birthdate = calendar.getTime();
        String email = edEmail.getText().toString();
        String phone = edPhone.getText().toString();
        String password = edPassword.getText().toString();
        String confirmPassword = edConfirmPassword.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name is not empty!", Toast.LENGTH_SHORT).show();
        }
        else if (email.isEmpty()) {
            Toast.makeText(this, "Email is not empty!", Toast.LENGTH_SHORT).show();
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            Toast.makeText(this, "Email is formatted incorrect!", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty()) {
            Toast.makeText(this, "Phone is not empty!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is not empty!", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password must be more 6 characters!", Toast.LENGTH_SHORT).show();
        } else if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Confirm Password is not empty!", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Confirm Password is not correct!", Toast.LENGTH_SHORT).show();
        } else {
//            createUser(email, password, "", name, gender, birthdate, phone);
            uploadImage(email, password, name, gender, birthdate, phone);
        }
    }

    private void addUser(String id, String avatar, String name, Boolean gender, Date birthdate, String email, String phone, String password) {
        DocumentReference docRef = db.collection("user").document();

        user = new User(docRef.getId(), avatar, name, gender, birthdate, email, phone, password, true);
        docRef.set(user)
                .addOnSuccessListener(result -> {
                    Toast.makeText(RegisterActivity.this, "Add User Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("Add User Error", e.toString());
                    Toast.makeText(RegisterActivity.this, "An unknown error occurred!", Toast.LENGTH_SHORT).show();
                });
    }

    private void createUser(String email, String password, String avatar, String name, Boolean gender, Date birthdate, String phone) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            FirebaseUser firebaseUser = authResult.getUser();
            addUser(firebaseUser.getUid(), avatar, name, gender, birthdate, email, phone, password);
            mAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(Uri.parse(avatar)).build());
        }).addOnFailureListener(e -> {

        });
    }

    private void uploadImage(String email, String password, String name, Boolean gender, Date birthdate, String phone) {
        Bitmap bitmap = ((BitmapDrawable) civAvatar.getDrawable()).getBitmap();

        UUID uuid = UUID.randomUUID();

        StorageReference stoRef = storage.getReference().child("images/" + uuid.toString() + "/" + uuid.toString() + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = stoRef.putBytes(data);
        uploadTask.addOnCompleteListener(task -> {
            stoRef.getDownloadUrl().addOnSuccessListener(task1 -> {
                String downloadLink = task1.toString();
                createUser(email, password, downloadLink, name, gender, birthdate, phone);

            }).addOnFailureListener(e -> {

            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUrl = data.getData();
            if (selectedImageUrl != null)
                civAvatar.setImageURI(data.getData());
        }
    }
}