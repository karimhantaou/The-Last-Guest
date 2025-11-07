package mpl1.thelastguest.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.model.Board;
import mpl1.thelastguest.view.GameScreen;
import mpl1.thelastguest.view.MenuScreen;

public class GameController {
    private final Main game;
    private GameScreen view;

    public GameController(Main game, GameScreen view) {
        this.game = game;
        this.view = view;
    }


    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new MenuScreen(game));
        }
        if  (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            view.getBoard().playerMoveUp(view.getMap());
        }
        if  (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            view.getBoard().playerMoveDown(view.getMap());
        }
        if  (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            view.getBoard().playerMoveLeft(view.getMap());
        }
        if  (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            view.getBoard().playerMoveRight(view.getMap());
        }
    }
}
