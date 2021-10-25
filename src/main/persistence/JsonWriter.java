package persistence;

import model.PlayableCharacter;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of player data to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    /***************************************************************************************
     *    A constructor from JsonWriter class in JsonSerializationDemo
     *    Title: JsonSerializationDemo - Simple application to illustrate JSON serialization
     *    Author: CPSC210
     *    Date: n/a
     *    Code version: n/a
     *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
     *
     ***************************************************************************************/
    // EFFECTS: construct writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /***************************************************************************************
     *    A method from JsonWriter class in JsonSerializationDemo
     *    Title: JsonSerializationDemo - Simple application to illustrate JSON serialization
     *    Author: CPSC210
     *    Date: n/a
     *    Code version: n/a
     *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
     *
     ***************************************************************************************/
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(PlayableCharacter player) {
        JSONObject json = player.toJson();
        saveToFile(json.toString(TAB));
    }

    /***************************************************************************************
     *    A method from JsonWriter class in JsonSerializationDemo
     *    Title: JsonSerializationDemo - Simple application to illustrate JSON serialization
     *    Author: CPSC210
     *    Date: n/a
     *    Code version: n/a
     *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
     *
     ***************************************************************************************/
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    /***************************************************************************************
     *    A method from JsonWriter class in JsonSerializationDemo
     *    Title: JsonSerializationDemo - Simple application to illustrate JSON serialization
     *    Author: CPSC210
     *    Date: n/a
     *    Code version: n/a
     *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
     *
     ***************************************************************************************/
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
