package mpl1.thelastguest.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Character {
    private String name;
    private Map<String, Integer> stats; //str, per, lck, ap, inv
    private Map<String, Integer> position; // x, y
    private List<String> items;
    private boolean alive = true;

    private String texturePath;

    public Character(String name, Map<String, Integer> stats, String texturePath) {
        this.name = name;
        this.stats = stats;
        this.texturePath = texturePath;

        Map<String, Integer> position = new HashMap<>();
        position.put("x", 0);
        position.put("y", 0);
        this.position = position;

    }

    // GETTERS
    public String getName() {
        return name;
    }

    //stats
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

    // position
    public Map<String, Integer> getPosition() {
        return position;
    }

    public int getPositionX(){
        return this.position.get("x");
    }

    public int getPositionY(){
        return this.position.get("y");
    }

    // items
    public List<String> getItems() {
        return items;
    }

    public int countItems() {
        return items.size();
    }

    public boolean isAlive() {
        return alive;
    }

    public String getTexturePath() {
        return texturePath;
    }

    // SETTERS

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

    public void setPosition(Map<String, Integer> position) {
        this.position = position;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}

