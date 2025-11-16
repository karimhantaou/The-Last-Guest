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
import mpl1.thelastguest.model.Room;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.MenuScreen;

import java.util.List;

public class GameController {

    private final Main game;
    private final GameScreen view;

    private Player player;
    private List<Npc> npcs;
    private Murderer murderer;
    private List<Item> items;

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

    public Player getPlayer() { return player; }
    public List<Npc> getNpcs() { return npcs; }
    public Board getBoard()   { return board; }


    public void update(float delta) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MenuScreen(game));
        }

        // Clic droit: menu d'action
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            Vector2 mouse = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            view.getActionMenu().display(mouse);
        }

        // Clic gauche: déplacement si pas de menus ouverts
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !view.getActionMenu().isOpen() && !view.getRoomMenu().isOpen()) {

            move(Gdx.input.getX(), Gdx.input.getY());
        }

        // F: affiche l'inventaire de la salle
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            search();
        }
    }

    public void move(float x, float y){
        board.moveToPoint((int)(x / board.getStep()), (int)(y / board.getStep()));
    }

    public void spoofFingerprints(){
        String[] fingerprints = {"A", "L", "W"};
        player.setFingerprint(fingerprints[(int)(Math.random() * fingerprints.length)]);
        view.getActionMenu().close();
    }

    public void kill(Npc npc, Item weapon){
        System.out.println("Kill");
        view.getActionMenu().close();
    }

    public void inspect(Npc npc){
        System.out.println("Inspect");
        view.getActionMenu().close();
    }

    public String scanFingerprints(Npc npc){
        view.getActionMenu().close();
        return npc.getFingerprint();
    }

    public void search(){
        view.getActionMenu().close();

        Room room = board.findRoom(player.getRoom());
        List<Item> items = room.getItems();

        if(items.isEmpty()){
            System.out.println("No items found");
        } else {
            view.getRoomMenu().display(items);
        }
    }

    public void pickItem(Item item){
        if (player.pickItem(item)) {

            Room room = board.findRoom(player.getRoom());
            room.removeItem(item);

            view.getRoomMenu().close();
            view.getInventoryMenu().display();
        }
    }

    public void itemUse(Item item){
        System.out.println("Item use");
    }

    public void itemDrop(Item item){
        System.out.println("Item drop");

        player.dropItem(item);

        Room room = board.findRoom(player.getRoom());
        room.addItem(item);

        view.getInventoryMenu().display();
    }
}
