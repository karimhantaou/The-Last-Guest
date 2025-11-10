package mpl1.thelastguest.model.Item;

public abstract class Item {
    private String name;

    public Item(String name){
        this.name = name;
    }

    // GETTER

    public String getName(){
        return this.name;
    }
}
