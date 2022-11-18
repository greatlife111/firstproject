package ui;

import model.Alert;

import javax.swing.*;
import java.awt.*;

public class AlertRenderer extends DefaultListCellRenderer {

    // EFFECTS: configures alert rendering for JList object based on Alert class
    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Alert alert = (Alert) value;
        String name = alert.getDueName();
        String dueDate = alert.getFutureDate().toString();
        int repeat = alert.getRepeat();
        String alertText = "<html>Name: " + name + "<br/>Due: " + dueDate + "<br/>Repeat:" + repeat;
        setText(alertText);

        return this;
    }
}
