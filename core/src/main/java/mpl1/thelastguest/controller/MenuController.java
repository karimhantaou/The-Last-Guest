package mpl1.thelastguest.controller;

import mpl1.thelastguest.Main;
import mpl1.thelastguest.view.MenuScreen;

public class MenuController {
    private final Main game;

    public MenuController(Main game, MenuScreen view) {
        this.game = game;
    }

    public void play(){
        game.screenManager.showCharacterSelection();
    }
}
