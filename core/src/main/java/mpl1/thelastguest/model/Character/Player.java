package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.List;
import java.util.Objects;

public class Player{
    private String username;
    private Character playerCharacter;

    public Player(String username, Character playerCharacter) {
        this.username = username;
        this.playerCharacter = playerCharacter;
    }

    public String getUsername() {
        return username;
    }

    public Character getPlayerCharacter() {
        return playerCharacter;
    }

    // Itemsss

    private void addStats(StatItem item){
        playerCharacter.setStr(playerCharacter.getStr() + item.getStr());
        playerCharacter.setPer(playerCharacter.getPer() + item.getPer());
        playerCharacter.setLck(playerCharacter.getLck() + item.getLck());
        playerCharacter.setAp(playerCharacter.getAp() + item.getAp());
        playerCharacter.setInv(playerCharacter.getInv() + item.getInv());
    }

    private void removeStats(StatItem item){
        playerCharacter.setStr(playerCharacter.getStr() - item.getStr());
        playerCharacter.setPer(playerCharacter.getPer() - item.getPer());
        playerCharacter.setLck(playerCharacter.getLck() - item.getLck());
        playerCharacter.setAp(playerCharacter.getAp() - item.getAp());
        playerCharacter.setInv(playerCharacter.getInv() - item.getInv());
    }

    public void pickItem(Item item){
        if(this.playerCharacter.getItems().size() < playerCharacter.getInv()){
            if(item.getClass() == StatItem.class){
                StatItem statItem = (StatItem)item;
                addStats(statItem);
            }
            this.playerCharacter.setItem(item);
            System.out.println(item.getName() + " added to the inventory.");
        } else{
            System.out.println("Inventory full !");
        }
    }

    public void dropItem(Item item){
        if(item.getClass() == StatItem.class){
            StatItem statItem = (StatItem)item;
            removeStats(statItem);
        }
        this.playerCharacter.dropItem(item);
        System.out.println(item.getName() + " dropped from the inventory.");
    }

    // Actions

    // Permet de savoir si un item permet de faire une action spécial
    private boolean canDoAction(String action){
        List<Item> items = playerCharacter.getItems();
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
