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
import mpl1.thelastguest.model.Movable;

import java.util.*;

public abstract class Character implements Movable {
    // Nom du personnage
    private final String name; //name of character

    // Statistiques du personnage
    private Map<String, Integer> stats; //str, per, lck, ap, inv

    // Position du personnage
    private int x;
    private int y;

    // Inventaire du personnage
    private List<Item> items =  new ArrayList<>(); //List of items of character

    // Empreintes du personnages
    private String fingerprint;

    // Empreintes du personnages
    private Map<String,String> clues = new HashMap<>(); // fingerprint,

    // Etat du personnage
    private boolean alive = true; //if character is current alive or not


    private Sprite sprite; //Sprite of character
    private String texturePath;


    private Integer step = 1600 / 50; // Step (size of tiled when display)
    private List<int[]> path =  new ArrayList<>(); //path to move with mousse
    private Integer nbPath;

    // Constructeur pour les pnj
    public Character() {
        Texture texture = new Texture(Gdx.files.internal("placeholder.png"));
        String[] fingerprints = {"A", "L", "W"};

        this.name = "placeholder";
        this.x = 0;
        this.y = 0;
        this.texturePath = "placeholder.png";
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    public Character(String name, Map<String, Integer> stats, String texturePath) {
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        String[] fingerprints = {"A", "L", "W"};

        this.name = name;
        this.stats = stats;
        this.x = 0;
        this.y = 0;
        this.sprite = new Sprite(texture);

        this.texturePath = texturePath;

        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    // Constructeur pour le joueur et le tueur
    public Character(String name, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName, Integer step) {
        Texture texture = new Texture(Gdx.files.internal(spriteName));
        String[] fingerprints = {"A", "L", "W"};


        this.name = name;
        this.stats = stats;
        this.x = posX;
        this.y = posY;
        this.texturePath = spriteName;
        this.step = step;
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(posX * step, posY * step);
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    public String getTexturePath() {
        return texturePath;
    }
    public void buildSprite(){
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
    }
    // NAME
    public String getName() {
        return this.name;
    }

    //STEP
    public int getStep() {
        return this.step;
    }
    public void setStep(int step) {
        this.step = step;
    }
    //Path
    public int[] getPath(){
        int[] tmp;

        if (this.path.isEmpty())
            return null;
        tmp = this.path.get(0);
        this.path.remove(0);
        return tmp;
    }

    //SPRITE
    public Sprite getSprite() {
        int[] pos = getPath();

        if (pos != null) {
            setPosition(pos[0], pos[1]);
            hiddenPassage();
            try {
                Thread.sleep(50); //For display tiled by tiled
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.sprite;
    }

    // position

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setPosition(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.sprite.setPosition(x * this.step, y * this.step);
        hiddenPassage();
    }

    // STATS
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
        return this.items;
    }

    public int countItems() {
        return this.items.size();
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
        if(this.items.contains(item)){
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
        return this.fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }


    // CLUES
    public Map<String, String> getClues(){
        return this.clues;
    }

    public void addClues(Character murderer, Item weapon){
        this.clues.put("fingerprint", murderer.getFingerprint());
        this.clues.put("wound", weapon.getWoundType());
    }

    // ALIVE

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // ACTIONS

    // Permet de savoir si un item permet de faire une action spécial
    public boolean canDoAction(String action){
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

        for(Item item : this.items){
            if(item.getClass() == ActionItem.class){
                ActionItem actionItem = (ActionItem)item;
                System.out.println(actionItem.getAction());
            }
        }
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

    public String getRoom() {
        TiledMap map = new TmxMapLoader().load("maps/map.tmx");
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("sol");
        int tiled = murInt.getCell(this.x,this.y).getTile().getId();
        if (tiled == 1593)
            return "cuisine";
        else if (tiled == 1484)
            return "chambre2";
        else if (tiled == 1569)
            return "chambre1";
        else if (tiled == 1482)
            return "laboratoire";
        else if (tiled == 1483)
            return "grande salle à manger";
        else if (tiled == 1485)
            return "salon";
        else if (tiled == 1486)
            return "petite salle à manger";
        else if (tiled == 1637 || tiled == 1638 || tiled == 1639 || tiled == 1645 || tiled == 1646 || tiled == 1647 || tiled == 1653 || tiled == 1654 || tiled == 1655)
            return "buanderie";
        return "hall";
    }

}
