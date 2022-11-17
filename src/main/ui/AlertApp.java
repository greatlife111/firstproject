package ui;

import model.Account;
import model.Alert;
import model.AlertList;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AlertApp {
    private static final String JSON_STORE = "./data/MyAlertList.json";
    private final Scanner input;
    Account myAccount;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    Map<String, Runnable> exeMap;

    // EFFECTS: runs the map and application
    public AlertApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        exeMap = new HashMap<>();
        initExeMap();
        runAlertApp();
    }

    // EFFECTS: Assign each command to a specific key
    void initExeMap() {
        exeMap.put("A", this::addAlert);
        exeMap.put("B", this::deleteAlert);
        exeMap.put("C", this::viewAllAlerts);
        exeMap.put("D", this::viewNextDays);
        exeMap.put("E", this::viewNotifications);
        exeMap.put("F", this::viewOnTheDay);
        exeMap.put("G", this::confirmNotification);
        exeMap.put("H", this::accountInformation);
        exeMap.put("I", this::alertDetails);
        exeMap.put("J", this::editAlertDetails);
        exeMap.put("K", this::saveAccount);
        exeMap.put("L", this::loadAccount);
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    public void runAlertApp() {
        boolean keepGoing = true;
        String command = null;

        System.out.println("Enter your name:");
        String name = input.nextLine();


        myAccount = new Account(5998, name, new AlertList());

        System.out.println("Welcome " + name + "!");
        while (keepGoing) {
            displayFunctions();
            command = input.nextLine();
            command = command.toUpperCase();

            if (command.equals("Q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("STOP PROCRASTINATING -_-");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        Runnable func = exeMap.get(command);
        if (func != null) {
            func.run();
        } else {
            System.out.println("SELECTION INVALID");

        }
    }

    // MODIFIES: this
    // EFFECTS: loads account from file
    private void loadAccount() {
        try {
            myAccount = jsonReader.read();
            System.out.println("Loaded " + myAccount.getName() + "'s account from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the account to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(myAccount);
            jsonWriter.close();
            System.out.println("New changes saved to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: prompts user for alert name and display alert details
    private void alertDetails() {
        System.out.println("WHAT IS THE ALERT NAME?");
        String alertName = input.nextLine().toUpperCase();
        for (Alert alert : myAccount.getAlerts().getList()) {
            if (alert.getDueName().equals(alertName)) {
                System.out.println("ALERT NAME:" + alertName);
                System.out.println("DUE TIME:" + alert.getFutureDate());
                System.out.println("REPEAT TIMES:" + alert.getRepeat());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompt the user to change alert details
    private void editAlertDetails() {
        System.out.println("TYPE THE ALERT NAME YOU WOULD LIKE TO CHANGE");
        String name = input.nextLine().toUpperCase();
        boolean alertDoesNotExist = true;
        for (Alert alert : myAccount.getAlerts().getList()) {
            if (alert.getDueName().equals(name)) {
                alertDoesNotExist = false;
                System.out.println("TYPE N TO CHANGE NAME, D TO CHANGE DATE, R TO CHANGE REPEAT");
                String answer = input.nextLine().toUpperCase();

                if (answer.equals("N")) {
                    changeAlertName(alert);
                } else if (answer.equals("D")) {
                    changeAlertDueDate(alert);
                } else if (answer.equals("R")) {
                    changeRepeat(alert);
                } else {
                    System.out.println("INPUT INVALID");
                }
            }
        }
        if (alertDoesNotExist) {
            System.out.println("ALERT DOES NOT EXIST");
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the alert name of input alert
    private void changeAlertName(Alert alert) {
        System.out.println("WHAT IS THE NEW ALERT NAME?");
        try {
            String newName = input.nextLine().toUpperCase();
            alert.changeDue(newName);
            System.out.println("CHANGE SUCCESSFUL");
        } catch (Exception ee) {
            System.out.println("CHANGE UNSUCCESSFUL");
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the due date of input alert
    private void changeAlertDueDate(Alert alert) {
        System.out.println("WHAT IS THE NEW DATE?");
        System.out.println("ANSWER IN yyyy-MM-dd (space) HH:mm");
        String newDate = input.nextLine();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime day = LocalDateTime.parse(newDate, dateFormat);
            alert.changeDate(day);
            System.out.println("CHANGE SUCCESSFUL");
        } catch (DateTimeException ee) {
            System.out.println("CHANGE UNSUCCESSFUL");
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the repeat times of input alert
    private void changeRepeat(Alert alert) {
        System.out.println("HOW MANY TIMES WOULD THE ALERT REPEAT?");
        String repeat = input.nextLine();
        try {
            alert.changeRepeat(Integer.parseInt(repeat));
            System.out.println("CHANGE SUCCESSFUL");
        } catch (NumberFormatException ee) {
            System.out.println("CHANGE UNSUCCESSFUL");
        }
    }

    // EFFECTS: displays account information for user
    private void accountInformation() {
        System.out.println("Account ID:" + myAccount.getId());
        System.out.println("Name:" + myAccount.getName());
        System.out.println("Alerts:");
        viewAllAlerts();

        System.out.println("DO YOU WISH TO MAKE CHANGES TO YOUR ACCOUNT? ANSWER YES OR NO");
        String answer = input.nextLine().toUpperCase();

        if (answer.equals("YES")) {
            editAccountInformation();
        }
    }


    // MODIFIES: this
    // EFFECTS: prompts user to edit account information
    private void editAccountInformation() {
        System.out.println("ACCOUNT ID CANNOT BE CHANGED; TYPE NAME TO CHANGE NAME, TYPE ALERTS TO VIEW ALL ALERTS.");
        String change = input.nextLine().toUpperCase();

        if (change.equals("NAME")) {
            myAccount.changeName(change);
        } else if (change.equals("ALERTS")) {
            viewAllAlerts();
        } else {
            System.out.println("INPUT INVALID");
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts the user to confirm a notification in notification list
    private void confirmNotification() {
        System.out.println("CONFIRM THE NOTIFICATION BY TYPING ALERT NAME");
        String name = input.nextLine().toUpperCase();
        LocalDateTime now = LocalDateTime.now();
        Boolean nothing = true;
        Boolean notSuchAlert = true;

        for (Alert alert : myAccount.getAlerts().getList()) {
            if (alert.getDueName().equals(name)) {
                notSuchAlert = false;
                if (alert.shouldBeNotified(now)) {
                    nothing = false;
                    alert.confirmNotification(now);
                    System.out.println("CONFIRMATION SUCCESSFUL");
                }
            }
        }
        if (notSuchAlert) {
            System.out.println("ALERT DOES NOT EXIST");
        } else if (nothing) {
            System.out.println("ALL NOTIFICATIONS ALREADY CONFIRMED");
        }
    }

    // EFFECTS: displays all alerts on input date
    private void viewOnTheDay() {
        System.out.println("ENTER THE DATE IN THE FORMAT yyyy-MM-dd");
        try {
            String date = input.nextLine();
            date += " 00:00";
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime theDay = LocalDateTime.parse(date, dateFormat);

            for (Alert a : myAccount.getAlerts().viewAlertsOnTheDay(theDay)) {
                System.out.println("Alert name:" + a.getDueName());
                System.out.println("Due time:" + a.getFutureDate());
                System.out.println("Repeat:" + a.getRepeat());
            }
        } catch (DateTimeException ee) {
            System.out.println("INVALID INPUT DATE");
        }
    }

    // EFFECTS: displays all notifications
    private void viewNotifications() {
        boolean thereIsNothing = true;
        for (Alert a : myAccount.getAlerts().getList()) {
            if (a.shouldBeNotified(LocalDateTime.now())) {
                thereIsNothing = false;
                System.out.println("Alert name:" + a.getDueName());
                System.out.println("Due time:" + a.getFutureDate());
            }
        }
        if (thereIsNothing) {
            System.out.println("NOTHING FOR NOW");
        }
    }

    // EFFECTS: displays all alerts in the next input days
    private void viewNextDays() {
        System.out.println("HOW MANY DAYS?");
        String day = input.nextLine();
        try {
            if (myAccount.getAlerts().isEmpty()
                    || myAccount.getAlerts().viewAlertNextDays(Integer.parseInt(day)).isEmpty()) {
                System.out.println("NOTHING FOR NOW!");
            } else {
                for (Alert a : myAccount.getAlerts().viewAlertNextDays(Integer.parseInt(day))) {
                    System.out.println("Alert name:" + a.getDueName());
                    System.out.println("Due time:" + a.getFutureDate());
                    System.out.println("Repeat:" + a.getRepeat());
                }
            }
        } catch (NumberFormatException ee) {
            System.out.println("INVALID INPUT DAYS");
        }
    }

    // EFFECTS: displays all alerts added
    private void viewAllAlerts() {
        if (myAccount.getAlerts().isEmpty()) {
            System.out.println("NOTHING FOR NOW!");
        } else {
            for (int i = 0; i < myAccount.getAlerts().getSize(); i++) {
                System.out.println("Alert name:" + myAccount.getAlerts().getList().get(i).getDueName());
                System.out.println("Due time:" + myAccount.getAlerts().getList().get(i).getFutureDate());
                System.out.println("Repeat:" + myAccount.getAlerts().getList().get(i).getRepeat());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: delete an alert in the existing alert list
    private void deleteAlert() {
        System.out.println("ENTER ALERT NAME");
        String alertName = input.nextLine().toUpperCase();
        myAccount.getAlerts().removeAlert(alertName);
    }

    // MODIFIES: this
    // EFFECTS: add an alert to the alert list
    private void addAlert() {
        System.out.println("ENTER ALERT NAME");
        String alertName = input.nextLine().toUpperCase();
        boolean alertDoesntExist = true;

        for (Alert a : myAccount.getAlerts().getList()) {
            if (a.getDueName().equals(alertName)) {
                alertDoesntExist = false;
                System.out.println("ALERT NAME ALREADY EXISTS");
            }
        }
        if (alertDoesntExist) {
            try {
                System.out.println("ENTER DATE AS yyyy-MM-dd (space) HH:mm");
                String dueDate = input.nextLine();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime dueTime = LocalDateTime.parse(dueDate, dateFormat);

                System.out.println("HOW MANY TIMES DO YOU WANT IT TO REPEAT?");
                String repeat = input.nextLine();

                Alert theOneAdded = new Alert(dueTime, alertName, Integer.parseInt(repeat));
                myAccount.getAlerts().addAlert(theOneAdded);

            } catch (Exception ee) {
                System.out.println("INVALID INPUT");
            }

        }
    }


    // EFFECTS: displays menu of options to user
    private void displayFunctions() {
        System.out.println("A: ADD AN ALERT");
        System.out.println("B: DELETE AN ALERT");
        System.out.println("C: VIEW ALL ALERTS");
        System.out.println("D: VIEW ALERTS OF NEXT _ DAYS");
        System.out.println("E: VIEW NOTIFICATIONS");
        System.out.println("F: VIEW ON THE DAY");
        System.out.println("G: CONFIRM NOTIFICATION");
        System.out.println("H: ACCOUNT INFORMATION");
        System.out.println("I: VIEW ALERT DETAILS");
        System.out.println("J: EDIT ALERT DETAILS");
        System.out.println("K: SAVE YOUR ACCOUNT");
        System.out.println("L: LOAD YOUR ACCOUNT");
        System.out.println("Q: QUIT");
    }
}
