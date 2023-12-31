package model;


import org.json.JSONObject;
import persistance.Writable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


// A class that includes all fields of an Alert
public class Alert implements Writable {
    private List<LocalDateTime> notifications; // List of dates where an alert will be notified
    private LocalDateTime date;        // due date of the alert
    private String due;                // name of alert
    private int repeat;                // how many times the alert is repeated
    private final LocalDateTime createdDate;   // when the alert is made


    //REQUIRES: none
    //MODIFIES: none
    //EFFECTS: uses method overload to create an alert at current time
    public Alert(LocalDateTime date, String due, int repeat) throws NumberFormatException {
        this(LocalDateTime.now(), date, due, repeat);
    }

    //REQUIRES: due is of non-zero length
    //MODIFIES: none
    //EFFECTS: creates an alert with starting time of createdDated; due date of the alert is set to input
    //         LocalDateTime date; alert name is set to input due; How many times the alert repeat is set to int repeat.
    public Alert(LocalDateTime createdDate, LocalDateTime date, String due, int repeat) throws NumberFormatException {
        if (repeat < 0) {
            throw new NumberFormatException();
        }
        if (repeat == 0) {
            this.notifications = null;
        }
        this.notifications = calculateNotifications(createdDate, date, repeat);

        this.date = date;
        this.due = due;
        this.createdDate = createdDate;
        this.repeat = repeat;
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: return specific dates(type LocalDateTime) when the alert will be notified as an arraylist
    public List<LocalDateTime> calculateNotifications(LocalDateTime createdDate, LocalDateTime finalAlertTime,
                                                      int repeat) {
        List<LocalDateTime> notificationList;
        notificationList = new ArrayList<>();
        if (repeat == 0) {
            return notificationList;
        } else {
            long deltaTime = ChronoUnit.NANOS.between(createdDate, finalAlertTime) / repeat;

            for (LocalDateTime now = createdDate.plusNanos(deltaTime);
                    now.isBefore(finalAlertTime) || now.isEqual(finalAlertTime); now = now.plusNanos(deltaTime)) {
                notificationList.add(now);
            }
            return notificationList;
        }
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

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: remove existing notifications in the list of notifications at timeAtCheck
    public void confirmNotification(LocalDateTime timeAtCheck) {
        for (int i = 0; i < notifications.size(); i++) {
            LocalDateTime n = notifications.get(i);
            if ((n.isBefore(timeAtCheck)) || n.isEqual(timeAtCheck)) {
                notifications.remove(i);
                i--;
                EventLog.getInstance().logEvent(new Event(this.getDueName() + " confirmed"));
            }
        }
    }


    // REQUIRES: month is between 1 and 12; day is between 1 and 31; hour is between 00 and 23; minute is between
    //           00 and 59
    // MODIFIES: this
    // EFFECTS: changes the date of the alert
    public void changeDate(LocalDateTime d) {
        date = d;
        if (!(d.isBefore(LocalDateTime.now()))) {
            this.notifications = calculateNotifications(createdDate, d, repeat);
        } else {
            this.notifications = null;
        }
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: changes the name of the alert
    public void changeDue(String due) throws RuntimeException {
        if (due.isEmpty()) {
            throw new RuntimeException();
        } else {
            this.due = due;
        }
    }

    // REQUIRES: none
    // MODIFIES: this
    // EFFECTS: changes the amount of times an alert repeat
    public void changeRepeat(int repeat) throws NumberFormatException {
        if (repeat < 0) {
            throw new NumberFormatException();
        } else {
            this.repeat = repeat;
            this.notifications = calculateNotifications(createdDate, date, repeat);
        }
    }

    public LocalDateTime getFutureDate() {
        return date;
    }

    public String getDueName() {
        return due;
    }

    public int getRepeat() {
        return repeat;
    }

    public List<LocalDateTime> getNotifications() {
        return notifications;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }


    // EFFECTS: converts the Alert object into a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("created", createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        json.put("date", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        json.put("due", due);
        json.put("repeat", repeat);
//        json.put("notifications", notifications);
        return json;
    }
}
