package mpl1.thelastguest.model.Item;

import java.util.Map;

public abstract class Item {

    private String name;
    private String fingerprint;
    private String woundType = null;

    public Item(String name){
        this.name = name;
    }

    // GETTER

    public String getName(){
        return this.name;
    }

    public String getFingerprint(){return this.fingerprint;}
    public void setFingerprint(String fingerprint){this.fingerprint = fingerprint;}

    public String getWoundType(){return this.woundType;}

    abstract Map<String, Integer> getStats();
    abstract String getAction();

}
