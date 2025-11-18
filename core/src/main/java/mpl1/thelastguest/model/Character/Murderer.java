package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.ActionItem;

import java.util.Map;

public class Murderer extends Character{

    private int killNbr = 1;

    public Murderer(Npc npc) {
        super(npc.getName(), npc.getStats(), npc.getX(),  npc.getY(), npc.getTexturePath(), npc.getStep());
    }

    public int getKillNbr(){
        return this.killNbr;
    }

    public void addKillNbr(){
        this.killNbr++;
    }
}
