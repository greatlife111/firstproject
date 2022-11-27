package ui;

import model.Account;
import model.Alert;
import model.AlertList;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Represents the alert GUI
public class AlertGUI extends JFrame implements ListSelectionListener {
    private static final String JSON_STORE = "./data/MyAlertList.json";
    public static final int WIDTH = 900;
    public static final int HEIGHT = 900;

    private Account myAccount;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private ImageIcon image;

    private DefaultListModel<Alert> alertListModel;
    private DefaultListModel<String> notificationsListModel;


    // display area
    JList<Alert> alertJList;
    JList<String> notificationsJList;
    JScrollPane allAlerts;
    JScrollPane allNotifications;
    JTextField accountName;

    // Buttons
    JButton editName;
    JButton deleteAlert;
    JButton addAlert;
    JButton viewAlert;
    JButton viewBeforeDate;
    JButton viewNextdays;
    JButton viewOntheDate;
    JButton confirmNotification;

    // for user input
    JTextField forName;
    JTextField forDate;
    JTextField forRepeat;
    JPanel panelForAddAlert;

    // EFFECTS: sets up the app window
    public AlertGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeFields();
        this.pack();
        startPrompt();
        this.setContentPane(initializeGraphics());
        savePrompt();
    }

    // MODIFIES: this
    // EFFECTS: initializes the data fields
    private void initializeFields() {
        myAccount = new Account(5628, "No Name Yet", new AlertList());
        image = new ImageIcon("./data/yikes-emoji.png");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        alertListModel = new DefaultListModel<>();
        notificationsListModel = new DefaultListModel<>();
    }


    // MODIFIES: this
    // EFFECTS: prompts the user to save the account
    private void savePrompt() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                int save = JOptionPane.showConfirmDialog(null,
                        "Would you like to save your account before exiting?", "Save",
                        JOptionPane.YES_NO_OPTION);
                if (save == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(myAccount);
                        jsonWriter.close();
                    } catch (FileNotFoundException e) {
                        JOptionPane.showMessageDialog(null,
                                "Unable to write to" + JSON_STORE, "FILE NOT FOUND",
                                JOptionPane.YES_OPTION);
                    }
                    dispose();
                }
            }
        });
    }

    // EFFECTS: prompts the user to load saved data before the app starts
    private void startPrompt() {
        int load = JOptionPane.showConfirmDialog(null,
                "Would you like to load your account?", "Load", JOptionPane.YES_NO_OPTION);
        if (load == JOptionPane.YES_OPTION) {
            try {

                myAccount = jsonReader.read();
                loadAccount();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Unable to read file: " + JSON_STORE, "FILE NOT FOUND",
                        JOptionPane.YES_OPTION);
            }
        }
    }

    // EFFECTS: loads account with previously saved information
    private void loadAccount() {
        updateAlerts();
        updateNotifications();
    }

    // MODIFIES: this
    // EFFECTS: sets up the main content panel of the app
    public JPanel initializeGraphics() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(setTabs(), BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.setSize(WIDTH, HEIGHT);
        mainPanel.setVisible(true);
        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up tabs and their contents in the main content panel
    public JTabbedPane setTabs() {
        JTabbedPane tabs = new JTabbedPane();

        JComponent alertList = makeAlertPanel();
        tabs.addTab("Alert List", alertList);

        JComponent notifications = makeNotificationPanel();
        tabs.addTab("Notifications", notifications);

        JComponent others = makeCommandsPanel();
        tabs.addTab("Others", others);

        JComponent account = makeAccountPanel();
        tabs.addTab("Account", account);

        tabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (tabs.getSelectedIndex() == 1) {
                    updateNotifications();
                }
                System.out.println("Tab: " + tabs.getSelectedIndex());
            }
        });

        return tabs;
    }

    // MODIFIES: this
    // EFFECTS: sets up the panel for account
    private JComponent makeAccountPanel() {
        editName = new JButton("EDIT NAME");
        editName.setActionCommand("edit");
        editName.addActionListener(new ButtonListener());

        accountName = new JTextField();
        accountName.setEditable(false);
        accountName.setText("NAME:" + myAccount.getName());

        JPanel accountPanel = new JPanel(false);
        JPanel commandsPanel = new JPanel();

        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.PAGE_AXIS));

        commandsPanel.setLayout(new FlowLayout());
        commandsPanel.add(editName);

        accountPanel.add(accountName);
        accountPanel.add(commandsPanel);

        return accountPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up the notifications panel
    private JComponent makeNotificationPanel() {
        confirmNotification = new JButton("CONFIRM NOTIFICATION");
        confirmNotification.setActionCommand("confirm");
        confirmNotification.addActionListener(new ButtonListener());

        JPanel notificationPanel = new JPanel(false);
        JPanel commandsPanel = new JPanel();

        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.PAGE_AXIS));

        commandsPanel.setLayout(new FlowLayout());
        commandsPanel.add(confirmNotification);

        notificationPanel.add(commandsPanel);
        notificationPanel.add(notificationsPane());

        return notificationPanel;
    }

    // MODIFIES: this
    // EFFECTS: displays notifications
    private JScrollPane notificationsPane() {
        notificationsJList = new JList<>(notificationsListModel);
        allNotifications = new JScrollPane();

        notificationsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notificationsJList.setSelectedIndex(0);
        notificationsJList.addListSelectionListener(this);
        notificationsJList.setVisibleRowCount(10);


        JScrollPane notificationsScrollPane = new JScrollPane(notificationsJList);
        notificationsScrollPane.createVerticalScrollBar();
        notificationsScrollPane.setHorizontalScrollBar(null);

        return notificationsScrollPane;
    }

    // MODIFIES: this
    // EFFECTS: sets up the alert list panel
    private JComponent makeAlertPanel() {
        deleteAlert = new JButton("DELETE AN ALERT");
        deleteAlert.setActionCommand("delete");
        deleteAlert.addActionListener(new ButtonListener());

        addAlert = new JButton("ADD AN ALERT");
        addAlert.setActionCommand("add");
        addAlert.addActionListener(new ButtonListener());

        viewAlert = new JButton("VIEW AN ALERT");
        viewAlert.setActionCommand("view");
        viewAlert.addActionListener(new ButtonListener());

        JPanel alertPanel = new JPanel(false);
        JPanel commandsPanel = new JPanel();

        alertPanel.setLayout(new BoxLayout(alertPanel, BoxLayout.PAGE_AXIS));

        commandsPanel.setLayout(new FlowLayout());
        commandsPanel.add(addAlert);
        commandsPanel.add(deleteAlert);
        commandsPanel.add(viewAlert);

        alertPanel.add(commandsPanel);
        alertPanel.add(alertsPane());

        return alertPanel;
    }

    // MODIFIES: this
    // EFFECTS: displays list of added alerts
    private JScrollPane alertsPane() {
        alertJList = new JList<>(alertListModel);
        allAlerts = new JScrollPane();

        alertJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alertJList.setSelectedIndex(0);
        alertJList.addListSelectionListener(this);
        alertJList.setVisibleRowCount(10);
        alertJList.setCellRenderer(new AlertRenderer());

        JScrollPane alertScrollPane = new JScrollPane(alertJList);
        alertScrollPane.createVerticalScrollBar();
        alertScrollPane.setHorizontalScrollBar(null);

        return alertScrollPane;
    }


    // MODIFIES: this
    // EFFECTS: sets up a panel for viewing alerts before a date, in the next __ days, and on a specific date
    private JComponent makeCommandsPanel() {
        viewBeforeDate = new JButton("VIEW BEFORE (DATE)");
        viewBeforeDate.setActionCommand("beforedate");
        viewBeforeDate.addActionListener(new ButtonListener());

        viewNextdays = new JButton("NEXT __ DAYS");
        viewNextdays.setActionCommand("next");
        viewNextdays.addActionListener(new ButtonListener());

        viewOntheDate = new JButton("ENTER A DATE");
        viewOntheDate.setActionCommand("date");
        viewOntheDate.addActionListener(new ButtonListener());

        JPanel commands = new JPanel();
        commands.setLayout(new FlowLayout());

        commands.add(viewBeforeDate);
        commands.add(viewNextdays);
        commands.add(viewOntheDate);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel choose = new JLabel("CHOOSE OPTION TO VIEW");

        panel.add(choose, BorderLayout.PAGE_START);
        panel.add(commands, BorderLayout.CENTER);

        return panel;
    }

    // No implementations for this method as not neccessary.
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }


    // CITATION: https://docs.oracle.com/javase/tutorial/uiswing/events/actionlistener.html
    // creates Action Listener for button presses
    class ButtonListener implements ActionListener {

        // EFFECTS: processes button clicks and runs appropriate methods
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            if ("edit".equals(actionCommand)) {
                editAccountNameAction();
            } else if ("delete".equals(actionCommand)) {
                deleteAlertAction();
            } else if ("add".equals(actionCommand)) {
                addAlertAction();
            } else if ("view".equals(actionCommand)) {
                viewAlertAction();
            } else if ("beforedate".equals(actionCommand)) {
                viewBeforeDateAction();
            } else if ("next".equals(actionCommand)) {
                viewNextDaysAction();
            } else if ("date".equals(actionCommand)) {
                viewOnTheDayAction();
            } else if ("confirm".equals(actionCommand)) {
                confirmNotificationAction();
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: updates alert list in the alert list tab
    private void updateAlerts() {
        alertListModel.clear();
        List<Alert> alerts = myAccount.getAlerts().getList();
        for (Alert a : alerts) {
            alertListModel.addElement(a);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates notifications in the notifications tab
    private void updateNotifications() {
        notificationsListModel.clear();
        boolean thereIsNothing = true;
        for (Alert a : myAccount.getAlerts().getList()) {
            if (!(null == a.getNotifications()) && a.getNotifications().size() > 0) {
                if (a.getNotifications().get(0).isBefore(LocalDateTime.now())) {
                    thereIsNothing = false;
                    notificationsListModel.addElement("<html>Name:" + a.getDueName() + "<br/>Due:" + a.getFutureDate());
                }
            }
        }

        if (thereIsNothing) {
            notificationsListModel.addElement("Nothing for now!");
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the account name to user input
    private void editAccountNameAction() {
        JTextField newName = new JTextField();
        newName.setEditable(true);

        JPanel panelForNewName = new JPanel();
        panelForNewName.add(new JLabel("NEW ACCOUNT NAME:"));
        panelForNewName.add(newName);

        panelForNewName.setLayout(new BoxLayout(panelForNewName, BoxLayout.PAGE_AXIS));
        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForNewName,
                "EDIT", JOptionPane.OK_CANCEL_OPTION);

        if (selectedAlert == JOptionPane.YES_OPTION) {
            String name = newName.getText();
            myAccount.changeName(name);
            this.accountName.setText("Name: " + name);
        }
    }


    // MODIFIES: this
    // EFFECTS: deletes the alert if it exists in the alert list
    private void deleteAlertAction() {
        JTextField nameForDelete = new JTextField();
        nameForDelete.setEditable(true);

        JPanel panelForDeleteAlert = new JPanel();
        panelForDeleteAlert.add(new JLabel("ALERT NAME TO DELETE:"));
        panelForDeleteAlert.add(nameForDelete);

        panelForDeleteAlert.setLayout(new BoxLayout(panelForDeleteAlert, BoxLayout.PAGE_AXIS));

        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForDeleteAlert,
                "", JOptionPane.OK_CANCEL_OPTION);

        boolean alertDoesntExist = true;
        if (selectedAlert == JOptionPane.YES_OPTION) {
            String name = nameForDelete.getText().toUpperCase();

            for (Alert a : myAccount.getAlerts().getList()) {
                if (a.getDueName().equals(name)) {
                    alertDoesntExist = false;
                    myAccount.getAlerts().removeAlert(name);
                    updateAlerts();
                    updateNotifications();
                }
            }
            if (alertDoesntExist) {
                displayAlertDoesNotExist();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an alert if it does not exist in the alert list and updates the list in the panel
    private void addAlertAction() {
        forName = new JTextField();
        forName.setEditable(true);
        forDate = new JTextField();
        forDate.setEditable(true);
        forRepeat = new JTextField();
        forRepeat.setEditable(true);

        panelForAddAlert = new JPanel();
        panelForAddAlert.add(new JLabel("Alert name:"));
        panelForAddAlert.add(forName);
        panelForAddAlert.add(new JLabel("Due Date: (yyyy-mm-dd HH:MM)"));
        panelForAddAlert.add(forDate);
        panelForAddAlert.add(new JLabel("Repeat"));
        panelForAddAlert.add(forRepeat);

        panelForAddAlert.setLayout(new BoxLayout(panelForAddAlert, BoxLayout.PAGE_AXIS));

        addSelectedAlertToList();
        updateAlerts();
        updateNotifications();
    }


    // MODIFIES: this
    // EFFECTS: helper method for addAlertAction(); adds the alert to the alert list if not already exists
    private void addSelectedAlertToList() {
        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForAddAlert,
                "ENTER ALERT DETAILS", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, image);

        if (selectedAlert == JOptionPane.YES_OPTION) {
            String name = forName.getText().toUpperCase();
            String date = forDate.getText();
            String repeat = forRepeat.getText();

            boolean alertDoesntExist = true;

            for (Alert a : myAccount.getAlerts().getList()) {
                if (a.getDueName().equals(name)) {
                    alertDoesntExist = false;
                    displayAlertAlreadyExist();
                }
            }

            if (alertDoesntExist) {
                try {
                    LocalDateTime dueTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    Alert theOneAdded = new Alert(dueTime, name, Integer.parseInt(repeat));
                    myAccount.getAlerts().addAlert(theOneAdded);

                } catch (NumberFormatException ee) {
                    displayInvalidInput();
                }
            }
        }
    }

    // EFFECTS: displays that the user input is invalid
    private void displayInvalidInput() {
        JOptionPane.showMessageDialog(null, "INVALID INPUT",
                "", JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: displays that the alert already exists
    private void displayAlertAlreadyExist() {
        JOptionPane.showMessageDialog(null, "Alert Already Exist",
                "", JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: displays the alert does not exist
    private void displayAlertDoesNotExist() {
        JOptionPane.showMessageDialog(null, "Alert Does Not Exist",
                "", JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: displays the alert details of a selected alert
    private void viewAlertAction() {
        JTextField nameToView = new JTextField();
        nameToView.setEditable(true);

        JPanel panelForViewAlert = new JPanel();
        panelForViewAlert.add(new JLabel("ALERT NAME TO VIEW:"));
        panelForViewAlert.add(nameToView);

        panelForViewAlert.setLayout(new BoxLayout(panelForViewAlert, BoxLayout.PAGE_AXIS));

        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForViewAlert,
                "", JOptionPane.OK_CANCEL_OPTION);

        boolean alertDoesntExist = true;
        if (selectedAlert == JOptionPane.YES_OPTION) {
            String name = nameToView.getText().toUpperCase();

            for (Alert a : myAccount.getAlerts().getList()) {
                if (a.getDueName().equals(name)) {
                    alertDoesntExist = false;
                    displayAlert(a);
                }
            }
            if (alertDoesntExist) {
                displayAlertDoesNotExist();
            }
        }
        updateAlerts();
    }

    // EFFECTS: helped method for viewAlertAction(); displays alert details in a dialog
    private void displayAlert(Alert a) {
        JLabel name = new JLabel("Name: " + a.getDueName());
        JLabel date = new JLabel("Due Date: " + a.getFutureDate().toString());
        JLabel repeat = new JLabel("Repeat: " + a.getRepeat());

        JPanel panelToDisplayAlert = new JPanel();
        panelToDisplayAlert.setLayout(new BoxLayout(panelToDisplayAlert, BoxLayout.PAGE_AXIS));

        panelToDisplayAlert.add(name);
        panelToDisplayAlert.add(date);
        panelToDisplayAlert.add(repeat);

        JOptionPane.showConfirmDialog(null, panelToDisplayAlert, "ALERT DETAILS", JOptionPane.OK_CANCEL_OPTION);
    }

    // EFFECTS: displays all alerts before a chosen date
    private void viewBeforeDateAction() {
        List<Alert> displayAlerts = new ArrayList<>();
        JTextField beforeDate = new JTextField();
        beforeDate.setEditable(true);

        JPanel panelForBeforeDate = new JPanel();
        panelForBeforeDate.add(new JLabel("TYPE DATE (yyyy-MM-dd) TO VIEW ALL ALERTS BEFORE:"));
        panelForBeforeDate.add(beforeDate);

        panelForBeforeDate.setLayout(new BoxLayout(panelForBeforeDate, BoxLayout.PAGE_AXIS));

        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForBeforeDate,
                "", JOptionPane.OK_CANCEL_OPTION);

        try {
            if (selectedAlert == JOptionPane.YES_OPTION) {
                String dateText = beforeDate.getText() + " 00:00";
                LocalDateTime dateTime = LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                for (Alert a : myAccount.getAlerts().getList()) {
                    if (a.getFutureDate().isBefore(dateTime) || a.getFutureDate().isEqual(dateTime)) {
                        displayAlerts.add(a);
                    }
                }
                displayMultipleAlerts(displayAlerts);
            }
        } catch (DateTimeException ee) {
            displayInvalidInput();
        }
    }

    // EFFECTS: helper method for viewBeforeDateAction(); displays all the alerts before chosen date in a dialog
    private void displayMultipleAlerts(List<Alert> displayAlerts) {
        JPanel panelToDisplayMultipleAlerts = new JPanel();
        panelToDisplayMultipleAlerts.setLayout(new BoxLayout(panelToDisplayMultipleAlerts, BoxLayout.PAGE_AXIS));
        for (Alert a : displayAlerts) {
            JLabel name = new JLabel("Name: " + a.getDueName());
            JLabel date = new JLabel("Due Date: " + a.getFutureDate().toString());
            JLabel repeat = new JLabel("Repeat: " + a.getRepeat());


            panelToDisplayMultipleAlerts.add(name);
            panelToDisplayMultipleAlerts.add(date);
            panelToDisplayMultipleAlerts.add(repeat);
        }
        JOptionPane.showConfirmDialog(null, panelToDisplayMultipleAlerts, "ALERT DETAILS",
                JOptionPane.OK_CANCEL_OPTION);
    }

    // EFFECTS: displays all alerts in the next ___ days
    private void viewNextDaysAction() {
        List<Alert> displayAlerts = new ArrayList<>();
        JTextField nextDays = new JTextField();
        nextDays.setEditable(true);

        JPanel panelForNextDays = new JPanel();
        panelForNextDays.add(new JLabel("TYPE HOW MANY DAYS:"));
        panelForNextDays.add(nextDays);

        panelForNextDays.setLayout(new BoxLayout(panelForNextDays, BoxLayout.PAGE_AXIS));

        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForNextDays,
                "", JOptionPane.OK_CANCEL_OPTION);

        if (selectedAlert == JOptionPane.YES_OPTION) {
            try {
                displayAlerts.addAll(myAccount.getAlerts().viewAlertNextDays(Integer.parseInt(nextDays.getText())));
                displayMultipleAlerts(displayAlerts);
            } catch (NumberFormatException ee) {
                displayInvalidInput();
            }
        }
    }


    // EFFECTS: displays all alerts on a chosen date
    private void viewOnTheDayAction() {
        JTextField onTheDay = new JTextField();
        onTheDay.setEditable(true);

        JPanel panelForOnTheDay = new JPanel();
        panelForOnTheDay.add(new JLabel("TYPE THE DATE:(yyyy-MM-dd)"));
        panelForOnTheDay.add(onTheDay);

        panelForOnTheDay.setLayout(new BoxLayout(panelForOnTheDay, BoxLayout.PAGE_AXIS));

        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForOnTheDay,
                "", JOptionPane.OK_CANCEL_OPTION);

        String newDate = onTheDay.getText() + " 00:00";
        LocalDateTime d = LocalDateTime.parse(newDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if (selectedAlert == JOptionPane.YES_OPTION) {
            try {
                List<Alert> displayAlerts = new ArrayList<>(myAccount.getAlerts().viewAlertsOnTheDay(d));
                displayMultipleAlerts(displayAlerts);
            } catch (NumberFormatException ee) {
                displayInvalidInput();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: confirms a notification in the notification list
    private void confirmNotificationAction() {
        JTextField confirmAlert = new JTextField();
        confirmAlert.setEditable(true);

        JPanel panelForConfirm = new JPanel();
        panelForConfirm.add(new JLabel("TYPE THE ALERT NAME YOU WOULD LIKE TO CONFIRM:"));
        panelForConfirm.add(confirmAlert);

        panelForConfirm.setLayout(new BoxLayout(panelForConfirm, BoxLayout.PAGE_AXIS));

        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForConfirm,
                "", JOptionPane.OK_CANCEL_OPTION);

        if (selectedAlert == JOptionPane.YES_OPTION) {
            try {
                for (Alert a : myAccount.getAlerts().getList()) {
                    if (a.getDueName().equals(confirmAlert.getText().toUpperCase())) {
                        a.confirmNotification(LocalDateTime.now());
                        updateNotifications();
                    }
                }
            } catch (Exception ee) {
                displayInvalidInput();
            }
        }
    }

}



