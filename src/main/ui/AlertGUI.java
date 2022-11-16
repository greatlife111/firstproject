package ui;


import javax.swing.*;
import java.awt.*;


public class AlertGUI extends JFrame {

    public AlertGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(setMainPanel());
        this.pack();
    }

    public JPanel setMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0,0));
        mainPanel.add(setTabs(), BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.setSize(500, 500);
        mainPanel.setVisible(true);

        return mainPanel;
    }

    public JTabbedPane setTabs() {
        JTabbedPane tabs = new JTabbedPane();

        JComponent alertList = makeTestPanel("hi");
        tabs.addTab("Alert List", alertList);

        JComponent notifications = makeTestPanel("hello");
        tabs.addTab("Notifications",notifications);

        JComponent others = makeCommands();
        tabs.addTab("Others", others);

        return tabs;
    }

    private JComponent makeTestPanel(String s) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(s);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    private JComponent makeCommands() {
        JPanel commands = new JPanel();
        commands.setLayout(new CardLayout());

        String[] comboBoxItems = { "CHOOSE OPTION", "Today", "Next __ Days", "Enter Date" };
        JComboBox<String> view = new JComboBox<>(comboBoxItems);
        view.setEditable(false);

        JButton editAccount = new JButton();

        commands.add(view, BorderLayout.CENTER);
        commands.add(editAccount, BorderLayout.PAGE_END);

        return commands;
    }

    public static void main(String[] args) {
        JFrame frame = new ui.AlertGUI("My Alert List");
        frame.setVisible(true);
    }
}
