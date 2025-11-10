package mpl1.thelastguest.model.Item;

public class ActionItem extends Item {
    private String action;


    public ActionItem(String name, String action) {
        super(name);
        this.action = action;
    }

    // GETTER

    public String getAction() {
        return this.action;
    }
}
