package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Board;
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;

import java.util.List;
import java.util.Objects;

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

    public void display(Vector2 mousePosition, List<Character> npcs, Board board) {
        Vector3 worldPos = controller.getView().getCamera().unproject(new Vector3(mousePosition.x, mousePosition.y, 0));
        int tileX = (int)((worldPos.x / 32));
        int tileY =  (int)(worldPos.y / 32);

        Character target = null;

        for (Character npc : npcs) {
            if(npc.getX() == tileX && npc.getY() == tileY && player.isClose(npc)) {
                target = npc;
            }
        }

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);


        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f); // R,G,B,A
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        root.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));


        float x = mousePosition.x;
        float y = Gdx.graphics.getHeight() - mousePosition.y;
        float width = 200;

        root.setWidth(width);
        root.setPosition(x, y);
        root.defaults().width(width).fillX();

        // HEADER

        if(target != null) {

            String lifeStatus;

            if(target.isAlive()){
                lifeStatus = ": Alive";
            }else{
                lifeStatus = ": Dead";
            }

            Label header = new Label(target.getName() + lifeStatus, skin);
            root.add(header).pad(10).row();
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
            Character finalTarget = target;
            btnInspect.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.inspect(finalTarget);
                }
            });
           root.add(btnInspect).row();
        }

        if(player.canDoAction("scan_fingerprints") &&  target != null) {
            TextButton scan = new TextButton("Scan fingerprints", skin);
            Character finalTarget = target;
            scan.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.scanFingerprints(finalTarget);
                }
            });
            root.add(scan).row();
        }

        if(player.canDoAction("scan_fingerprints") &&  target != null && !target.isAlive()) {
            TextButton scan = new TextButton("Scan clues fingerprints", skin);
            Character finalTarget = target;
            scan.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.scanClueFingerprints(finalTarget);
                }
            });
            root.add(scan).row();
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
