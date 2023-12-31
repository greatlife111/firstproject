package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.time.DateTimeException;
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
            EventLog.getInstance().logEvent(new Event(a.getDueName() + " Alert added"));
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
                EventLog.getInstance().logEvent(new Event(alertName + " Alert deleted"));
            }
        }
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
        EventLog.getInstance().logEvent(new Event("Viewed alerts before " + d));
        return alertListBeforeDate;
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: outputs list of alerts in the next d days
    public List<Alert> viewAlertNextDays(int d, LocalDateTime now) throws NumberFormatException {
        List<Alert> alertListOfNextDays;
        alertListOfNextDays = new ArrayList<>();

        if (d < 1) {
            throw new NumberFormatException();
        } else {

            LocalDateTime endDate = now.plusDays(d + 1);
            endDate = LocalDateTime.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), 0, 0);
            for (Alert a : list) {
                if (a.getFutureDate().isBefore(endDate) && a.getFutureDate().isAfter(LocalDateTime.now())) {
                    alertListOfNextDays.add(a);
                }
            }
            EventLog.getInstance().logEvent(new Event("Viewed alerts of next " + d + " days"));
            return alertListOfNextDays;
        }
    }

    public List<Alert> viewAlertNextDays(int d) throws NumberFormatException {
        if (d < 1) {
            throw new NumberFormatException();
        } else {
            return this.viewAlertNextDays(d, LocalDateTime.now());
        }
    }

    // REQUIRES: none
    // MODIFIES: none
    // EFFECTS: displays all alerts on a given date
    public List<Alert> viewAlertsOnTheDay(LocalDateTime d) throws DateTimeException {
        List<Alert> alertListOfTheDay;
        alertListOfTheDay = new ArrayList<>();
        for (Alert a : list) {
            if (a.getFutureDate().getYear() == d.getYear() && a.getFutureDate().getMonthValue() == d.getMonthValue()
                    && a.getFutureDate().getDayOfMonth() == d.getDayOfMonth()) {
                alertListOfTheDay.add(a);
            }
        }
        EventLog.getInstance().logEvent(new Event("Viewed alerts on " + d));
        return alertListOfTheDay;
    }


    // EFFECTS: converts the AlertList into a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("list", alertListToJson());
        return json;
    }


    // EFFECTS: converts the list of alerts into a JSONArray
    private JSONArray alertListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Alert alert : list) {
            jsonArray.put(alert.toJson());
        }

        return jsonArray;
    }
}
