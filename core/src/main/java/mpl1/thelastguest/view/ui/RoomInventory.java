package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Room;

import java.util.List;

public class RoomInventory {

    private Stage stage;
    private Skin skin;
    private GameController controller;

    public RoomInventory(GameController controller, Player player) {
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    public void display(Room room) {

        List<Item> items = room.getItems();
        System.out.println(items);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        float width = 200;

        root.setWidth(width);
        root.setPosition((float) Gdx.graphics.getWidth() /2 - width / 2, (float) Gdx.graphics.getHeight() /2);
        root.defaults().width(width).fillX();

        // HEADER

        Label header = new Label(room.getName(), skin);
        root.add(header).row();

        // BUTTONS

        for (Item item : items) {
            TextButton itemBtn = new TextButton(item.getName(), skin);
            itemBtn.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.pickItem(item);
                }
            });
            root.row(); root.add(itemBtn);
        }

        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.row(); root.add(btnClose);

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
