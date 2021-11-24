package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reads player data from JSON data stored in file
public class JsonReader {
    private String source;

    /***************************************************************************************
     *    A constructor from JsonReader class in JsonSerializationDemo
     *    Title: JsonSerializationDemo - Simple application to illustrate JSON serialization
     *    Author: CPSC210
     *    Date: n/a
     *    Code version: n/a
     *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
     *
     ***************************************************************************************/
    // EFFECTS: construct reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: read player data from file and return it;
    // throws IOException if an error occurs reading data from file
    public PlayableCharacter read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayableCharacter(jsonObject);
    }

    /***************************************************************************************
     *    A method from JsonReader class in JsonSerializationDemo
     *    Title: JsonSerializationDemo - Simple application to illustrate JSON serialization
     *    Author: CPSC210
     *    Date: n/a
     *    Code version: n/a
     *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
     ***************************************************************************************/
    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses playableCharacter from JSON object and returns it
    private PlayableCharacter parsePlayableCharacter(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int attack = jsonObject.getInt("attack");
        int defence = jsonObject.getInt("defence");
        int hitPoint = jsonObject.getInt("hitPoint");
        int posX = jsonObject.getInt("posX");
        int posY = jsonObject.getInt("posY");
        List<Item> inventory = new ArrayList<>();
        List<Item> mainHand = new ArrayList<>();
        addInventory(inventory, jsonObject);
        addMainHand(mainHand, jsonObject);
        PlayableCharacter pc = new PlayableCharacter(name, attack, defence, hitPoint, posX, posY,
                inventory, mainHand);
        return pc;
    }

    // MODIFIES: pc
    // EFFECTS: parses inventory from JSON object and adds them to pc
    private void addInventory(List<Item> inventory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inventory, nextItem);
        }
    }

    // MODIFIES: pc
    // EFFECTS: parses mainHand slot from JSON object and equip it to pc
    private void addMainHand(List<Item> mainHand, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("mainHand");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            equipItem(mainHand, nextItem);
        }
    }


    // MODIFIES: pc
    // EFFECTS: parses item from JSON object and adds it to pc
    private void addItem(List<Item> inventory, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int amount = jsonObject.getInt("amount");
        Item item;
        try {
            int atk = jsonObject.getInt("attack");
            int def = jsonObject.getInt("defence");
            //this is a weapon
            item = new Weapon(name, atk, def);
            inventory.add(item);
        } catch (JSONException e) {
            //this is not a gear
            item = new Potion(name);
            inventory.add(item);
        }
        EventLog.getInstance().logEvent(
                new Event(item.getName() + " has been added to player's inventory."));
    }


    // MODIFIES: pc
    // EFFECTS: parses item from JSON object and equip it to pc
    private void equipItem(List<Item> mainHand, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int atk = jsonObject.getInt("attack");
        int def = jsonObject.getInt("defence");
        Item weapon = new Weapon(name, atk, def);
        mainHand.add(weapon);
        EventLog.getInstance().logEvent(
                new Event(weapon.getName() + " has been added to player's main hand."));
    }
}
