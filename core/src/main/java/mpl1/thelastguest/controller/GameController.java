package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Board;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Room;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.ui.notification.Notification;

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
       if(
            !view.isActionMenuOpen()
            && !view.isRoomInventoryOpen()
            && !view.isItemActionMenuOpen()
            && !view.isGuessMenuOpen()
            && !view.isPlayerMenuOpen()
       ){
           if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
               Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
               view.displayActionMenu(mousePosition);
           }
           if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){

               int tileX = (int)((Gdx.input.getX() / board.getStep()) - 11);
               int tileY =  (int)(50 - Gdx.input.getY() / board.getStep() - 1);

               boolean tileIsEmpty = true;

               for (Npc npc : npcs) {
                   if(npc.getX() == tileX && npc.getY() == tileY) {
                       tileIsEmpty = false;
                   }
               }

               if(tileIsEmpty){
                   move(Gdx.input.getX(), Gdx.input.getY());
               }
           }
       }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            displayGuessMenu();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            displayPlayerMenu();
        }
    }


    public void closeActionMenu(){
        view.closeActionMenu();
        if (!view.isRoomInventoryOpen()) view.getPlayerInventory().rebuild();
    }

    public void move(float x, float y){
       board.moveToPoint((int) (x /board.getStep()), (int) (y /board.getStep()));
    }

    public void spoofFingerprints(){
        String[] fingerprints = {"A", "L", "W"};
        String fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
        player.setFingerprint(fingerprint);
        view.getNotificationManager().addNotification(new Notification("New fingerprints: " + player.getFingerprint()));
        closeActionMenu();
    }


    public void scanFingerprints(Item item){

        Notification notification;

        if(item.getFingerprint() != null){
            notification = new Notification("Fingerprints: " + item.getFingerprint(), 5f);
        } else{
            notification = new Notification("No fingerprints found");
        }

        view.getNotificationManager().addNotification(notification);

    }

    public void scanFingerprints(Npc npc){
        Notification notification;

        if(npc.getFingerprint() != null){
            notification = new Notification("Fingerprints: " + npc.getFingerprint(), 5f);
        } else{
            notification = new Notification("No fingerprints found");
        }

        view.getNotificationManager().addNotification(notification);    }

    public void scanClueFingerprints(Npc npc){
        Notification notification;

        if(npc.getFingerprint() != null){
            notification = new Notification("Fingerprints: " + npc.getFingerprint(), 5f);
        } else{
            notification = new Notification("No fingerprints found");
        }

        view.getNotificationManager().addNotification(notification);
    }

    public void kill(Item weapon, Npc npc){
        player.kill(npc, weapon);
    }

    public void inspect(Npc npc){
        view.getNotificationManager().addNotification(new Notification("Wound type: " + npc.getClueWound(), 5f));
    }
    // ROOM SEARCH

    public void search(){
        Room actualRoom = board.findRoom(player.getRoom());
        if(!actualRoom.getItems().isEmpty()){
            view.displayRoomInventory(actualRoom);
        } else{
            view.getNotificationManager().addNotification(new Notification("Nothing in the room"));
        }
    }

    public void pickItem(Item item){
        if(player.pickItem(item)){
            Room actualRoom = board.findRoom(player.getRoom());
            actualRoom.removeItem(item);
            view.closeRoomInventory();
            view.getPlayerInventory().rebuild();
            view.getNotificationManager().addNotification(new Notification(item.getName() + " picked"));
        } else{
            view.getNotificationManager().addNotification(new Notification("No more space in the inventory"));
        }
    }

    public void closeRoomInventory(){
        view.closeRoomInventory();
        view.getPlayerInventory().rebuild();
    }

    // PLAYER INVENTORY

    public void displayItemActionMenu(Item item){
        Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        view.displayItemActionMenu(mousePosition, item);
    }

    public void closeItemActionMenu(){
        view.closeItemActionMenu();
        view.getPlayerInventory().rebuild();
    }

    public void dropItem(Item item){
        if(player.dropItem(item)){
            Room actualRoom = board.findRoom(player.getRoom());
            actualRoom.addItem(item);
            view.getNotificationManager().addNotification(new Notification(item.getName() + " droped"));
        }
    }

    public void destroyItem(Item item){
        player.dropItem(item);
        view.getNotificationManager().addNotification(new Notification(item.getName() + " out of use        "));

    }

    // GUESS MENU

    public void displayGuessMenu(){
        view.displayGuessMenu();
    }

    public void closeGuessMenu(){
        view.closeGuessMenu();
        view.getPlayerInventory().rebuild();
    }

   public void guess(Character character){
        view.closeGuessMenu();
        view.getPlayerInventory().rebuild();

        if(character == murderer){
            view.getNotificationManager().addNotification(new Notification("You found the murderer !"));
            ScreenManager screenManager = new ScreenManager(game);
            screenManager.showEnd(murderer);
        } else{
            view.getNotificationManager().addNotification(new Notification(character.getName() + " is innocent."));
        }
    }

    // PLAYER MENU
    public void displayPlayerMenu(){
        view.displayPlayerMenu();
    }

    public void closePlayerMenu(){
        view.closePlayerMenu();
        view.getPlayerInventory().rebuild();
    }
}
