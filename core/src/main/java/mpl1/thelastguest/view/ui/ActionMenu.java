package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Player;

public class ActionMenu {

    private GameController controller;
    private Player player;
    private Stage stage;
    private Skin skin;

    public ActionMenu(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    public void display(Vector2 mousePosition) {
        //close();

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

        // BUTTONS -------------------------------------------------------------

        TextButton btnMove = new TextButton("Move", skin);
        btnMove.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.move(mousePosition.x, mousePosition.y);
                close();
            }
        });
        root.row(); root.add(btnMove);

        TextButton btnSearch = new TextButton("Search", skin);
        btnSearch.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.search();
                close();
            }
        });
        root.row(); root.add(btnSearch);

        if(player.canDoAction("inspect")) {
            TextButton btnInspect = new TextButton("Inspect", skin);
            btnInspect.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                }
            });
            root.row(); root.add(btnInspect);
        }

        if(player.canDoAction("kill")) {
            TextButton btnKill = new TextButton("Kill", skin);
            btnKill.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                }
            });
            root.row(); root.add(btnKill);
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
        controller.closeActionMenu();
        if(stage != null) stage.dispose();
        stage = null;
    }
}
