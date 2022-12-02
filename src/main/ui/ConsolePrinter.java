package ui;

import model.Event;
import model.EventLog;

public class ConsolePrinter  {


    public void printLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e.toString() + "\n\n");
        }
    }
}
