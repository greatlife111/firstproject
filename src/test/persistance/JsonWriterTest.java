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

    @Test
    void testWriterInvalidFile() {
        try {
            Account account = new Account(5998, "l", null);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            AlertList alertlist = new AlertList();
            Account acc = new Account(5998, "spring", alertlist);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            acc = reader.read();
            assertEquals(5998, acc.getId());
            assertEquals("spring", acc.getName());
            assertEquals(0, acc.getAlerts().getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAccount() {
        try {
            AlertList alertList = new AlertList();
            Account acc = new Account(5998, "spring", alertList);

            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dueTime1 = LocalDateTime.parse("2022-11-02 10:00", dateFormat);
            LocalDateTime createdDate1 = LocalDateTime.parse("2022-10-30 17:20", dateFormat);
            LocalDateTime dueTime2 = LocalDateTime.parse("2022-11-01 10:00", dateFormat);
            LocalDateTime createdDate2 = LocalDateTime.parse("2022-10-30 17:21", dateFormat);

            alertList.addAlert(new Alert(createdDate1, dueTime1,"ALERT1", 1));
            alertList.addAlert(new Alert(createdDate2, dueTime2,"ALERT2", 3));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(acc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            acc = reader.read();
            assertEquals(5998, acc.getId());
            assertEquals("spring", acc.getName());
            List<Alert> alert = alertList.getList();
            assertEquals(2, acc.getAlerts().getSize());


            checkAlert(createdDate1, dueTime1, "ALERT1", 1, alert.get(0));

            checkAlert(createdDate2, dueTime2, "ALERT2", 3, alert.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
