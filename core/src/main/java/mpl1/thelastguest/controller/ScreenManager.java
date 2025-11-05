package mpl1.thelastguest.controller;

import mpl1.thelastguest.Main;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.MenuScreen;

public class ScreenManager {
    private final Main game;

    public ScreenManager(Main game) {
        this.game = game;
    }

    public void showMenu() {
        game.setScreen(new MenuScreen(game));
    }

    public void showGame() {
        game.setScreen(new GameScreen(game));
    }
}
