package mpl1.thelastguest.view;

// Import des composants du jeu

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.JsonReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.controller.TestController;
import mpl1.thelastguest.model.Npc;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class TestScreen implements Screen {
    private final Main game;
    private final BitmapFont font;
    private final TestController controller;


    // Constructeur de salopard
    public TestScreen(Main game) {
        this.game = game;
        this.font = new BitmapFont();
        this.controller = new TestController(game, this);
    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(10f, 0, 0, 1);
    }

    //C'est ici on initialise les élements
    @Override
    public void show() {
        System.out.println(this.controller.getMurderer().getName());
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
