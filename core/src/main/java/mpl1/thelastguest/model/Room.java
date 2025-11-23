package mpl1.thelastguest.model;

import mpl1.thelastguest.model.Item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the game's environment.
 */
public class Room {
    private String name;
    private List<Item> items;
    private boolean locked = false;

    /**
     * Creates a new room with the given name and an empty item list.
     * @param name the name of the room
     */
    public Room(String name){
        this.name = name;
        this.items = new ArrayList<>();
    }

    /**
     * Returns the name of the room.
     * @return the room name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an item to this room.
     * @param item the item to add
     */
    public void addItem(Item item){
        this.items.add(item);
    }

    /**
     * Removes an item from this room.
     * @param item the item to remove
     */
    public void removeItem(Item item){
        this.items.remove(item);
    }

    /**
     * Returns the list of items inside this room.
     * @return a list of items
     */
    public List<Item> getItems(){
        return this.items;
    }

    /**
     * Indicates whether the room is locked.
     * @return ture if the room is locked, false otherwise
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the locked state of the room.
     * @param locked true to lock the room, false to unlock it
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
