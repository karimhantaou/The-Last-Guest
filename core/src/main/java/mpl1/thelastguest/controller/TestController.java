package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Character;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.model.*;
import mpl1.thelastguest.view.TestScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestController {
    private final Main game;
    private List<Npc> npcs = new ArrayList<>();
    private Murderer murderer;

    public TestController(Main game, TestScreen view) {
        this.game = game;
        createCharacters();
    }

    public void createCharacters(){
        try{
            Gson gson = new Gson();

            FileHandle file = Gdx.files.internal("data/Characters.json");
            String json = file.readString();

            List<Npc> npcs = gson.fromJson(json, new TypeToken<List<Npc>>(){}.getType());

            int murdererIndex = (int)(Math.random() * npcs.size());
            Npc murdererNpc = npcs.get(murdererIndex);
            Murderer murderer = new Murderer(murdererNpc.getName(), murdererNpc.getStats());

            npcs.remove(murdererIndex);

            for(Npc npc : npcs){
                System.out.println(npc.getName() + " is innocent.");
            }

            System.out.println(murderer.getName() + " is the murderer !");

            this.npcs = npcs;
            this.murderer = murderer;

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<Npc> getNpcs() {
        return npcs;
    }

    public Murderer getMurderer() {
        return murderer;
    }
}
