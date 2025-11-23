package mpl1.thelastguest.model;

import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private String name = "Kitchen";
    private Room room;


    @BeforeEach
    void setUp() {
        room = new Room(name);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getName() {
        assertEquals(name, room.getName());
    }

    @Test
    void addItem() {
        Item item = new ActionItem("cut", "kill");
        room.addItem(item);
        assertEquals(1, room.getItems().size());
        assertEquals(item, room.getItems().get(0));
    }

    @Test
    void removeItem() {
        Item item = new ActionItem("cut", "kill");
        room.addItem(item);
        assertEquals(1, room.getItems().size());
        room.removeItem(item);
        assertEquals(0, room.getItems().size());
    }

    @Test
    void getItems() {
        Item item = new ActionItem("cut", "kill");
        room.addItem(item);
        assertEquals(1, room.getItems().size());
        assertEquals(item, room.getItems().get(0));
    }
}
