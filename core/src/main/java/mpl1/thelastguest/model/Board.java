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
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Board {
    private final List<Character> characters; //List of characters
    private Integer step; // Step (size of tiled when display)
    private ShapeRenderer tiledGrey; //Shape for select tiled with mousse
    private final List<Room> rooms;
    private Player player;
    private List<Item> items;

    public Board(Integer step, List<Character> characters, Player player, List<Item> items, boolean test) {
        this.characters = characters;
        this.player = player;
        this.items = items;
        this.step = step;
        for (Character character : this.characters) {
            int x = (int) (Math.random() * 50);
            int y = (int) (Math.random() * 50);
            character.setPosition(x, y, test);
        }
        player.setPosition(25, 25, test);
        this.rooms = createAllRoom();
    }

    public Board(Integer step, List<Character> characters, Player player, List<Item> items) {
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

    }

    //GETTER
    public int getStep() {
        return this.step;
    }

    //SETTER
    public void setSize(Integer x, Integer y) {
        if (x >= y)
            this.step = y / 50;
        else
            this.step = x / 50;
    }

    //display
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

    public void drawTileSelection(OrthographicCamera camera) {
        this.tiledGrey.setProjectionMatrix(camera.combined);
        Vector3 worldPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        int tileX = (int)(worldPos.x / 32);
        int tileY = (int)(worldPos.y / 32);

        this.tiledGrey.begin(ShapeRenderer.ShapeType.Line);
        this.tiledGrey.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
        this.tiledGrey.rect(tileX * 32, tileY * 32, 32, 32);
        this.tiledGrey.end();
    }

    //Movable
    public boolean moveToPoint(Integer posX, Integer posY) {
        return this.player.moveToPoint(posX, posY);
    }

        //Movable with collision
    public void playerMoveUp(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");

        if (murInt.getCell(this.player.getX(), this.player.getY() + 1) == null
        && murExt.getCell(this.player.getX(), this.player.getY() + 1) == null)
            this.player.moveUp();
    }

    public void playerMoveDown(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");

        if (murInt.getCell(this.player.getX(), this.player.getY() - 1) == null
        && murExt.getCell(this.player.getX(), this.player.getY() -1) == null)
            this.player.moveDown();
    }

    public void playerMoveLeft(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");

        if (murInt.getCell(this.player.getX() - 1, this.player.getY()) == null
        && murExt.getCell(this.player.getX() - 1, this.player.getY()) == null)
            this.player.moveLeft();
    }

    public void playerMoveRight(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");

        if (murInt.getCell(this.player.getX() + 1, this.player.getY()) == null
        && murExt.getCell(this.player.getX() + 1, this.player.getY()) == null)
            this.player.moveRight();
    }

    public Room findRoom(String roomName) {
        for (Room room : this.rooms) {
            if (Objects.equals(room.getName(), roomName))
                return room;
        }
        return null;
    }
    public void displayItem() {
        String room = this.player.getRoom();
        Room currentRoom = this.findRoom(room);
        List<Item> items = currentRoom.getItems();
        for (Item item : items) {
            System.out.println(item.getName());
        }
    }

    public Item randomItem(){
        Item item = items.get(new Random().nextInt(items.size()));
        items.remove(item);
        return item;
    }

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
        for(Room room : rooms){
            if(!items.isEmpty()){
                room.addItem(randomItem());
            }
        }
        return rooms;
    }
}
