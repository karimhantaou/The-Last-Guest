package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Character.Npc;

import java.util.List;

public class ActionMenu {

    private final Stage stage;
    private final Skin skin;
    private final GameController controller;
    private final Player player;
    private final List<Npc> npcs;

    private boolean open = false;

    public ActionMenu(Skin skin, GameController controller, Player player, List<Npc> npcs) {
        this.stage = new Stage();
        this.skin = skin;
        this.controller = controller;
        this.player = player;
        this.npcs = npcs;
    }

    public Stage getStage() { return stage; }
    public boolean isOpen() { return open; }

    public void display(Vector2 mousePosition) {

        open = true;
        stage.clear();

        Table table = new Table();
        stage.addActor(table);

        float x = mousePosition.x;
        float y = Gdx.graphics.getHeight() - mousePosition.y;
        float tableWidth = 200;

        TextButton move = new TextButton("Move", skin);
        move.addListener(new ClickListener() {
            public void clicked(InputEvent evt, float x, float y) {
                controller.move(mousePosition.x, mousePosition.y);
                close();
            }
        });

        TextButton search = new TextButton("Search", skin);
        search.addListener(new ClickListener() {
            public void clicked(InputEvent evt, float x, float y) {
                controller.search();
            }
        });

        TextButton inspect = new TextButton("Inspect", skin);
        inspect.addListener(new ClickListener() {
            public void clicked(InputEvent evt, float x, float y) {
                controller.inspect(npcs.get(0));
            }
        });

        table.add(move).width(tableWidth).fillX().row();
        table.add(search).width(tableWidth).fillX().row();
        if(player.canDoAction("inspect")) table.add(inspect).width(tableWidth).fillX().row();

        TextButton close = new TextButton("X", skin);
        close.addListener(new ClickListener() {
            public void clicked(InputEvent evt, float x, float y) {
                close();
            }
        });

        table.add(close).width(tableWidth).fillX().row();

        table.pack();
        table.setPosition(x, y - table.getHeight());
    }

    public void close() {
        open = false;
        stage.clear();
    }
}
