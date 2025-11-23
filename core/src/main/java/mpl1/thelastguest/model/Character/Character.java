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
    private final String name; //name of character

    // Statistiques du personnage
    private Map<String, Integer> stats = new HashMap<>(); //str, per, lck, ap, inv

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
    private int startAp;

    private Integer step = 1600 / 50; // Step (size of tiled when display)
    protected List<int[]> path =  new ArrayList<>(); //path to move with mousse
    protected Integer nbPath = 0;
    protected boolean isEnd = false;

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
        if (texturePath != null) {
            Texture texture = new Texture(Gdx.files.internal(texturePath));
            this.sprite = new Sprite(texture);
        }
        String[] fingerprints = {"A", "L", "W"};

        this.name = name;
        this.stats = stats;
        this.x = 0;
        this.y = 0;

        this.texturePath = texturePath;
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    // Constructeur pour le joueur et le tueur
    public Character(String name, Map<String, Integer> stats, Integer posX, Integer posY, String spriteName, Integer step) {
        if (spriteName != null) {
            Texture texture = new Texture(Gdx.files.internal(spriteName));
            this.sprite = new Sprite(texture);
            this.sprite.setPosition(posX * step, posY * step);
        }
        String[] fingerprints = {"A", "L", "W"};

        this.name = name;
        this.stats = stats;
        this.x = posX;
        this.y = posY;
        this.texturePath = spriteName;
        this.step = step;
        this.fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void buildSprite(){
        Texture texture = new Texture(Gdx.files.internal(texturePath));
        this.sprite = new Sprite(texture);
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
        this.nbPath--;
        if (nbPath == 0)
            isEnd = true;
        return tmp;
    }

    public int getNbPath(){
        return this.nbPath;
    }

    public void  setNbPath(int nbPath){
        this.nbPath = nbPath;
    }
    public boolean getIsEnd() {
        return this.isEnd;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
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
        if (this.sprite != null)
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

    public int getStartAp(){
        return this.startAp;
    }

    public void  setStartAp(int startAp){
        this.startAp = startAp;
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

    public Item getItem(String itemName) {
        for (Item item : this.items) {
            if (item.getName().equals(itemName)) {
                return  item;
            }
        }
        return null;
    }

    public Item getItemByAction(String action) {
        for (Item item : this.items) {
            if (item.getClass() == ActionItem.class && ((ActionItem) item).getAction().equals(action)) {
                return  item;
            }
        }
        return null;
    }

    public void getItem(List<Item> items) {
    }

    public int countItems() {
        return this.items.size();
    }

    private void addStats(StatItem item){
        this.setStr(this.getStr() + item.getStr());
        this.setPer(this.getPer() + item.getPer());
        this.setLck(this.getLck() + item.getLck());
        this.setStartAp(this.getStartAp() + item.getAp());
        this.setInv(this.getInv() + item.getInv());
    }

    private void removeStats(StatItem item){
        this.setStr(this.getStr() - item.getStr());
        this.setPer(this.getPer() - item.getPer());
        this.setLck(this.getLck() - item.getLck());
        this.setStartAp(this.getStartAp() - item.getAp());
        this.setInv(this.getInv() - item.getInv());
    }

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

    public boolean enoughInventory(){
        return this.items.size() < this.getInv();
    }

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

    public boolean enoughAp(int ap){
        return this.getAp() >= ap;
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

    public String getClueWound(){
        return this.clues.get("wound");
    }

    public String getClueFingerprint(){
        return this.clues.get("fingerprint");
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
        }
    }

    public boolean kill(List <Character> npcs){
        return false;
    }

    // La méthode qui sera utilisée pour afficher les actions. On mettra un objet en paramètre pour savoir ce qu'il peut faire.
    // Pour l'instant ça print juste mais à therme afficher des boutons différents. (dans la vue surement)
    public void displayActions(){
        for(Item item : this.items){
            if(item.getClass() == ActionItem.class){
                ActionItem actionItem = (ActionItem)item;
            }
        }
    }

    //Movable the player to select point (implement BFS algo)
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

    public String getRoom() {
        TiledMap map = new TmxMapLoader().load("maps/map.tmx");
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("sol");
        int tiled = murInt.getCell(this.x,this.y).getTile().getId();
        if (tiled == 1593)
            return "Kitchen";
        else if (tiled == 1484)
            return "Second bedroom";
        else if (tiled == 1569)
            return "First bedroom";
        else if (tiled == 1482)
            return "Laboratory";
        else if (tiled == 1483)
            return "Diner room";
        else if (tiled == 1485)
            return "Living room";
        else if (tiled == 1486)
            return "Small diner room";
        else if (tiled == 1637 || tiled == 1638 || tiled == 1639 || tiled == 1645 || tiled == 1646 || tiled == 1647 || tiled == 1653 || tiled == 1654 || tiled == 1655)
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
