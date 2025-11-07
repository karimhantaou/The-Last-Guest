package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.*;
import mpl1.thelastguest.view.SelectCharacterScreen;

import java.util.List;


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
}
