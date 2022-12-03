package model.exceptions;

public class LogException extends Exception {

    // EFFECTS: constructs an error message using the Exception constructor
    public LogException() {
        super("Error printing log");
    }

    // EFFECTS: constructs input msg as error message for the exception
    public LogException(String msg) {
        super(msg);
    }
}

