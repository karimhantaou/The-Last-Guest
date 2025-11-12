package mpl1.thelastguest.model.Item;

import java.util.HashMap;
import java.util.Map;

public class StatItem extends Item {

    private Map<String, Integer> stats = new HashMap<>(); //str, per, lck, ap, inv

    public StatItem(String name, Map<String, Integer> stats){
        super(name);
        this.stats = stats;
    }

    // GETTER

    @Override
    public Map<String, Integer> getStats(){
        return this.stats;
    }

    @Override
    public String getAction(){
        return null;
    }

    public int getStr(){
        return stats.get("str");
    }

    public int getPer(){
        return stats.get("per");
    }

    public int getLck(){
        return stats.get("lck");
    }

    public int getAp(){
        return stats.get("ap");
    }

    public int getInv(){
        return stats.get("inv");
    }
}
