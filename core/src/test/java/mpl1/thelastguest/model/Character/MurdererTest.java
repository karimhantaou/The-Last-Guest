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

class MurdererTest {

    Murderer murderer;
    Npc npc;

    @BeforeEach
    void setUp() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", 1);
        stats.put("per", 1);
        stats.put("lck", 1);
        stats.put("ap", 1);
        stats.put("inv", 2);

        npc = mock(Npc.class);
        when(npc.getName()).thenReturn("NPC1");
        when(npc.getStats()).thenReturn(stats);
        when(npc.getX()).thenReturn(0);
        when(npc.getY()).thenReturn(0);
        when(npc.getTexturePath()).thenReturn(null);
        when(npc.getStep()).thenReturn(50);

        murderer = (Murderer) CharacterFactory.create("murderer", npc);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getKillNbr() {
        assertEquals(1, murderer.getKillNbr());
        murderer.addKillNbr();
        assertEquals(2, murderer.getKillNbr());
    }

    @Test
    void addKillNbr() {
        assertEquals(1, murderer.getKillNbr());
        murderer.addKillNbr();
        assertEquals(2, murderer.getKillNbr());
    }

    @Test
    void getItem() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", 0);
        stats.put("per", 0);
        stats.put("lck", 0);
        stats.put("ap", 0);
        stats.put("inv", 2);
        List <Item> items = new ArrayList<>();
        List <Item> items2 = new ArrayList<>();
        items.add(new ActionItem("cut", "kill"));
        items.add(new ActionItem("cut", "kll"));
        items.add(new StatItem("inventaire", stats));
        murderer.getItem(items);
        assertEquals("cut", murderer.getItems().get(0).getName());
        List<Character> npcs = new ArrayList<>();
        List<Character> npcs2 = new ArrayList<>();
        Npc npc = mock(Npc.class);
        npc.setPosition(murderer.getX() + 1, murderer.getY());
        npcs.add(npc);
        murderer.kill(npcs);
        murderer.getItem(items);
        murderer.getItem(items);
        murderer.getItem(items2);
    }

    @Test
    void kill() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", 0);
        stats.put("per", 0);
        stats.put("lck", 0);
        stats.put("ap", 0);
        stats.put("inv", 2);
        List<Character> npcs = new ArrayList<>();
        List<Character> npcs2 = new ArrayList<>();
        Npc npc = mock(Npc.class);
        when(npc.getX()).thenReturn(10);
        when(npc.getY()).thenReturn(10);
        Npc npc2 = mock(Npc.class);
        when(npc2.getX()).thenReturn(0);
        when(npc2.getY()).thenReturn(0);
        npc.setPosition(murderer.getX() + 1, murderer.getY());
        npcs.add(npc);
        npcs.add(npc2);
        List <Item> items = new ArrayList<>();
        items.add(new ActionItem("cut", "kill"));
        items.add(new StatItem("inventaire", stats));
        assertFalse(murderer.kill(npcs));
        murderer.getItem(items);
        assertEquals("cut", murderer.getItems().get(0).getName());
        murderer.kill(npcs);
        assertEquals(2, murderer.getKillNbr());
        assertFalse(murderer.kill(npcs2));
        murderer.getItem(items);
    }

    @Test
    void getWeaponTest() {
        List<Item> weapons = new ArrayList<>();
        weapons.add(new ActionItem("kill", "kill"));
        murderer.pickItem(weapons.get(0));
        assertEquals(weapons.get(0), murderer.getWeapons().get(0));
    }

    @Test
    void getTexturePath() {
        assertNull(murderer.getTexturePath());
    }

}
