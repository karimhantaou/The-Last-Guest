package mpl1.thelastguest.model.Character;

import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Character {

    // Nom du personnage
    protected String name;

    // Statistiques du personnage
    protected Map<String, Integer> stats; //str, per, lck, ap, inv

    // Position du personnage
    protected Map<String, Integer> position; // x, y

    // Inventaire du personnage
    protected List<Item> items =  new ArrayList<>();

    // Empreintes du personnages
    protected String fingerprint;

    // Indices si personne morte
    private Map<String,String> clues; // fingerprint,

    // Etat du personnage
    protected boolean alive;

    protected String texturePath;

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
        if(item.getClass() == StatItem.class){
            StatItem statItem = (StatItem)item;
            removeStats(statItem);
        }
        this.items.remove(item);
        System.out.println(item.getName() + " dropped from the inventory.");
    }


    // FINGERPRINT

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }


    // ALIVE

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


}


