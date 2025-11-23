package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.controller.GameController;

/**
 * Represents the pause menu UI displayed when the game is paused.
 * Provides access to common pause actions such as resuming the game,
 * restarting, toggling god mode, adjusting volume, and exiting.
 *
 * <p>This class dynamically builds a Scene2D UI when {@link #display(float)} is called
 * and manages its own {@link Stage} instance.</p>
 */
public class PauseMenu {

    private Stage stage;
    private Skin skin;
    private GameController controller;

    /**
     * Constructs a PauseMenu linked to the given game controller.
     *
     * @param controller the game's main controller used to trigger actions
     *                   such as restarting, exiting, or updating settings.
     */
    public PauseMenu(GameController controller) {
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    /**
     * Displays the pause menu, initializing and configuring all UI components.
     *
     * @param volume the current music volume (0.0 to 1.0); used to set the slider's starting value.
     */
    public void display(float volume) {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        // Semi-transparent background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        root.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        float width = 500;
        root.setWidth(width);
        root.defaults().width(width).fillX();

        // HEADER
        Label header = new Label("Pause", skin);
        root.add(header).pad(10).row();

        // RESUME BUTTON
        TextButton btnClose = new TextButton("Resume", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.row();
        root.add(btnClose);

        // RESTART GAME
        TextButton restart = new TextButton("Restart game", skin);
        restart.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.restartGame();
            }
        });
        root.row();
        root.add(restart);

        // GOD MODE
        TextButton gm = new TextButton("God mode", skin);
        gm.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.godMode();
            }
        });
        root.row();
        root.add(gm);

        // VOLUME SLIDER
        Label music = new Label("Music's volume", skin);
        root.row();
        root.add(music).padTop(10).padLeft(10);

        Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(volume);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.setVolume(volumeSlider.getValue());
            }
        });

        root.row();
        root.add(volumeSlider).padBottom(5);

        // EXIT GAME
        TextButton exit = new TextButton("Exit game", skin);
        exit.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.exitGame();
            }
        });
        root.row();
        root.add(exit);

        // Final positioning
        root.pack();
        float x = (Gdx.graphics.getWidth() - root.getWidth()) / 2;
        float y = (Gdx.graphics.getHeight() - root.getHeight()) / 2;
        root.setPosition(x, y);
        root.pack();
    }

    /**
     * Returns the current {@link Stage} used by the pause menu.
     *
     * @return the stage containing the menu, or {@code null} if not displayed.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Closes the pause menu, disposes the stage, and notifies the controller.
     */
    public void close() {
        controller.closePauseMenu();
        if (stage != null) stage.dispose();
        stage = null;
    }
}
