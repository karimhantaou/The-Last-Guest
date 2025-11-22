package mpl1.thelastguest.view;

// Import des composants du jeu
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.MenuController;

// Import des libs gdx
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {
    private final BitmapFont font;
    private final MenuController controller;

    private Stage stage;
    private Skin skin;

    // Constructeur de salopard
    public MenuScreen(Main game) {
        this.font = new BitmapFont();
        this.controller = new MenuController(game, this);
    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 10f, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    //C'est ici on initialise les élements
    @Override
    public void show() {

        // Skins de base
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Font
        BitmapFont bigFont = new BitmapFont();
        bigFont.getData().setScale(2f);

        // Font pour le titre


        Label.LabelStyle style = skin.get("default", Label.LabelStyle.class);
        style.font.getData().setScale(2f);

        // Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Gère les cliques sur le stage

        Texture bgTexture = new Texture(Gdx.files.internal("assets/backgrounds/StartMenu.jpg"));
        Image bg = new Image(bgTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        // Création d'une table pour pouvoir placer les élements dans une grid
        Table table = new Table();
        table.setFillParent(true); // Table fait tout le stage
        //table.setWidth(200);
        stage.addActor(table); // Ajout de la table au stage

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.5f); // R,G,B,A
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        // Label
        Label title = new Label("The Last Guest", style);

        // Bouton avec du text
        TextButton play = new TextButton("Play",skin);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.play();
            }
        });

        TextButton rules = new TextButton("Rules",skin);
        rules.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        TextButton quit = new TextButton("Quit",skin);
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.quit();
            }
        });

        // Ajout du bouton à la table
        table.center(); // Centre les élements
        table.add(title).pad(10).row(); // Row -> passe à la ligne
        table.add(play).size(200, 50).pad(10).row();  // Ajout du bouton, de sa taille etc...
        table.add(rules).size(200, 50).pad(10).row();  // Ajout du bouton, de sa taille etc...
        table.add(quit).size(200, 50).pad(10).row();  // Ajout du bouton, de sa taille etc...

        table.pack();

        //table.setPosition((Gdx.graphics.getWidth() - table.getWidth()) / 2, (Gdx.graphics.getHeight() - table.getHeight()) / 2);
    }

    // Permet de gérer le comportement du jeu lors du resize
    @Override public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}

    // Action quand le screen est changé
    @Override public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    // Garbage collector en gros
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}
