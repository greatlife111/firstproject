package ui;

import model.Account;
import model.Alert;
import model.AlertList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AlertApp {
    private final Scanner input;
    private Account myAccount;

    public AlertApp() {
        input = new Scanner(System.in);
        runAlertApp();
    }

    public void runAlertApp() {
        boolean keepGoing = true;
        String command = null;

        System.out.println("Enter your name:");
        String name = input.nextLine();


        myAccount = new Account(5211314, name, new AlertList());

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

    private void processCommand(String command) {
        if (command.equals("A")) {
            addAlert();
        } else if (command.equals("B")) {
            deleteAlert();
        } else if (command.equals("C")) {
            viewAllAlerts();
        } else if (command.equals("D")) {
            viewNextDays();
        } else if (command.equals("E")) {
            viewNotifications();
        } else if (command.equals("F")) {
            viewOnTheDay();
        } else if (command.equals("G")) {
            confirmNotification();
        } else if (command.equals("H")) {
            accountInformation();
        } else if (command.equals("I")) {
            alertDetails();
        } else if (command.equals("J")) {
            editAlertDetails();
        } else {
            System.out.println("SELECTION INVALID");
        }
    }


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

    private void editAlertDetails() {
        System.out.println("TYPE THE ALERT NAME YOU WOULD LIKE TO CHANGE");
        String name = input.nextLine().toUpperCase();
        boolean alertDoesNotExist = true;
        for (Alert alert : myAccount.getAlerts().getList()) {
            if (alert.getDueName().equals(name)) {
                alertDoesNotExist = false;
                System.out.println("TYPE N TO CHANGE NAME, D TO CHANGE DATE, R TO CHANGE REPEAT");
                String answer = input.nextLine().toUpperCase();

                if (answer.equals("CHANGENAME")) {
                    changeAlertName(alert);
                }
                if (answer.equals("CHANGEDATE")) {
                    changeAlertDueDate(alert);
                }
                if (answer.equals("CHANGEREPEAT")) {
                    changeRepeat(alert);
                }
            }
        }
        if (alertDoesNotExist) {
            System.out.println("ALERT DOES NOT EXIST");
        }
    }

    private void changeAlertName(Alert alert) {
        System.out.println("WHAT IS THE NEW ALERT NAME?");
        String newName = input.nextLine().toUpperCase();
        alert.changeDue(newName);
    }

    private void changeAlertDueDate(Alert alert) {
        System.out.println("WHAT IS THE NEW DATE?");
        System.out.println("ANSWER IN yyyy-MM-dd (space) HH:mm");
        String newDate = input.nextLine();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime day = LocalDateTime.parse(newDate, dateFormat);
        alert.changeDate(day);
    }

    private void changeRepeat(Alert alert) {
        System.out.println("HOW MANY TIMES WOULD THE ALERT REPEAT?");
        String repeat = input.nextLine();
        alert.changeRepeat(Integer.parseInt(repeat));
    }


    private void accountInformation() {
        System.out.println("Account ID:" + myAccount.getId());
        System.out.println("Name:" + myAccount.getName());
        System.out.println("Alerts:" + myAccount.getAlerts());
        editAccountInformation();
    }

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


    private void viewOnTheDay() {
        System.out.println("ENTER THE DATE IN THE FORMAT yyyy-MM-dd");
        String date = input.nextLine();
        date += " 00:00";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime theDay = LocalDateTime.parse(date, dateFormat);

        for (Alert a : myAccount.getAlerts().viewAlertsOnTheDay(theDay)) {
            System.out.println("Alert name:" + a.getDueName());
            System.out.println("Due time:" + a.getFutureDate());
            System.out.println("Repeat:" + a.getRepeat());
        }
    }


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

    private void viewNextDays() {
        System.out.println("HOW MANY DAYS?");
        String day = input.nextLine();

        if (myAccount.getAlerts().isEmpty()) {
            System.out.println("NOTHING FOR NOW!");
        } else {
            for (Alert a : myAccount.getAlerts().viewAlertNextDays(Integer.parseInt(day))) {
                System.out.println("Alert name:" + a.getDueName());
                System.out.println("Due time:" + a.getFutureDate());
                System.out.println("Repeat:" + a.getRepeat());
            }
        }

    }

    private void viewAllAlerts() {
        if (myAccount.getAlerts().isEmpty()) {
            System.out.println("NOTHING FOR NOW!");
        } else {
            for (Alert a : myAccount.getAlerts().getList()) {
                System.out.println("Alert name:" + a.getDueName());
                System.out.println("Due time:" + a.getFutureDate());
                System.out.println("Repeat:" + a.getRepeat());
            }
        }
    }

    private void deleteAlert() {
        System.out.println("ENTER ALERT NAME");
        String alertName = input.nextLine().toUpperCase();
        myAccount.getAlerts().removeAlert(alertName);
    }

    private void addAlert() {
        System.out.println("ENTER ALERT NAME");
        String alertName = input.nextLine().toUpperCase();

        System.out.println("ENTER DATE AS yyyy-MM-dd (space) HH:mm");
        String dueDate = input.nextLine();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dueTime = LocalDateTime.parse(dueDate, dateFormat);

        System.out.println("HOW MANY TIMES DO YOU WANT IT TO REPEAT? MINIMUM IS 1");
        String repeat = input.nextLine();

        Alert theOneAdded = new Alert(dueTime, alertName, Integer.parseInt(repeat));
        myAccount.getAlerts().addAlert(theOneAdded);

    }

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
        System.out.println("Q: QUIT");
    }
}
