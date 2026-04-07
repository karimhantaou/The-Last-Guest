package mpl1.thelastguest.view;

import com.badlogic.gdx.audio.Music;
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
import mpl1.thelastguest.model.Dialogue;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Room;
import mpl1.thelastguest.view.ui.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Main game screen displayed during gameplay.
 * <p>
 * Handles rendering of the map, characters, and interactive elements.
 * Manages all game UI menus such as inventory, action menus, guess menus, player menus,
 * pause menu, notifications, and dialogue interactions.
 * Also manages camera, music, and input processing.
 * </p>
 */
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
    private final Music backgroundMusic;

    private List<Npc> npcs = new ArrayList<>();
    private Player player;
    private Murderer murderer;

    // UI components
    private PlayerInventory playerInventory;
    private NotificationManager notificationManager;
    private ActionMenu actionMenu;
    private boolean actionMenuOpen;
    private RoomInventory roomInventory;
    private boolean roomInventoryOpen;
    private ItemActionMenu itemActionMenu;
    private boolean itemActionMenuOpen;
    private GuessMenu guessMenu;
    private boolean guessMenuOpen;
    private PlayerMenu playerMenu;
    private boolean playerMenuOpen;
    private PauseMenu pauseMenu;
    private boolean pauseMenuOpen;
    private TalkMenu talkMenu;
    private boolean talkMenuOpen;

    /**
     * Constructs a GameScreen instance.
     *
     * @param game      The main game instance.
     * @param player    The player character.
     * @param npcs      The list of NPC characters.
     * @param murderer  The murderer character.
     * @param items     List of items in the game world.
     * @param dialogues List of dialogues for interactions.
     */
    public GameScreen(Main game, Player player, List<Npc> npcs, Murderer murderer, List<Item> items, List<Dialogue> dialogues) {
        this.game = game;
        this.font = new BitmapFont();
        this.map = new TmxMapLoader().load("maps/map.tmx");
        if (this.map == null)
            Gdx.app.log("MAP", "Erreur : la carte n'a pas été chargée !");
        else
            Gdx.app.log("MAP", "Carte chargée avec succès !");

        this.controller = new GameController(game, this, player, npcs, murderer, items, dialogues);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();

        this.npcs = npcs;
        this.player = player;
        this.murderer = murderer;
        this.board = controller.getBoard();

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/music/gameMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        // UI initialization
        playerInventory = new PlayerInventory(controller, player);
        notificationManager = new NotificationManager();
        actionMenu = new ActionMenu(controller, player);
        actionMenuOpen = false;
        roomInventory = new RoomInventory(controller, player);
        roomInventoryOpen = false;
        itemActionMenu = new ItemActionMenu(controller, player);
        itemActionMenuOpen = false;
        guessMenu = new GuessMenu(controller);
        guessMenuOpen = false;
        playerMenu = new PlayerMenu(controller, player);
        playerMenuOpen = false;
        pauseMenu = new PauseMenu(controller);
        pauseMenuOpen = false;
        talkMenu = new TalkMenu(controller, player, dialogues);
        talkMenuOpen = false;
    }

    /**
     * Main render loop, updates and draws all game elements.
     *
     * @param delta Time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();
        batch.setProjectionMatrix(camera.combined);

        board.displayAllSprites(batch);
        board.drawTileSelection(camera);

        controller.update(delta);

        playerInventory.getStage().act(delta);
        playerInventory.getStage().draw();

        notificationManager.getStage().act(delta);
        notificationManager.getStage().draw();

        if (actionMenuOpen && actionMenu.getStage() != null) {
            actionMenu.getStage().act(delta);
            actionMenu.getStage().draw();
        }

        if (roomInventoryOpen && roomInventory.getStage() != null) {
            roomInventory.getStage().act(delta);
            roomInventory.getStage().draw();
        }

        if (itemActionMenuOpen && itemActionMenu.getStage() != null) {
            itemActionMenu.getStage().act(delta);
            itemActionMenu.getStage().draw();
        }

        if (guessMenuOpen && guessMenu.getStage() != null) {
            guessMenu.getStage().act(delta);
            guessMenu.getStage().draw();
        }

        if (playerMenuOpen && playerMenu.getStage() != null) {
            playerMenu.getStage().act(delta);
            playerMenu.getStage().draw();
        }

        if (pauseMenuOpen && pauseMenu.getStage() != null) {
            pauseMenu.getStage().act(delta);
            pauseMenu.getStage().draw();
        }

        if (talkMenuOpen && talkMenu.getStage() != null) {
            talkMenu.getStage().act(delta);
            talkMenu.getStage().draw();
        }
    }

    /**
     * Initializes the screen, camera, and UI elements.
     */
    @Override
    public void show() {
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapSize, mapSize);
        camera.position.set(mapSize / 2f, mapSize / 2f, 0);
        camera.update();

        playerInventory.rebuild();
        notificationManager.rebuild();
    }

    /**
     * Handles window resizing, adjusting the camera and board display.
     *
     * @param width  New window width.
     * @param height New window height.
     */
    @Override
    public void resize(int width, int height) {
        float ratioScreen = (float) width / height;
        if (ratioScreen >= 1f)
            camera.setToOrtho(false, mapSize * ratioScreen, mapSize);
        else
            camera.setToOrtho(false, mapSize, mapSize / ratioScreen);

        board.displayAllSprites(batch);
        board.setSize(width, height);
        camera.position.set(mapSize / 2f, mapSize / 2f, 0);
        camera.update();
        renderer.setView(camera);
    }

    @Override public void pause() {}
    @Override public void resume() {}

    /**
     * Stops background music when the screen is hidden.
     */
    @Override
    public void hide() {
        backgroundMusic.stop();
    }

    /**
     * Disposes resources used by this screen.
     */
    @Override
    public void dispose() {
        backgroundMusic.dispose();
        map.dispose();
        renderer.dispose();
        batch.dispose();
    }

    // --- GETTERS AND UI MANAGEMENT ---

    public Board getBoard() { return board; }
    public TiledMap getMap() { return map; }
    public PlayerInventory getPlayerInventory() { return playerInventory; }
    public NotificationManager getNotificationManager() { return notificationManager; }
    public OrthographicCamera getCamera() { return camera; }
    public int getTiledSize() { return map.getProperties().get("tilewidth", Integer.class); }
    public float getMusicVolume() { return backgroundMusic.getVolume(); }
    public void setMusicVolume(float volume) { backgroundMusic.setVolume(volume); }

    // Action Menu
    public void displayActionMenu(Vector2 position) { actionMenuOpen = true; actionMenu.display(position, controller.getNpcs(), board); }
    public void closeActionMenu() { actionMenuOpen = false; }
    public boolean isActionMenuOpen() { return actionMenuOpen; }

    // Room Inventory
    public void displayRoomInventory(Room actualRoom, int nbrItems) { roomInventoryOpen = true; roomInventory.display(actualRoom, nbrItems); }
    public void closeRoomInventory() { roomInventoryOpen = false; }
    public boolean isRoomInventoryOpen() { return roomInventoryOpen; }

    // Item Action Menu
    public void displayItemActionMenu(Vector2 mousePosition, Item item) { itemActionMenuOpen = true; itemActionMenu.display(mousePosition, item); }
    public void closeItemActionMenu() { itemActionMenuOpen = false; }
    public boolean isItemActionMenuOpen() { return itemActionMenuOpen; }

    // Guess Menu
    public void displayGuessMenu() { guessMenuOpen = true; guessMenu.display(controller.getNpcs(), murderer); }
    public void closeGuessMenu() { guessMenuOpen = false; }
    public boolean isGuessMenuOpen() { return guessMenuOpen; }

    // Player Menu
    public void displayPlayerMenu() { playerMenuOpen = true; playerMenu.display(); }
    public void closePlayerMenu() { playerMenuOpen = false; }
    public boolean isPlayerMenuOpen() { return playerMenuOpen; }

    // Pause Menu
    public void displayPauseMenu() { pauseMenuOpen = true; pauseMenu.display(getMusicVolume()); }
    public void closePauseMenu() { pauseMenuOpen = false; }
    public boolean isPauseMenuOpen() { return pauseMenuOpen; }

    // Talk Menu
    public void displayTalkMenu(Character npc, String answer) { talkMenuOpen = true; talkMenu.display(npc, answer); }
    public void closeTalkMenu() { talkMenuOpen = false; }
    public boolean isTalkMenuOpen() { return talkMenuOpen; }
}
