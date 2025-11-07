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
    private Batch batch;




    // Constructeur de salopard
    public GameScreen(Main game) {
        this.game = game;
        this.font = new BitmapFont();
        this.board = new Board();
        this.controller = new GameController(game, this);
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        board.displayAllSprites(batch);
        controller.update(delta);

    }

    //C'est ici on initialise les élements
    @Override
    public void show() {
        this.map =  new TmxMapLoader().load("maps/map.tmx");
        if (map == null) {
            Gdx.app.log("MAP", "Erreur : la carte n'a pas été chargée !");
        } else {
            Gdx.app.log("MAP", "Carte chargée avec succès !");
        }
        this.renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 1600);
    }

    // Permet de gérer le comportement du jeu lors du resize
    @Override
    public void resize(int width, int height) {
        float mapSize = 1600f;
        float ratioScreen = (float) width / (float) height;
        if (ratioScreen >= 1f) {
            camera.setToOrtho(false, mapSize * ratioScreen, mapSize);
        } else {
            camera.setToOrtho(false, mapSize, mapSize / ratioScreen);
        }
        board.displayAllSprites(batch);
        board.setSize(width / 50, height / 50);
        camera.position.set(mapSize / 2f, mapSize / 2f, 0);
        camera.update();
        renderer.setView(camera);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {

    }

    public Board getBoard() {
        return this.board;
    }

    public TiledMap getMap() {
        return this.map;
    }
}
