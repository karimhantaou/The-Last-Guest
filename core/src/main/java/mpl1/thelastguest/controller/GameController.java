package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.MenuScreen;

public class GameController {
    private final Main game;

    public GameController(Main game, GameScreen view) {
        this.game = game;
    }


    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MenuScreen(game));
        }
    }
}
