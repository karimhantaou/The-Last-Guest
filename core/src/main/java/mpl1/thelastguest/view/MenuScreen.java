package mpl1.thelastguest.view;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Main menu screen of the game "The Last Guest".
 * <p>
 * Displays the game title, and main menu buttons: Play, Rules, and Quit.
 * Handles input processing and delegates actions to {@link MenuController}.
 * Manages stage, table layout, and background visuals.
 * </p>
 */
public class MenuScreen implements Screen {

    private final BitmapFont font;
    private final MenuController controller;
    private Stage stage;
    private Skin skin;

    /**
     * Constructs the MenuScreen.
     *
     * @param game The main game instance.
     */
    public MenuScreen(Main game) {
        this.font = new BitmapFont();
        this.controller = new MenuController(game, this);
    }

    /**
     * Renders the menu screen.
     * Clears the screen and draws all stage actors.
     *
     * @param delta Time in seconds since last frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 10f, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Initializes UI components when the screen is shown.
     * Creates the stage, table layout, background, title label, and buttons.
     * Sets up button click listeners and input processor.
     */
    @Override
    public void show() {
        // Load UI skin
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Font setup
        BitmapFont bigFont = new BitmapFont();
        bigFont.getData().setScale(2f);

        Label.LabelStyle style = skin.get("default", Label.LabelStyle.class);
        style.font.getData().setScale(2f);

        // Stage setup
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Background
        Texture bgTexture = new Texture(Gdx.files.internal("assets/backgrounds/StartMenu.jpg"));
        Image bg = new Image(bgTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        // Table for layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.5f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        // Title label
        Label title = new Label("The Last Guest", style);

        // Buttons
        TextButton play = new TextButton("Play", skin);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.play();
            }
        });

        TextButton rules = new TextButton("Rules", skin);
        rules.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Rules screen logic can be added here
            }
        });

        TextButton quit = new TextButton("Quit", skin);
        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.quit();
            }
        });

        // Layout table
        table.center();
        table.add(title).pad(10).row();
        table.add(play).size(200, 50).pad(10).row();
        table.add(rules).size(200, 50).pad(10).row();
        table.add(quit).size(200, 50).pad(10).row();
        table.pack();
    }

    /**
     * Handles window resizing and updates the stage viewport.
     *
     * @param w New window width.
     * @param h New window height.
     */
    @Override
    public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    /**
     * Called when the screen is hidden.
     * Disposes the stage and removes input processor.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    /**
     * Disposes resources used by the menu screen.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}
