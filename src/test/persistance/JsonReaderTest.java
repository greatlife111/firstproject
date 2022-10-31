package persistance;

import model.Account;
import model.Alert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTestAlert {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account acc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAlertList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account acc = reader.read();
            assertEquals(5998, acc.getId());
            assertEquals("spring", acc.getName());
            assertEquals(0, acc.getAlerts().getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlertList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account acc = reader.read();
            assertEquals(5998, acc.getId());
            assertEquals("spring", acc.getName());
            List<Alert> alert = acc.getAlerts().getList();
            assertEquals(2, alert.size());

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dueTime1 = LocalDateTime.parse("2022-11-02 10:00", dateFormat);
            LocalDateTime createdDate1 = LocalDateTime.parse("2022-10-30 17:20", dateFormat);
            checkAlert(createdDate1, dueTime1, "ALERT1", 1, alert.get(0));

            LocalDateTime dueTime2 = LocalDateTime.parse("2022-11-01 10:00", dateFormat);
            LocalDateTime createdDate2 = LocalDateTime.parse("2022-10-30 17:21", dateFormat);
            checkAlert(createdDate2, dueTime2, "ALERT2", 3, alert.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
