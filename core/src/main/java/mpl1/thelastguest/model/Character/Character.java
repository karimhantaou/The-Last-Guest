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

public abstract class Character {

    // Nom du personnage
    private String name;

    // Statistiques du personnage
    private Map<String, Integer> stats; //str, per, lck, ap, inv

    // Position du personnage
    private int x;
    private int y;

    // Inventaire du personnage
    private List<Item> items =  new ArrayList<>();

    // Empreintes du personnages
    private String fingerprint;

    // Indices si personne morte
    private Map<String,String> clues; // fingerprint,

    // Etat du personnage
    private boolean alive;

    private String texturePath;
    private final Sprite sprite; //Sprite of character

    private List<int[]> path =  new ArrayList<>(); //path to move with mousse

    private int step;

    private Integer nbPath;


    // Constructeur pour les pnj
    public Character() {
        this.items = new ArrayList<>();
        this.alive = true;
        this.x = 0; this.y = 0;
        this.texturePath = "placeholder.png";
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
        int step = 50;
        this.sprite.setSize(step, step);
        this.sprite.setPosition((this.x * step), this.y * step);
        this.step = step;

        String[] fingerprints = {"A", "L", "W"};
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];

        this.clues = new HashMap<>();
    }

    // Constructeur pour le joueur et le tueur
    public Character(String name, Map<String, Integer> stats, String texturePath) {
        this.name = name;
        this.stats = stats;
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
        int step = 50;
        this.sprite.setSize(step, step);
        this.sprite.setPosition((this.x * step), this.y * step);


        this.x = 0; this.y = 0;

        String[] fingerprints = {"A", "L", "W"};
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];

        this.clues = new HashMap<>();

        this.alive = true;
    }

    // NAME
    public String getName() {
        return name;
    }

    // TEXTUREPATH
    public String getTexturePath(){
        return texturePath;
    }

    // POSITION


    public int getX(){
        return x;
    }

    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        this.sprite.setPosition(x * this.step, y * this.step);
        hiddenPassage();
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
        this.setStr(this.getStr() + item.getStr());
        this.setPer(this.getPer() + item.getPer());
        this.setLck(this.getLck() + item.getLck());
        this.setAp(this.getAp() + item.getAp());
        this.setInv(this.getInv() + item.getInv());
    }

    private void removeStats(StatItem item){
        this.setStr(this.getStr() - item.getStr());
        this.setPer(this.getPer() - item.getPer());
        this.setLck(this.getLck() - item.getLck());
        this.setAp(this.getAp() - item.getAp());
        this.setInv(this.getInv() - item.getInv());
    }

    public boolean pickItem(Item item){
        if(countItems() < getInv()){
            if(item.getClass() == StatItem.class){
                StatItem statItem = (StatItem)item;
                this.addStats(statItem);
            }
            this.items.add(item);
            System.out.println(item.getName() + " added to the inventory.");
            return true;
        } else{
            System.out.println("Inventory full !");
            return false;
        }
    }

    public boolean dropItem(Item item){
        if(items.contains(item)){
            if(item.getClass() == StatItem.class){
                StatItem statItem = (StatItem)item;
                removeStats(statItem);
            }
            this.items.remove(item);
            System.out.println(item.getName() + " dropped from the inventory.");
            return true;
        } else{
            System.out.println("No item: " + item.getName());
            return false;
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
                Thread.sleep(100 / this.nbPath); //For display tiled by tiled
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.sprite;
    }

    public void hiddenPassage(){
        if (getY() == 42 || getY() == 43)
            if (getX() == 14 || getX() == 15)
                setPosition(32, 6);
        if (getY() == 6 || getY() == 5)
            if (getX() == 33 || getX() == 34)
                setPosition(45, 41);
        if (getX() == 7 && (getY() == 30 || getY() == 31))
            setPosition(40, 45);
        if (getY() == 46 && (getX() == 40 || getX() == 41))
            setPosition(8, 30);
    }

    //Movable
    public void moveRight() {
        setPosition((getX() + 1), getY());
    }

    public void moveLeft() {
        setPosition((getX() - 1), getY());
    }

    public void moveUp() {
        setPosition(getX(), (getY() + 1));
    }

    public void moveDown() {
        setPosition(getX(), (getY() - 1));
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
        int[] start = { getX(), getY() };
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
        this.nbPath = path.size();
    }

}


