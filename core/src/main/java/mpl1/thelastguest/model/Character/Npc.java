package mpl1.thelastguest.model.Character;

import java.util.Map;

/**
 * Represents a non-player character (NPC) in the game.
 * <p>
 * An NPC inherits all functionality from {@link Character} and mainly serves
 * as a simple game entity with no additional behavior.
 */
public class Npc extends Character {

    /**
     * Creates a default NPC with placeholder values.
     */
    public Npc() {
        super();
    }

    /**
     * Creates an NPC with basic attributes and an associated texture.
     *
     * @param name the name of npc
     * @param description NPC description
     * @param stats a map containing the NPC's statistics (STR, PER, LCK, AP, INV)
     * @param texturePath the file path to the NPC's texture
     */
    public Npc(String name, String description, Map<String, Integer> stats, String texturePath) {
        super(name, description, stats, texturePath);
    }

    /**
     * Creates an NPC with full initialization including its position
     * and the sprite tile step size.
     *
     * @param name the name of npc
     * @param description NPC description
     * @param stats a map containing the NPC's statistics
     * @param posX the NPC's initial X position on the map between 0 and 50
     * @param posY the NPC's initial Y position on the map between 0 and 50
     * @param spriteName filename of the NPC's sprite
     * @param step tile size in pixels
     */
    public Npc(String name, String description, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName, Integer step) {
        super(name, description, stats, posX, posY, spriteName, step);
    }
}
