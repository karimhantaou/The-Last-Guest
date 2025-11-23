package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NpcTest {
    Npc npc;
    Npc npc2;
    Map<String, Integer> stats;
    StatItem statItem;
    @BeforeEach
    void setUp() {
        new CharacterFactory();
        stats = new HashMap<>();
        stats.put("str", 1);
        stats.put("per", 1);
        stats.put("lck", 1);
        stats.put("ap", 1);
        stats.put("inv", 2);
        Map<String, Integer> stats2 = new HashMap<>();
        stats2.put("str", 0);
        stats2.put("per", 0);
        stats2.put("lck", 10);
        stats2.put("ap", 0);
        stats2.put("inv", 0);
        List<Item> items = new ArrayList<>();
        statItem = new StatItem("stats", stats2);
        npc = new Npc("npc","coucou", stats, null);
        npc.getItem(items);
        assertEquals("coucou", npc.getDescription());
        npc2 = new Npc("npc","", stats, 10, 10, null, 14);
    }

    @Test
    void getName() {
        assertEquals("npc", npc.getName());
    }

    @Test
    void getStep() {
        assertEquals(14, npc2.getStep());
        npc2.setStep(10);
        assertEquals(10, npc2.getStep());
    }

    @Test
    void getX() {
        assertEquals(10, npc2.getX());
    }

    @Test
    void getY() {
        assertEquals(10, npc2.getY());
    }

    @Test
    void setY() {
        npc.setY(10);
        assertEquals(10, npc.getY());
    }

    @Test
    void setX() {
        npc.setX(10);
        assertEquals(10, npc.getX());
    }

    @Test
    void setPosition() {
        npc.setPosition(10, 10);
        assertEquals(10, npc.getX());
    }

    @Test
    void getStats() {
        assertEquals(stats, npc.getStats());
    }

    @Test
    void getStr() {
        assertEquals(1, npc.getStr());
        npc.setStr(3);
        assertEquals(3, npc.getStr());
    }

    @Test
    void getPer() {
        assertEquals(1, npc.getPer());
        npc.setPer(3);
        assertEquals(3, npc.getPer());
    }

    @Test
    void getLck() {
        assertEquals(1, npc.getLck());
        npc.setLck(3);
        assertEquals(3, npc.getLck());
    }

    @Test
    void getAp() {
        assertEquals(1, npc.getAp());
        npc.setAp(3);
        assertEquals(3, npc.getAp());
    }

    @Test
    void getInv() {
        assertEquals(2, npc.getInv());
        npc.setInv(3);
        assertEquals(3, npc.getInv());
    }

    @Test
    void getStartAp() {
        npc.setStartAp(10);
        assertEquals(10, npc.getStartAp());
    }

    @Test
    void setStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", 4);
        stats.put("per", 5);
        stats.put("lck", 2);
        stats.put("ap", 3);
        stats.put("inv", 2);
        npc.setStats(stats);
        assertEquals(4, npc.getStats().get("str"));
    }

    @Test
    void addStats() {
        assertEquals(1, npc.getLck());
        npc.pickItem(statItem);
        assertEquals(11, npc.getLck());
    }

    @Test
    void dropItem() {
        assertEquals(1, npc.getLck());
        npc.pickItem(statItem);
        assertEquals(11, npc.getLck());
        npc.dropItem(statItem);
        npc.dropItem(statItem);
        assertEquals(1, npc.getLck());
    }

    @Test
    void getFingerprint() {
        assertNotNull(npc.getFingerprint());
    }

    @Test
    void setFingerprint() {
        npc.setFingerprint("fingerprint");
        assertEquals("fingerprint", npc.getFingerprint());
    }

    @Test
    void getClues() {
        assertTrue(npc.getClues().isEmpty());
    }

    @Test
    void getClueWound() {
        assertNull(npc.getClueWound());
    }

    @Test
    void isAlive() {
        assertTrue(npc.isAlive());
        npc.setAlive(false);
        assertFalse(npc.isAlive());
    }
    @Test
    void addClues() {
        npc.addClues(npc, new ActionItem("cut", "kill"));
        assertEquals(npc.getFingerprint(), npc.getClueFingerprint());
    }

    @Test
    void action() {
        npc.openDoor();
        npc.pickItem(new ActionItem("action", "Open door"));
        npc.pickItem(statItem);
        npc.openDoor();
        npc.pickItem(new ActionItem("action", "Open"));
        npc.openDoor();
        npc.displayActions();
    }

    @Test
    void kill() {
        List<Character> characters = new ArrayList<>();
        assertFalse(npc.kill(characters));
    }

    @Test
    void nbPath() {
        npc.setNbPath(10);
        assertEquals(10, npc.getNbPath());
    }

    @Test
    void isEnd() {
        npc.setIsEnd(true);
        assertTrue(npc.getIsEnd());
    }

    @Test
    void sprite() {
        assertNull(npc.getSprite());
    }

    @Test
    void hiddenPassage() {
        npc.setPosition(12,43);
        npc.setPosition(14,43);
        npc.setPosition(15,43);
        npc.setPosition(34,5);
        npc.setPosition(3,5);
        npc.setPosition(7, 31);
        npc.setPosition(5,30);
        npc.setPosition(5,31);
        npc.setPosition(7,12);
        npc.setPosition(40,45);
        npc.setPosition(41,36);
        npc.setPosition(12,46);
        npc.setPosition(12,47);
        int posX = npc.getX();
        npc.setPosition(14, 42);
        assertNotEquals(posX, npc.getX());
        posX = npc.getX();
        npc.setPosition(33, 6);
        assertNotEquals(posX, npc.getX());
        posX = npc.getX();
        npc.setPosition(7, 30);
        assertNotEquals(posX, npc.getX());
        posX = npc.getX();
        npc.setPosition(40, 46);
        assertNotEquals(posX, npc.getX());
    }

    @Test
    void isPerceptible() {
        assertFalse(npc.isPerceptible(12, 12));
    }

    @Test
    void getItemTest() {
        Item item = new ActionItem("action", "Open door");
        npc.pickItem(item);
        npc.getItem("action");
        npc.getItemByAction("Open door");
        npc.getItemByAction("Open dor");
        assertEquals(item, npc.getItem("action"));
        assertNull(npc.getItem("actn"));
    }

    @Test
    void isFingerPrintFoundTest() {
        assertFalse(npc.isFingerPrintFound());
        npc.setFingerPrintFound(true);
        assertTrue(npc.isFingerPrintFound());
    }

    @Test
    void enoughInventoryTest() {
        assertTrue(npc.enoughInventory());
        npc.setInv(-1);
        assertFalse(npc.enoughInventory());
    }

    @Test
    void enoughApTest() {
        assertTrue(npc.enoughAp(0));
        assertFalse(npc.enoughAp(10000));
    }
    @AfterEach
    void tearDown() {
    }
}
