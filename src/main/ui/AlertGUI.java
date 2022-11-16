package ui;

import model.AlertList;

import javax.swing.*;
import java.awt.*;


public class AlertGUI extends JFrame {

    private AlertList alerts;

    public AlertGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(setMainPanel());
        this.pack();
    }

    public JPanel setMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.add(setTabs());
        mainPanel.setSize(500, 500);
        mainPanel.setVisible(true);
//        mainPanel.setLayout(new BorderLayout(0,0));
//
//        JButton account = new JButton("ACCOUNT");
//        mainPanel.add(account, BorderLayout.BEFORE_FIRST_LINE);
//
//        JButton quit = new JButton("QUIT");
//        mainPanel.add(quit, BorderLayout.AFTER_LAST_LINE);

        return mainPanel;
    }

    public JTabbedPane setTabs() {
        JTabbedPane tabs = new JTabbedPane();

        JComponent alertList = makeAlertlistPanel("my alerts");
        JComponent notifications = makeNotificationsPanel();
        JComponent others = makeCommands();
        tabs.addTab("Alert List", alertList);
        tabs.addTab("Notifications",notifications);
        tabs.addTab("Others", others);

        return tabs;
    }

    private JComponent makeCommands() {
        JPanel commands = new JPanel();
        commands.setLayout(new CardLayout());

        String[] comboBoxItems = { "Today", "Next __ Days", "Enter Date" };
        JComboBox view = new JComboBox(comboBoxItems);
        view.setEditable(false);

        JButton editAccount = new JButton();

        commands.add(view, BorderLayout.CENTER);
        commands.add(editAccount, BorderLayout.PAGE_END);

        return commands;
    }

    private JComponent makeNotificationsPanel() {
        JPanel notifications = new JPanel();
        return notifications;
    }

    private JComponent makeAlertlistPanel(AlertList alerts) {

        JPanel alertList = new JPanel(false);
        JPanel filler = new JPanel(text);
        alertList.setLayout(new GridLayout(1,1));
        alertList.add(filler);
        return alertList;
    }

    public static void main(String[] args) {
        JFrame frame = new ui.AlertGUI("My Alert List");
        frame.setVisible(true);
    }
}
