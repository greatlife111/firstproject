package ui;

import model.Account;
import model.Alert;
import model.AlertList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AlertApp {
    private Account myAccount;
    private final Scanner input;

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
        } else {
            System.out.println("SELECTION INVALID");
        }
    }

    private void alertDetails() {
    }

    private void accountInformation() {

    }

    private void confirmNotification() {

    }

    private void viewOnTheDay() {

    }

    private void viewNotifications() {

    }

    private void viewNextDays() {

    }

    private void viewAllAlerts() {
        if (myAccount.getAlerts().isEmpty()) {
            System.out.println("NOTHING FOR NOW!");
        } else {
            System.out.println(myAccount.getAlerts());
        }
    }

    private void deleteAlert() {
        System.out.println("ENTER ALERT NAME");
        String alertName = input.nextLine();
        myAccount.getAlerts().removeAlert(alertName);
    }

    private void addAlert() {
        System.out.println("ENTER ALERT NAME");
        String alertName = input.nextLine();

        System.out.println("ENTER DATE AS yyyy-MM-dd (space) HH:mm");
        String dueDate = input.nextLine();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dueTime = LocalDateTime.parse(dueDate, dateFormat);

        System.out.println("HOW MANY TIMES DO YOU WANT IT TO REPEAT?");
        int repeat = input.nextInt();

        Alert theOneAdded = new Alert(dueTime, alertName, repeat);
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
        System.out.println("I: VIEW ALERT DETAIL");
        System.out.println("Q: QUIT");
    }
}
