package mpl1.thelastguest.controller;

import mpl1.thelastguest.Main;
import mpl1.thelastguest.view.EndScreen;
/**
 * Controller for display end game
 * <p>
 * This class interact with this view {@link EndScreen} and the principal game {@link Main}
 * Its objective is to trigger the action leading to the end of the game.
 */
public class EndController {
    private final Main game;

    /**
     * It's the construtor of the class
     *
     * @param game instance of the principal game {@link Main}
     */
    public EndController(Main game, EndScreen view) {
        this.game = game;
    }

    /**
     * It's for restart game
     */
    public void play(){
        game.screenManager.showCharacterSelection();
    }

}
