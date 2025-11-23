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
import mpl1.thelastguest.model.Dialogue;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Room;
import mpl1.thelastguest.view.EndScreen;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.model.Notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Controller for all game
 * <p>
 * This class interact with all class for the game logic
 * manage the turn by turn, all interaction of pnj and player
 */
public class GameController {
    private final Main game;
    private Player player;
    private List<Character> npcs;
    private List<Character> allCharacters;
    private Murderer murderer;
    private List<Item> items;
    private List<Dialogue> dialogues;
    private GameScreen view;
    private Board board;
    private int currentNpc;
    private boolean playerTurn = false;
    private boolean leftClicked = false;
    private boolean deadRound = false;

    /**
     * it's the constructor of the class
     * <p>
     * @param game instance of the principal game {@link Main}
     * @param view instance of the display class for game {@link GameScreen}
     * @param player instance of the class player (it's the current player of the game) {@link Player}
     * @param npcs list of instance of npc (it's all pnj on the game {@link Npc}
     * @param murderer instance of the class murderer (it's the current murderer of the game) {@link Murderer}
     * @param items list of instance of item (it's all items on the game) {@link Item}
     */
    public GameController(Main game, GameScreen view, Player player, List<Npc> npcs, Murderer murderer, List<Item> items, List<Dialogue> dialogues) {
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
        this.dialogues = dialogues;
        this.board = new Board(1600 / 50, this.npcs, this.player, this.items, view.getTiledSize());
    }

    /**
     * get the private list of Npc of the game
     * @return the list of Character on the game
     */
    public List<Character> getNpcs() {
        return this.npcs;
    }

    /**
     * get the private instance of player
     * @return the isntance of curretn player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * get the private instance of board
     * @return the instance of board
     */
    public Board getBoard(){
        return this.board;
    }

