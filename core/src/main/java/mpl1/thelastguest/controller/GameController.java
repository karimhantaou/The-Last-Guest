package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.MenuScreen;

import java.util.List;

public class GameController {
    private final Main game;
    private Player player;
    private List<Npc> npcs;
    private Murderer murderer;
    private List<Item> items;
    private GameScreen view;

    public GameController(Main game, GameScreen view, Player player, List<Npc> npcs, Murderer murderer, List<Item> items) {
        this.game = game;
        this.view = view;
        this.player = player;
        this.npcs = npcs;
        this.murderer = murderer;
        this.items = items;
    }

    public List<Npc> getNpcs() {
        return this.npcs;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MenuScreen(game));
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            view.displayActionMenu(mousePosition);
        }
    }

    public void closeActionMenu(){
        view.closeActionMenu();
    }

    public void move(){
        System.out.println("Move");
        view.closeActionMenu();
        if  (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            view.getBoard().playerMoveUp(view.getMap());
        }
        if  (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            view.getBoard().playerMoveDown(view.getMap());
        }
        if  (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            view.getBoard().playerMoveLeft(view.getMap());
        }
        if  (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            view.getBoard().playerMoveRight(view.getMap());
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            view.getBoard().moveToPoint(Gdx.input.getX() / view.getBoard().getStep(), Gdx.input.getY() / view.getBoard().getStep());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            view.getBoard().displayItem();
        }
    }

    public void spoofFingerprints(){
        String[] fingerprints = {"A", "L", "W"};
        String fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
        player.setFingerprint(fingerprint);
        System.out.println(player.getFingerprint());
        closeActionMenu();
    }

    public void kill(Npc npc, Item weapon){
        System.out.println("Kill");
        closeActionMenu();
    }

    public void inspect(Npc npc){
        System.out.println("Inspect");
        closeActionMenu();
    }

    public void scanFingerprints(Npc npc){
        System.out.println("Scan Fingerprints");
        closeActionMenu();
    }

}
