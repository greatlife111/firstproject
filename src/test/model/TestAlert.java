package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


import static org.junit.jupiter.api.Assertions.*;

class TestAlert {

    Alert alertModel;
    LocalDateTime futureAlertDate;
    LocalDateTime createdDate;
    LocalDateTime dateModel2;


    @BeforeEach
    public void setup() {

        futureAlertDate = LocalDateTime.of(2023, 10, 19, 17, 7);
        dateModel2 = LocalDateTime.of(2024, 10, 20, 17, 7);
        createdDate = LocalDateTime.of(2022, 10, 20, 17, 7);
        alertModel = new Alert(createdDate, futureAlertDate, "phase1", 2);

    }

    @Test
    void testConstructor(){
        Alert alert = new Alert(futureAlertDate, "phase 1", 3);
        assertEquals(futureAlertDate, alert.getFutureDate());
        assertEquals("phase 1", alert.getDueName());
        assertEquals(3, alert.getRepeat());
    }

    @Test
    void testChangeDate() {
        alertModel.changeDate(dateModel2);
        assertEquals(dateModel2, alertModel.getFutureDate());
    }

    @Test
    void testChangeName(){
        alertModel.changeDue("phase2");
        assertEquals("phase2", alertModel.getDueName());
    }

    @Test
    void testChangeRepeat(){
        alertModel.changeRepeat(3);
        assertEquals(3, alertModel.getRepeat());
    }

    @Test
    void testShouldBeNotifiedYes(){
        assertTrue(alertModel.shouldBeNotified(futureAlertDate));
        assertTrue(alertModel.shouldBeNotified(futureAlertDate.plusDays(1)));
    }

    @Test
    void testShouldBeNotifiedNo(){
        assertFalse(alertModel.shouldBeNotified(createdDate));

    }

    @Test
    void testCheckNotification() {
        alertModel.confirmNotification(futureAlertDate);
        assertEquals(0, alertModel.getNotifications().size());
        assertFalse(alertModel.shouldBeNotified(futureAlertDate));
    }

    @Test
    void testCheckNotificationBefore() {
        alertModel.confirmNotification(futureAlertDate.plusDays(1));
        assertEquals(0, alertModel.getNotifications().size());
        assertFalse(alertModel.shouldBeNotified(futureAlertDate));
    }

    @Test
    void testCalculatingNotifications(){
        long deltaTime = ChronoUnit.NANOS.between(alertModel.getCreatedDate(), futureAlertDate) / alertModel.getRepeat();

        LocalDateTime firstRepeat = (alertModel.getCreatedDate().plusNanos(deltaTime));
        assertEquals(firstRepeat, alertModel.getNotifications().get(0));
        assertEquals(futureAlertDate, alertModel.getNotifications().get(1));
    }
}