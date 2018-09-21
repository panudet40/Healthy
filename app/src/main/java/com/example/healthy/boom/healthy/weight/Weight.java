package com.example.healthy.boom.healthy.weight;

public class Weight {
    private String date;
    private String weight;

    public Weight() {
    }

    public Weight(String date, String weight) {
        this.date = date;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}