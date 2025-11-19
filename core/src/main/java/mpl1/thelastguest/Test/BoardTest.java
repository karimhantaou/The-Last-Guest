package mpl1.thelastguest.Test;

import mpl1.thelastguest.model.Board;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    int step = 14;
    List<Npc> npcs = new ArrayList<>();
    Player player;
    List<Item> items = new ArrayList<>();
    Board board;
    private Map<String, Integer> createStats(int str, int per, int lck, int ap, int inv) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", str);
        stats.put("per", per);
        stats.put("lck", lck);
        stats.put("ap", ap);
        stats.put("inv", inv);
        return stats;
    }
    @BeforeEach
    void setUp() {
        npcs.add(new Npc("Npc1", null, null));
        npcs.add(new Npc("Npc2",  null, null));
        player = new Player(new Npc("player",  null, null));
        items.add(new ActionItem("Knife", "kill"));
        items.add(new ActionItem("Gun", "kill"));
        items.add(new ActionItem("Fork", "kill"));
        items.add(new ActionItem("Key", "kill"));
        items.add(new ActionItem("Magnifying glass", "inspect"));
        items.add(new ActionItem("Fingerprint Scanner", "scan_fingerprints"));
        items.add(new ActionItem("Lies detector", "detect_lie"));
        items.add(new ActionItem("Fingerprints spoofer", "spoof_fingerprints"));
        items.add(new StatItem("Steroids", createStats(2,0,0,0,0)));
        items.add(new StatItem("Glasses", createStats(0,2,0,0,0)));
        items.add(new StatItem("Lucky charm", createStats(0,0,2,0,0)));
        items.add(new StatItem("Nike Air Max", createStats(0,0,0,2,0)));
        items.add(new StatItem("Backpack", createStats(0,0,0,0,1)));

        board = null;
        //board = new Board(step, npcs, player, items);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void randomItem() {
        Item newItem = board.randomItem();
        assertFalse(items.contains(newItem));
    }

    @Test
    void createAllRoom() {
        board.createAllRoom();
        assertEquals("cuisine", board.findRoom("cuisine").getName());
        assertNull(board.findRoom("blabla"));
    }

    @Test
    void getStep() {
        assertEquals(step, board.getStep());
    }

    @Test
    void setSize() {
        board.setSize(100, 200);
        assertEquals(2, board.getStep());
        board.setSize(200, 100);
        assertEquals(2, board.getStep());
    }

    @Test
    void findRoom() {
        board.createAllRoom();
        assertEquals("chambre1", board.findRoom("chambre1").getName());
        assertNull(board.findRoom("chambre12"));
    }
}
