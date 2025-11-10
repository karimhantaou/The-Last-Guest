package mpl1.thelastguest.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {
    private final List<Character> characters;
    private final Player player;
    private Integer step;

    public Board(Integer step) {
        this.characters = new ArrayList<>();
        this.characters.add(new Npc("Red", new HashMap<String, Integer>() {{
            put("str", 8);
            put("per", 6);
            put("lck", 4);
            put("ap", 9);
            put("in", 2);
        }}, 20, 25, "pion.png", step));
        this.characters.add(new Npc("Blue", new HashMap<String, Integer>() {{
            put("str", 5);
            put("per", 9);
            put("lck", 4);
            put("ap", 4);
            put("in", 2);
        }}, 25, 25, "pion.png", step));
        this.player = new Player("bastien", this.characters.get(0));
        this.step = step;
    }

    public void displayAllSprites(Batch batch) {
        batch.begin();
        for  (Character character : this.characters) {
            Sprite sprite = character.getSprite();
            sprite.draw(batch);
        }
        batch.end();
    }

    public void playerMoveUp(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");
        if (murInt.getCell(this.player.getPlayerCharacter().getPositionX(), this.player.getPlayerCharacter().getPositionY() + 1) == null
        && murExt.getCell(this.player.getPlayerCharacter().getPositionX(), this.player.getPlayerCharacter().getPositionY() + 1) == null)
            this.player.getPlayerCharacter().moveUp();
    }

    public void playerMoveDown(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");
        if (murInt.getCell(this.player.getPlayerCharacter().getPositionX(), this.player.getPlayerCharacter().getPositionY() - 1) == null
        && murExt.getCell(this.player.getPlayerCharacter().getPositionX(), this.player.getPlayerCharacter().getPositionY() -1) == null)
            this.player.getPlayerCharacter().moveDown();
    }

    public void playerMoveLeft(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");
        if (murInt.getCell(this.player.getPlayerCharacter().getPositionX() - 1, this.player.getPlayerCharacter().getPositionY()) == null
        && murExt.getCell(this.player.getPlayerCharacter().getPositionX() - 1, this.player.getPlayerCharacter().getPositionY()) == null)
            this.player.getPlayerCharacter().moveLeft();
    }

    public void playerMoveRight(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");
        if (murInt.getCell(this.player.getPlayerCharacter().getPositionX() + 1, this.player.getPlayerCharacter().getPositionY()) == null
        && murExt.getCell(this.player.getPlayerCharacter().getPositionX() + 1, this.player.getPlayerCharacter().getPositionY()) == null)
            this.player.getPlayerCharacter().moveRight();
    }

    public void setSize(Integer x, Integer y) {
        if (x >= y)
            this.step = y / 50;
        else
            this.step = x / 50;
    }
}
