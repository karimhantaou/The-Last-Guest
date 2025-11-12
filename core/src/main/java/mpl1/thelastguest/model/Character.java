package mpl1.thelastguest.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.*;

public abstract class Character implements Movable {
    private final String name; //name of character
    private Map<String, Integer> stats; //str, per, lck, ap, inv
    private Map<String, Integer> position; // x, y
    private List<String> items; //List of items of character
    private boolean alive; //if character is current alive or not
    private final Sprite sprite; //Sprite of character
    private Integer step; // Step (size of tiled when display)
    private List<int[]> path =  new ArrayList<>(); //path to move with mousse

    //constructor
    public Character(String name, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName, Integer step) {
        Map<String, Integer> position = new HashMap<>();
        Texture texture = new Texture(Gdx.files.internal(spriteName));

        position.put("x", posX);
        position.put("y", posY);
        this.name = name;
        this.stats = stats;
        this.position = position;
        this.sprite = new Sprite(texture);
        this.sprite.setSize(step, step);
        this.sprite.setPosition((posX * step), posY * step);
        this.step = step;
        this.alive = true;
    }

    // GETTERS
    public String getName() {
        return this.name;
    }

    public int[] getPath(){
        int[] tmp;

        if (this.path.isEmpty())
            return null;
        tmp = this.path.get(0);
        this.path.remove(0);
        return tmp;
    }

    public Sprite getSprite() {
        int[] pos = getPath();

        if (pos != null) {
            setPosition(pos[0], pos[1]);
            hiddenPassage();
            try {
                Thread.sleep(100); //For display tiled by tiled
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.sprite;
    }

    //stats
    public Map<String, Integer> getStats() {
        return this.stats;
    }

    public int getStr() {
        return this.stats.get("str");
    }

    public int getPer() {
        return this.stats.get("per");
    }

    public int getLck() {
        return this.stats.get("lck");
    }

    public int getAp() {
        return this.stats.get("ap");
    }

    public int getInv() {
        return this.stats.get("inv");
    }

    // position
    public Map<String, Integer> getPosition() {
        return this.position;
    }

    public Integer getPositionX() {
        return this.position.get("x");
    }

    public Integer getPositionY() {
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

    public void setLck(int lck) {
        this.stats.put("lck", lck);
    }

    public void setAp(int ap) {
        this.stats.put("ap", ap);
    }

    public void setInv(int inv) {
        this.stats.put("inv", inv);
    }

    public void setPosition(Map<String, Integer> position) {
        this.position = position;
    }

    public void setPosition(int x, Integer y) {
        this.position.put("x", x);
        this.position.put("y", y);
        this.sprite.setPosition(x * this.step, y * this.step);
        hiddenPassage();
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    //Movable
    public void moveRight() {
        setPosition((getPositionX() + 1), getPositionY());
    }

    public void moveLeft() {
        setPosition((getPositionX() - 1), getPositionY());
    }

    public void moveUp() {
        setPosition(getPositionX(), (getPositionY() + 1));
    }

    public void moveDown() {
        setPosition(getPositionX(), (getPositionY() - 1));
    }

        //Movable the player to select point (implement BFS algo)
    public void moveToPoint(int posX, int posY) {
        TiledMap map = new TmxMapLoader().load("maps/map.tmx");
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        Queue<int[]> queue = new ArrayDeque<>();
        Map<String, String> prev = new HashMap<>();
        List<int[]> path = new ArrayList<>();
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int[][] grid = new int[murInt.getHeight()][murInt.getWidth()];
        int[] start = { getPositionX(), getPositionY() };
        int[] goal  = { posX - 11 , 50 - posY  - 1 };
        int[] current;

        for (int y = 0; y < murInt.getHeight(); y++) {
            for (int x = 0; x < murInt.getWidth(); x++) {
                if (murInt.getCell(x, y) == null)
                    grid[y][x] = 0;
                else
                    grid[y][x] = 1;
            }
        }
        queue.add(start);
        prev.put(Arrays.toString(start), null);
        while (!queue.isEmpty()) {
            current = queue.poll();
            if (Arrays.equals(current, goal))
                break;
            for (int[] dir : directions) {
                int nextX = current[0] + dir[0];
                int nextY = current[1] + dir[1];
                if (nextX >= 0 && nextY >= 0 && nextX < murInt.getWidth() && nextY < murInt.getHeight()) {
                    if (grid[nextY][nextX] == 0) {
                        int[] around = {nextX, nextY};
                        String key = Arrays.toString(around);
                        if (!prev.containsKey(key)) {
                            prev.put(key, Arrays.toString(current));
                            queue.add(around);
                        }
                    }
                }
            }
        }
        String key = Arrays.toString(goal);
        while (key != null) {
            String[] parts = key.replaceAll("[\\[\\] ]", "").split(",");
            path.add(new int[]{
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
            });
            key = prev.get(key);
        }
        Collections.reverse(path);
        if (path.size() == 1)
            return;
        this.path = path;
    }

    public void hiddenPassage(){
        if (getPositionY() == 42 || getPositionY() == 43)
            if (getPositionX() == 14 || getPositionX() == 15)
                setPosition(32, 6);
        if (getPositionY() == 6 || getPositionY() == 5)
            if (getPositionX() == 33 || getPositionX() == 34)
                setPosition(45, 41);
        if (getPositionX() == 7 && (getPositionY() == 30 || getPositionY() == 31))
            setPosition(40, 45);
        if (getPositionY() == 46 && (getPositionX() == 40 || getPositionX() == 41))
            setPosition(8, 30);
    }
}
