package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.MenuScreen;

import java.util.List;

public class GameController {
    private final Main game;
    private Player player;
    private List<Npc> npcs;
    private Murderer murderer;

    public GameController(Main game, GameScreen view, Player player, List<Npc> npcs, Murderer murderer) {
        this.game = game;
        this.player = player;
        this.npcs = npcs;
        this.murderer = murderer;
    }

    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MenuScreen(game));
        }
    }
}
