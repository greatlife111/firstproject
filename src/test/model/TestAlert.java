package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestAlert {

    Alert alertModel;
    LocalDateTime dateModel;
    LocalDateTime dateModel2;


    @BeforeEach
    public void setup() {
        dateModel = LocalDateTime.of(2022, 10, 19, 17, 7);
        dateModel2 = LocalDateTime.of(2022, 10, 20, 17, 7);
        alertModel = new Alert(dateModel, "phase1", 2);
    }

    @Test
    public void testChangeDate() {
        alertModel.changeDate(dateModel);
        LocalDateTime dateModel2 = LocalDateTime.of(2022, 10, 19, 17, 8);
        alertModel.changeDate(dateModel2);
        assertEquals(dateModel2, alertModel.getDate());
    }

    @Test
    public void testChangeName(){
        alertModel.changeDue("phase2");
        assertEquals("phase2", alertModel.getDue());
    }

    @Test
    public void testChangeRepeat(){
        alertModel.changeRepeat(2);
        assertEquals(2, alertModel.getRepeat());
    }

    @Test
    public void testShouldBeNotifiedYes(){
        alertModel.notifications.add(dateModel);
        assertTrue(alertModel.shouldBeNotified(dateModel2));
        assertTrue(alertModel.shouldBeNotified(dateModel));
    }

    @Test
    public void testShouldBeNotifiedNo(){
        alertModel.notifications.add(dateModel2);
        assertFalse(alertModel.shouldBeNotified(dateModel));
    }

    @Test
    public void testCalculatingNotifications(){
        LocalDateTime future = LocalDateTime.of(2023, 10,20,1,1);
        Alert alertmodel2 = new Alert(future, "test", 2);
        alertmodel2.calculateNotifications(future);
        long deltaTime = ChronoUnit.MINUTES.between(LocalDateTime.now(), future) / alertmodel2.getRepeat();

        LocalDateTime firstRepeat = (LocalDateTime.now().plusMinutes(deltaTime));
        assertEquals(firstRepeat, alertmodel2.getNotifications().get(0));
        assertEquals(future, alertmodel2.getNotifications().get(1));
    }
}