package mpl1.thelastguest.view;

// Import des composants du jeu
import com.badlogic.gdx.graphics.g2d.Batch;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.GameController;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import mpl1.thelastguest.model.Board;

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

    // Constructeur de salopard
    public GameScreen(Main game) {
        this.game = game;
        this.font = new BitmapFont();
        this.board = new Board(700 / 50);
        this.controller = new GameController(game, this);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
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
        board.drawTileSelection();
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
        this.board = new Board(32);
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

}
