package mpl1.thelastguest.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Map {
    private List<Character> characters;

    public Map() {
        this.characters = new ArrayList<>();
        this.characters.add(new Npc("Red", new HashMap<String, Integer>() {{
            put("str", 8);
            put("per", 6);
            put("lck", 4);
            put("ap", 9);
            put("in", 2);
        }}, 50, 50));
        this.characters.add(new Npc("Blue", new HashMap<String, Integer>() {{
            put("str", 5);
            put("per", 9);
            put("lck", 4);
            put("ap", 4);
            put("in", 2);
        }}, 150, 150));
    }

    public void displayAllSprites(Batch batch) {
        batch.begin();
        for  (Character character : characters) {
            Sprite sprite = character.getSprite();
            sprite.draw(batch);
        }
        batch.end();
    }
}
