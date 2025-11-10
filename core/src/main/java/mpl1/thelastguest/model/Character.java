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
    private Integer step;

    public Character(String name, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName, Integer step) {
        this.name = name;
        this.stats = stats;

        Map<String, Integer> position = new HashMap<>();
        position.put("x", posX);
        position.put("y", posY);
        this.position = position;
        Texture texture = new Texture(Gdx.files.internal(spriteName));
        this.sprite = new Sprite(texture);
        this.sprite.setSize(step, step);
        this.sprite.setPosition((posX * step), posY * step);
        this.step = step;
        this.alive = true;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    // GETTERS
    public String getName() {
        return this.name;
    }

    //stats
    public Map<String, Integer> getStats() {
        return this.stats;
    }

    public int getStr(){
        return this.stats.get("str");
    }

    public int getPer(){
        return this.stats.get("per");
    }

    public int getLck(){
        return this.stats.get("lck");
    }

    public int getAp(){
        return this.stats.get("ap");
    }

    public int getInv(){
        return this.stats.get("inv");
    }

    // position
    public Map<String, Integer> getPosition() {
        return this.position;
    }

    public Integer getPositionX(){
        return this.position.get("x");
    }

    public Integer getPositionY(){
        return this.position.get("y");
    }

    // items
    public List<String> getItems() {
        return this.items;
    }

    public int countItems() {
        return this.items.size();
    }

    public boolean isAlive() {
        return this.alive;
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
        this.sprite.setPosition(x, y);
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void moveRight() {
        this.sprite.setPosition((getPositionX() + 1) * this.step, getPositionY() * this.step);
        this.position.put("x", this.position.get("x") + 1);
    }

    public void moveLeft() {
        this.sprite.setPosition((getPositionX() - 1) * this.step, getPositionY() * this.step);
        this.position.put("x", this.position.get("x") - 1);
    }

    public void moveUp() {
        this.sprite.setPosition(getPositionX() * this.step, (getPositionY() + 1) * this.step);
        this.position.put("y", this.position.get("y") + 1);
    }

    public void moveDown() {
        this.sprite.setPosition(getPositionX() * this.step, (getPositionY() - 1) * this.step);
        this.position.put("y", this.position.get("y") - 1);
    }
}
