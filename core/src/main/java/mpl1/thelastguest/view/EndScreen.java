package mpl1.thelastguest.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.EndController;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Player;

/**
 * Screen displayed at the end of the game.
 * <p>
 * Shows whether the player found the murderer or died, displays the murderer’s name,
 * the number of kills, and provides buttons to restart the game or return to the main menu.
 * </p>
 */
public class EndScreen implements Screen {

    private final BitmapFont font;
    private final EndController controller;

    private Stage stage;
    private Skin skin;
    private Murderer murderer;
    private Player player;

    /**
     * Constructs an EndScreen.
     *
     * @param game      The main game instance.
     * @param murderer  The murderer character.
     * @param player    The player character.
     */
    public EndScreen(Main game, Murderer murderer, Player player) {
        this.font = new BitmapFont();
        this.controller = new EndController(game, this);
        this.murderer = murderer;
        this.player = player;
    }

    /**
     * Main render loop.
     * Clears the screen and updates/draws the stage.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(11, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    /**
     * Initializes the screen and its UI components.
     * <p>
     * Creates the stage, background image, table layout, labels for the end message,
     * murderer’s name, number of kills, and buttons for "Play again" and "Menu".
     * </p>
     */
    @Override
    public void show() {
        // Skins and fonts
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        BitmapFont bigFont = new BitmapFont();
        bigFont.getData().setScale(2f);

        Label.LabelStyle style = skin.get("default", Label.LabelStyle.class);
        style.font.getData().setScale(2f);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture bgTexture = new Texture(Gdx.files.internal("assets/backgrounds/EndMenu.jpg"));
        Image bg = new Image(bgTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Semi-transparent overlay
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.5f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        // Labels for end messages
        String titleText = player.isAlive() ? "You found the murderer !" : "GAME OVER ! You died !";
        Label title = new Label(titleText, style);
        Label name = new Label("It was " + murderer.getName(), style);

        String killStr = murderer.getKillNbr() + " kill";
        if (murderer.getKillNbr() > 1) {
            killStr += "s";
        }
        killStr += " have been made...";
        Label killNbr = new Label(killStr, style);

        // Buttons
        TextButton play = new TextButton("Play again", skin);
        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.play();
            }
        });

        TextButton menu = new TextButton("Menu", skin);
        menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.menu();
            }
        });

        // Layout
        table.center();
        table.add(title).pad(10).row();
        table.add(name).pad(10).row();
        table.add(killNbr).pad(10).row();
        table.add(play).size(200, 50).pad(10).row();
        table.add(menu).size(200, 50).pad(10).row();
    }

    /**
     * Handles window resizing.
     *
     * @param w New width.
     * @param h New height.
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
     * Called when the screen is no longer visible.
     * <p>
     * Disposes of the stage and removes input processor.
     * </p>
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    /**
     * Disposes all resources used by this screen.
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}
