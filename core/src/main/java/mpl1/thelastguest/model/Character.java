package mpl1.thelastguest.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Character implements Movable{
    private final String name;
    private Map<String, Integer> stats; //str, per, lck, ap, inv
    private Map<String, Integer> position; // x, y
    private List<String> items;
    private boolean alive;
    private final Sprite sprite;

    public Character(String name, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName) {
        this.name = name;
        this.stats = stats;

        Map<String, Integer> position = new HashMap<>();
        position.put("x", posX);
        position.put("y", posY);
        this.position = position;
        Texture texture = new Texture(Gdx.files.internal(spriteName));
        this.sprite = new Sprite(texture);
        this.sprite.setSize(14, 14);
        this.sprite.setPosition(posX, posY);
        this.alive = true;
    }

    public Sprite getSprite() {
        return sprite;
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

    public Integer getPositionX(){
        return this.position.get("x");
    }

    public Integer getPositionY(){
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

    public void setPosition(Integer x, Integer y) {
        this.position.put("x", x);
        this.position.put("y", y);
        sprite.setPosition(x, y);
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void moveRight(int step) {
        this.sprite.setPosition(this.sprite.getX() + step, this.sprite.getY());
        position.put("x", position.get("x") + step);
    }

    public void moveLeft(int step) {
        this.sprite.setPosition(this.sprite.getX() - step, this.sprite.getY());
        position.put("x", position.get("x") - step);
    }

    public void moveUp(int step) {
        this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() + step);
        position.put("y", position.get("y") + step);
    }

    public void moveDown(int step) {
        this.sprite.setPosition(this.sprite.getX(), this.sprite.getY() - step);
        position.put("y", position.get("y") - step);
    }
}
