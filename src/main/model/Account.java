package model;

// represents an account having an id, name, and list of alerts.
public class Account {
    private static int id;
    private String name;
    private AlertList alerts;

    public Account(int id, String name, AlertList alerts) {
        this.id = id;
        this.name = name;
        this.alerts = alerts;
    }
}



