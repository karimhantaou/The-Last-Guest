package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    private final Main game;
    private Player player;
    private List<Character> npcs;
    private List<Character> allCharacters;
    private Murderer murderer;
    private List<Item> items;
    private GameScreen view;
    private Board board;
    private int currentNpc;
    private boolean playerTurn = false;
    private boolean leftClicked = false;

    public GameController(Main game, GameScreen view, Player player, List<Npc> npcs, Murderer murderer, List<Item> items) {
        this.game = game;
        this.view = view;
        this.player = player;
        this.npcs = new ArrayList<>();
        this.npcs.addAll(npcs);
        this.npcs.add(murderer);
        this.allCharacters = new ArrayList<>();
        this.allCharacters.addAll(npcs);
        this.allCharacters.add(player);

        Collections.shuffle(npcs);
        this.murderer = murderer;
        this.items = items;
        this.board = new Board(1600 / 50, this.npcs, this.player, this.items);

        System.out.println(murderer.getName());
    }

    public List<Character> getNpcs() {
        return this.npcs;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Board getBoard(){
        return this.board;
    }

    public void startRound() {
        if (currentNpc == this.npcs.size()) {
            return;
        }
        Character current = npcs.get(currentNpc);
        if (!current.isAlive()) {
            this.allCharacters.remove(current);
            currentNpc++;
            return;
        }
        if (!playerTurn) {
            view.getNotificationManager().addNotification(new Notification("Round of " + current.getName()));
            playerTurn = true;
        }
        current.setAp(current.getStartAp());
        while(current.getNbPath() == 0 && !current.getIsEnd()) {
            int x = (int) (Math.random() * (current.getStartAp() + 1)) - current.getStartAp() / 2;
            int y = (int) (Math.random() * (current.getStartAp() + 1)) - current.getStartAp() / 2;
            current.moveToPoint(current.getX() + x, current.getY() + y);
        }
        if (current.getIsEnd()) {
            current.getItem(board.findRoom(current.getRoom()).getItems());
            if (current.kill(this.allCharacters))
                view.getNotificationManager().addNotification(new Notification("A new person has been killed !", 5));
            current.setIsEnd(false);
            currentNpc++;
            playerTurn = false;
        }
    }

    public int remainingNpc(List<Character> npcs) {
        int result = 0;
        for (Character npc : npcs) {
            if (npc.isAlive()) result++;
        }
        return result;
    }

    public void update(float delta) {
        if(!player.isAlive()){
            game.screenManager.showEnd(murderer, player);
        }
        if (currentNpc == this.npcs.size()) {
            if (!playerTurn) {
                view.getNotificationManager().addNotification(new Notification("your turn!"));
                playerTurn = true;
            }
            // PLAYERS ACTION
            if(!isMenuOpen()){
                if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
                    Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                    view.displayActionMenu(mousePosition);
                }
                if(!leftClicked && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                    leftClicked = true;

                    int tileX = (int)((Gdx.input.getX() / board.getStep()) - 11);
                    int tileY =  (int)(50 - Gdx.input.getY() / board.getStep() - 1);

                    boolean tileIsEmpty = true;

                    for (Character npc : npcs) {
                        if(npc.getX() == tileX && npc.getY() == tileY) {
                            tileIsEmpty = false;
                        }
                    }
                    if(tileIsEmpty){
                        move(Gdx.input.getX(), Gdx.input.getY());
                    }
                }
                if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                    leftClicked = false;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isMenuOpen()) {
                player.setIsEnd(false);
                playerTurn = false;
                currentNpc = 0;
                player.setAp(player.getStartAp());
                displayGuessMenu();
            }
        }
        if(!view.isGuessMenuOpen()){
            startRound();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !isMenuOpen()) {
            displayPauseMenu();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C) && !isMenuOpen()) {
            displayPlayerMenu();
        }
    }

    public boolean isMenuOpen(){
        return
            view.isActionMenuOpen()
            || view.isRoomInventoryOpen()
            || view.isItemActionMenuOpen()
            || view.isGuessMenuOpen()
            || view.isPlayerMenuOpen()
            || view.isPauseMenuOpen();
    }

    public void closeActionMenu(){
        view.closeActionMenu();
        if (!view.isRoomInventoryOpen()) view.getPlayerInventory().rebuild();
    }

    public void move(float x, float y){
        Vector3 worldPos = view.getCamera().unproject(new Vector3(x, y, 0));
        if(!board.moveToPoint((int) (worldPos.x /32), (int) (worldPos.y /32))){
            view.getNotificationManager().addNotification(new Notification("Not enough action points."));
        } else{
            view.getPlayerInventory().rebuild();
        }
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

    public void scanFingerprints(Character ch){
        Notification notification;

        if(ch.getFingerprint() != null){
            notification = new Notification("Fingerprints: " + ch.getFingerprint(), 5f);
        } else{
            notification = new Notification("No fingerprints found");
        }

        view.getNotificationManager().addNotification(notification);    }

    public void scanClueFingerprints(Character npc){
        Notification notification;

        if(npc.getFingerprint() != null){
            notification = new Notification("Fingerprints: " + npc.getClueFingerprint(), 5f);
        } else{
            notification = new Notification("No fingerprints found");
        }

        view.getNotificationManager().addNotification(notification);
    }

    public void inspect(Character npc){
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
        if(playerAp()){
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
            game.screenManager.showEnd(murderer, player);
        } else{
            view.getNotificationManager().addNotification(new Notification(character.getName() + " was an innocent."));
            character.setAlive(false);
            if(remainingNpc(npcs) == 1){
                player.setAlive(false);
                murderer.addKillNbr();
                game.screenManager.showEnd(murderer, player);
            } else if (remainingNpc(npcs) == 2){
                displayGuessMenu();
            } else{
                startRound();
            }
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

    // PAUSE MENU

    public void displayPauseMenu(){
        view.displayPauseMenu();
    }

    public void closePauseMenu(){
        view.closePauseMenu();
        view.getPlayerInventory().rebuild();
    }

    public void restartGame(){
        game.screenManager.showCharacterSelection();
    }

    public void godMode(){
        player.setStr(999);
        player.setStartAp(999);
        player.setAp(player.getStartAp());
        player.setPer(999);
        player.setLck(999);
        player.setInv(999);
    }

    public void exitGame(){
        Gdx.app.exit();
    }

    // PLAYER AP

    public boolean playerAp(){
        if(player.getAp() > 0){
            player.setAp(player.getAp() - 1);
            return true;
        } else{
            view.getNotificationManager().addNotification(new Notification("Not enough action points."));
            return false;
        }
    }

    public GameScreen getView() {
        return view;
    }
}
