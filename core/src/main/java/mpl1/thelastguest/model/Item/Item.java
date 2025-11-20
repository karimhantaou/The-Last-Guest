package mpl1.thelastguest.model.Item;

import java.util.Map;

public abstract class Item {

    private String name;
    private String fingerprint;
    private String description;
    protected String woundType = null;


    public Item(String name){
        this.name = name;
        String[] fingerprints = {"A", "L", "W"};
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    public Item(){
        String[] fingerprints = {"A", "L", "W"};
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
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

    public String getDescription() {
        return description;
    }
}
