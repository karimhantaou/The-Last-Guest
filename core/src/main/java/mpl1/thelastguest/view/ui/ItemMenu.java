package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;

public class ItemMenu {

    private final Stage stage;
    private final Skin skin;
    private final Player player;
    private final GameController controller;

    public ItemMenu(Skin skin, Player player, GameController controller) {
        this.stage = new Stage();
        this.skin = skin;
        this.player = player;
        this.controller = controller;
    }

    public Stage getStage() { return stage; }

    public void display(Vector2 mouse, Item item) {

        stage.clear();

        Table table = new Table();
        stage.addActor(table);

        float x = mouse.x;
        float y = Gdx.graphics.getHeight() - mouse.y;
        float width = 200;

        TextButton use = new TextButton("Use", skin);
        TextButton drop = new TextButton("Drop", skin);
        TextButton close = new TextButton("X", skin);

        drop.addListener(new ClickListener() {
            public void clicked(InputEvent evt, float xx, float yy) {
                controller.itemDrop(item);
            }
        });

        close.addListener(new ClickListener() {
            public void clicked(InputEvent evt, float xx, float yy) {
                stage.clear();
            }
        });

        table.add(use).width(width).row();
        table.add(drop).width(width).row();

        if(player.canDoAction("scan_fingerprints"))
            table.add(new TextButton("Scan fingerprints", skin)).width(width).row();

        table.add(close).width(width).row();

        table.pack();
        table.setPosition(x, y - table.getHeight());
    }
}
