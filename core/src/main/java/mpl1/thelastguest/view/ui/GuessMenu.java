package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Displays a UI menu allowing the player to guess the identity of the murderer.
 * <p>
 * The menu shows a button for each alive character in randomized order.
 * The player can either guess a character or choose to pass.
 * </p>
 * <p>
 * The menu is rendered on a separate {@link Stage} and uses LibGDX Scene2D UI.
 * </p>
 */
public class GuessMenu {

    private Stage stage;
    private Skin skin;
    private GameController controller;

    /**
     * Constructs a new GuessMenu.
     *
     * @param controller The game controller used to process guesses and manage UI state.
     */
    public GuessMenu(GameController controller) {
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    /**
     * Displays the guess menu centered on the screen.
     * <p>
     * Each alive character in the provided list is shown as a button.
     * Clicking a character button triggers {@link GameController#guess(Character)}.
     * </p>
     *
     * @param npcs      The list of characters to include as guess options.
     * @param murderer  The actual murderer (used internally by the game controller).
     */
    public void display(List<Character> npcs, Murderer murderer) {

        List<Character> guessChar = new ArrayList<Character>();

        for (Character character : npcs) {
            if(character.isAlive()) guessChar.add(character);
        }

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        root.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        float width = 500;

        root.setWidth(width);
        root.defaults().width(width).fillX();

        // Header
        Label header = new Label("Did you find the murderer ?", skin);
        root.add(header).pad(10).row();

        // Shuffle NPCs to randomize button order
        Collections.shuffle(npcs);

        for (Character character : guessChar) {
            TextButton charBtn = new TextButton(character.getName(), skin);
            charBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent ev, float x, float y) {
                    controller.guess(character);
                }
            });
            root.row();
            root.add(charBtn);
        }

        // Pass button
        if(guessChar.size() > 2) {

            TextButton btnClose = new TextButton("Pass", skin);
            btnClose.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent ev, float x, float y) {
                    close();
                }
            });
            root.row();
            root.add(btnClose);
        }


        root.pack();

        // Center the menu
        float x = (Gdx.graphics.getWidth() - root.getWidth()) / 2;
        float y = (Gdx.graphics.getHeight() - root.getHeight()) / 2;
        root.setPosition(x, y);
        root.pack();
    }

    /**
     * Returns the stage containing the guess menu.
     *
     * @return The Stage used to render the menu, or {@code null} if closed.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Closes the guess menu, disposes its stage, and informs the controller.
     */
    public void close() {
        controller.closeGuessMenu();
        if (stage != null) stage.dispose();
        stage = null;
    }
}
