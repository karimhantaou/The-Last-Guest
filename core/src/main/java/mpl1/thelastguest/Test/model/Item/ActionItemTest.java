package mpl1.thelastguest.Test.model.Item;

import mpl1.thelastguest.model.Item.ActionItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionItemTest {

    String name = "item_name";
    String action = "item_action";
    String wound = "item_wound";
    ActionItem actionItem = new ActionItem(name, action);
    ActionItem actionItemWithWound = new ActionItem(name, action, wound);

    @Test
    void getName() {
        assertEquals(name, actionItem.getName());
    }

    @Test
    void getAction() {
        assertEquals(name, actionItem.getName());
    }

    @Test
    void getStats() {
        assertNull(actionItem.getStats());
    }

    @Test
    void getWound() {
        assertEquals(wound, actionItemWithWound.getWoundType());
    }
}
