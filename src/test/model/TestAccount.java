package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAccount {
    Alert alert;
    AlertList list;
    Account acc;

    @BeforeEach
    void setup() {
        alert = new Alert(LocalDateTime.now().plusMinutes(1), "1", 1);
        list = new AlertList();
        list.addAlert(alert);
        acc = new Account(101, "mine", list);
    }

    @Test
    void testAll() {

        assertEquals(101, acc.getId());
        assertEquals("mine", acc.getName());
        assertEquals(1, acc.getAlerts().getSize());
    }

    @Test
    void testChangeName() {
        acc.changeName("Spring");
        assertEquals("Spring", acc.getName());

    }
}
