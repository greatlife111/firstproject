package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

class TestAlert {

    Alert alertModel;
    Date dateModel;

    @BeforeEach
    public void setup() {
        dateModel = new Date();
        alertModel = new Alert(dateModel 2359, "phase1", 1 );
    }

    @Test
    public void testChangeDate() {
        alertModel.changeDate();
    }
}