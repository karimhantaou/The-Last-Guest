package mpl1.thelastguest;

// Import des composants du jeu
import mpl1.thelastguest.controller.ScreenManager;
import mpl1.thelastguest.view.MenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {
    public SpriteBatch batch;
    public ScreenManager screenManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        screenManager = new ScreenManager(this);
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) getScreen().dispose();
    }
}
