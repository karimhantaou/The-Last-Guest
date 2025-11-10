package mpl1.thelastguest.controller;

import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.MenuScreen;
import mpl1.thelastguest.view.SelectCharacterScreen;

import java.util.List;

public class ScreenManager {
    private final Main game;

    public ScreenManager(Main game) {
        this.game = game;
    }

    public void showMenu() {
        game.setScreen(new MenuScreen(game));
    }

    public void showGame(Player player, List<Npc> npcs, Murderer murderer) {
        game.setScreen(new GameScreen(game, player, npcs, murderer));
    }

    public void showCharacterSelection() {
        game.setScreen(new SelectCharacterScreen(game));
    }

}
