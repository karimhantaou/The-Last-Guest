package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;
import mpl1.thelastguest.view.SelectCharacterScreen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;


public class SelectCharacterController {
    private final Main game;

    private List<Npc> characters;
    private int selectedCharacter;

    private Murderer murderer;
    private Player player;

    private List<Item> items;

    public SelectCharacterController(Main game, SelectCharacterScreen view) {
        this.game = game;
        this.characters = createCharacters();
        this.selectedCharacter = 0;
        this.items = createItems();
        for (Npc npc : this.characters) {
            npc.setStartAp(npc.getAp());
        }
        npcBuildSprite();
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

            return gson.fromJson(json, new TypeToken<List<Npc>>(){}.getType());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void npcBuildSprite(){
        for(Npc npc : this.characters){
            npc.buildSprite();
        }
    }
    public List<Item> createItems(){
        List<Item> items =  new ArrayList<>();
        try{
            Gson gson = new Gson();

            FileHandle file = Gdx.files.internal("data/StatsItems.json");
            String json = file.readString();

            List<Item> statItems = gson.fromJson(json, new TypeToken<List<StatItem>>(){}.getType());
            items.addAll(statItems);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        try{
            Gson gson = new Gson();

            FileHandle file = Gdx.files.internal("data/ActionItems.json");
            String json = file.readString();

            List<Item> actionItems = gson.fromJson(json, new TypeToken<List<ActionItem>>(){}.getType());
            items.addAll(actionItems);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return items;
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
        // Création du joueur
        this.player = new Player(getSelectedCharacter());
        this.player.setStartAp(this.player.getAp());
        this.characters.remove(getSelectedCharacter()); // On retire le personnage de la liste des pnjs

        // Tueur aléatoire
        int randomMurdererIndex = (int)(Math.random() * characters.size() - 1);
        this.murderer = new Murderer(characters.get(randomMurdererIndex));
        this.characters.remove(characters.get(randomMurdererIndex));

        // Choisit l'arme du crime et rajoute l'empreinte du tueur.
        List<Integer> weapons = new ArrayList<>();
        int itemIndex = 0;
        for(Item item : this.items){
            if(item.getClass() == ActionItem.class){
                ActionItem actionItem = (ActionItem)item;
                if(Objects.equals(actionItem.getAction(), "kill")){
                    weapons.add(itemIndex);
                }
            }
            itemIndex++;
        }

        int randomIndex = weapons.get(new Random().nextInt(weapons.size()));
        items.get(randomIndex).setFingerprint(murderer.getFingerprint());

        // Première victime
        Npc victim = new Npc("Victim", null, 0, 0, "placeholder.png", 1600 / 50);
        victim.addClues(murderer, items.get(randomIndex));
        victim.setAlive(false);
        this.characters.add(victim);

        // Lance le jeu
        playGame();
    }

    public void playGame(){
        game.screenManager.showGame(this.player, this.characters, this.murderer, this.items);
    }

    public List<Npc> getNpcs() {
        return characters;
    }

    public Murderer getMurderer() {
        return murderer;
    }

}
