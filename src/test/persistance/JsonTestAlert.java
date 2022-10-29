package persistance;

import model.Alert;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTestAlert {
    protected void checkAlert(LocalDateTime date, String due, int repeat, Alert alert) {
        assertEquals(date, alert.getFutureDate());
        assertEquals(due, alert.getDueName());
        assertEquals(repeat, alert.getRepeat());
    }
}
