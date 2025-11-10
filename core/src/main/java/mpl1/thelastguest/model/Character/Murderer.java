package mpl1.thelastguest.model.Character;

public class Murderer extends Character{

    public Murderer(Npc npc) {
        super(npc.getName(), npc.getStats(), npc.getTexturePath());
    }
}
