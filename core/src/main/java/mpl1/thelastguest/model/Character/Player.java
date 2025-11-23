package mpl1.thelastguest.model.Character;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.*;

public class Player extends Character {

    private boolean inspector = true;

    public Player(Npc npc){
        super(npc.getName(), npc.getDescription() ,npc.getStats(), npc.getX(), npc.getY(), npc.getTexturePath(), npc.getStep());
    }

    public Player(String hero, HashMap<Object, Object> objectObjectHashMap, int i, int i1, Object o, int i2) {
    }

    public boolean isInspector() {
        return inspector;
    }


    @Override
    public boolean isPerceptible(int posX, int posY) {
        return posX >= getX() - getPer() && posX <= getX() + getPer() && posY >= getY() - getPer() && posY <= getY() + getPer();
    }
}
