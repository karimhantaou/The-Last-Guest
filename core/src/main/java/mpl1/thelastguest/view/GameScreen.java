package mpl1.thelastguest.view;

// Import des composants du jeu
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.GameController;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameScreen implements Screen {
    private final Main game;
    private final BitmapFont font;
    private final GameController controller;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;




    // Constructeur de salopard
    public GameScreen(Main game) {
        this.game = game;
        this.font = new BitmapFont();
        this.controller = new GameController(game, this);
    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        controller.update(delta);

    }

    //C'est ici on initialise les élements
    @Override
    public void show() {
        this.map =  new TmxMapLoader().load("maps/sans titre.tmx");
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
    @Override public void resize(int w, int h) {
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
    }
}
