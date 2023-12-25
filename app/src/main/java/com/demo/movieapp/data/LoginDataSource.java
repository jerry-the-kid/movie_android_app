package com.demo.movieapp.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.demo.movieapp.data.callback.LoginResultCallback;
import com.demo.movieapp.data.exception.LoginException;
import com.demo.movieapp.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void login(String username, String password, LoginResultCallback callback) {
        try {
            mAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Authentication successfully => Get Data
                            FirebaseUser user = task.getResult().getUser();
                            String userId = user.getUid();
                            String displayName = user.getDisplayName();
                            String imageUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null;

                            LoggedInUser loggedInUser = new LoggedInUser(userId, displayName, imageUrl);
                            callback.onSuccess(loggedInUser);
                        } else {
                            // Authentication failed
                            Exception e = task.getException();
                            if (e instanceof FirebaseAuthException) {
                                FirebaseAuthException firebaseAuthException = (FirebaseAuthException) e;
                                String errorCode = firebaseAuthException.getErrorCode();
                                callback.onError(new LoginException(errorCode));
                            }
                        }
                    });
        } catch (Exception e) {
            callback.onError(new LoginException());
        }
    }

    public void logout() {
        mAuth.signOut();
    }
}