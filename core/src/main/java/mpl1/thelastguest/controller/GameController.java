package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Board;
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
    private Board board;

    public GameController(Main game, GameScreen view, Player player, List<Npc> npcs, Murderer murderer, List<Item> items) {
        this.game = game;
        this.view = view;
        this.player = player;
        this.npcs = npcs;
        this.murderer = murderer;
        this.items = items;
        this.board = new Board(1600 / 50, this.npcs, this.player, this.items);
    }

    public List<Npc> getNpcs() {
        return this.npcs;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Board getBoard(){
        return this.board;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MenuScreen(game));
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            view.displayActionMenu(mousePosition);
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !view.isActionMenuOpen()){
            move(Gdx.input.getX(), Gdx.input.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            board.displayItem();
        }
    }

    public void closeActionMenu(){
        view.closeActionMenu();
    }

    public void move(float x, float y){
       board.moveToPoint((int) (x /board.getStep()), (int) (y /board.getStep()));
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
