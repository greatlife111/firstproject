package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// A class that contains the list of all alerts an account has starting from current time
public class AlertList implements Writable {

    private List<Alert> list;


    //Constructor that creates an empty AlertList ArrayList
    public AlertList() {
        list = new ArrayList<>();
    }


    public List<Alert> getList() {
        return list;
    }


    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: returns the size of alert list
    public boolean isEmpty() {
        return list.size() == 0;
    }

    public int getSize() {
        return list.size();
    }

    // REQUIRES: alert is not already in the list
    // MODIFIES: this
    // EFFECTS: add an alert to the list if it does not exist in the list
    public boolean addAlert(Alert a) {
        if (!(list.contains(a))) {
            list.add(a);
        }
        return false;
    }

    // REQUIRES: list must be nonempty
    // MODIFIES: this
    // EFFECTS: removes an alert in the list arraylist
    public void removeAlert(String alertName) {
        for (int i = 0; i < list.size(); i++) {
            Alert a = list.get(i);
            if (a.getDueName().equals(alertName)) {
                list.remove(i);
                i--;
            }
        } //remember boolean case
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: outputs list of alerts before input date
    public List<Alert> viewAlertBeforeDate(LocalDateTime d) {
        List<Alert> alertListBeforeDate;
        alertListBeforeDate = new ArrayList<>();
        for (Alert a : list) {
            if (a.getFutureDate().isBefore(d)) {
                alertListBeforeDate.add(a);
            }
        }
        return alertListBeforeDate;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: outputs list of alerts in the next d days
    public List<Alert> viewAlertNextDays(int d, LocalDateTime now) {
        List<Alert> alertListOfNextDays;
        alertListOfNextDays = new ArrayList<>();

        LocalDateTime endDate = now.plusDays(d + 1);
        endDate = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), 0, 0);
        for (Alert a : list) {
            if (a.getFutureDate().isBefore(endDate)) {
                alertListOfNextDays.add(a);
            }
        }
        return alertListOfNextDays;
    }

    public List<Alert> viewAlertNextDays(int d) {
        return this.viewAlertNextDays(d, LocalDateTime.now());
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: displays all alerts on a given date
    public List<Alert> viewAlertsOnTheDay(LocalDateTime d) {
        List<Alert> alertListOfTheDay;
        alertListOfTheDay = new ArrayList<>();
        for (Alert a : list) {
            if (a.getFutureDate().getYear() == d.getYear() && a.getFutureDate().getMonthValue() == d.getMonthValue()
                    && a.getFutureDate().getDayOfMonth() == d.getDayOfMonth()) {
                alertListOfTheDay.add(a);
            }
        }
        return alertListOfTheDay;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("list", alertListToJson());
        return json;
    }

    private JSONArray alertListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Alert alert : list) {
            jsonArray.put(alert.toJson());
        }

        return jsonArray;
    }
}
