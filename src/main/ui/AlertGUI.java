package ui;

import model.Account;
import model.AlertList;
import persistance.JsonReader;
import persistance.JsonWriter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

public class AlertGUI extends JFrame {
    private static final String JSON_STORE = "./data/MyAlertList.json";
    public static final int WIDTH = 900;
    public static final int HEIGHT = 900;

    private Account myAccount;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    JTextArea allAlerts;
    JTextArea allNotifications;
    JTextArea accountInfo;

    JButton editName;
    JButton deleteAlert;
    JButton addAlert;
    JButton viewAlert;
    JButton viewToday;
    JButton viewNextdays;
    JButton viewOntheDate;
    JButton confirmNotification;

    public AlertGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(initializeGraphics());
        this.pack();

        initializeFields();
        startLoadPrompt();
        saveLoadPrompt();

    }

    private void initializeFields() {
        myAccount = new Account(5628, "EnterYourName", new AlertList());
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    private void saveLoadPrompt() {
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
                        System.out.println("Unable to write to file: " + JSON_STORE);
                    }
                    dispose();
                }
            }
        });
    }

    private void startLoadPrompt() {
        int load = JOptionPane.showConfirmDialog(null,
                "Would you like to load your account?", "Load",
                JOptionPane.YES_NO_OPTION);
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

        allNotifications = new JTextArea();
        allNotifications.setEditable(false);

        JPanel notificationPanel = new JPanel(false);
        JPanel commandsPanel = new JPanel();

        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.PAGE_AXIS));

        commandsPanel.setLayout(new FlowLayout());
        commandsPanel.add(confirmNotification);

        notificationPanel.add(commandsPanel);
        notificationPanel.add(allNotifications);

        return notificationPanel;
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

        allAlerts = new JTextArea();
        allAlerts.setEditable(false);
        JPanel alertPanel = new JPanel(false);
        JPanel commandsPanel = new JPanel();

        alertPanel.setLayout(new BoxLayout(alertPanel, BoxLayout.PAGE_AXIS));

        commandsPanel.setLayout(new FlowLayout());
        commandsPanel.add(addAlert);
        commandsPanel.add(deleteAlert);
        commandsPanel.add(viewAlert);

        alertPanel.add(commandsPanel);
        alertPanel.add(allAlerts);

        return alertPanel;
    }

//    private String readAlertsAsString() throws Exception {
//        String s = "./data/MyAlertList.json";
//        String json = readFileAsString(s);
//
//        JSONObject j = new JSONObject(json);
//        JSONObject alertlist = j.getJSONObject("alertlist");
//
//        JSONArray list = alertlist.getJSONArray("list");
//        int length = list.length();
//
//        ArrayList<String> individualAlerts = new ArrayList<>();
//        for (int i = 0; i < length; i++) {
//            JSONObject o = list.getJSONObject(i);
//            String lineWithBreak = o.getString("date") + "\r";
//            individualAlerts.add(lineWithBreak);
//        }
//
//        return individualAlerts.toString();
//    }
//
//    private String readFileAsString(String s) throws IOException {
//        return new String(Files.readAllBytes(Paths.get(s)));
//    }


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

//        JButton[] comboBoxItems = {new JButton("CHOOSE OPTION TO VIEW"), viewToday, viewNextdays, viewOntheDate};
//        JComboBox<JButton> view = new JComboBox<>(comboBoxItems);
//
//        JButton editAccount = new JButton();
//
//        commands.add(view, BorderLayout.CENTER);
//        commands.add(editAccount, BorderLayout.PAGE_END);

        return panel;
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

    private void editAccountNameAction() {
    }

    private void deleteAlertAction() {
    }

    private void addAlertAction() {
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
