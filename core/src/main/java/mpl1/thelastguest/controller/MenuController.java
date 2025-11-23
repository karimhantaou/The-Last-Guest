package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.view.EndScreen;
import mpl1.thelastguest.view.MenuScreen;

/**
 * Controller for display menu
 * <p>
 * This class interact with this view {@link MenuScreen} and the principal game {@link Main}
 * Its objective is to trigger the action leading to the menu of the game.
 */
public class MenuController {
    private final Main game;

    /**
     * It's the construtor of the class
     *
     * @param game instance of the principal game {@link Main}
     */
    public MenuController(Main game, MenuScreen view) {
        this.game = game;
    }

    /**
     * It's for restart game
     */
    public void play(){
        game.screenManager.showCharacterSelection();
    }

    /**
     * It's for quit game
     */
    public void quit(){
        Gdx.app.exit();
    }

}
