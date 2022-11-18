package ui;

import model.Account;
import model.Alert;
import model.AlertList;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AlertGUI extends JFrame implements ListSelectionListener {
    private static final String JSON_STORE = "./data/MyAlertList.json";
    public static final int WIDTH = 900;
    public static final int HEIGHT = 900;

    private Account myAccount;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private DefaultListModel<Alert> alertListModel;
    private DefaultListModel<String> notificationsListModel;

    // display area
    JList<Alert> alertJList;
    JList<String> notificationsJList;
    JScrollPane allAlerts;
    JScrollPane allNotifications;
    JTextArea accountInfo;

    // Buttons
    JButton editName;
    JButton deleteAlert;
    JButton addAlert;
    JButton viewAlert;
    JButton viewToday;
    JButton viewNextdays;
    JButton viewOntheDate;
    JButton confirmNotification;

    // for user input
    JTextField forName;
    JTextField forDate;
    JTextField forRepeat;
    JPanel panelForAddAlert;

    public AlertGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeFields();
        this.setContentPane(initializeGraphics());
        this.pack();


        startPrompt();
        savePrompt();
    }

    private void initializeFields() {
        myAccount = new Account(5628, "EnterYourName", new AlertList());
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        alertListModel = new DefaultListModel<>();
        notificationsListModel = new DefaultListModel<>();
    }

    private void savePrompt() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                int save = JOptionPane.showConfirmDialog(null,
                        "Would you like to save your account before exiting?", "Save", JOptionPane.YES_NO_OPTION);
                if (save == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(myAccount);
                        jsonWriter.close();
                    } catch (FileNotFoundException e) {
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }
                    dispose();
                }
            }
        });
    }

    private void startPrompt() {
        int load = JOptionPane.showConfirmDialog(null,
                "Would you like to load your account?", "Load", JOptionPane.YES_NO_OPTION);
        if (load == JOptionPane.YES_OPTION) {
            try {
                myAccount = jsonReader.read();
                loadAccount();
            } catch (Exception e) {
                System.out.println("Unable to read from file " + JSON_STORE);
            }
        }
    }

    private void loadAccount() {
        updateAlerts();
        updateNotifications();
    }

    public JPanel initializeGraphics() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(setTabs(), BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.setSize(WIDTH, HEIGHT);
        mainPanel.setVisible(true);
        return mainPanel;
    }

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

        return tabs;
    }

    private JComponent makeAccountPanel() {
        editName = new JButton("EDIT NAME");
        editName.setActionCommand("edit");
        editName.addActionListener(new ButtonListener());

        accountInfo = new JTextArea();
        accountInfo.setEditable(false);

        JPanel accountPanel = new JPanel(false);
        JPanel commandsPanel = new JPanel();

        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.PAGE_AXIS));

        commandsPanel.setLayout(new FlowLayout());
        commandsPanel.add(editName);

        accountPanel.add(accountInfo);
        accountPanel.add(commandsPanel);

        return accountPanel;
    }

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


    private JComponent makeCommandsPanel() {
        viewToday = new JButton("TODAY");
        viewToday.setActionCommand("today");
        viewToday.addActionListener(new ButtonListener());

        viewNextdays = new JButton("NEXT __ DAYS");
        viewNextdays.setActionCommand("next");
        viewNextdays.addActionListener(new ButtonListener());

        viewOntheDate = new JButton("ENTER A DATE");
        viewOntheDate.setActionCommand("date");
        viewOntheDate.addActionListener(new ButtonListener());

        JPanel commands = new JPanel();
        commands.setLayout(new FlowLayout());

        commands.add(viewToday);
        commands.add(viewNextdays);
        commands.add(viewOntheDate);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel choose = new JLabel("CHOOSE OPTION TO VIEW");

        panel.add(choose, BorderLayout.PAGE_START);
        panel.add(commands, BorderLayout.CENTER);

        return panel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }


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
            } else if ("today".equals(actionCommand)) {
                viewTodayAction();
            } else if ("next".equals(actionCommand)) {
                viewNextDaysAction();
            } else if ("date".equals(actionCommand)) {
                viewOnTheDayAction();
            } else if ("confirm".equals(actionCommand)) {
                confirmNotificationAction();
            }
        }
    }

    // EFFECTS: updates alert list in the alert list tab
    private void updateAlerts() {
        alertListModel.clear();
        List<Alert> alerts = myAccount.getAlerts().getList();
        for (Alert a : alerts) {
            alertListModel.addElement(a);
        }
    }

    private void updateNotifications() {
        notificationsListModel.clear();
        boolean thereIsNothing = true;
        for (Alert a : myAccount.getAlerts().getList()) {
            if (a.shouldBeNotified(LocalDateTime.now())) {
                thereIsNothing = false;
                notificationsListModel.addElement("Name:" + a.getDueName());
                notificationsListModel.addElement("Due:" + a.getFutureDate());
            }
        }
        if (thereIsNothing) {
            notificationsListModel.addElement("Nothing for now!");
        }
    }

    private void editAccountNameAction() {
    }

    private void deleteAlertAction() {
    }

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
        panelForAddAlert.add(new JLabel("Due Date: (yyyy-mm-dd HH-MM)"));
        panelForAddAlert.add(forDate);
        panelForAddAlert.add(new JLabel("Repeat"));
        panelForAddAlert.add(forRepeat);

        panelForAddAlert.setLayout(new BoxLayout(panelForAddAlert, BoxLayout.PAGE_AXIS));

        addSelectedAlertToList();
        updateAlertList();
    }

    private void updateAlertList() {
        alertListModel.clear();
        List<Alert> alerts = myAccount.getAlerts().getList();
        for (Alert a : alerts) {
            alertListModel.addElement(a);
        }
    }

    private void addSelectedAlertToList() {
        int selectedAlert = JOptionPane.showConfirmDialog(null, panelForAddAlert,
                "ENTER ALERT DETAILS", JOptionPane.OK_CANCEL_OPTION);

        if (selectedAlert == JOptionPane.YES_OPTION) {
            String name = forName.getText();
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

                } catch (Exception ee) {
                    displayInvalidInput();
                }
            }
        }
    }

    private void displayInvalidInput() {
        JOptionPane.showMessageDialog(null, "INVALID INPUT",
                "", JOptionPane.ERROR_MESSAGE);
    }

    private void displayAlertAlreadyExist() {
        JOptionPane.showMessageDialog(null, "Alert Already Exist",
                "", JOptionPane.ERROR_MESSAGE);
    }

    private void viewAlertAction() {
    }

    private void viewTodayAction() {
    }

    private void viewNextDaysAction() {
    }

    private void viewOnTheDayAction() {
    }

    private void confirmNotificationAction() {
    }

}