    /**
     * Action for the pnj and the murderer
     * for all turn,
     * the pnj move to random point and take random item
     * the murderer move to random point, take random item and kill random people
     */
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
            {
                deadRound = true;
            }
            current.setIsEnd(false);
            currentNpc++;
            playerTurn = false;
        }
    }

    /**
     * count all npcs is alive
     * @param npcs
     * @return the number of alive npc
     */
    public int remainingNpc(List<Character> npcs) {
        int result = 0;
        for (Character npc : npcs) {
            if (npc.isAlive()) result++;
        }
        return result;
    }

    /**
     * this methode its call for all loop of game, and manage the turn by turn for pnj or for the player
     * for player handle all input
     * @param delta Time elapsed since last frame
     */
    public void update(float delta) {
        if(!player.isAlive()){
            if(!isSaved()) game.screenManager.showEnd(murderer, player);
        }
        if (currentNpc == this.npcs.size()) {
            if (!playerTurn) {
                view.getNotificationManager().addNotification(new Notification("your turn!"));
                skullCloseMurderer();
                playerTurn = true;
                if(deadRound) {skullDead();}
                deadRound = false;
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

    /**
     * verify if one menu are open
     * @return true if one menu are open false otherwise
     */
    public boolean isMenuOpen(){
        return
            view.isActionMenuOpen()
            || view.isRoomInventoryOpen()
            || view.isItemActionMenuOpen()
            || view.isGuessMenuOpen()
            || view.isPlayerMenuOpen()
            || view.isPauseMenuOpen()
            || view.isTalkMenuOpen();
    }

    public void closeAllMenu(){
        view.closeActionMenu();
        view.closeGuessMenu();
        view.closeItemActionMenu();
        view.closePlayerMenu();
        view.closeRoomInventory();
        view.closePauseMenu();
        view.closeTalkMenu();
        view.getPlayerInventory().rebuild();
    }

    /**
     * close menu of action
     * if the inventory of room is closed, the inventory of th player
     */
    public void closeActionMenu(){
        view.closeActionMenu();
        if (!view.isRoomInventoryOpen() && !view.isTalkMenuOpen()) view.getPlayerInventory().rebuild();
    }

    /**
     * if player has enough action points
     * move the player to the pos x y of the window (is divide my the tiled size on map)
     * @param x posx of screen
     * @param y posy of screen
     */
    public void move(float x, float y){
        Vector3 worldPos = view.getCamera().unproject(new Vector3(x, y, 0));
        if(!board.moveToPoint((int) (worldPos.x /view.getTiledSize()), (int) (worldPos.y /view.getTiledSize()))){
            view.getNotificationManager().addNotification(new Notification("Not enough action points."));
        } else{
            view.getPlayerInventory().rebuild();
        }
    }

    /**
     * if player as enough action points generate new fingerprints for the player
     */
    public void spoofFingerprints(){

        if(!enoughAp(1)) return;

        String[] fingerprints = {"A", "L", "W"};
        String fingerprint =  fingerprints[(int)(Math.random() * fingerprints.length)];
        player.setFingerprint(fingerprint);
        view.getNotificationManager().addNotification(new Notification("New fingerprints: " + player.getFingerprint()));
        closeActionMenu();
    }

    /**
     * if player as enough action points,
     * scan fingerprints present on the item (weapon) and
     * if player has good luck, the fingerprints are display
     * otherwise notiffication display No fingerprints found
     * @param item item to scan
     */
    public void scanFingerprints(Item item){

        if(!enoughAp(1)) return;

        Notification notification;
        if(item.getFingerprint() != null && player.getLck() > 3){
            notification = new Notification("Fingerprints: " + item.getFingerprint(), 5f);
            item.setFingerPrintFound(true);
        } else{
            notification = new Notification("No fingerprints found");
        }

        view.getNotificationManager().addNotification(notification);
    }

    /**
     * if player as enough action points,
     * scan fingerprints present on the Character and
     * if the player has more strength than the character the fingerprints are display
     * otherwise notification display won't let you scan him without fighting back
     * @param ch character to scan
     */
    public void scanFingerprints(Character ch){

        if(!enoughAp(1)) return;

        Notification notification;

        if(player.getStr() > ch.getStr()){
            if(ch.getFingerprint() != null){
                notification = new Notification("Fingerprints: " + ch.getFingerprint(), 5f);
                ch.setFingerPrintFound(true);
            } else{
                notification = new Notification("No fingerprints found");
            }
        } else{
            notification = new Notification(ch.getName() + " won't let you scan him without fighting back");
        }
        view.getNotificationManager().addNotification(notification);
    }

    /**
     * if player as enough action points,
     * scan clues fingerprints present on the Character and
     * if the player has enough luck, display the npc clues fingerprints
     * otherwise notification display No fingerprints found
     * @param npc character to scan
     */
    public void scanClueFingerprints(Character npc){

        if(!enoughAp(2)) return;

        Notification notification;

        if(npc.getFingerprint() != null && player.getLck() > 8 && player.getPer() > 8){
            notification = new Notification("Fingerprints: " + npc.getClueFingerprint(), 5f);
        } else{
            notification = new Notification("No fingerprints found");
        }

        view.getNotificationManager().addNotification(notification);
    }

    /**
     * if player as enough action points,
     * inspect the character (victim)
     * if the player has enough perception, display the Wound type
     * otherwise notification display No wounds found...
     * @param npc character to scan
     */
    public void inspect(Character npc){

        if(!enoughAp(1)) return;

        if(player.getPer() > 5){
            view.getNotificationManager().addNotification(new Notification("Wound type: " + npc.getClueWound(), 5f));
        } else{
            view.getNotificationManager().addNotification(new Notification("No wounds found...", 5f));
        }
    }

    public void displayTalkMenu(Character npc, String answer){
        closeActionMenu();
        view.displayTalkMenu(npc, answer);
    }

    public void closeTalkMenu(){
        view.closeTalkMenu();
        view.getPlayerInventory().rebuild();
    }

    public void askForFingerprint(Character npc){
        if(!enoughAp(1)) return;

        view.closeTalkMenu();
        view.displayTalkMenu(npc, "alibi");
        if(player.canDoAction("detect_lie")){
             int randomNbr = (int) Math.floor(Math.random() * 11);

             // Sucess -> Tell if npc is murderer, Failure -> Tell npc is murderer even if he is not.
             if(
                 randomNbr <= player.getLck() && npc.getClass() == Murderer.class
                 || randomNbr > player.getLck() && npc.getClass() != Murderer.class){
                 view.getNotificationManager().addNotification(new Notification("Lie detector: bip bip bip"));
             }
        }
    }

    // ROOM SEARCH

    /**
     * if player as enough action points,
     * search item in room
     * if the room is locked, display The room is locked
     * otherwise display item present in room only first nbrItems of the list
     * @param nbrItems number of display item
     */
    public void search(int nbrItems){
        if(!enoughAp(1)) return;

        view.getPlayerInventory().rebuild();

        Room actualRoom = board.findRoom(player.getRoom());
        if(actualRoom.isLocked()){
            view.getNotificationManager().addNotification(new Notification("The room is locked"));
            return;
        }
        if(!actualRoom.getItems().isEmpty()){
            view.displayRoomInventory(actualRoom, nbrItems);
        } else{
            view.getNotificationManager().addNotification(new Notification("Nothing in the room"));
        }
    }

    /**
     * if player as enough action points,
     * if the player has a key item,
     * the room are unlocked
     */
    public void unlock(){

        if(!enoughAp(1)) return;

        board.findRoom(player.getRoom()).setLocked(false);
        destroyItem(player.getItemByAction("unlock"));
        view.getNotificationManager().addNotification(new Notification("You unlocked the room"));
    }

    /**
     * add item on inventory
     * if player don't have enough space, a notification displayed Not enough space in the inventory.
     * @param item it's item to pick
     */
    public void pickItem(Item item){
        if(player.pickItem(item)){
            Room actualRoom = board.findRoom(player.getRoom());
            actualRoom.removeItem(item);
            closeRoomInventory();
            view.getNotificationManager().addNotification(new Notification(item.getName() + " picked"));
        } else{
            view.getNotificationManager().addNotification(new Notification("Not enough space in the inventory."));
        }
    }

    /**
     * close room inventory
     * and rebuild the player inventory
     */
    public void closeRoomInventory(){
        view.closeRoomInventory();
        view.getPlayerInventory().rebuild();
    }

    // PLAYER INVENTORY

    /**
     * display menu of action for the item
     * @param item Action item
     */
    public void displayItemActionMenu(Item item){
        Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        view.displayItemActionMenu(mousePosition, item);
    }

    /**
     * close action menu of item
     */
    public void closeItemActionMenu(){
        view.closeItemActionMenu();
        view.getPlayerInventory().rebuild();
    }

    /**
     * drop select item
     * @param item item to drop
     */
    public void dropItem(Item item){
        if(player.dropItem(item)){
            Room actualRoom = board.findRoom(player.getRoom());
            actualRoom.addItem(item);
            view.getNotificationManager().addNotification(new Notification(item.getName() + " dropped"));
        }
    }

    /**
     * destroys the item when it can no longer be used
     * @param item item to destroy
     */
    public void destroyItem(Item item){
        player.dropItem(item);
        view.getNotificationManager().addNotification(new Notification(item.getName() + " out of use"));
        view.getPlayerInventory().rebuild();
    }

    /**
     * display description of item
     * @param item item to display description
     */
    public void displayDescription(Item item){
        view.getNotificationManager().addNotification(new Notification(item.getDescription(), 5f));
    }

    // GUESS MENU

    /**
     * display menu guess for try to find the murderer
     */
    public void displayGuessMenu(){
        view.displayGuessMenu();
    }

    /**
     * close menu guess
     */
    public void closeGuessMenu(){
        view.closeGuessMenu();
        view.getPlayerInventory().rebuild();
    }

    /**
     * to handle the result of guess menu
     * if the selected characetr it's the murderer display You found the murderer and show end
     * otherwise display character was an innocent and kill this character
     * @param character selected character on guess menu
     */
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

    /**
     * display menu player
     */
    public void displayPlayerMenu(){
        view.displayPlayerMenu();
    }

    /**
     * close menu player and rebuild inventory
     */
    public void closePlayerMenu(){
        view.closePlayerMenu();
        view.getPlayerInventory().rebuild();
    }

    // PAUSE MENU

    /**
     * display pause menu
     */
    public void displayPauseMenu(){
        view.displayPauseMenu();
    }

    /**
     * close pause menu and rebuild inventory
     */
    public void closePauseMenu(){
        view.closePauseMenu();
        view.getPlayerInventory().rebuild();
    }

    /**
     * restart game
     */
    public void restartGame(){
        game.screenManager.showCharacterSelection();
    }

    /**
     * active godmode and set all stats to 999
     */
    public void godMode(){
        player.setStr(999);
        player.setStartAp(999);
        player.setAp(player.getStartAp());
        player.setPer(999);
        player.setLck(999);
        player.setInv(999);
    }

    /**
     * exit game
     */
    public void exitGame(){
        Gdx.app.exit();
    }

    // PLAYER AP

    /**
     * return true if the player have enough action points and update action points
     * return false otherwise and display Not enough action points
     * @param ap action points
     * @return boolean
     */
    public boolean enoughAp(int ap){
        if(player.enoughAp(ap)){
            player.setAp(player.getAp() - ap);
            return true;
        } else{
            view.getNotificationManager().addNotification(new Notification("Not enough action points."));
            return false;
        }
    }

    /**
     * if the player has item with action save the player is saved, the item is destroyed, display You have been saved and return true
     * otherwise return  false
     * @return boolean
     */
    public boolean isSaved(){
        if(player.canDoAction("save")) {
            player.setAlive(true);
            Item item = player.getItemByAction("save");
            destroyItem(item);
            view.getNotificationManager().addNotification(new Notification("You have been saved by " + item.getName()));
            return true;
        } else{
            return false;
        }
    }

    // SKULL ACTION

    /**
     * verify if the player can do action skull
     * @return boolean
     */
    public boolean isSkull(){
        return player.canDoAction("skull");
    }

    /**
     * if player has enough perception, the skull is activated and return true
     * @param per minimum perception
     * @return boolean
     */
    public boolean skullStats(int per){
        if(player.getPer() >= per){
            return true;
        } else{
            view.getNotificationManager().addNotification(new Notification("The skull stay silent..."));
            return false;
        }
    }

    /**
     * if player has enough perception
     * add notification if a new merder have been made
     */
    public void skullDead(){
        if(isSkull() && skullStats(5)) view.getNotificationManager().addNotification(new Notification("The skull vibrated... someone's perished"));
    }

    /**
     * if player has enough perception
     * add notification if a murderer is near
     */
    public void skullCloseMurderer(){
        if(
            isSkull()
            && skullStats(8)
            && murderer.getX() >= player.getX() - 10
            && murderer.getX() <= player.getX() + 10
            && murderer.getY() >= player.getY() - 10
            && murderer.getY() <= player.getY() + 10
        ){
            view.getNotificationManager().addNotification(new Notification("The skull vibrated... a dark presence is near"));
        }
    }

    /**
     * return view
     * @return View
     */
    public GameScreen getView() {
        return view;
    }


    public void setVolume(float volume){
        view.setMusicVolume(volume);
    }
}
