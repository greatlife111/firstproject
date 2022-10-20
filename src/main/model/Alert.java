package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


// A class that includes all fields of a DueAlert
public class Alert {
    private LocalDateTime date;
    private String due;
    private int repeat;
    List<LocalDateTime> notifications;

    // Constructor method that creates a default Alert.
    public Alert(LocalDateTime date, String due, int repeat) {
        this.date = date;
        this.due = due;
        this.repeat = repeat;
        notifications = calculateNotifications(date, repeat);
    }


    // REQUIRES: month has to be 1 to 12; days have to be 1-31
    // MODIFIES: this
    // EFFECTS: changes the date of the alert
    public void changeDate(LocalDateTime d) {
        date = d;
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

    public LocalDateTime getDate() {
        return date;
    }

    public String getDue() {
        return due;
    }

    public int getRepeat() {
        return repeat;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: if input tim
    public boolean shouldBeNotified(LocalDateTime time) {
        return false;
    }

    public static List<LocalDateTime> calculateNotifications(LocalDateTime finalAlertTime, int repeat) {
        List<LocalDateTime> notificationList;
        notificationList = new ArrayList<>();
        LocalDateTime delta_time = finalAlertTime.minus(LocalDateTime.now());
        for ()

    }
}
