package mpl1.thelastguest.model.Item;

import java.util.Map;

public class ActionItem extends Item {

    private String action;

    public ActionItem(){super();}

    public ActionItem(String name, String action) {
        super(name);
        this.action = action;
    }

    public ActionItem(String name, String action, String woundType) {
        super(name);
        this.action = action;
        this.woundType = woundType;
    }

    // GETTER

    @Override
    public String getAction() {
        return this.action;
    }

    @Override
    public Map<String, Integer> getStats(){return null;}

    @Override
    public String getWoundType() {
        return woundType;
    }
}
