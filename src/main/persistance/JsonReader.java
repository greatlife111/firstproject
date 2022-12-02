package persistance;


import model.Account;
import model.Alert;
import model.AlertList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;


    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        JSONObject alerts = jsonObject.getJSONObject("alertlist");

        Account acc = new Account(id, name, parseAlertList(alerts));

        return acc;
    }

    // EFFECTS: parses alertlist from JSON object and returns it
    private AlertList parseAlertList(JSONObject jsonObject) {
        AlertList list = new AlertList();
        addAlerts(list, jsonObject);
        return list;
    }

    // MODIFIES: alertlist
    // EFFECTS: parses alerts from JSON object and adds them to alertlist
    private void addAlerts(AlertList alertlist, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (Object json : jsonArray) {
            JSONObject nextAlert = (JSONObject) json;
            addAlert(alertlist, nextAlert);
        }
    }

    // MODIFIES: alertlist
    // EFFECTS: parses alert from JSON object and adds it to alertlist
    private void addAlert(AlertList alertlist, JSONObject jsonObject) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime createdDate = LocalDateTime.parse(jsonObject.getString("created"), dateFormat);
        LocalDateTime dueTime = LocalDateTime.parse(jsonObject.getString("date"), dateFormat);
        String due = jsonObject.getString("due");
        int repeat = jsonObject.getInt("repeat");
//        List<LocalDateTime> dates = (List<LocalDateTime>) jsonObject.get("notifications");

        Alert alert = new Alert(createdDate, dueTime, due, repeat);
        alertlist.addAlert(alert);
    }
}

