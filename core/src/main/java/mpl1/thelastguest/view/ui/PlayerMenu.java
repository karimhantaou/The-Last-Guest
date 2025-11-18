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
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerMenu {

    private Stage stage;
    private Skin skin;
    private GameController controller;

    private Player player;

    public PlayerMenu(GameController controller, Player player) {
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.player = player;

    }

    public void display() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        float width = 200;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f); // R,G,B,A
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        root.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        root.setWidth(width);
        root.defaults().width(width).fillX();

        // HEADER

        Label header = new Label("Your character", skin);
        root.add(header).padTop(10).padBottom(10).row();

        // STATS

        Label str = new Label("Strength: " + player.getStr(), skin);
        root.add(str).pad(2).row();

        Label per = new Label("Perception: " + player.getPer(), skin);
        root.add(per).pad(2).row();

        Label lck = new Label("Luck: " + player.getLck(), skin);
        root.add(lck).pad(2).row();

        Label inv = new Label("Strength: " + player.getInv(), skin);
        root.add(inv).pad(2).row();

        Label ap = new Label("Action points: " + player.getAp(), skin);
        root.add(ap).pad(2).row();

        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.add(btnClose).pad(20).row();

        root.pack();

        float x = (Gdx.graphics.getWidth() - root.getWidth()) / 2;
        float y = (Gdx.graphics.getHeight() - root.getHeight()) /2;

        root.setPosition(x, y);
        root.pack();

    }

    public Stage getStage() {
        return stage;
    }

    public void close() {
        controller.closePlayerMenu();
        if(stage != null) stage.dispose();
        stage = null;
    }
}
