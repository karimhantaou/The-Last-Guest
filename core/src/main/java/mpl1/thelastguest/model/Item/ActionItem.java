package mpl1.thelastguest.model.Item;

import java.util.Map;

/**
 * Represents an item capable of performing a specific action.
 * <p>
 * Unlike {@link StatItem}, an ActionItem does not modify character statistics.
 * Instead, it grants the ability to perform special actions such as "kill" or "open door".
 */
public class ActionItem extends Item {

    private String action;

    /**
     * Default constructor .
     */
    public ActionItem(){
        super();
    }

    /**
     * Creates an ActionItem that grants a specific action.
     *
     * @param name the name of the item
     * @param action the action that this item enables
     */
    public ActionItem(String name, String action) {
        super(name);
        this.action = action;
    }

    /**
     * Creates an ActionItem with an associated action and a wound type,
     * used for items that can kimm.
     *
     * @param name the name of the item
     * @param action the action that this item enables
     * @param woundType the wound type caused when the item is used as a weapon
     */
    public ActionItem(String name, String action, String woundType) {
        super(name);
        this.action = action;
        this.woundType = woundType;
    }

    // GETTER

    /**
     * Returns the action associated with this item.
     *
     * @return the action name
     */
    @Override
    public String getAction() {
        return this.action;
    }

    /**
     * Action items do not provide statistic , so this always returns null.
     *
     * @return null, since ActionItem has no stats
     */

    @Override
    public Map<String, Integer> getStats(){return null;}

    /**
     * Returns the wound type caused by this item, if any.
     *
     * @return the wound type, or null if the item does not inflict wounds
     */
    @Override
    public String getWoundType() {
        return woundType;
    }
}
