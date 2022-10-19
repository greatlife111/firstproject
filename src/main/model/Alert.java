package model;

// A class that includes all fields of a DueAlert
public class Alert {
    private String date;
    private int time;
    private String due;
    private int repeat;

    // Constructor method that creates a default Alert.
    public Alert(String date, int time, String due, int repeat) {
        this.date = date;
        this.time = time;
        this.due = due;
        this.repeat = repeat;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: changes the date of the alert
    public void changeDate(String s) {
        date = s;
    }

    // REQUIRES: none
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

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: changes the name of the alert
    public void changeRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String getDate() {
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
