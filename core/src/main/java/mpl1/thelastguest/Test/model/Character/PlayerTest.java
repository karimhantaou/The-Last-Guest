package mpl1.thelastguest.Test.model.Character;

import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerTest {
    Player player;
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
        when(npc.getName()).thenReturn("player");
        when(npc.getStats()).thenReturn(stats);
        when(npc.getX()).thenReturn(0);
        when(npc.getY()).thenReturn(0);
        when(npc.getTexturePath()).thenReturn(null);
        when(npc.getStep()).thenReturn(50);

        player = new Player(npc);
    }

    @Test
    void isInspector() {
        assertTrue(player.isInspector());
    }

    @Test
    void isPerceptible() {
        assertFalse(player.isPerceptible(12, 12));
        assertTrue(player.isPerceptible(0, 0));
        assertTrue(player.isPerceptible(1, 0));
        assertTrue(player.isPerceptible(0, 1));
        assertFalse(player.isPerceptible(12, 0));
        assertFalse(player.isPerceptible(0, 12));
        assertTrue(player.isPerceptible(-1, 0));
        assertTrue(player.isPerceptible(0, -1));
        assertFalse(player.isPerceptible(-12, 0));
        assertFalse(player.isPerceptible(0, -12));

    }
}
