package com.demo.movieapp.model;

public class OnlineCard {
    //    String name;
    String cardNumber;
    String cvv;
    String userName;
    boolean isSelected;
    Double amountRemain;

    public OnlineCard() {
    }


    public OnlineCard(String cardNumber, String cvv, String userName, double amountRemain, boolean isSelected) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.userName = userName;
        this.isSelected = isSelected;
        this.amountRemain = amountRemain;
    }

    public OnlineCard(String cardNumber, String cvv, String userName, Double amountRemain) {
        this(cardNumber, cvv, userName, amountRemain, false);
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber.trim();
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName.trim();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Double getAmountRemain() {
        return amountRemain;
    }

    public void setAmountRemain(Double amountRemain) {
        this.amountRemain = amountRemain;
    }

    public boolean isCardValid(Double ticketAmount) {
        return ticketAmount < this.amountRemain;
    }

    public String getFormatCardNumber() {
        String digitsOnly = this.cardNumber.replaceAll("\\D", "");

        if (digitsOnly.length() != 16) {
            throw new IllegalArgumentException("Invalid card number length");
        }

        return "**** **** **** " + digitsOnly.substring(12);
    }
}
