package mpl1.thelastguest.model.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.*;

/**
 * Abstract representation of a game character (NPC, Player, Murderer).
 * <p>
 * A character has:
 * <ul>
 *     <li>A name and description</li>
 *     <li>Base statistics (Strength, Perception, Luck, Action Points, Inventory size)</li>
 *     <li>Position on the tile map</li>
 *     <li>Items and clues</li>
 *     <li>A sprite and texture</li>
 *     <li>A fingerprint used for investigations</li>
 * </ul>
 * This class also handles movement using BFS pathfinding, hidden passages,
 * inventory management, and basic action validation.
 */
public abstract class Character {
    // Nom du personnage
    private final String name; //name of character
    private final String description;

    // Statistiques du personnage
    private Map<String, Integer> stats = new HashMap<>(); //str, per, lck, ap, inv

    // Position du personnage
    private int x;
    private int y;

    // Inventaire du personnage
    private List<Item> items =  new ArrayList<>(); //List of items of character

    // Empreintes du personnages
    private String fingerprint;
    private boolean fingerPrintFound = false;

    // Empreintes du personnages
    private Map<String,String> clues = new HashMap<>(); // fingerprint,

    // Etat du personnage
    private boolean alive = true; //if character is current alive or not

    private Sprite sprite; //Sprite of character
    private String texturePath;
    private int startAp;

    private Integer step = 1600 / 50; // Step (size of tiled when display)
    protected List<int[]> path =  new ArrayList<>(); //path to move with mousse
    protected Integer nbPath = 0;
    protected boolean isEnd = false;

