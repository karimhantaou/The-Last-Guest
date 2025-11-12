package mpl1.thelastguest.Test.model.Item;

import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.StatItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    ActionItem actionItem;
    String actionName = "action_name";
    String action =  "action";

    StatItem statItem;
    String statName = "stat_name";
    Map<String, Integer> stats = new HashMap<>();

    String fingerprint = "A";

    @BeforeEach
    void setUp(){

        //Action Item
        actionItem = new ActionItem(actionName, action);

        // Stat item
        stats.put("str", 0);
        stats.put("per", 0);
        stats.put("lck", 10);
        stats.put("ap", 0);
        stats.put("inv", 0);
        statItem = new StatItem(statName,  stats);

        actionItem.setFingerprint(fingerprint);
        statItem.setFingerprint(fingerprint);
    }

    @Test
    void getName() {
        assertEquals(actionName, actionItem.getName());
        assertEquals(statName, statItem.getName());
    }


    @Test
    void getFingerprint() {
        assertEquals(fingerprint, actionItem.getFingerprint());
        assertEquals(fingerprint, statItem.getFingerprint());
    }

    @Test
    void setFingerprint() {
        String newFingerprint = "B";

        actionItem.setFingerprint(newFingerprint);
        assertEquals(newFingerprint, actionItem.getFingerprint());

        statItem.setFingerprint(newFingerprint);
        assertEquals(newFingerprint, statItem.getFingerprint());
    }

    @Test
    void getWoundType() {
        assertNull(actionItem.getWoundType());
        assertNull(actionItem.getWoundType());
    }
}
