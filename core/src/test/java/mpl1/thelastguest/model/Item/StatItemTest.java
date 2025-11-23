package mpl1.thelastguest.model.Item;

import mpl1.thelastguest.model.Item.StatItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatItemTest {

    StatItem statItem;
    String name = "item_name";
    Map<String, Integer> stats = new HashMap<>();;

    @BeforeEach
    void setUp() {
        stats.put("str", 0);
        stats.put("per", 0);
        stats.put("lck", 10);
        stats.put("ap", 0);
        stats.put("inv", 0);

        statItem = new StatItem(name, stats);
    }

    @Test
    void getName() {
        assertEquals(name, statItem.getName());
    }

    @Test
    void getStats() {
        assertEquals(stats, statItem.getStats());
    }

    @Test
    void getAction() {
        assertNull(statItem.getAction());
    }

    @Test
    void getStr() {
        assertEquals(0, statItem.getStr());
    }

    @Test
    void getPer() {
        assertEquals(0, statItem.getPer());
    }

    @Test
    void getLck() {
        assertEquals(10, statItem.getLck());
    }

    @Test
    void getAp() {
        assertEquals(0, statItem.getAp());
    }

    @Test
    void getInv() {
        assertEquals(0, statItem.getInv());
    }
}