    /**
     * Default constructor for NPC generation without predefined data.
     * Creates a placeholder NPC with a random fingerprint.
     */
    public Character() {
        Texture texture = new Texture(Gdx.files.internal("placeholder.png"));
        String[] fingerprints = {"A", "L", "W"};

        this.name = "placeholder";
        this.description = "No description";
        this.x = 0;
        this.y = 0;
        this.texturePath = "placeholder.png";
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    /**
     * @return Character description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Constructor for predefined NPCs loaded from JSON.
     *
     * @param name Character name
     * @param description Character description
     * @param stats Character statistics map
     * @param texturePath Sprite texture path
     */
    public Character(String name, String description, Map<String, Integer> stats, String texturePath) {
        if (texturePath != null) {
            Texture texture = new Texture(Gdx.files.internal(texturePath));
            this.sprite = new Sprite(texture);
        }
        String[] fingerprints = {"A", "L", "W"};

        this.name = name;
        this.description = description;
        this.stats = stats;
        this.x = 0;
        this.y = 0;

        this.texturePath = texturePath;
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    /**
     * Constructor used for Player and Murderer characters.
     *
     * @param name Character name
     * @param description Character description
     * @param stats Character statistics
     * @param posX Initial X position
     * @param posY Initial Y position
     * @param spriteName Sprite texture file
     * @param step Tile step size
     */
    public Character(String name, String description, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName, Integer step) {
        if (spriteName != null) {
            Texture texture = new Texture(Gdx.files.internal(spriteName));
            this.sprite = new Sprite(texture);
            this.sprite.setPosition(posX * step, posY * step);
        }
        String[] fingerprints = {"A", "L", "W"};

        this.name = name;
        this.description = description;
        this.stats = stats;
        this.x = posX;
        this.y = posY;
        this.texturePath = spriteName;
        this.step = step;
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    /**
     * @return Path to the texture file.
     */
    public String getTexturePath() {
        return texturePath;
    }

    /**
     * Builds the sprite using the texture path.
     */
    public void buildSprite(){
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
    }

    /**
     * Returns the sprite, updating its position for all path.
     * Includes a small delay to visually animate tile by tile movement.
     *
     * @return Character sprite
     */
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

    /**
     * @return Character name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Tile step size.
     */
    public int getStep() {
        return this.step;
    }

    /**
     * Sets tile step size.
     *
     * @param step step value
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * Gets the next tile in the movement path.
     * Remove one tile from the path each time it's called.
     *
     * @return Next coordinate [x,y] or null if path is empty.
     */
    public int[] getPath(){
        int[] tmp;

        if (this.path.isEmpty())
            return null;
        tmp = this.path.get(0);
        this.path.remove(0);
        this.nbPath--;
        if (nbPath == 0)
            isEnd = true;
        return tmp;
    }
    /**
     * @return Number of tiles left in the movement path.
     */
    public int getNbPath(){
        return this.nbPath;
    }

    /**
     * Sets number of current steps to point.
     *
     * @param nbPath number of tiles
     */
    public void  setNbPath(int nbPath){
        this.nbPath = nbPath;
    }

    /**
     * @return True if the character has reached the end of its path.
     */
    public boolean getIsEnd() {
        return this.isEnd;
    }

    /**
     * Sets movement state.
     *
     * @param isEnd true if end
     */
    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    // position

    /**
     * @return X tile position.
     */
    public Integer getX() {
        return this.x;
    }

    /**
     * @return Y tile position.
     */
    public Integer getY() {
        return this.y;
    }

    /**
     * Sets Y coordinate.
     *
     * @param y new Y position
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Sets X coordinate.
     *
     * @param x new X position
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Updates character and sprite position.
     *
     * @param x new X position
     * @param y new Y position
     */
    public void setPosition(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if (this.sprite != null)
            this.sprite.setPosition(x * this.step, y * this.step);
        hiddenPassage();
    }

    /**
     * @return Stats map.
     */
    public Map<String, Integer> getStats() {
        return this.stats;
    }

    /**
     * @return Strength stat.
     */
    public int getStr(){
        return this.stats.get("str");
    }

    /**
     * @return Perception stat.
     */
    public int getPer(){
        return this.stats.get("per");
    }

    /**
     * @return Luck stat.
     */
    public int getLck(){
        return this.stats.get("lck");
    }

    /**
     * @return Action Point stat.
     */
    public int getAp(){
        return this.stats.get("ap");
    }

    /**
     * @return Inventory size stat.
     */
    public int getInv(){
        return this.stats.get("inv");
    }

    /**
     * @return Start Action Point stat.
     */
    public int getStartAp(){
        return this.startAp;
    }

    /**
     * Sets starting Action Point.
     *
     * @param startAp initial Action Point
     */
    public void  setStartAp(int startAp){
        this.startAp = startAp;
    }

    /**
     * Sets Stats map.
     *
     * @param stats Stats map.
     */
    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    }

    /**
     * Sets Strength stat.
     *
     * @param str Strength stat.
     */
    public void setStr(int str) {
        this.stats.put("str", str);
    }

    /**
     * Sets perception stat.
     *
     * @param per perception stat.
     */
    public void setPer(int per) {
        this.stats.put("per", per);
    }

    /**
     * Sets luck stat.
     *
     * @param lck luck stat.
     */
    public void setLck(int lck){
        this.stats.put("lck", lck);
    }

    /**
     * Sets Action point stat.
     *
     * @param ap Action point stat.
     */
    public void setAp(int ap){
        this.stats.put("ap", ap);
    }

    /**
     * Sets inventory size stat.
     *
     * @param inv inventory size stat.
     */
    public void setInv(int inv){
        this.stats.put("inv", inv);
    }

    // ITEMS

    /**
     * @return List of items in inventory.
     */
    public List<Item> getItems() {
        return this.items;
    }

    /**
     * Searches item by name.
     *
     * @param itemName item name
     * @return Item or null
     */

    public Item getItem(String itemName) {
        for (Item item : this.items) {
            if (item.getName().equals(itemName)) {
                return  item;
            }
        }
        return null;
    }

    /**
     * Searches an item based on its action type.
     *
     * @param action action string
     * @return Item or null
     */
    public Item getItemByAction(String action) {
        for (Item item : this.items) {
            if (item.getClass() == ActionItem.class && ((ActionItem) item).getAction().equals(action)) {
                return  item;
            }
        }
        return null;
    }

    /**
     * @return All weapons ("kill" action) in inventory.
     */
    public List<Item> getWeapons(){
        List<Item> weapons = new ArrayList<>();
        for (Item item : this.items) {
            if(item.getClass() == ActionItem.class && ((ActionItem) item).getAction().equals("kill")){
                weapons.add(item);
            }
        }
        return weapons;
    }

    /**
     * @return True if fingerprint was already found.
     */
    public boolean isFingerPrintFound() {
        return fingerPrintFound;
    }

    /**
     * Sets fingerprint discovery state.
     *
     * @param fingerPrintFound whether found
     */
    public void setFingerPrintFound(boolean fingerPrintFound) {
        this.fingerPrintFound = fingerPrintFound;
    }

    /**
     * @param items list of Item.
     */
    public void getItem(List<Item> items) {
    }

    /**
     * @return number of items in inventory.
     */
    public int countItems() {
        return this.items.size();
    }

    /**
     * Adds stats from a StatItem.
     *
     * @param item stat item
     */
    private void addStats(StatItem item){
        this.setStr(this.getStr() + item.getStr());
        this.setPer(this.getPer() + item.getPer());
        this.setLck(this.getLck() + item.getLck());
        this.setStartAp(this.getStartAp() + item.getAp());
        this.setInv(this.getInv() + item.getInv());
    }

    /**
     * Remove stats from a StatItem.
     *
     * @param item stat item
     */
    private void removeStats(StatItem item){
        this.setStr(this.getStr() - item.getStr());
        this.setPer(this.getPer() - item.getPer());
        this.setLck(this.getLck() - item.getLck());
        this.setStartAp(this.getStartAp() - item.getAp());
        this.setInv(this.getInv() - item.getInv());
    }

    /**
     * try to pick up an item.
     *
     * @param item item to pick
     * @return True if successful, false if you don't have enough space
     */
    public boolean pickItem(Item item){
        if(countItems() < getInv()){
            if(item.getClass() == StatItem.class){
                StatItem statItem = (StatItem)item;
                this.addStats(statItem);
            }
            this.items.add(item);
            return true;
        } else{
            return false;
        }
    }

    /**
     * @return True if inventory has free space.
     */
    public boolean enoughInventory(){
        return this.items.size() < this.getInv();
    }

    /**
     * Drops an item and removes its stats of character.
     *
     * @param item item to drop
     * @return True if removed
     */
    public boolean dropItem(Item item){
        if(this.items.contains(item)){
            if(item.getClass() == StatItem.class){
                StatItem statItem = (StatItem)item;
                removeStats(statItem);
            }
            this.items.remove(item);
            return true;
        } else{
            return false;
        }
    }

    /**
     * Checks if the character has enough Action point for action.
     *
     * @param ap required AP
     * @return true if enough
     */
    public boolean enoughAp(int ap){
        return this.getAp() >= ap;
    }

    /**
     * @return Character fingerprint.
     */
    public String getFingerprint() {
        return this.fingerprint;
    }

    /**
     * Sets fingerprint.
     *
     * @param fingerprint value
     */
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }


    /**
     * @return Map of collected clues.
     */
    public Map<String, String> getClues(){
        return this.clues;
    }

    /**
     * @return Wound clue.
     */
    public String getClueWound(){
        return this.clues.get("wound");
    }

    /**
     * @return fingerprint clue.
     */
    public String getClueFingerprint(){
        return this.clues.get("fingerprint");
    }

    /**
     * Adds clues to this character based on murder weapon and murderer.
     *
     * @param murderer murderer character
     * @param weapon   weapon used
     */
    public void addClues(Character murderer, Item weapon){
        this.clues.put("fingerprint", murderer.getFingerprint());
        this.clues.put("wound", weapon.getWoundType());
    }

    /**
     * @return True if character is alive.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Sets if character is alive or not.
     *
     * @param alive state
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Checks whether one of the character's items allows performing a given action.
     *
     * @param action action string
     * @return true if allowed
     */    public boolean canDoAction(String action){
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

    /**
     * do action open door
     */
    public void openDoor(){
        if(canDoAction("Open door")){
        }
    }

    /**
     * Attempts to kill a character.
     *
     * @param npcs list of characters
     * @return true if Character is murderer and kill succeeded
     */
    public boolean kill(List <Character> npcs){
        return false;
    }

    /**
     * Displays available actions
     */
    public void displayActions(){
        for(Item item : this.items){
            if(item.getClass() == ActionItem.class){
                ActionItem actionItem = (ActionItem)item;
            }
        }
    }

    /**
     * Moves the character to the specified tile using BFS pathfinding.
     *
     * @param posX target X tile
     * @param posY target Y tile
     * @return True if movement is possible
     */
    public boolean moveToPoint(int posX, int posY) {
        TiledMap map = new TmxMapLoader().load("maps/map.tmx");
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        Queue<int[]> queue = new ArrayDeque<>();
        Map<String, String> prev = new HashMap<>();
        List<int[]> path = new ArrayList<>();
        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
        int[][] grid = new int[murInt.getHeight()][murInt.getWidth()];
        int[] start = { getX(), getY() };
        int[] goal  = { posX , posY };
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

        if (path.size() == 1 || path.size() - 1 > getAp()){
            return false;
        }
        setAp(getAp() - (path.size() - 1));
        this.path = path;
        this.nbPath = path.size();
        this.isEnd = false;
        return true;
    }

    /**
     * Teleports the character when reaching special tiles containing hidden passages.
     */
    public void hiddenPassage(){
        if (getY() == 42 || getY() == 43)
            if (getX() == 14 || getX() == 15)
                setPosition(32, 6);
        if (getY() == 6 || getY() == 5)
            if (getX() == 33 || getX() == 34)
                setPosition(17, 41);
        if (getX() == 7 && (getY() == 30 || getY() == 31))
            setPosition(40, 45);
        if ((getX() == 40 || getX() == 41) && getY() == 46)
            setPosition(8, 30);
    }

    /**
     * Detects the room the character is currently standing in based on the map tile ID.
     *
     * @return Room name (Kitchen, Bedroom, Hall, ...)
     */
    public String getRoom() {
        TiledMap map = new TmxMapLoader().load("maps/map.tmx");
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("sol");
        int tiled = murInt.getCell(this.x,this.y).getTile().getId();
        if (tiled == 1593)
            return "Kitchen";
        else if (tiled == 1484)
            return "Second bedroom";
        else if (tiled >= 1569 && tiled <= 1579)
            return "First bedroom";
        else if (tiled == 1482)
            return "Laboratory";
        else if (tiled == 1483)
            return "Diner room";
        else if (tiled == 1485)
            return "Living room";
        else if (tiled == 1486)
            return "Small diner room";
        else if (tiled >= 1636 && tiled <= 1657)
            return "Laundry room";
        return "Hall";
    }
    public boolean isPerceptible(int posX, int posY) {
        return false;
    }

    public boolean isClose(Character character){
        return character.getX() >= getX() - 1 && character.getX() <= getX() + 1 && character.getY() >= getY() - 1 && character.getY() <= getY() + 1;
    }
}
