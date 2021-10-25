package persistence;

import model.PlayableCharacter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonReaderTest {
    @Test
    /***************************************************************************************
     *    A test method from JsonReaderTest in JsonSerializationDemo
     *    Title: JsonSerializationDemo - Simple application to illustrate JSON serialization
     *    Author: CPSC210
     *    Date: n/a
     *    Code version: n/a
     *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
     **************************************************************************************/
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PlayableCharacter pc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Case: a default playableCharacter
    // hp = max = 8
    // loc = (0,0)
    // empty inventory
    // empty mainHand slot
    @Test
    void testReaderDefaultPlayableCharacter() {
        JsonReader reader = new JsonReader("./data/testReaderDefaultPlayableCharacter.json");
        try {
            PlayableCharacter pc = reader.read();
            assertEquals("My character", pc.getName());
            assertEquals(8, pc.getHitPoint());
            assertEquals(0, pc.getPosX());
            assertEquals(0, pc.getPosY());
            assertTrue(pc.getInventory().isEmpty());
            assertTrue(pc.getMainHand().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // Case: a general playableCharacter
    // hp/max = 6/8
    // loc = (3,3)
    // empty inventory
    // empty mainHand slot
    @Test
    void testReaderGeneralPlayableCharacter() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlayableCharacter.json.json");
        try {
            PlayableCharacter pc = reader.read();
            assertEquals("My character", pc.getName());
            assertEquals(6, pc.getHitPoint());
            assertEquals(3, pc.getPosX());
            assertEquals(3, pc.getPosY());
            assertEquals(2, pc.getInventory().size());
            assertEquals(1, pc.getMainHand().size());
            assertEquals("test potion", pc.getInventory().get(0).getName());
            assertEquals("test sword1", pc.getInventory().get(1).getName());
            assertEquals("test sword2", pc.getMainHand().get(0).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
