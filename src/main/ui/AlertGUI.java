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

        JButton account = new JButton("ACCOUNT");
        mainPanel.add(account, BorderLayout.BEFORE_FIRST_LINE);

        JButton quit = new JButton("QUIT");
        mainPanel.add(quit, BorderLayout.AFTER_LAST_LINE);

        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new ui.AlertGUI("My Alert List");
        frame.setVisible(true);
    }
}
