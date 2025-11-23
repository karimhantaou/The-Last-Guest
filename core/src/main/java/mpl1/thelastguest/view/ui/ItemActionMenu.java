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
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;

public class ItemActionMenu {

    private GameController controller;
    private Player player;
    private Stage stage;
    private Skin skin;

    public ItemActionMenu(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    public void display(Vector2 mousePosition, Item item) {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        float x = mousePosition.x;
        float y = Gdx.graphics.getHeight() - mousePosition.y;
        float width = 200;

        root.setWidth(width);
        root.setPosition(x, y);
        root.defaults().width(width).fillX();

        Label header = new Label(item.getName(), skin);
        root.add(header).pad(10).row();

        // BUTTONS

        // Scan item's fingerprints
        if(player.canDoAction("scan_fingerprints")) {
            TextButton btnScan = new TextButton("Scan", skin);
            btnScan.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.scanFingerprints(item);
                }
            });
            root.row(); root.add(btnScan);
        }

        // DISPLAY DESCRIPTION IF CAN INSCPECT
        if(player.canDoAction("inspect")) {
            TextButton btnScan = new TextButton("Inspect", skin);
            btnScan.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.displayDescription(item);
                }
            });
            root.row(); root.add(btnScan);
        }

        // Drop item
        TextButton btnDrop = new TextButton("Drop", skin);
        btnDrop.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.dropItem(item);
                close();
            }
        });
        root.row(); root.add(btnDrop);

        // Close menu
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
        controller.closeItemActionMenu();
        if(stage != null) stage.dispose();
        stage = null;
    }
}
