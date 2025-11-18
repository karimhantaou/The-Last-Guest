package mpl1.thelastguest.Test.model.Character;

import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NPCTest {
    Npc character;
    Map<String, Integer> stats1;
    @BeforeEach
    void setUp() {
        stats1 = new HashMap<>();
        stats1.put("str", 5);
        stats1.put("per", 9);
        stats1.put("lck", 4);
        stats1.put("ap", 4);
        stats1.put("inv", 2);
        this.character = new Npc("character", stats1, 25, 25, null, 14);
    }
    @Test
    void getName() {
        assertEquals("character", character.getName());
    }

    @Test
    void getStep() {
        assertEquals(14, character.getStep());
    }

    @Test
    void setStep() {
        character.setStep(12);
        assertEquals(12, character.getStep());
    }

    @Test
    void getNbPath() {
        assertEquals(0, character.getNbPath());
    }

    @Test
    void getIsEnd() {
        character.setIsEnd(false);
        assertFalse(character.getIsEnd());
    }

    @Test
    void setIsEnd() {
        character.setIsEnd(true);
        assertTrue(character.getIsEnd());
    }

    @Test
    void getX() {
        assertEquals(25, character.getX());
        character.setX(20);
        assertEquals(20, character.getX());
    }

    @Test
    void getY() {
        assertEquals(25, character.getY());
        character.setY(12);
        assertEquals(12, character.getY());
    }

    @Test
    void setY() {
        assertEquals(25, character.getY());
        character.setY(42);
        assertEquals(42, character.getY());
    }

    @Test
    void setX() {
        assertEquals(25, character.getX());
        character.setX(36);
        assertEquals(36, character.getX());
    }

    @Test
    void setPosition() {
        assertEquals(25, character.getX());
        assertEquals(25, character.getY());
        character.setPosition(12, 12, true);
        assertEquals(12, character.getX());
        assertEquals(12, character.getY());
    }

    @Test
    void getStats() {
        Map<String, Integer> stats = character.getStats();
        assertEquals(5, stats.get("str"));
        assertEquals(9, stats.get("per"));
        assertEquals(stats1, stats);
    }

    @Test
    void getStr() {
        assertEquals(5, character.getStr());
    }

    @Test
    void getPer() {
        assertEquals(9, character.getPer());
    }

    @Test
    void getLck() {
        assertEquals(4, character.getLck());
    }

    @Test
    void getAp() {
        assertEquals(4, character.getAp());
    }

    @Test
    void getInv() {
        assertEquals(2, character.getInv());
    }

    @Test
    void getStartAp() {
        character.setStartAp(4);
        assertEquals(4, character.getStartAp());
    }

    @Test
    void setStartAp() {
        character.setStartAp(3);
        assertEquals(3, character.getStartAp());
    }

    @Test
    void setStats() {
        assertEquals(stats1, character.getStats());
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", 8);
        stats.put("per", 6);
        stats.put("lck", 4);
        stats.put("ap", 9);
        stats.put("inv", 2);
        character.setStats(stats);
        assertEquals(stats, character.getStats());
    }

    @Test
    void setStr() {
        assertEquals(5, character.getStr());
        character.setStr(6);
        assertEquals(6, character.getStr());
    }

    @Test
    void setPer() {
        assertEquals(9, character.getPer());
        character.setPer(7);
        assertEquals(7, character.getPer());
    }

    @Test
    void setLck() {
        assertEquals(4, character.getLck());
        character.setLck(5);
        assertEquals(5, character.getLck());
    }

    @Test
    void setAp() {
        assertEquals(4, character.getAp());
        character.setAp(6);
        assertEquals(6, character.getAp());
    }

    @Test
    void setInv() {
        assertEquals(2, character.getInv());
        character.setInv(3);
        assertEquals(3, character.getInv());
    }

    @Test
    void getItems() {
        character.pickItem(new ActionItem("Knife", "kill"));
        assertEquals("Knife", character.getItems().get(0).getName());
    }

    @Test
    void countItems() {
        character.pickItem(new ActionItem("Knife", "kill"));
        assertEquals(1, character.countItems());
        character.pickItem(new ActionItem("Gun", "kill"));
        assertEquals(2, character.countItems());
    }

    @Test
    void pickItem() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", 2);
        stats.put("per", 0);
        stats.put("lck", 0);
        stats.put("ap", 0);
        stats.put("inv", 0);
        character.pickItem(new ActionItem("Knife", "kill"));
        assertEquals(1, character.countItems());
        character.pickItem(new StatItem("Steroids", stats));
        assertEquals(2, character.countItems());
        assertFalse(character.pickItem(new ActionItem("Fork", "kill")));
    }

    @Test
    void dropItem() {
        Item item1 = (new ActionItem("Knife", "kill"));
        Item item2 = (new ActionItem("Fork", "kill"));
        Item item3 = (new ActionItem("lala", "kill"));
        character.pickItem(item1);
        character.pickItem(item2);
        assertFalse(character.dropItem(item3));
        assertTrue(character.dropItem(item2));
    }

    @Test
    void getFingerprint() {
    }

    @Test
    void setFingerprint() {
    }

    @Test
    void getClues() {
    }

    @Test
    void addClues() {
    }

    @Test
    void isAlive() {
        assertTrue(character.isAlive());
        character.setAlive(false);
        assertFalse(character.isAlive());
    }

    @Test
    void setAlive() {
        character.setAlive(true);
        assertTrue(character.isAlive());
    }

    @Test
    void canDoAction() {
    }

    @Test
    void openDoor() {
    }

    @Test
    void kill() {
    }

    @Test
    void displayActions() {
    }

    @Test
    void moveRight() {
        assertEquals(25, character.getX());
        assertEquals(25, character.getY());
        character.moveRight(true);
        assertEquals(26, character.getX());
    }

    @Test
    void moveLeft() {
        assertEquals(25, character.getX());
        assertEquals(25, character.getY());
        character.moveLeft(true);
        assertEquals(24, character.getX());
    }

    @Test
    void moveUp() {
        assertEquals(25, character.getX());
        assertEquals(25, character.getY());
        character.moveUp(true);
        assertEquals(26, character.getY());
    }

    @Test
    void moveDown() {
        assertEquals(25, character.getX());
        assertEquals(25, character.getY());
        character.moveDown(true);
        assertEquals(24, character.getY());
    }

    @Test
    void hiddenPassage() {
        assertEquals(25, character.getX());
        assertEquals(25, character.getY());
        character.setPosition(14, 42, true);
        character.hiddenPassage(true);
        assertEquals(32, character.getX());
        assertEquals(6, character.getY());
    }
}
