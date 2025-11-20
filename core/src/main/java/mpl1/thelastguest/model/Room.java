package mpl1.thelastguest.model;

import mpl1.thelastguest.model.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private List<Item> items;

    public Room(String name){
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    public void removeItem(Item item){
        this.items.remove(item);
    }

    public List<Item> getItems(){
        return this.items;
    }


}
