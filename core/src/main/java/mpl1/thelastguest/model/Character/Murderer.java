package mpl1.thelastguest.model.Character;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import java.util.*;
import static java.lang.Math.random;

public class Murderer extends Character{

    private int killNbr = 1;
    private int roundBeforeDrop;
    private Item currentWeapon;

    public Murderer(Npc npc) {
        super(npc.getName(), npc.getDescription() ,npc.getStats(), npc.getX(),  npc.getY(), npc.getTexturePath(), npc.getStep());
    }

    public int getKillNbr(){
        return this.killNbr;
    }

    public void addKillNbr(){
        this.killNbr++;
    }

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
