package ui;

import model.exceptions.LogException;

import javax.swing.*;

public class MainGUI {
    public static void main(String[] args) throws LogException {
        JFrame frame = new ui.AlertGUI("My Alert List");
        frame.setVisible(true);

    }
}
