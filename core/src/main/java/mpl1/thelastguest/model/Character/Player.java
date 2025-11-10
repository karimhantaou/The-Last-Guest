package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.List;
import java.util.Objects;

public class Player extends Character {

    private boolean inspector = true;

    public Player(Npc npc){
        super(npc.getName(), npc.getStats(), npc.getTexturePath());
    }

    public boolean isInspector() {
        return inspector;
    }

    // Actions

    // Permet de savoir si un item permet de faire une action spécial
    private boolean canDoAction(String action){
        List<Item> items = getItems();
        for(Item item : items){
            if(item.getClass() == ActionItem.class){
                ActionItem actionItem = (ActionItem)item;
                if(Objects.equals(actionItem.getAction(), action)){
                    return true;
                }
            }
        }
        return false;
    }

    public void openDoor(){
        if(canDoAction("Open door")){
            System.out.println("open door");
        }
    }

    public void kill(){
        if(canDoAction("kill")){
            System.out.println("kill");
        }
    }

    // La méthode qui sera utilisée pour afficher les actions. On mettra un objet en paramètre pour savoir ce qu'il peut faire.
    public void displayActions(){
        System.out.println("Move");

        if(canDoAction("kill")){
            System.out.println("Kill");
        }

        if(canDoAction("Open door")){
            System.out.println("Open door");
        }
    }
}
