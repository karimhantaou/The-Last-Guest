package mpl1.thelastguest.view;

// Import des composants du jeu
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.GameController;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import mpl1.thelastguest.model.Board;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.view.ui.ActionMenu;
import mpl1.thelastguest.view.ui.ItemMenu;
import mpl1.thelastguest.view.ui.PlayerInventoryMenu;
import mpl1.thelastguest.view.ui.RoomInventoryMenu;

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

    private ActionMenu actionMenu;
    private PlayerInventoryMenu invMenu;
    private ItemMenu itemMenu;
    private RoomInventoryMenu roomMenu;

    private InputMultiplexer multiplexer = new InputMultiplexer();private Skin skin;

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
    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        board.displayAllSprites(batch);

        controller.update(delta);

        invMenu.getStage().act(delta);
        invMenu.getStage().draw();

        itemMenu.getStage().act(delta);
        itemMenu.getStage().draw();

        actionMenu.getStage().act(delta);
        actionMenu.getStage().draw();

        roomMenu.getStage().act(delta);
        roomMenu.getStage().draw();
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

        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        itemMenu = new ItemMenu(skin, player, controller);
        invMenu = new PlayerInventoryMenu(skin, player, controller, itemMenu);
        actionMenu = new ActionMenu(skin, controller, player, npcs);
        roomMenu = new RoomInventoryMenu(skin, player, controller);

        multiplexer.addProcessor(invMenu.getStage());
        multiplexer.addProcessor(itemMenu.getStage());
        multiplexer.addProcessor(actionMenu.getStage());
        multiplexer.addProcessor(roomMenu.getStage());

        Gdx.input.setInputProcessor(multiplexer);

        invMenu.display();
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

    public ActionMenu getActionMenu() {
        return actionMenu;
    }

    public PlayerInventoryMenu getInventoryMenu() {
        return invMenu;
    }

    public ItemMenu getItemMenu() {
        return itemMenu;
    }

    public RoomInventoryMenu getRoomMenu() {
        return roomMenu;
    }

}
