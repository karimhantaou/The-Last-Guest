package mpl1.thelastguest.model.Item;

import java.util.Map;

/**
 * Base abstract class for all items in the game.
 * <p>
 * An {@link Item} may represent objects such as weapons, clues, or stat modifiers.
 */
public abstract class Item {

    private String name;
    private String fingerprint;
    private boolean fingerPrintFound = false;
    private final String description = null;
    protected String woundType = null;

    /**
     * Returns whether the fingerprint of this item has been discovered.
     * true if the fingerprint is found, false otherwise
     */
    public boolean isFingerPrintFound() {
        return fingerPrintFound;
    }

    /**
     * Sets whether the player has discovered this item's fingerprint.
     *
     * @param fingerPrintFound true if the fingerprint is found
     */

    public void setFingerPrintFound(boolean fingerPrintFound) {
        this.fingerPrintFound = fingerPrintFound;
    }


    /**
     * Creator with the given name.
     * A random fingerprint is automatically assigned
     * from the set {"A", "L", "W"}.
     * @param name the name of the item
     */
    public Item(String name){
        this.name = name;
        String[] fingerprints = {"A", "L", "W"};
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    /**
     * default constructor
     */
    public Item(){
        String[] fingerprints = {"A", "L", "W"};
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    // GETTER

    /**
     * Returns the name of the item.
     *
     * @return the item's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the fingerprint of the item.
     *
     * @return the fingerprint's name
     */
    public String getFingerprint(){return this.fingerprint;}

    /**
     * Assigns a fingerprint identifier to this item.
     *
     * @param fingerprint the new fingerprint value
     */
    public void setFingerprint(String fingerprint){this.fingerprint = fingerprint;}

    /**
     * Returns the woundType of the item.
     *
     * @return the woundType's name
     */
    public String getWoundType(){return this.woundType;}

    /**
     * Returns the statistical effects of this item.
     * This is implemented by subclasses such as {@link StatItem}.
     * @return a map of stat modifiers, or null if none
     */
    abstract Map<String, Integer> getStats();

    /**
     * Returns the action granted by this item, if any.
     * Implemented by subclasses such as {@link ActionItem}.
     * @return the action name, or null if the item provides no action
     */
    abstract String getAction();

    /**
     * Returns the description of the item.
     *
     * @return the description's name
     */
    public String getDescription() {
        return description;
    }
}
