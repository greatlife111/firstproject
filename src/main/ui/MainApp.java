package ui;

import java.io.FileNotFoundException;

// starts the console-user interaction
public class MainApp {
    public static void main(String[] args) {
        try {
            new AlertApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
