package mpl1.thelastguest.Test.model.Character;

import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.StatItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NpcTest {

    Npc npc;
    String name = "npc_name";
    Map<String, Integer> stats = new HashMap<>();
    String texturePath = "texture_path";
    ActionItem actionItem= new ActionItem("item_name", "item_action");
    StatItem statItem;

    @BeforeEach
    void setUp() {

        stats.put("str", 1);
        stats.put("per", 2);
        stats.put("lck", 3);
        stats.put("ap", 4);
        stats.put("inv",2);

        npc = new Npc(name, stats, texturePath);

        Map<String, Integer> itemStats = new HashMap<>();
        itemStats.put("str", 10);
        itemStats.put("per", 20);
        itemStats.put("lck", 30);
        itemStats.put("ap", 40);
        itemStats.put("inv",20);

        statItem = new StatItem("item_name", itemStats);
    }

    @Test
    void getName() {
        assertEquals(name, npc.getName());
    }

    @Test
    void getX(){
        assertEquals(0, npc.getX());
    }

    @Test
    void getY(){
        assertEquals(0, npc.getY());
    }

    @Test
    void setX(){
        npc.setX(10);
        assertEquals(10, npc.getX());
    }

    @Test
    void setY(){
        npc.setY(10);
        assertEquals(10, npc.getY());
    }

     @Test
     void setPosition(){
        npc.setPosition(10, 10);
        assertEquals(10, npc.getX());
        assertEquals(10, npc.getY());
     }

     @Test
     void getTexturePath(){
        assertEquals(texturePath, npc.getTexturePath());
     }

     @Test
     void getStats(){
        assertEquals(stats, npc.getStats());
     }

    @Test
    void getStr() {
        assertEquals(1, npc.getStr());
    }

    @Test
    void getPer() {
        assertEquals(2, npc.getPer());
    }

    @Test
    void getLck() {
        assertEquals(3, npc.getLck());
    }

    @Test
    void getAp() {
        assertEquals(4, npc.getAp());
    }

    @Test
    void getInv() {
        assertEquals(2, npc.getInv());
    }

    @Test
    void setStats(){
        Map<String, Integer> newStats = new HashMap<>();
        newStats.put("str", 10);
        newStats.put("per", 10);
        newStats.put("lck", 10);
        newStats.put("ap", 10);
        newStats.put("inv", 10);

        npc.setStats(newStats);

        assertEquals(newStats, npc.getStats());
    }

    @Test
    void pickItem(){
        assertTrue(npc.pickItem(actionItem));
        assertTrue(npc.pickItem(actionItem));
        assertFalse(npc.pickItem(actionItem));
    }

    @Test
    void pickItemStats(){
        assertTrue(npc.pickItem(statItem));
        assertEquals(statItem.getStr() + stats.get("str"), stats.get("str") + statItem.getStr() );
    }

    @Test
    void dropItem(){
        npc.pickItem(statItem);
        assertTrue(npc.dropItem(statItem));
        assertFalse(npc.dropItem(statItem));
    }

    @Test
    void dropItemStats(){
        npc.pickItem(statItem);
        npc.dropItem(statItem);
        assertEquals(1,npc.getStr());
    }

    @Test
    void isAlive() {
        assertTrue(npc.isAlive());
    }
}
