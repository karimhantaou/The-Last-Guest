package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.ActionItem;

import java.util.Map;

public class Murderer extends Character{
        public Murderer(Npc npc) {
        super(npc.getName(), npc.getStats(), npc.getX(),  npc.getY(), npc.getTexturePath(), npc.getStep());
    }
}
