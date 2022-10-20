package model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

// A class that contains the list of all alerts an account has starting from current time
public class AlertList {

    ArrayList<Alert> list;

    //Constructor that creates an empty AlertList ArrayList
    public AlertList() {
        list = new ArrayList<>();
    }

    //REQUIRES: none
    //MODIFIES: this
    //EFFECTS: add an alert to the list arraylist
    public ArrayList<Alert> addAlert() {
        return null;
    }

    //REQUIRES: list must be nonempty
    //MODIFIES: this
    //EFFECTS: removes an alert in the list arraylist
    public ArrayList<Alert> removeAlert() {
        return null;
    }

    //REQUIRES: none
    //MODIFIES: none
    //EFFECTS: outputs list of alerts before input date
    public ArrayList<Alert> viewAlertBeforeDate(Date d) {
        return null;
    }

    //REQUIRES: none
    //MODIFIES: none
    //EFFECTS: outputs list of alerts in the next d days
    public ArrayList<Alert> viewAlertNextDays(int d) {
        return null;
    }

    //REQUIRES: none
    //MODIFIES: none
    //EFFECTS: displays all alerts on a given date
    public ArrayList<Alert> viewAlertsOnTheDay(LocalDate d) {
        return null;
    }


}
