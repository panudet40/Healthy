package com.example.healthy.boom.healthy.Sleep;

import java.io.Serializable;

public class Sleep implements Serializable {
    private int id;
    private String sleep_date;
    private String bedtime_period;
    private String awaktime;


    public Sleep() {}

    public Sleep(int id, String sleep_date, String bedtime_period, String awaktime) {
        this.id = id;
        this.sleep_date = sleep_date;
        this.bedtime_period = bedtime_period;
        this.awaktime = awaktime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSleep_date() {
        return sleep_date;
    }

    public void setSleep_date(String sleep_date) {
        this.sleep_date = sleep_date;
    }

    public String getBedtime_period() {
        return bedtime_period;
    }

    public void setBedtime_period(String bedtime_period) {
        this.bedtime_period = bedtime_period;
    }

    public String getAwaktime() {
        return awaktime;
    }

    public void setAwaktime(String awaktime) {
        this.awaktime = awaktime;
    }
}

