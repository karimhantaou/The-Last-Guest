package mpl1.thelastguest.Test.model.Character;

import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MurdererTest {
    Murderer character;
    Map<String, Integer> stats1;

    @BeforeEach
    void setUp() {
        stats1 = new HashMap<>();
        stats1.put("str", 5);
        stats1.put("per", 9);
        stats1.put("lck", 4);
        stats1.put("ap", 4);
        stats1.put("inv", 2);
        this.character = new Murderer(new Npc("character", stats1, 25, 25, null, 14));
    }

    @Test
    void getName() {
        assertEquals("character", character.getName());
    }
}
