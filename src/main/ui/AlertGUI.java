package ui;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class AlertGUI extends JFrame {

    public AlertGUI(String title) throws Exception {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(setMainPanel());
        this.pack();
    }

    public JPanel setMainPanel() throws Exception {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.add(setTabs(), BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.setSize(500, 500);
        mainPanel.setVisible(true);

        return mainPanel;
    }

    public JTabbedPane setTabs() throws Exception {
        JTabbedPane tabs = new JTabbedPane();

        JComponent alertList = makePanel(readAlertsAsString());
        tabs.addTab("Alert List", alertList);

        JComponent notifications = makePanel((readNotificationsAsString()));
        tabs.addTab("Notifications", notifications);

        JComponent others = makeCommands();
        tabs.addTab("Others", others);

        return tabs;
    }


    private JComponent makePanel(String s) {
        JPanel panel = new JPanel(false);
        JTextField filler = new JTextField(s);
        filler.setHorizontalAlignment(JTextField.LEFT);
        filler.setEditable(false);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    private String readAlertsAsString() throws Exception {
        String s = "./data/MyAlertList.json";
        String json = readFileAsString(s);

        JSONObject j = new JSONObject(json);
        JSONObject alertlist = j.getJSONObject("alertlist");

        JSONArray list = alertlist.getJSONArray("list");
        int length = list.length();

        ArrayList<String> individualAlerts = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            JSONObject o = list.getJSONObject(i);
            String lineWithBreak = o.getString("date") + "\r";
            individualAlerts.add(lineWithBreak);
//            individualAlerts.add(System.lineSeparator());
//            individualAlerts.add(o.toString() + System.lineSeparator());

        }

        return individualAlerts.toString();
    }

    private String readFileAsString(String s) throws IOException {
        return new String(Files.readAllBytes(Paths.get(s)));
    }

    private String readNotificationsAsString() {
        return null;
    }

    private JComponent makeCommands() {
        JPanel commands = new JPanel();
        commands.setLayout(new CardLayout());

        String[] comboBoxItems = {"CHOOSE OPTION", "Today", "Next __ Days", "Enter Date"};
        JComboBox<String> view = new JComboBox<>(comboBoxItems);
        view.setEditable(false);

        JButton editAccount = new JButton();

        commands.add(view, BorderLayout.CENTER);
        commands.add(editAccount, BorderLayout.PAGE_END);

        return commands;
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new ui.AlertGUI("My Alert List");
        frame.setVisible(true);
    }
}
