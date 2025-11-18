package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GuessMenu {

    private Stage stage;
    private Skin skin;
    private GameController controller;

    private List<Npc> npcs;
    private Murderer murderer;

    public GuessMenu(GameController controller, List<Npc> npcs, Murderer murderer) {
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.npcs = npcs;
        this.murderer = murderer;

    }

    public void display() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        float width = 500;

        root.setWidth(width);
        root.defaults().width(width).fillX();

        // HEADER

        Label header = new Label("Did you find the murderer ?", skin);
        root.add(header).row();

        // BUTTONS

        List<Character> characters = new ArrayList<>();

        characters.add(murderer);
        characters.addAll(npcs);

        Collections.shuffle(characters);

        for (Character character : characters) {
            if(character.isAlive()){
                TextButton charBtn = new TextButton(character.getName(), skin);
                charBtn.addListener(new ClickListener() {
                    @Override public void clicked(InputEvent ev, float x, float y) {
                        controller.guess(character);
                    }
                });
                root.row(); root.add(charBtn);
            }
        }

        TextButton btnClose = new TextButton("Pass", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.row(); root.add(btnClose);

        float x = (Gdx.graphics.getWidth() - root.getWidth()) / 2;
        float y = 0;
        root.setPosition(x, y);

        root.pack();
    }

    public Stage getStage() {
        return stage;
    }

    public void close() {
        controller.closeRoomInventory();
        if(stage != null) stage.dispose();
        stage = null;
    }
}
