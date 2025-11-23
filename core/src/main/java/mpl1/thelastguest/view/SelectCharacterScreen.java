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
import mpl1.thelastguest.controller.SelectCharacterController;
import mpl1.thelastguest.model.Character.Npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Screen for selecting a character in "The Last Guest".
 * <p>
 * Displays a character selection menu where the player can view character portraits,
 * descriptions, and stats, and navigate between multiple characters.
 * Provides buttons to select a character or browse through previous/next characters.
 * Delegates selection and navigation logic to {@link SelectCharacterController}.
 * </p>
 */
public class SelectCharacterScreen implements Screen {

    private final Main game;
    private final BitmapFont font;
    private final SelectCharacterController controller;

    private Npc character;
    private Stage stage;
    private Skin skin;
    private Texture charTexture;
    private Image charImage;
    private Label charName;
    private Label charDesc;
    private final List<Label> charStats = new ArrayList<>();

    /**
     * Constructs the SelectCharacterScreen.
     *
     * @param game The main game instance.
     */
    public SelectCharacterScreen(Main game) {
        this.game = game;
        this.font = new BitmapFont();
        this.controller = new SelectCharacterController(game, this);
        this.character = controller.getSelectedCharacter();
    }

    /**
     * Initializes the screen and sets up UI components.
     * Called automatically when this screen becomes active.
     */
    @Override
    public void show() {
        setupUI();
    }

    /**
     * Main render loop. Clears the screen, updates the controller,
     * updates the UI for the selected character, and renders the stage.
     *
     * @param delta Time in seconds since last frame.
     */
    @Override
    public void render(float delta) {
        clearScreen();
        controller.update(delta);
        character = controller.getSelectedCharacter();
        updateCharacterUI();
        stage.act(delta);
        stage.draw();
    }

    /**
     * Clears the screen with a black background.
     */
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Sets up all UI components: background, character portrait,
     * labels for name, description, stats, and navigation buttons.
     */
    private void setupUI() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Background image
        Texture bgTexture = new Texture(Gdx.files.internal("assets/backgrounds/SelectMenu.jpg"));
        Image bg = new Image(bgTexture);
        bg.setFillParent(true);
        stage.addActor(bg);

        // Table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // Semi-transparent background for table
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.5f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        table.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        // Character name label
        Label.LabelStyle labelStyle = skin.get("default", Label.LabelStyle.class);
        labelStyle.font.getData().setScale(2f);
        charName = new Label(character.getName(), labelStyle);
        table.add(charName).pad(10).row();

        // Character image
        charTexture = new Texture(Gdx.files.internal(character.getTexturePath()));
        charImage = new Image(charTexture);
        table.add(charImage).size(128, 128).pad(10).row();

        // Character stats
        Table statTable = new Table();
        charStats.clear();
        for (Map.Entry<String, Integer> entry : character.getStats().entrySet()) {
            Label statLabel = new Label(formatStatName(entry.getKey()) + ": " + entry.getValue(), labelStyle);
            charStats.add(statLabel);
            statTable.add(statLabel).pad(50);
        }
        table.add(statTable).row();

        // Character description
        charDesc = new Label(character.getDescription(), labelStyle);
        table.add(charDesc).pad(20).row();

        // Navigation buttons: Previous, Next, Select
        Table buttonRow = new Table();
        buttonRow.center();
        buttonRow.setFillParent(true);
        buttonRow.bottom();

        TextButton prev = new TextButton("Previous", skin);
        TextButton next = new TextButton("Next", skin);
        TextButton select = new TextButton("Select Character", skin);

        prev.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                controller.previousCharacter();
            }
        });
        next.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                controller.nextCharacter();
            }
        });
        select.addListener(new ClickListener() {
            @Override public void clicked(InputEvent event, float x, float y) {
                controller.selectPlayer();
            }
        });

        buttonRow.add(prev).size(250, 50).pad(50);
        buttonRow.add(select).size(250, 50).pad(50);
        buttonRow.add(next).size(250, 50).pad(50);

        table.add(buttonRow).center().expandX().fill();
    }

    /**
     * Updates UI components for the currently selected character.
     * Updates name, description, portrait, and stats dynamically.
     */
    private void updateCharacterUI() {
        charName.setText(character.getName());
        charDesc.setText(character.getDescription());

        Texture newTexture = new Texture(Gdx.files.internal(character.getTexturePath()));
        charImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newTexture)));
        charTexture.dispose();
        charTexture = newTexture;

        int i = 0;
        for (Map.Entry<String, Integer> entry : character.getStats().entrySet()) {
            charStats.get(i).setText(formatStatName(entry.getKey()) + ": " + entry.getValue());
            i++;
        }
    }

    /**
     * Converts internal stat keys to human-readable labels.
     *
     * @param stat Stat key.
     * @return Human-readable stat name.
     */
    private String formatStatName(String stat) {
        switch (stat) {
            case "str": return "Strength";
            case "per": return "Perception";
            case "lck": return "Luck";
            case "inv": return "Inventory";
            case "ap": return "Action Points";
            default: return "Unknown";
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}

    /**
     * Called when the screen is hidden.
     * Removes input processor to avoid conflicts.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    /**
     * Disposes all resources used by this screen.
     */
    @Override
    public void dispose() {
        font.dispose();
        if (charTexture != null) charTexture.dispose();
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}
