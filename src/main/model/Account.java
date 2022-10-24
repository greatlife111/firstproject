package model;

// represents an account having an id, name, and list of alerts.
public class Account {
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
    }
}



