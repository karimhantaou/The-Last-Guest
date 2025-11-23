package mpl1.thelastguest;

// Import des composants du jeu
import mpl1.thelastguest.controller.ScreenManager;
import mpl1.thelastguest.view.MenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The main entry point of the game.
 * <p>
 * This class initializes the game, manages the main SpriteBatch,
 * and sets up the initial screen (MenuScreen).
 */
public class Main extends Game {
    public SpriteBatch batch;
    public ScreenManager screenManager;

    /**
     * Called when the game is first created.
     * Initializes the SpriteBatch and ScreenManager,
     * and sets the initial screen to the menu.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        screenManager = new ScreenManager(this);
        setScreen(new MenuScreen(this));
    }

    /**
     * Called continuously to render the game.
     * Delegates rendering to the currently active screen.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Called when the game is closed.
     * Disposes of the SpriteBatch and the active screen.
     */
    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) getScreen().dispose();
    }
}
