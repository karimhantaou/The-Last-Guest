package mpl1.thelastguest.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {
    private final List<Npc> characters; //List of characters
    private Integer step; // Step (size of tiled when display)
    private final ShapeRenderer tiledGrey; //Shape for select tiled with mousse
    private final List<Room> rooms;
    private Player player;

    public Board(Integer step, List<Npc> characters, Player player) {
        this.characters = characters;
        this.player = player;
        this.step = step;
        for (Character character : this.characters) {
            character.getSprite().setSize(step, step);
            character.setStep(step);
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
            sprite.draw(batch);
        }
        Sprite sprite = player.getSprite();
        sprite.draw(batch);
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

    //Movable
    public void moveToPoint(Integer posX, Integer posY) {
        this.player.moveToPoint(posX, posY);
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
        return rooms;
    }
}
