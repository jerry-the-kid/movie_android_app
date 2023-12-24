package com.demo.movieapp.model;

import java.util.ArrayList;

public class Room {
    int roomId;
    String startTime;
    String endTime;
    ArrayList<String> reservedSeats;

    public Room() {
    }

    public Room(int roomId, String startTime, String endTime, ArrayList<String> reservedSeats) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservedSeats = reservedSeats;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<String> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(ArrayList<String> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }
}
