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

    public Board() {
        this.characters = new ArrayList<>();
        this.characters.add(new Npc("Red", new HashMap<String, Integer>() {{
            put("str", 8);
            put("per", 6);
            put("lck", 4);
            put("ap", 9);
            put("in", 2);
        }}, 140, 7, "pion.png"));
        this.characters.add(new Npc("Blue", new HashMap<String, Integer>() {{
            put("str", 5);
            put("per", 9);
            put("lck", 4);
            put("ap", 4);
            put("in", 2);
        }}, 150, 150, "pion.png"));
        this.player = new Player("bastien", this.characters.get(0));
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
        if (murInt.getCell(((this.player.getPlayerCharacter().getPositionX() - (this.step * 10)) / this.step),  ((this.player.getPlayerCharacter().getPositionY()) / this.step) + 1) == null
        && murExt.getCell(((this.player.getPlayerCharacter().getPositionX() - (this.step * 10)) / this.step),  ((this.player.getPlayerCharacter().getPositionY()) / this.step) + 1) == null)
            this.player.getPlayerCharacter().moveUp(this.step);
    }

    public void playerMoveDown(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");
        if (murInt.getCell(((this.player.getPlayerCharacter().getPositionX() - (this.step * 10)) / this.step),  ((this.player.getPlayerCharacter().getPositionY()) / this.step) -1) == null
        && murExt.getCell(((this.player.getPlayerCharacter().getPositionX() - (this.step * 10)) / this.step),  ((this.player.getPlayerCharacter().getPositionY()) / this.step) -1) == null)
            this.player.getPlayerCharacter().moveDown(this.step);
    }

    public void playerMoveLeft(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");
        if (murInt.getCell(((this.player.getPlayerCharacter().getPositionX() - (this.step * 10)) / this.step) - 1,  ((this.player.getPlayerCharacter().getPositionY()) / this.step)) == null
        && murExt.getCell(((this.player.getPlayerCharacter().getPositionX() - (this.step * 10)) / this.step) - 1,  ((this.player.getPlayerCharacter().getPositionY()) / this.step)) == null)
            this.player.getPlayerCharacter().moveLeft(this.step);
    }

    public void playerMoveRight(TiledMap map) {
        TiledMapTileLayer murInt = (TiledMapTileLayer) map.getLayers().get("mur interrieur");
        TiledMapTileLayer murExt = (TiledMapTileLayer) map.getLayers().get("mur exterieur");
        if (murInt.getCell(((this.player.getPlayerCharacter().getPositionX() - (this.step * 10)) / this.step) + 1,  ((this.player.getPlayerCharacter().getPositionY()) / this.step)) == null
        && murExt.getCell(((this.player.getPlayerCharacter().getPositionX() - (this.step * 10)) / this.step) + 1,  ((this.player.getPlayerCharacter().getPositionY()) / this.step)) == null)
            this.player.getPlayerCharacter().moveRight(this.step);
    }

    public void setSize(Integer x, Integer y) {
        if (x >= y)
            this.step = y;
        else
            this.step = x;
    }
}
