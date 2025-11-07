package mpl1.thelastguest.model;

import java.util.List;
import java.util.Map;

public class Murderer extends Character{

    public Murderer(Npc npc) {
        super(npc.getName(), npc.getStats(), npc.getTexturePath());
    }
}
