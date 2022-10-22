package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        foralert1 = LocalDateTime.of(2022, 10, 20, 1,1);
        foralert2 = LocalDateTime.of(2022, 10, 21, 1,1);
        foralert3 = LocalDateTime.of(2022, 10, 22, 1,1);
        list1 = new AlertList();
        alert1 = new Alert(foralert1, "alert1", 1);
        alert2 = new Alert(foralert2, "alert2", 2);
        alert3 = new Alert(foralert3, "alert3", 3);

    }

    @Test
    public void testIsEmpty() {
        assertTrue(list1.isEmpty());
    }

    @Test
    public void testAddAlert() {
        list1.addAlert(alert1);
        assertEquals(1, list1.getSize());
    }

    @Test
    public void testisEmpty() {
        assertTrue(list1.isEmpty());
    }

    @Test
    public void removeAlert() {
        list1.addAlert(alert1);
        list1.addAlert(alert2);
        list1.removeAlert(alert2);


    }
}
