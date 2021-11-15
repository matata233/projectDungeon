package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    Item item1;
    Item item2;

    @BeforeEach
    void setUp() {
        item1 = new Potion("TestPotion");
        item2 = new Weapon("TestWeapon", 1, 0);
    }

    @Test
    void testToString() {
        assertEquals("TestPotion x1 \n", item1.toString());
        assertEquals("TestWeapon x1 \n", item2.toString());
    }
}
