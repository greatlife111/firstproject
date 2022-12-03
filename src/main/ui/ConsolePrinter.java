package ui;

import model.Event;
import model.EventLog;

public class ConsolePrinter  {

    // EFFECTS: prints user activity in the console after quitting the GUI
    public void printLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e.toString() + "\n\n");
        }
    }
}
