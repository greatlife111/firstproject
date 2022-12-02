package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEventLog {

    Alert a1;
    Alert a2;
    AlertList alertList;
    EventLog eventLog;

    @BeforeEach
    void setup() {
        eventLog = EventLog.getInstance();
        a1 = new Alert(LocalDateTime.of(2022, 02, 03, 11, 00), "phase3", 1);
        a2 = new Alert(LocalDateTime.of(2022, 03, 03, 11, 00), "phase4", 1);
        alertList = new AlertList();
        alertList.addAlert(a1);
    }

    @Test
    void testAddCommand() {
        eventLog.logEvent(new Event(""));
    }
}
