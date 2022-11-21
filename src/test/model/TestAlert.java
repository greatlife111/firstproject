package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class TestAlert {

    Alert alertModel;
    Alert alertModel2;
    LocalDateTime futureAlertDate;
    LocalDateTime createdDate;
    LocalDateTime dateModel2;


    @BeforeEach
    public void setup() {

        futureAlertDate = LocalDateTime.of(2023, 10, 19, 17, 7);
        dateModel2 = LocalDateTime.of(2024, 10, 20, 17, 7);
        createdDate = LocalDateTime.of(2022, 10, 20, 17, 7);
        alertModel = new Alert(createdDate, futureAlertDate, "phase1", 2);
        alertModel2 = new Alert(createdDate, futureAlertDate, "phase0", 0);

    }

    @Test
    void testConstructor() {
        try {
            Alert alert = new Alert(futureAlertDate, "phase 1", 3);
            assertEquals(futureAlertDate, alert.getFutureDate());
            assertEquals("phase 1", alert.getDueName());
            assertEquals(3, alert.getRepeat());
        } catch (NumberFormatException ee) {
            fail();
        }

        try {
            Alert alert = new Alert(futureAlertDate, "phase 1", -1);
            fail();
        } catch (NumberFormatException ee) {
            //
        }

    }


    @Test
    void testChangeDate() {
        alertModel.changeDate(dateModel2);
        assertEquals(dateModel2, alertModel.getFutureDate());
    }

    @Test
    void testChangeName() {
        try {
            alertModel.changeDue("phase2");
            assertEquals("phase2", alertModel.getDueName());
        } catch (RuntimeException ee) {
            fail("valid input");
        }

        try {
            alertModel.changeDue("");
            fail("invalid input");
        } catch (RuntimeException ee) {
            // pass
        }
    }

    @Test
    void testChangeRepeat() {
        try {
            alertModel.changeRepeat(3);
        } catch (NumberFormatException ee) {
            fail("valid repeat");
        }
        assertEquals(3, alertModel.getRepeat());

        try {
            alertModel.changeRepeat(-1);
            fail("invalid repeat");
        } catch (NumberFormatException ee) {
            // pass
        }
        assertEquals(3, alertModel.getRepeat());
    }

    @Test
    void testShouldBeNotifiedYes() {
        assertTrue(alertModel.shouldBeNotified(futureAlertDate));
        assertTrue(alertModel.shouldBeNotified(futureAlertDate.plusDays(1)));
    }

    @Test
    void testShouldBeNotifiedNo() {
        assertFalse(alertModel.shouldBeNotified(createdDate));
        assertFalse(alertModel.shouldBeNotified(createdDate.plusDays(1)));
    }

    @Test
    void testCheckNotification() {
        alertModel.confirmNotification(futureAlertDate);
        assertEquals(0, alertModel.getNotifications().size());
        assertFalse(alertModel.shouldBeNotified(futureAlertDate));
    }

    @Test
    void testCheckNotificationAfter() {
        alertModel.confirmNotification(futureAlertDate.plusDays(1));
        assertEquals(0, alertModel.getNotifications().size());
        assertFalse(alertModel.shouldBeNotified(futureAlertDate));
    }

    @Test
    void testCheckNotificationBefore() {
        alertModel.confirmNotification(futureAlertDate.minusDays(1));
        assertEquals(1, alertModel.getNotifications().size());
        assertTrue(alertModel.shouldBeNotified(futureAlertDate));
    }

    @Test
    void testCalculatingNotifications() {
        alertModel2.calculateNotifications(alertModel2.getCreatedDate(), alertModel2.getFutureDate(),
                alertModel2.getRepeat());
        assertEquals(0, alertModel2.getNotifications().size());

        long deltaTime = ChronoUnit.NANOS.between(alertModel.getCreatedDate(), futureAlertDate) / alertModel.getRepeat();

        LocalDateTime firstRepeat = (alertModel.getCreatedDate().plusNanos(deltaTime));
        assertEquals(firstRepeat, alertModel.getNotifications().get(0));
        assertEquals(futureAlertDate, alertModel.getNotifications().get(1));
    }
}