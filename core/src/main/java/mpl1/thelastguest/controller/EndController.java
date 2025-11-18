package mpl1.thelastguest.controller;

import mpl1.thelastguest.Main;
import mpl1.thelastguest.view.EndScreen;

public class EndController {
    private final Main game;

    public EndController(Main game, EndScreen view) {
        this.game = game;
    }

    public void play(){
        game.screenManager.showCharacterSelection();
    }

}
