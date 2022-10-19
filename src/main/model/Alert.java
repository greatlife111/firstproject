package model;

import java.util.Date;

// A class that includes all fields of a DueAlert
public class Alert {
    private Date date;
    private int time;
    private String due;
    private int repeat;

    // Constructor method that creates a default Alert.
    public Alert(Date date, int time, String due, int repeat) {
        this.date = date;
        this.time = time;
        this.due = due;
        this.repeat = repeat;
    }


    // REQUIRES: month has to be 1 to 12; days have to be 1-31
    // MODIFIES: this
    // EFFECTS: changes the date of the alert
    public Date changeDate(Date d) {
        date = d;
    }

    // REQUIRES: time is in between 0000 and 2359
    // MODIFIES: this
    // EFFECTS: changes the time of the alert
    public void changeTime(int time) {
        this.time = time;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: changes the name of the alert
    public void changeDue(String due) {
        this.due = due;
    }

    // REQUIRES: repeat times >= 0
    // MODIFIES: this
    // EFFECTS: changes the name of the alert
    public void changeRepeat(int repeat) {
        this.repeat = repeat;
    }

    public Date getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }

    public String getDue() {
        return due;
    }

    public int getRepeat() {
        return repeat;
    }
}
