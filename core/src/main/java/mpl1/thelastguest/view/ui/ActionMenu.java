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
import mpl1.thelastguest.model.Board;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Character.Npc;

import java.util.List;

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

    public void display(Vector2 mousePosition, List<Npc> npcs, Board board) {

        int tileX = (int)((mousePosition.x / board.getStep()) - 11);
        int tileY =  (int)(50 - mousePosition.y / board.getStep());

        Npc target = null;

        for (Npc npc : npcs) {
            if(npc.getX() == tileX && npc.getY() == tileY) {
                target = npc;
            }
        }

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

        // HEADER

        if(target != null) {
            Label header = new Label(target.getName(), skin);
            root.add(header).row();
        }

        // BUTTONS

        if(target == null)
        {
            TextButton btnMove = new TextButton("Move", skin);
            btnMove.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.move(mousePosition.x, mousePosition.y);
                    close();
                }
            });

            root.add(btnMove).row();
            TextButton btnSearch = new TextButton("Search", skin);
            btnSearch.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.search();
                    close();
                }
            });
            root.add(btnSearch).row();
        }

        if(player.canDoAction("inspect") && target != null) {
            TextButton btnInspect = new TextButton("Inspect", skin);
            Npc finalTarget = target;
            btnInspect.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.inspect(finalTarget);
                }
            });
           root.add(btnInspect).row();
        }

        if(player.canDoAction("scan_fingerprints") &&  target != null) {
            TextButton scan = new TextButton("Scan fingerprints", skin);
            Npc finalTarget = target;
            scan.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.scanFingerprints(finalTarget);
                }
            });
            root.add(scan).row();
        }

        if(player.canDoAction("scan_fingerprints") &&  target != null && !target.isAlive()) {
            TextButton scan = new TextButton("Scan clues fingerprints", skin);
            Npc finalTarget = target;
            scan.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.scanClueFingerprints(finalTarget);
                }
            });
            root.add(scan).row();
        }

        if(player.canDoAction("kill") &&  target != null) {
            TextButton btnKill = new TextButton("Kill", skin);
            btnKill.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                }
            });
            root.add(btnKill).row();
        }

        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.add(btnClose).row();

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
