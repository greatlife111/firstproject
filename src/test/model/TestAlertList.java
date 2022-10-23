package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestAlertList {

    AlertList list1;
    Alert alert1;
    LocalDateTime foralert1;
    Alert alert2;
    LocalDateTime foralert2;
    Alert alert3;
    LocalDateTime foralert3;

    @BeforeEach
    public void setup(){
        foralert1 = LocalDateTime.of(2023, 10, 20, 1,1);
        foralert2 = LocalDateTime.of(2023,10, 21, 1,1);
        foralert3 = LocalDateTime.of(2023, 10, 22, 1, 1);
        list1 = new AlertList();
        alert1 = new Alert(foralert1, "alert1", 1);
        alert2 = new Alert(foralert2, "alert2", 2);
        alert3 = new Alert(foralert3, "alert3", 3);

    }

    @Test
    void testIsEmpty() {
        assertTrue(list1.isEmpty());
        assertEquals(0, list1.getList().size());
    }

    @Test
    void testAddAlert() {
        list1.addAlert(alert1);
        assertEquals(1, list1.getSize());
        assertFalse(list1.isEmpty());
    }

    @Test
    void testisEmpty() {
        assertTrue(list1.isEmpty());
    }

    @Test
    void testRemoveAlert() {
        list1.addAlert(alert1);
        list1.addAlert(alert2);
        list1.removeAlert(alert2.getDueName());
        assertEquals(1, list1.getSize());
    }

    @Test
    void testBeforeDate() {
        list1.addAlert(alert1);
        list1.addAlert(alert2);
        list1.viewAlertBeforeDate(alert2.getFutureDate());
        assertTrue(list1.getAlertListBeforeDate().contains(alert1));
        assertEquals(1, list1.getAlertListBeforeDate().size());

        list1.viewAlertBeforeDate(alert1.getFutureDate());
        assertEquals(0, list1.getAlertListBeforeDate().size());
    }

    @Test
    void testAlertOfTheDay() {
        list1.addAlert(alert1);
        list1.addAlert(alert2);
        assertTrue(list1.viewAlertsOnTheDay(alert1.getFutureDate()).contains(alert1));
        assertFalse(list1.viewAlertsOnTheDay(alert1.getFutureDate()).contains(alert2));
    }

    @Test
    void testViewAlertNextDays() {
        list1.addAlert(alert1);
        list1.addAlert(alert2);
        list1.addAlert(alert3);
        LocalDateTime begin = LocalDateTime.of(2023,10,19,1,1);
        assertEquals(2, list1.viewAlertNextDays(2, begin).size());
    }

    @Test
    void testViewAlertOverload() {
        Alert alert = new Alert(LocalDateTime.now().plusDays(1), "alert", 2);
        list1.addAlert(alert);
        assertEquals(1, list1.viewAlertNextDays(1).size());
    }


}
