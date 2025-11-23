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
import mpl1.thelastguest.model.Character.Player;

/**
 * Displays a menu showing the player's character stats and action points.
 * <p>
 * Uses a dedicated {@link Stage} and {@link Table} to layout the stats and a close button.
 * The menu provides an overview of the player's current attributes:
 * strength, perception, luck, inventory capacity, and action points (AP).
 * </p>
 */
public class PlayerMenu {

    private Stage stage;
    private Skin skin;
    private GameController controller;
    private Player player;

    /**
     * Constructs a PlayerMenu for the given player.
     *
     * @param controller The game controller used to manage menu closure.
     * @param player     The player whose stats will be displayed.
     */
    public PlayerMenu(GameController controller, Player player) {
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.player = player;
    }

    /**
     * Displays the player menu centered on the screen.
     * <p>
     * The menu shows the player's stats (strength, perception, luck, inventory, and AP)
     * and includes a close button to dismiss the menu.
     * </p>
     */
    public void display() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        float width = 200;

        // Semi-transparent background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        root.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        root.setWidth(width);
        root.defaults().width(width).fillX();

        // Header
        Label header = new Label("Your character", skin);
        root.add(header).pad(10).row();

        // Stats
        root.add(new Label("Strength: " + player.getStr(), skin)).pad(5).row();
        root.add(new Label("Perception: " + player.getPer(), skin)).pad(5).row();
        root.add(new Label("Luck: " + player.getLck(), skin)).pad(5).row();
        root.add(new Label("Inventory: " + player.getInv(), skin)).pad(5).row();
        root.add(new Label("Action points: " + player.getAp(), skin)).pad(5).row();

        // Close button
        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.add(btnClose).pad(20).row();

        root.pack();

        // Center the menu on the screen
        float x = (Gdx.graphics.getWidth() - root.getWidth()) / 2;
        float y = (Gdx.graphics.getHeight() - root.getHeight()) / 2;
        root.setPosition(x, y);
        root.pack();
    }

    /**
     * Returns the stage containing the player menu.
     *
     * @return The {@link Stage} used to render the player menu.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Closes the player menu, disposes its stage, and informs the controller.
     */
    public void close() {
        controller.closePlayerMenu();
        if (stage != null) stage.dispose();
        stage = null;
    }
}
