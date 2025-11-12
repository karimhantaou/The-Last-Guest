package mpl1.thelastguest.model.Item;

import java.util.Map;

public class ActionItem extends Item {

    private String action;

    public ActionItem(String name, String action) {
        super(name);
        this.action = action;
    }

    // GETTER

    @Override
    public String getAction() {
        return this.action;
    }

    @Override
    public Map<String, Integer> getStats(){return null;}
}
