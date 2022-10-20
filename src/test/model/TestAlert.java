package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAlert {

    Alert alertModel;
    LocalDateTime dateModel;

    @BeforeEach
    public void setup() {
        dateModel = LocalDateTime.of(2022, 10, 19, 17, 7);
        alertModel = new Alert(dateModel, "phase1", 1 );
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
}