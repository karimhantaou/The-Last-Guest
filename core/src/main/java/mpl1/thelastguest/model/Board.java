package mpl1.thelastguest.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;

import java.util.*;

/**
 * Represents the game board, including characters, rooms, items, and tile-based interactions.
 * <p>
 * The board is responsible for:
 * Initializing character positions on a tiled map
 * Displaying sprites and tile highlights
 * Managing rooms and distributing items randomly among them
 * Delegating player movement
 */
public class Board {
    private final List<Character> characters; //List of characters
    private Integer step; // Step (size of tiled when display)
    private ShapeRenderer tiledGrey; //Shape for select tiled with mousse
    private List<Room> rooms;
    public Player player;
    private List<Item> items;
    private int tiledSize;

    /**
     * Creates the game board and initializes character positions, rooms, and item placement.
     *
     * @param step the scaling factor used for sprite rendering
     * @param characters list of non-player characters
     * @param player the player character
     * @param items all items to distribute into rooms
     * @param tiledSize the pixel size of a tile on the map
     */

    public Board(Integer step, List<Character> characters, Player player, List<Item> items, int tiledSize, boolean test) {
        this.characters = characters;
        this.player = player;
        this.items = items;
        this.step = step;
        player.setPosition(25, 25);
        this.rooms = createAllRoom();
        this.tiledSize = tiledSize;
    }

    public Board(Integer step, List<Character> characters, Player player, List<Item> items, int tiledSize) {
        TiledMap map = new TmxMapLoader().load("maps/map.tmx");
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("sol");
        this.characters = characters;
        this.player = player;
        this.items = items;
        this.step = step;
        for (Character character : this.characters) {
            int x = (int) (Math.random() * 50);
            int y = (int) (Math.random() * 50);
            while (murInt.getCell(x, y) == null) {
                x = (int) (Math.random() * 50);
                y = (int) (Math.random() * 50);
            }
            character.getSprite().setSize(step, step);
            character.setStep(step);
            character.setPosition(x, y);

        }
        player.getSprite().setSize(step, step);
        player.setPosition(25, 25);
        this.tiledGrey = new ShapeRenderer();
        this.rooms = createAllRoom();
        this.tiledSize = tiledSize;
    }

    //GETTER
    /**
     * Returns the current sprite step size.
     * @return the tile step value
     */
    public int getStep() {
        return this.step;
    }

    //SETTER
    /**
     * Sets the sprite scaling based on the window size.
     * @param x window width
     * @param y window height
     */
    public void setSize(Integer x, Integer y) {
        if (x >= y)
            this.step = y / 50;
        else
            this.step = x / 50;
    }

    //display
    /**
     * Draws all visible sprites on the board.
     * NPCs are drawn only if they are within the player's perception range.
     * @param batch the batch used for rendering sprites
     */
    public void displayAllSprites(Batch batch) {
        batch.begin();
        for  (Character character : this.characters) {
                Sprite sprite = character.getSprite();
            if (player.isPerceptible(character.getX(), character.getY()))
                sprite.draw(batch);
        }
        Sprite sprite = player.getSprite();
        sprite.draw(batch);
        batch.end();
    }

    /**
     * Draws a grey outline on the tile currently under the mouse cursor.
     * @param camera the camera used to project mouse coordinates to world space
     */
    public void drawTileSelection(OrthographicCamera camera) {
        this.tiledGrey.setProjectionMatrix(camera.combined);
        Vector3 worldPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        int tileX = (int)(worldPos.x / tiledSize);
        int tileY = (int)(worldPos.y / tiledSize);

        this.tiledGrey.begin(ShapeRenderer.ShapeType.Line);
        this.tiledGrey.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
        this.tiledGrey.rect(tileX * 32, tileY * 32, 32, 32);
        this.tiledGrey.end();
    }

    //Movable
    /**
     * Attempts to move the player toward the specified tile coordinate.
     * @param posX target tile X
     * @param posY target tile Y
     * @return true if movement was successful, false otherwise
     */
    public boolean moveToPoint(Integer posX, Integer posY) {
        return this.player.moveToPoint(posX, posY);
    }

    /**
     * Finds a room by its name.
     * @param roomName the name of the room
     * @return the matching room, or null if not found
     */
    public Room findRoom(String roomName) {
        for (Room room : this.rooms) {
            if (Objects.equals(room.getName(), roomName))
                return room;
        }
        return null;
    }


    /**
     * Returns a random item from the remaining undistributed items.
     * @return a randomly selected item
     */
    public Item randomItem(){
        return items.get(new Random().nextInt(items.size()));
    }


    /**
     * Creates all rooms in the game and distributes items randomly among them.
     * One room is locked, and items are shuffled before distribution.
     * The key cannot be placed inside a locked room.
     * @return the list of all created rooms
     */
    public List<Room> createAllRoom() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Kitchen"));
        rooms.add(new Room("Second bedroom"));
        rooms.add(new Room("First bedroom"));
        rooms.add(new Room("Laboratory"));
        rooms.add(new Room("Diner room"));
        rooms.add(new Room("Living room"));
        rooms.add(new Room("Small diner room"));
        rooms.add(new Room("Laundry room"));
        rooms.add(new Room("Hall"));


        // Lock one room
        int randomRoom = (int)(Math.random() * rooms.size());
        rooms.get(randomRoom).setLocked(true);

        Collections.shuffle(items);
        Collections.shuffle(rooms);

        while(!items.isEmpty()) {
            Collections.shuffle(rooms);
            for(Room room : rooms){
                if(!items.isEmpty()){
                    Item newItem = randomItem();
                    if(!(room.isLocked() && newItem.getName().equals("Key"))){
                        room.addItem(newItem);
                        items.remove(newItem);
                    }
                }
            }
        }
        return rooms;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
