package persistance;

import model.Alert;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTestAlert {
    protected void checkAlert(LocalDateTime createdDate, LocalDateTime date, String due, int repeat, Alert alert) {
        assertEquals(createdDate, alert.getCreatedDate());
        assertEquals(date, alert.getFutureDate());
        assertEquals(due, alert.getDueName());
        assertEquals(repeat, alert.getRepeat());
    }
}
