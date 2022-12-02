package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TestEventLog {

    Alert a1;
    Alert a2;
    AlertList alertList;


    @Test
    void testClear() {
        EventLog el = EventLog.getInstance();
        el.clear();
        Iterator<Event> iterator = el.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("Event log cleared.", iterator.next().getDescription());
        assertFalse(iterator.hasNext());
    }

    @Test
    void addCommands() {
        a1 = new Alert(LocalDateTime.of(2022, 11, 1, 11, 0),
                LocalDateTime.of(2022, 12, 1, 11, 0), "phase3", 1);
        a2 = new Alert(LocalDateTime.of(2022, 11, 1, 11, 0),
                LocalDateTime.of(2022, 12, 1, 11, 0), "phase4", 1);
        alertList = new AlertList();
        alertList.addAlert(a1);
        alertList.addAlert(a2);
        alertList.viewAlertsOnTheDay(a1.getFutureDate());
        alertList.viewAlertBeforeDate(a2.getFutureDate());
        alertList.viewAlertNextDays(3);
        alertList.getList().get(1).confirmNotification(LocalDateTime.now());
        alertList.removeAlert(a2.getDueName());


        EventLog el = EventLog.getInstance();
        Iterator<Event> iterator = el.iterator();

        assertEquals("phase3 Alert added", iterator.next().getDescription());
        assertEquals("phase4 Alert added", iterator.next().getDescription());
        assertEquals("Viewed alerts on " + a1.getFutureDate(), iterator.next().getDescription());
        assertEquals("Viewed alerts before " + a2.getFutureDate(), iterator.next().getDescription());
        assertEquals("Viewed alerts of next 3 days", iterator.next().getDescription());
        assertEquals("phase4 confirmed", iterator.next().getDescription());
        assertEquals("phase4 Alert deleted", iterator.next().getDescription());
        assertFalse(iterator.hasNext());

    }
}
