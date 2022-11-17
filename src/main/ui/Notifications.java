package ui;

import model.Alert;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Notifications extends AlertApp {

    private List<String> list;


    public Notifications() throws FileNotFoundException {
        list = new ArrayList<>();
    }

    public String notificationsToString() {
        boolean thereIsNothing = true;
        for (Alert a : super.myAccount.getAlerts().getList()) {
            if (a.shouldBeNotified(LocalDateTime.now())) {
                thereIsNothing = false;
                this.list.add("Alert name:" + a.getDueName());
                this.list.add("Alert name:" + a.getFutureDate());
            }
        }
        if (thereIsNothing) {
            this.list.add("NOTHING FOR NOW");
        }
        return this.list.toString();
    }

}
