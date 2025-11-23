package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Character.CharacterFactory;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Dialogue;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;
import mpl1.thelastguest.view.EndScreen;
import mpl1.thelastguest.view.SelectCharacterScreen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

/**
 * Controller for display select character menu (start game)
 * <p>
 * This class interact with this view {@link SelectCharacterScreen} and the principal game {@link Main}
 * Its objective is to trigger the action leading to select character.
 */
public class SelectCharacterController {
    private final Main game;

    private List<Npc> characters;
    private int selectedCharacter;

    private Murderer murderer;
    private Player player;

    private List<Item> items;
    private List<Dialogue> dialogues;

    /**
     * It's the construtor of the class
     *
     * @param game instance of the principal game {@link Main}
     */
    public SelectCharacterController(Main game, SelectCharacterScreen view) {
        this.game = game;
        this.characters = createCharacters();
        this.selectedCharacter = 0;
        this.items = createItems();
        for (Npc npc : this.characters) {
            npc.setStartAp(npc.getAp());
        }
        npcBuildSprite();
        this.dialogues = createDialogues();
    }

    /**
     * Updates the controller logic depending on user input.
     *
     * @param delta Time elapsed since last frame.
     */
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            previousCharacter();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            nextCharacter();
        }
    }

    /**
     * Loads the list of NPC characters from a JSON file.
     *
     * @return The list of loaded NPCs.
     */
    public List<Npc> createCharacters(){
        try{
            Gson gson = new Gson();

            FileHandle file = Gdx.files.internal("assets/data/Characters.json");
            String json = file.readString();

            return gson.fromJson(json, new TypeToken<List<Npc>>(){}.getType());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * Builds the sprites for all NPCs.
     */
    public void npcBuildSprite(){
        for(Npc npc : this.characters){
            npc.buildSprite();
        }
    }

    /**
     * Loads all items (stat items and action items) from JSON files.
     *
     * @return The list of loaded items.
     */
    public List<Item> createItems(){
        List<Item> items =  new ArrayList<>();
        try{
            Gson gson = new Gson();

            FileHandle file = Gdx.files.internal("assets/data/StatsItems.json");
            String json = file.readString();

            List<Item> statItems = gson.fromJson(json, new TypeToken<List<StatItem>>(){}.getType());
            items.addAll(statItems);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        try{
            Gson gson = new Gson();

            FileHandle file = Gdx.files.internal("assets/data/ActionItems.json");
            String json = file.readString();

            List<Item> actionItems = gson.fromJson(json, new TypeToken<List<ActionItem>>(){}.getType());
            items.addAll(actionItems);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

        return items;
    }

    /**
     * Loads all dialogues from a JSON file.
     *
     * @return The list of loaded dialogues.
     */
    public List<Dialogue> createDialogues(){
        try{
            Gson gson = new Gson();

            FileHandle file = Gdx.files.internal("assets/data/Dialogues.json");
            String json = file.readString();

            return gson.fromJson(json, new TypeToken<List<Dialogue>>(){}.getType());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * @return The current NPC.
     */
    public Npc getSelectedCharacter(){
        return characters.get(selectedCharacter);
    }

    /**
     * Moves the selection to the next character.
     */
    public void nextCharacter(){
        this.selectedCharacter++;
        if(this.selectedCharacter == characters.size()){
            this.selectedCharacter = 0;
        }
    }

    /**
     * Moves the selection to the previous character.
     */
    public void previousCharacter(){
        this.selectedCharacter--;
        if(this.selectedCharacter < 0){
            this.selectedCharacter = characters.size()-1;
        }
    }

    /**
     * Creates the player from the selected NPC
     * chooses a random murderer among the NPCs
     * selects a murder weapon and add fingerprint
     * creates the first victim
     * starts the game
     */
    public void selectPlayer(){
        // Création du joueur
        this.player = (Player) CharacterFactory.create("player", getSelectedCharacter());
        this.player.setStartAp(this.player.getAp());
        this.characters.remove(getSelectedCharacter()); // On retire le personnage de la liste des pnjs

        // Tueur aléatoire
        int randomMurdererIndex = (int)(Math.random() * characters.size() - 1);
        this.murderer = (Murderer) CharacterFactory.create("murderer", characters.get(randomMurdererIndex));
        this.murderer.setStartAp(characters.get(randomMurdererIndex).getAp());
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
        Npc victim = new Npc("Victim", "", null, 0, 0, "placeholder.png", 1600 / 50);
        victim.addClues(murderer, items.get(randomIndex));
        victim.setAlive(false);
        this.characters.add(victim);

        // Lance le jeu
        playGame();
    }

    /**
     * Launches the game screen.
     */
    public void playGame(){
        game.screenManager.showGame(this.player, this.characters, this.murderer, this.items, this.dialogues);
    }

    /**
     * @return The list of NPCs.
     */
    public List<Npc> getNpcs() {
        return characters;
    }

    /**
     * @return The murderer.
     */
    public Murderer getMurderer() {
        return murderer;
    }

}
