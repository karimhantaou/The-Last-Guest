package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.List;
import java.util.Objects;

public class Player extends Character {

    private boolean inspector = true;

    public Player(Npc npc){
        super(npc.getName(), npc.getStats(), npc.getTexturePath());
    }

    public boolean isInspector() {
        return inspector;
    }
}
