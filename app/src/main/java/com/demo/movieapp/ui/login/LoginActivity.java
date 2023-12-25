package com.demo.movieapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.movieapp.R;
import com.demo.movieapp.data.LoginDataSource;
import com.demo.movieapp.data.LoginRepository;
import com.demo.movieapp.data.callback.LoginResultCallback;
import com.demo.movieapp.data.exception.LoginException;
import com.demo.movieapp.data.model.LoggedInUser;
import com.demo.movieapp.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private EditText edEmail;
    private EditText edPassword;
    private CheckBox cbRemember;
    private Button btnLogin;
    private TextView tvForgot;
    private TextView tvSignUp;

    private LoginRepository loginRepository = LoginRepository.getInstance(new LoginDataSource());

    public void init() {
        ivLogo = findViewById(R.id.ivLogo);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgot = findViewById(R.id.tvForgot);
        tvSignUp = findViewById(R.id.tvSingUp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        btnLogin.setOnClickListener(v -> {
            String username = edEmail.getText().toString();
            String password = edPassword.getText().toString();

            loginRepository.login(username, password, new LoginResultCallback() {
                @Override
                public void onSuccess(LoggedInUser user) {
                    loginRepository.setLoggedInUser(user);
                    Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                    finish();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
                }

                @Override
                public void onError(LoginException e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });

        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

}