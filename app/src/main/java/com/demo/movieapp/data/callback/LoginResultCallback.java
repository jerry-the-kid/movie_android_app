package com.demo.movieapp.data.callback;

import com.demo.movieapp.data.exception.LoginException;
import com.demo.movieapp.data.model.LoggedInUser;

public interface LoginResultCallback {
    void onSuccess(LoggedInUser user);

    void onError(LoginException exception);
}
