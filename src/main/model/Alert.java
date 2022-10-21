package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


// A class that includes all fields of an Alert
public class Alert {
    List<LocalDateTime> notifications; // List of dates where an alert will be notified
    private LocalDateTime date;        // due date of the alert
    private String due;                // name of alert
    private int repeat;                // how many times the alert is repeated

    // Constructor method that creates a default Alert.
    public Alert(LocalDateTime date, String due, int repeat) {
        this.date = date;
        this.due = due;
        this.repeat = repeat;
        this.notifications = calculateNotifications(date);
    }

    //REQUIRES: the alert is nonempty
    //MODIFIES: this
    //EFFECTS: return specific dates(type LocalDateTime) when the alert will be notified as an arraylist
    public List<LocalDateTime> calculateNotifications(LocalDateTime finalAlertTime) {
        List<LocalDateTime> notificationList;
        notificationList = new ArrayList<>();

        long deltaTime = ChronoUnit.MINUTES.between(LocalDateTime.now(), finalAlertTime) / this.getRepeat();

        for (LocalDateTime.now(); LocalDateTime.now().isEqual(finalAlertTime); LocalDateTime.now().plusMinutes(deltaTime)) {
            notificationList.add(LocalDateTime.now().plusMinutes(deltaTime));
        }
        return notificationList;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: if the alert at the input time should be notified, return true; if not, return false
    public boolean shouldBeNotified(LocalDateTime time) {
        for (LocalDateTime n : notifications) {
            if ((n.isBefore(time)) || n.isEqual(time)) {
                return true;
            }
        }
        return false;
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

    public List<LocalDateTime> getNotifications() {
        return notifications;
    }

}
