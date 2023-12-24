package com.demo.movieapp.model;

public class OnlineCard {
    //    String name;
    String cardNumber;
    String cvv;
    String userName;
    boolean isSelected;

    public OnlineCard() {
    }


    public OnlineCard(String cardNumber, String cvv, String userName, boolean isSelected) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.userName = userName;
        this.isSelected = isSelected;
    }

    public OnlineCard(String cardNumber, String cvv, String userName) {
        this(cardNumber, cvv, userName, false);
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getFormatCardNumber() {
        String digitsOnly = this.cardNumber.replaceAll("\\D", "");

        if (digitsOnly.length() != 16) {
            throw new IllegalArgumentException("Invalid card number length");
        }

        return "**** **** **** " + digitsOnly.substring(12);
    }
}
