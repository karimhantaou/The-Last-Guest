package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;

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

    @Override
    public boolean kill(Character npc, Item weapon) {
        npc.addClues(this, weapon);
        npc.setAlive(false);
        addKillNbr();
        return true;
    }
}
