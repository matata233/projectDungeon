package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            PlayableCharacter pc = new PlayableCharacter("My character");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
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
    void testWriterDefaultPlayableCharacter() {
        try {
            PlayableCharacter pc = new PlayableCharacter("My character");
            JsonWriter writer = new JsonWriter("./data/testWriterDefaultPlayableCharacter.json");
            writer.open();
            writer.write(pc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDefaultPlayableCharacter.json");
            pc = reader.read();
            assertEquals("My character", pc.getName());
            assertEquals(8, pc.getHitPoint());
            assertEquals(0, pc.getPosX());
            assertEquals(0, pc.getPosY());
            assertTrue(pc.getInventory().isEmpty());
            assertTrue(pc.getMainHand().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // Case: a general playableCharacter
    // hp/max = 6/8
    // loc = (3,3)
    // empty inventory
    // empty mainHand slot
    @Test
    void testWriterGeneralPlayableCharacter() {
        try {
            PlayableCharacter pc = new PlayableCharacter("My character");
            pc.setHitPoint(6);
            pc.add(new Potion("test potion"));
            pc.add(new Weapon("test sword1", 1, 0));
            pc.equip(new Weapon("test sword2", 2, 0));
            pc.setPosX(3);
            pc.setPosY(3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlayableCharacter.json");
            writer.open();
            writer.write(pc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlayableCharacter.json");
            pc = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}
