package mpl1.thelastguest.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class Board {
    private final List<Npc> characters; //List of characters
    private Integer step; // Step (size of tiled when display)
    private ShapeRenderer tiledGrey = null; //Shape for select tiled with mousse
    private final List<Room> rooms;
    private Player player;
    private List<Item> items;

    public Board(Integer step, List<Npc> characters, Player player, List<Item> items, boolean test) {
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
    public Board(Integer step, List<Npc> characters, Player player, List<Item> items) {
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

    public Item randomItem(){
        Item item = items.get(new Random().nextInt(items.size()));
        items.remove(item);
        return item;
    }

    public List<Room> createAllRoom() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("cuisine"));
        rooms.add(new Room("chambre2"));
        rooms.add(new Room("chambre1"));
        rooms.add(new Room("laboratoire"));
        rooms.add(new Room("grande salle à manger"));
        rooms.add(new Room("salon"));
        rooms.add(new Room("petite salle à manger"));
        rooms.add(new Room("buanderie"));
        rooms.add(new Room("hall"));
        for(Room room : rooms){
            if(!items.isEmpty()){
                room.addItem(randomItem());
            }
        }
        return rooms;
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
        for (Character character : this.characters) {
            character.getSprite().draw(batch);
        }
        player.getSprite().draw(batch);
        batch.end();
    }

    public void drawTileSelection() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        int posx = (mouseX / this.step) * this.step;
        int posy = (mouseY / this.step) * this.step;

        this.tiledGrey.begin(ShapeRenderer.ShapeType.Line);
        this.tiledGrey.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
        this.tiledGrey.rect(posx - this.step / 3, posy, this.step, this.step);
        this.tiledGrey.end();
    }

    public void displayItem() {
        String room = this.player.getRoom();
        Room currentRoom = this.findRoom(room);
        List<Item> items = currentRoom.getItems();
        for (Item item : items) {
            System.out.println(item.getName());
        }
    }

    //Movable
    public void moveToPoint(Integer posX, Integer posY) {
        this.player.moveToPoint(posX, posY);
    }

    //ROOM
    public Room findRoom(String roomName) {
        for (Room room : this.rooms) {
            if (Objects.equals(room.getName(), roomName))
                return room;
        }
        return null;
    }
}
