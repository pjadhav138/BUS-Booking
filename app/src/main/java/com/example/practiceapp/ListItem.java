package com.example.practiceapp;

public class ListItem {
    String source;
    String destination;
    String date;
    String busType;
    String time;

    public ListItem(String source, String destination, String date, String busType, String time) {
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.busType = busType;
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
