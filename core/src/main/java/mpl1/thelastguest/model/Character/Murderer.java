package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import java.util.*;
import static java.lang.Math.random;

/**
 * Represents the murderer in the game.
 * The murderer inherits from {@link Character} and has additional behavior
 * such as killing NPCs and managing weapons used for murder.
 */
public class Murderer extends Character{

    private int killNbr = 1;
    private int roundBeforeDrop;
    private Item currentWeapon;

    /**
     * creator of murderer based on an existing NPC.
     *
     * @param npc The NPC whose attributes will be cloned to initialize the murderer.
     */
    public Murderer(Npc npc) {
        super(npc.getName(), npc.getDescription() ,npc.getStats(), npc.getX(),  npc.getY(), npc.getTexturePath(), npc.getStep());
    }

    /**
     * Returns the number of kills committed by the murderer.
     *
     * @return The number of kills.
     */
    public int getKillNbr(){
        return this.killNbr;
    }


    /**
     * Increments the kill counter by 1.
     */
    public void addKillNbr(){
        this.killNbr++;
    }

    /**
     * Allows the murderer to pick up items from the environment.
     * If the murderer is holding a weapon for too long (roundBeforeDrop ≤ 0),
     * he automatically drops it.
     * Then, he picks up any "kill" action items found in the list.
     *
     * @param items The list of items in the room.
     */

    @Override
    public void getItem(List<Item> items){
        if (roundBeforeDrop <= 0 && !getItems().isEmpty()){
            dropItem(currentWeapon);
        }
        for (Item item : items) {
            if (item.getClass() == ActionItem.class){
                if (Objects.equals(((ActionItem) item).getAction(), "kill")){
                    pickItem(item);
                }
            }
        }
        roundBeforeDrop -- ;
    }

    /**
     * try to kill a character in range.
     * The murderer can kill if he has at least one weapon in his inventory.
     * for kill, a random weapon is selected among the murderer's items.
     * the weapon's wound and fingerprint clues are applied to the victim.
     * the victim is marked dead.
     * the murderer's kill counter increases.
     * the murderer keeps the weapon for a random number between 0 and 3 rounds before dropping it.
     * @param npcs The list of characters on the map.
     * @return true if a kill occurred, false otherwise.
     */

    @Override
    public boolean kill(List <Character> npcs) {
        if (getItems().isEmpty())
            return false;
        for  (Character npc : npcs) {
            if (isClose(npc)) {
                Item weapon = getItems().get((int)(random() * getItems().size()));
                npc.addClues(this, weapon);
                npc.setAlive(false);
                this.addKillNbr();
                this.currentWeapon = weapon;
                this.roundBeforeDrop = (int) (random() * 3);
                return true;
            }
        }
        return false;
    }
}
