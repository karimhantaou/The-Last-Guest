package mpl1.thelastguest.model;

import java.util.Map;

public class Npc extends Character{

    public Npc() {
        super();
    }

    public Npc(String name, Map<String, Integer> stats, String texturePath) {
        super(name, stats, texturePath);
    }
}
