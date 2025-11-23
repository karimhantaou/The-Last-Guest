package mpl1.thelastguest.model.Character;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.*;

/**
 * Represents the player character.
 * <p>
 * The Player inherits all core functionality from {@link Character} and adds
 * player-specific behavior such as perception checks and inspector status.
 */
public class Player extends Character {

    private boolean inspector = true;

    /**
     * Creates a Player instance from an existing NPC, copying its base attributes.
     *
     * @param npc the NPC used as a template for the player character
     */
    public Player(Npc npc){
        super(npc.getName(), npc.getDescription() ,npc.getStats(), npc.getX(), npc.getY(), npc.getTexturePath(), npc.getStep());
    }

    /**
     * Returns whether the player is considered an inspector.
     *
     * @return true if the player is an inspector, false otherwise
     */
    public boolean isInspector() {
        return inspector;
    }

    /**
     * Determines whether a position on the map is perceptible by the player.
     * A position is perceptible if it falls within the player's perception range,
     * determined by the player's PER stat.
     *
     * @param posX X coordinate to check
     * @param posY Y coordinate to check
     * @return true if the position is within perception range, false otherwise
     */
    @Override
    public boolean isPerceptible(int posX, int posY) {
        return posX >= getX() - getPer() && posX <= getX() + getPer() && posY >= getY() - getPer() && posY <= getY() + getPer();
    }
}
