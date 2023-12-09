package com.demo.movieapp.model;

public class Seat {
    String seatId;
    SeatStatus status;

    public Seat(String seatId, SeatStatus status) {
        this.seatId = seatId;
        this.status = status;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}
