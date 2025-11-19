package mpl1.thelastguest.view;

// Import des composants du jeu
import com.badlogic.gdx.math.Vector2;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.GameController;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import mpl1.thelastguest.model.Board;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Room;
import mpl1.thelastguest.view.ui.*;
import mpl1.thelastguest.view.ui.notification.NotificationManager;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final Main game;
    private final BitmapFont font;
    private final GameController controller;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Board board;
    private final Batch batch;
    private final float mapSize = 1600f;

    private List<Npc> npcs =  new ArrayList<Npc>();
    private Player player;
    private Murderer murderer;

    // UI
    private PlayerInventory playerInventory;
    private PlayerAp playerAp;
    private NotificationManager notificationManager;

    private ActionMenu actionMenu;
    private boolean actionMenuOpen;

    private RoomInventory  roomInventory;
    private boolean roomInventoryOpen;

    private ItemActionMenu itemActionMenu;
    private boolean itemActionMenuOpen;

    private GuessMenu guessMenu;
    private boolean guessMenuOpen;

    private PlayerMenu playerMenu;
    private boolean playerMenuOpen;


    // Constructeur de salopard
    public GameScreen(Main game, Player player, List<Npc> npcs, Murderer murderer, List<Item> items) {
        this.game = game;
        this.font = new BitmapFont();
        this.controller = new GameController(game, this, player, npcs, murderer, items);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();

        this.npcs = npcs;
        this.player = player;
        this.murderer = murderer;

        this.board = controller.getBoard();

        // UI
        playerInventory = new PlayerInventory(controller, player);
        playerAp = new PlayerAp(player);
        notificationManager = new NotificationManager();

        actionMenu = new ActionMenu(controller, player);
        this.actionMenuOpen = false;

        roomInventory = new RoomInventory(controller, player);
        this.roomInventoryOpen = false;

        itemActionMenu = new ItemActionMenu(controller, player);
        itemActionMenuOpen = false;

        guessMenu = new GuessMenu(controller);
        guessMenuOpen = false;

        playerMenu = new PlayerMenu(controller, player);
        playerMenuOpen = false;
    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.renderer.setView(this.camera);
        this.renderer.render();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.board.displayAllSprites(this.batch);
        this.controller.update(delta);
        this.board.drawTileSelection(this.camera);
        Gdx.gl.glClearColor(10f, 0, 0, 1);
        controller.update(delta);

        playerInventory.getStage().act(delta);
        playerInventory.getStage().draw();

        playerAp.getStage().act(delta);
        playerAp.getStage().draw();

        notificationManager.getStage().draw();
        notificationManager.getStage().act(delta);

        if (isActionMenuOpen() && actionMenu.getStage() != null) {
            actionMenu.getStage().act(delta);
            actionMenu.getStage().draw();
        }

        if(isRoomInventoryOpen() && roomInventory.getStage() != null) {
            roomInventory.getStage().act(delta);
            roomInventory.getStage().draw();
        }

        if(isItemActionMenuOpen() && itemActionMenu.getStage() != null) {
            itemActionMenu.getStage().act(delta);
            itemActionMenu.getStage().draw();
        }

        if(isGuessMenuOpen() && guessMenu.getStage() != null) {
            guessMenu.getStage().act(delta);
            guessMenu.getStage().draw();
        }

        if(isPlayerMenuOpen() && playerMenu.getStage() != null) {
            playerMenu.getStage().act(delta);
            playerMenu.getStage().draw();
        }
    }

    //C'est ici on initialise les élements
    @Override
    public void show() {
        this.map = new TmxMapLoader().load("maps/map.tmx");
        if (this.map == null)
            Gdx.app.log("MAP", "Erreur : la carte n'a pas été chargée !");
        else
            Gdx.app.log("MAP", "Carte chargée avec succès !");
        this.renderer = new OrthogonalTiledMapRenderer(map);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, this.mapSize, this.mapSize);
        this.camera.position.set(this.mapSize / 2f, this.mapSize / 2f, 0);
        this.camera.update();

        playerInventory.rebuild();
        notificationManager.rebuild();
    }

    // Permet de gérer le comportement du jeu lors du resize
    @Override
    public void resize(int width, int height) {
        float ratioScreen = (float) width / (float) height;
        if (ratioScreen >= 1f)
            this.camera.setToOrtho(false, this.mapSize * ratioScreen, this.mapSize);
        else
            this.camera.setToOrtho(false, this.mapSize, this.mapSize / ratioScreen);
        this.board.displayAllSprites(this.batch);
        this.board.setSize(width, height);
        this.camera.position.set(this.mapSize / 2f, this.mapSize / 2f, 0);
        this.camera.update();
        this.renderer.setView(camera);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

    public Board getBoard() {
        return this.board;
    }

    public TiledMap getMap() {
        return this.map;
    }

    // PLAYER INVENTORY

    public PlayerInventory getPlayerInventory() {
        return this.playerInventory;
    }

    // ACTION MENU
    public void displayActionMenu(Vector2 position) {
        this.actionMenuOpen = true;
        actionMenu.display(position, npcs, board);
    }

    public void closeActionMenu() {
        this.actionMenuOpen = false;
    }

    public boolean isActionMenuOpen() {
        return this.actionMenuOpen;
    }

    // ROOM INVENTORY

    public void displayRoomInventory(Room actualRoom) {
        this.roomInventoryOpen = true;
        roomInventory.display(actualRoom);
    }

    public void closeRoomInventory(){
        this.roomInventoryOpen = false;
    }

    public boolean isRoomInventoryOpen() {
        return this.roomInventoryOpen;
    }

    // ITEM ACTION MENU

    public void displayItemActionMenu(Vector2 mousePosition, Item item) {
        this.itemActionMenuOpen = true;
        itemActionMenu.display(mousePosition, item);
    }

    public void closeItemActionMenu() {
        this.itemActionMenuOpen = false;
    }

    public boolean isItemActionMenuOpen() {
        return this.itemActionMenuOpen;
    }

    // GUESS MENU

    public void displayGuessMenu() {
        this.guessMenuOpen = true;
        guessMenu.display(controller.getNpcs(), murderer);
    }

    public void closeGuessMenu(){
        this.guessMenuOpen = false;
    }

    public boolean isGuessMenuOpen() {
        return this.guessMenuOpen;
    }

    // NOTIFICATION

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    // PLAYER MENU

    public void displayPlayerMenu(){
        this.playerMenuOpen = true;
        playerMenu.display();
    }

    public void closePlayerMenu(){
        this.playerMenuOpen = false;
    }

    public boolean isPlayerMenuOpen() {
        return this.playerMenuOpen;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }
}
