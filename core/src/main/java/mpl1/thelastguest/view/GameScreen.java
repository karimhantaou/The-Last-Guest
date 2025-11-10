package mpl1.thelastguest.view;

// Import des composants du jeu
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.GameController;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;

import java.util.List;

public class GameScreen implements Screen {
    private final Main game;
    private final BitmapFont font;
    private final GameController controller;


    // Constructeur de salopard
    public GameScreen(Main game, Player player, List<Npc> npcs, Murderer murderer, List<Item> items) {
        this.game = game;
        this.font = new BitmapFont();
        this.controller = new GameController(game, this, player, npcs, murderer, items);
    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(10f, 0, 0, 1);
        controller.update(delta);
    }

    //C'est ici on initialise les élements
    @Override
    public void show() {
    }

    // Permet de gérer le comportement du jeu lors du resize
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
    }
}
