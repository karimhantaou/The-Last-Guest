package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.*;
import mpl1.thelastguest.model.Items.ActionItem;
import mpl1.thelastguest.model.Items.Item;
import mpl1.thelastguest.model.Items.StatItem;
import mpl1.thelastguest.view.SelectCharacterScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SelectCharacterController {
    private final Main game;

    private List<Npc> characters;
    private int selectedCharacter;

    private Murderer murderer;
    private Player player;

    public SelectCharacterController(Main game, SelectCharacterScreen view) {
        this.game = game;
        this.characters = createCharacters();
        this.selectedCharacter = 0;

        testItem();
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            previousCharacter();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            nextCharacter();
        }
    }

    public List<Npc> createCharacters(){
        try{
            Gson gson = new Gson();

            FileHandle file = Gdx.files.internal("data/Characters.json");
            String json = file.readString();

            List<Npc> characters = gson.fromJson(json, new TypeToken<List<Npc>>(){}.getType());
            return characters;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Npc getSelectedCharacter(){
        return characters.get(selectedCharacter);
    }

    public void nextCharacter(){
        this.selectedCharacter++;
        if(this.selectedCharacter == characters.size()){
            this.selectedCharacter = 0;
        }
    }

    public void previousCharacter(){
        this.selectedCharacter--;
        if(this.selectedCharacter < 0){
            this.selectedCharacter = characters.size()-1;
        }
    }

    public void selectPlayer(){
        this.player = new Player("username", getSelectedCharacter());
        this.characters.remove(getSelectedCharacter());

        int randomMurdererIndex = (int)(Math.random() * characters.size() - 1);
        this.murderer = new Murderer(characters.get(randomMurdererIndex));
        this.characters.remove(characters.get(randomMurdererIndex));
        playGame();
    }

    public void playGame(){
        game.screenManager.showGame(this.player, this.characters, this.murderer);
    }

    public List<Npc> getNpcs() {
        return characters;
    }

    public Murderer getMurderer() {
        return murderer;
    }

    public void testItem(){
        // Item 1
        ActionItem item =  new ActionItem("Clef", "Open door");


        // Item 2
        Map<String, Integer> stats = new HashMap<>();
        stats.put("str", 0);
        stats.put("per", 0);
        stats.put("lck", 10);
        stats.put("ap", 0);
        stats.put("inv", 0);
        StatItem item2 = new StatItem("Chapeau de zgeg", stats);

        List<Item> items = new ArrayList<>();
        items.add(item); items.add(item2);

        Npc character = characters.get(0);
        Player player = new Player("username", character);
        player.pickItem(item2);
        System.out.println(player.getPlayerCharacter().getStats());
        player.dropItem(item2);
        System.out.println(player.getPlayerCharacter().getStats());
        player.displayActions();
    }
}
