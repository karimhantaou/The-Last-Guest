package mpl1.thelastguest.model.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an item that modifies one or more character statistics.
 * <p>
 * A {@code StatItem} contains a set of numerical modifiers such as:
 * <ul>
 *     <li>str — strength</li>
 *     <li>per — perception</li>
 *     <li>lck — luck</li>
 *     <li>ap — action points</li>
 *     <li>inv — inventory capacity</li>
 * </ul>
 * Stat items do not grant any special actions.
 */
public class StatItem extends Item {

    private Map<String, Integer> stats = new HashMap<>();

    /**
     * Default constructor
     */
    public StatItem() {
        super();
    }

    /**
     * Creates a stat-based item with the given name and stat.
     * @param name the name of the item
     * @param stats a map of statistics and their modifier values
     */
    public StatItem(String name, Map<String, Integer> stats) {
        super(name);
        this.stats = stats;
    }

    /**
     * Returns the stat modifiers contained in this item.
     * @return a map of stat names to integer values
     */
    @Override
    public Map<String, Integer> getStats() {
        return this.stats;
    }

    /**
     * Stat items do not provide actions, so this always returns null.
     * @return null, since StatItem has no associated action
     */
    @Override
    public String getAction() {
        return null;
    }

    /**
     * Returns the item's strength modifier.
     * @return the value of the "str" stat
     */
    public int getStr() {
        return stats.get("str");
    }

    /**
     * Returns the item's perception modifier.
     * @return the value of the "per" stat
     */
    public int getPer() {
        return stats.get("per");
    }

    /**
     * Returns the item's luck modifier.
     * @return the value of the "lck" stat
     */
    public int getLck() {
        return stats.get("lck");
    }

    /**
     * Returns the item's action points modifier.
     * @return the value of the "ap" stat
     */
    public int getAp() {
        return stats.get("ap");
    }

    /**
     * Returns the item's inventory capacity modifier.
     * @return the value of the "inv" stat
     */
    public int getInv() {
        return stats.get("inv");
    }
}
