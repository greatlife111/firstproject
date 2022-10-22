package model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// A class that contains the list of all alerts an account has starting from current time
public class AlertList {

    private List<Alert> list;

    //Constructor that creates an empty AlertList ArrayList
    public AlertList() {
        list = new ArrayList<>();
    }

    public List<Alert> getList() {
        return list;
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public int getSize() {
        return list.size();
    }

    //REQUIRES: alert is not already in the list
    //MODIFIES: this
    //EFFECTS: add an alert to the list arraylist
    public void addAlert(Alert a) {
        list.add(a);
    }

    //REQUIRES: list must be nonempty
    //MODIFIES: this
    //EFFECTS: removes an alert in the list arraylist
    public void removeAlert(Alert a) {
        list.remove(a);
    }

    //REQUIRES: none
    //MODIFIES: none
    //EFFECTS: outputs list of alerts before input date
    public List<Alert> viewAlertBeforeDate(LocalDateTime d) {
        return null;
    }

    //REQUIRES: none
    //MODIFIES: none
    //EFFECTS: outputs list of alerts in the next d days
    public List<Alert> viewAlertNextDays(int d) {
        return null;
    }

    //REQUIRES: none
    //MODIFIES: none
    //EFFECTS: displays all alerts on a given date
    public List<Alert> viewAlertsOnTheDay(LocalDate d) {
        return null;
    }


}
