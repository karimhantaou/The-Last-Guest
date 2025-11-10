package mpl1.thelastguest.model.Item;

import java.util.Map;

public abstract class Item {
    private String name;

    public Item(String name){
        this.name = name;
    }

    // GETTER

    public String getName(){
        return this.name;
    }
    public Map<String, Integer> getStats(){return null;}
    public String getAction(){return null;}
}
