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

public class PlayerInventoryMenu {

    private final Stage stage;
    private final Skin skin;
    private final Player player;
    private final GameController controller;
    private final ItemMenu itemMenu;

    public PlayerInventoryMenu(Skin skin, Player player, GameController controller, ItemMenu itemMenu) {
        this.stage = new Stage();
        this.skin = skin;
        this.player = player;
        this.controller = controller;
        this.itemMenu = itemMenu;
    }

    public Stage getStage() { return stage; }

    public void display() {

        stage.clear();

        Table table = new Table();
        stage.addActor(table);

        int width = 150;

        Label title = new Label("Inventory " + player.countItems() + "/" + player.getInv(), skin);
        table.add(title).width(width).row();

        for (Item i : player.getItems()) {
            TextButton btn = new TextButton(i.getName(), skin);
            btn.addListener(new ClickListener() {
                public void clicked(InputEvent evt, float x, float y) {
                    itemMenu.display(new Vector2(Gdx.input.getX(), Gdx.input.getY()), i);
                }
            });
            table.add(btn).width(width).row();
        }

        table.setPosition(10, Gdx.graphics.getHeight()/2);
    }
}
