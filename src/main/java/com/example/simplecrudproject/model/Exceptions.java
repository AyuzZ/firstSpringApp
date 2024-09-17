package com.example.simplecrudproject.model;

public class Exceptions {
    private String details;

    public Exceptions() {
    }

    public Exceptions(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Exceptions{" +
                "details= '" + details + '\'' +
                '}';
    }
}
