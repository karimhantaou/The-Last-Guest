package mpl1.thelastguest.view;

// Import des composants du jeu

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.EndController;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Player;

public class EndScreen implements Screen {
    private final BitmapFont font;
    private final EndController controller;

    private Stage stage;
    private Skin skin;
    private Murderer murderer;
    private Player player;

    // Constructeur de salopard
    public EndScreen(Main game, Murderer murderer, Player player) {
        this.font = new BitmapFont();
        this.controller = new EndController(game, this);
        this.murderer = murderer;
        this.player = player;
    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(11, 0, 0, 1);
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

        Texture bgTexture = new Texture(Gdx.files.internal("assets/backgrounds/EndMenu.jpg"));
        Image bg = new Image(bgTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        // Création d'une table pour pouvoir placer les élements dans une grid
        Table table = new Table();
        table.setFillParent(true); // Table fait tout le stage
        stage.addActor(table); // Ajout de la table au stage

        // Label

        String titleText = "You found the murderer !";
        if (!player.isAlive()) titleText = "GAME OVER ! You died !";

        Label title = new Label(titleText, style);
        Label name = new Label("It was " + murderer.getName(), style);
        Label killNbr = new Label(murderer.getKillNbr() + " kills have been made...", style);


        // Bouton avec du text
        TextButton play = new TextButton("Play again",skin);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.play();
            }
        });

        // Ajout du bouton à la table
        table.center(); // Centre les élements
        table.add(title).pad(10).row(); // Row -> passe à la ligne
        table.add(name).pad(10).row();
        table.add(killNbr).pad(10).row();
        table.add(play).size(200, 50).pad(10).row();  // Ajout du bouton, de sa taille etc...
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
