package mpl1.thelastguest.model.Character;

import java.util.Map;

public class Npc extends Character{

    public Npc() {
        super();
    }
    public Npc(String name, Map<String, Integer> stats, String texturePath) {
        super(name, stats, texturePath);
    }
    public Npc(String name, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName, Integer step) {
        super(name, stats,  posX, posY, spriteName,  step);
    }
}
