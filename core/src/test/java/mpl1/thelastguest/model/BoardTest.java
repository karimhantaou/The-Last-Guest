package mpl1.thelastguest.model;

import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private Player player;
    private List<Character> npcs;
    private List<Item> items;

    @BeforeEach
    void setUp() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", 1);
        stats.put("per", 1);
        stats.put("lck", 1);
        stats.put("ap", 1);
        stats.put("inv", 2);
        // Création d'un joueur
        player = new Player(new Npc("name", "", stats, null));

        // Création de quelques NPC
        npcs = new ArrayList<>();
        npcs.add(new Npc("name1", "", stats, null));
        npcs.add(new Npc("naame2", "", stats, null));

        // Création d'items
        items = new ArrayList<>();
        items.add(new ActionItem("action1", "action2"));
        items.add(new ActionItem("action1", "action2"));
        items.add(new ActionItem("action1", "action2"));

        // Création du board
        board = new Board(32, npcs, player, items, 32, true);
    }

    @Test
    void createRoomTest() {
       board.createAllRoom();
    }

    @Test
    void getStepTest() {
        assertEquals(32, board.getStep());
        board.setSize(100, 150);
        assertEquals(2, board.getStep());
        board.setSize(150, 100);
        assertEquals(2, board.getStep());
    }

    @Test
    void findRoomTest() {
        assertEquals(new Room("Kitchen").getName(), board.findRoom("Kitchen").getName());
        assertNull(board.findRoom("Kitcn"));
    }

    @Test
    void setItemTest() {
        board.setItems(items);
    }
}
