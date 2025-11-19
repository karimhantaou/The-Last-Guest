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
    public boolean kill(Npc npc, Item weapon) {
        npc.addClues(this, weapon);
        npc.setAlive(false);
        if (this.getClass() == Murderer.class) {
            ((Murderer) this).addKillNbr();
        }
        return true;
    }
}
