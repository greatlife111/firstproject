package model;

import org.json.JSONObject;
import persistance.Writable;


// represents an account having an id, name, and list of alerts.
public class Account implements Writable {
    private final int id;
    private String name;
    private AlertList alerts;


    // REQUIRES: a name of non-zero length
    // MODIFIES: none
    // EFFECTS: Constructs an account object where the account id is set to id, account name is set to name, and alert
    //          list set to alerts.
    public Account(int id, String name, AlertList alerts) {
        this.id = id;
        this.name = name;
        this.alerts = alerts;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AlertList getAlerts() {
        return alerts;
    }

    // REQUIRES: a string of non-zero length
    // MODIFIES: THIS
    // EFFECTS: changes the account name
    public void changeName(String s) {
        name = s;
        EventLog.getInstance().logEvent(new Event("Account name changed to: " + s));
    }

    // EFFECTS: converts the Account class into a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("alertlist", alerts.toJson());

        return json;
    }


}

