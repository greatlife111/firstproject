package persistance;

import model.Account;
import model.Alert;
import model.AlertList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTestAlert{

    void testWriterInvalidFile() {
        try {
            assertEquals("my account", );
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Account acc = new Account(5998, "spring", null);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals("5998", acc.getId());
            assertEquals("spring", acc.getName());
            assertEquals(0, acc.getAlerts().getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            AlertList alertList = new AlertList();
            Account acc = new Account(5998, "springg", alertList);

            alertList.addAlert(new Alert(LocalDateTime.of(2022, 10, 30, 10, 0),
                    "phase 1", 1));
            alertList.addAlert(new Alert(LocalDateTime.of(2022, 10, 30, 10, 0),
                    "phase 2", 2));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            acc = reader.read();
            assertEquals(5998, acc.getId());
            assertEquals("springg", acc.getName());
            List<Alert> alerts = alertList.getList();
            assertEquals(2, acc.getAlerts().getSize());

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dueTime1 = LocalDateTime.parse("2022-10-30 10:00", dateFormat);
            checkAlert(dueTime1, "phase 1", 1, alerts.get(0));

            LocalDateTime dueTime2 = LocalDateTime.parse("2022-10-31 10:00", dateFormat);
            checkAlert(dueTime2, "phase 2", 2, alerts.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
