package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.*;

public abstract class Character {

    // Nom du personnage
    private String name;

    // Statistiques du personnage
    private Map<String, Integer> stats; //str, per, lck, ap, inv

    // Position du personnage
    private Map<String, Integer> position; // x, y

    // Inventaire du personnage
    private List<Item> items =  new ArrayList<>();

    // Empreintes du personnages
    private String fingerprint;

    // Indices si personne morte
    private Map<String,String> clues; // fingerprint,

    // Etat du personnage
    private boolean alive;

    private String texturePath;

    // Constructeur pour les pnj
    public Character() {
        this.items = new ArrayList<>();
        this.alive = true;
        this.position = new HashMap<>();
        this.position.put("x", 0);
        this.position.put("y", 0);
        this.texturePath = "placeholder.png";

        String[] fingerprints = {"A", "L", "W"};
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];

        this.clues = new HashMap<>();
    }

    // Constructeur pour le joueur et le tueur
    public Character(String name, Map<String, Integer> stats, String texturePath) {
        this.name = name;
        this.stats = stats;
        this.texturePath = texturePath;

        Map<String, Integer> position = new HashMap<>();
        position.put("x", 0);
        position.put("y", 0);
        this.position = position;

        String[] fingerprints = {"A", "L", "W"};
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];

        this.clues = new HashMap<>();

        this.alive = true;
    }

    // NAME
    public String getName() {
        return name;
    }


    // POSITION

    public Map<String, Integer> getPosition() {
        return position;
    }

    public int getPositionX(){
        return this.position.get("x");
    }

    public int getPositionY(){
        return this.position.get("y");
    }

    public void setPosition(Map<String, Integer> position) {
        this.position = position;
    }

    public void move(int x, int y){
        this.position.put("x", x);
        this.position.put("y", y);
    }

    // TEXTURE

    public String getTexturePath() {
        return texturePath;
    }


    // STATS

    public Map<String, Integer> getStats() {
        return stats;
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

    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    }

    public void setStr(int str) {
        this.stats.put("str", str);
    }

    public void setPer(int per) {
        this.stats.put("per", per);
    }

    public void setLck(int lck){
        this.stats.put("lck", lck);
    }

    public void setAp(int ap){
        this.stats.put("ap", ap);
    }

    public void setInv(int inv){
        this.stats.put("inv", inv);
    }


    // ITEMS

    public List<Item> getItems() {
        return items;
    }

    public int countItems() {
        return items.size();
    }

    private void addStats(StatItem item){
        setStr(getStr() + item.getStr());
        setPer(getPer() + item.getPer());
        setLck(getLck() + item.getLck());
        setAp(getAp() + item.getAp());
        setInv(getInv() + item.getInv());
    }

    private void removeStats(StatItem item){
        setStr(getStr() - item.getStr());
        setPer(getPer() - item.getPer());
        setLck(getLck() - item.getLck());
        setAp(getAp() - item.getAp());
        setInv(getInv() - item.getInv());
    }

    public void pickItem(Item item){
        if(countItems() < getInv()){
            if(item.getClass() == StatItem.class){
                StatItem statItem = (StatItem)item;
                addStats(statItem);
            }
            this.items.add(item);
            System.out.println(item.getName() + " added to the inventory.");
        } else{
            System.out.println("Inventory full !");
        }
    }

    public void dropItem(Item item){
        if(items.contains(item)){
            if(item.getClass() == StatItem.class){
                StatItem statItem = (StatItem)item;
                removeStats(statItem);
            }
            this.items.remove(item);
            System.out.println(item.getName() + " dropped from the inventory.");
        } else{
            System.out.println("No item: " + item.getName());
        }
    }


    // FINGERPRINT

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }


    // CLUES
    public Map<String, String> getClues(){
        return clues;
    }

    public void addClues(Character murderer, Item weapon){
        this.clues.put("fingerprint", murderer.getFingerprint());
        this.clues.put("wound", weapon.getWoundType());
    }

    // ALIVE

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    // ACTIONS

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

    // Exemple d'actions
    public void openDoor(){
        if(canDoAction("Open door")){
            System.out.println("open door");
        }
    }

    public void kill(Npc npc, Item weapon){
        npc.addClues(this, weapon);
        npc.setAlive(false);
    }

    // La méthode qui sera utilisée pour afficher les actions. On mettra un objet en paramètre pour savoir ce qu'il peut faire.
    // Pour l'instant ça print juste mais à therme afficher des boutons différents. (dans la vue surement)
    public void displayActions(){
        System.out.println("Move");

        for(Item item : items){
            if(item.getClass() == ActionItem.class){
                ActionItem actionItem = (ActionItem)item;
                System.out.println(actionItem.getAction());
            }
        }
    }

}


