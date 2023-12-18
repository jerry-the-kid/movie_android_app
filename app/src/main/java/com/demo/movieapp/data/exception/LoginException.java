package com.demo.movieapp.data.exception;

import com.google.firebase.auth.FirebaseAuthException;

public class LoginException extends Exception {

    private String code;

    public LoginException() {
        this.code = "An error occurred!";
    }

    public LoginException(String code) {
        this.code = code;
    }

    public String getMessage() {
        switch (code) {
            case "ERROR_INVALID_EMAIL":
                return "The email is badly formatted or not correct! Please try again.";
            case "ERROR_WRONG_PASSWORD":
                return "The password is in valid!";
            case "ERROR_INVALID_CREDENTIAL":
                return "Email or password is not correct!";
            default:
                return "An error occurred!";
        }
    }
}

