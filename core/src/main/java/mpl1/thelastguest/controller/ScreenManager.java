package mpl1.thelastguest.controller;

import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Dialogue;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.view.EndScreen;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.MenuScreen;
import mpl1.thelastguest.view.SelectCharacterScreen;

import java.util.List;

/**
 * Controller for manage all display screen
 * <p>
 * This class interact with the principal game {@link Main}
 * Its objective is to manage the different state of screen on the game.
 */
public class ScreenManager {
    private final Main game;

    /**
     * It's the construtor of the class
     *
     * @param game instance of the principal game {@link Main}
     */
    public ScreenManager(Main game) {
        this.game = game;
    }

    /**
     * it's for show the menu screen
     */
    public void showMenu() {
        game.setScreen(new MenuScreen(game));
    }

    /**
     * it's for show the game screen
     * @param player the
     * @param player instance of the class player (it's the current player of the game) {@link Player}
     * @param npcs list of instance of npc (it's all pnj on the game {@link Npc}
     * @param murderer instance of the class murderer (it's the current murderer of the game) {@link Murderer}
     * @param items list of instance of item (it's all items on the game) {@link Item}
     * @param dialogues list of instance of dialog (it's all dialog with pnj on the game) {@link Dialogue}
     */
    public void showGame(Player player, List<Npc> npcs, Murderer murderer, List<Item> items, List<Dialogue> dialogues) {
        game.setScreen(new GameScreen(game, player, npcs, murderer, items, dialogues));
    }

    /**
     * it's for show the select character screen ( start game)
     */
    public void showCharacterSelection() {
        game.setScreen(new SelectCharacterScreen(game));
    }

    /**
     * it's for show the end screen
     */
    public void showEnd(Murderer murderer, Player player) {
        game.setScreen(new EndScreen(game, murderer, player));
    }
}
