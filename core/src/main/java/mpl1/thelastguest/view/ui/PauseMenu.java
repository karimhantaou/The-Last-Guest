package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Murderer;

import java.util.Collections;
import java.util.List;

public class PauseMenu {

    private Stage stage;
    private Skin skin;
    private GameController controller;

    public PauseMenu(GameController controller) {
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    public void display() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f); // R,G,B,A
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        root.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        float width = 500;

        root.setWidth(width);
        root.defaults().width(width).fillX();

        // HEADER

        Label header = new Label("Pause", skin);
        root.add(header).pad(10).row();

        // BUTTONS

        TextButton btnClose = new TextButton("Resume", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.row(); root.add(btnClose);

        TextButton restart = new TextButton("Restart game", skin);
        restart.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.restartGame();
            }
        });
        root.row(); root.add(restart);

        TextButton gm = new TextButton("God mode", skin);
        gm.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.godMode();
            }
        });
        root.row(); root.add(gm);

        TextButton exit = new TextButton("Exit game", skin);
        exit.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.exitGame();
            }
        });
        root.row(); root.add(exit);


        root.pack();

        float x = (Gdx.graphics.getWidth() - root.getWidth()) / 2;
        float y = (Gdx.graphics.getHeight() - root.getHeight()) / 2;
        root.setPosition(x, y);
        root.pack();
    }

    public Stage getStage() {
        return stage;
    }

    public void close() {
        controller.closePauseMenu();
        if(stage != null) stage.dispose();
        stage = null;
    }
}
