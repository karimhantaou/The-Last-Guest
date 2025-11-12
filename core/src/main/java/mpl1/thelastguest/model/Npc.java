package mpl1.thelastguest.model;

import java.util.List;
import java.util.Map;

public class Npc extends Character{

    public Npc(String name, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName, Integer step) {
        super(name, stats,  posX, posY, spriteName,  step);
    }
}
