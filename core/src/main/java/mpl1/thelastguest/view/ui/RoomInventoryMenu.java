package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;

import java.util.List;

public class RoomInventoryMenu {

    private final Stage stage;
    private final Skin skin;
    private final Player player;
    private final GameController controller;

    private boolean open = false;

    public RoomInventoryMenu(Skin skin, Player player, GameController controller) {
        this.stage = new Stage();
        this.skin = skin;
        this.player = player;
        this.controller = controller;
    }

    public Stage getStage() { return stage; }
    public boolean isOpen() { return open; }

    public void display(List<Item> items) {

        open = true;
        stage.clear();

        Table table = new Table();
        stage.addActor(table);

        int w = 200;

        Label title = new Label(player.getRoom() + " items", skin);
        table.add(title).width(w).row();

        for (Item item : items) {
            TextButton btn = new TextButton(item.getName(), skin);
            btn.addListener(new ClickListener() {
                public void clicked(InputEvent evt, float xx, float yy) {
                    controller.pickItem(item);
                }
            });
            table.add(btn).width(w).row();
        }

        TextButton close = new TextButton("X", skin);
        close.addListener(new ClickListener() {
            public void clicked(InputEvent evt, float xx, float yy) {
                close();
            }
        });
        table.add(close).width(w).row();

        table.setPosition(
            Gdx.graphics.getWidth()/2 - w/2,
            Gdx.graphics.getHeight()/2
        );
    }

    public void close() {
        open = false;
        stage.clear();
    }
}
