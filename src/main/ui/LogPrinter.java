package ui;

import model.EventLog;
import model.exceptions.LogException;

public interface LogPrinter {

    void printLog(EventLog el) throws LogException;
}
