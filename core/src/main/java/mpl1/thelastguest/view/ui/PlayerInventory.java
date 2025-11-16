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
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;

public class PlayerInventory {

    private final Stage stage;
    private final Skin skin;
    private final Player player;
    private final GameController controller;

    private final Table root;

    public PlayerInventory(GameController controller, Player player) {
        this.player = player;
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.stage = new Stage();
        this.root = new Table();

        stage.addActor(root);

        root.setWidth(200);
        root.left();
        root.defaults().width(200).fillX();
    }

    public Stage getStage() {
        return stage;
    }

    public void rebuild() {
        Gdx.input.setInputProcessor(stage);

        root.clearChildren();

        int count = player.countItems();
        int max = player.getInv();

        Label header = new Label("Inventory " + count + "/" + max, skin);
        root.add(header).row();

        for (Item item : player.getItems()) {
            TextButton itemBtn = new TextButton(item.getName(), skin);
            itemBtn.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.displayItemActionMenu(item);
                }
            });
            root.row();
            root.add(itemBtn);
        }

        root.pack();

        // Position left & vertically centered
        float y = (Gdx.graphics.getHeight() - root.getHeight()) / 2f;
        root.setPosition(0, y);
    }
}
