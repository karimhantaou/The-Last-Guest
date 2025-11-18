package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.Map;

public class PlayerAp {

    private Player player;
    private Stage stage;
    private Skin skin;

    private final Table root;

    public PlayerAp(Player player) {
        this.player = player;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.stage = new Stage();
        this.root = new Table();

        stage.addActor(root);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f); // R,G,B,A
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        root.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        root.setWidth(200);
        root.left();
        root.defaults().width(200).fillX();
    }

    public Stage getStage() {
        return stage;
    }

    public void rebuild() {
        root.clearChildren();

        String ap = String.valueOf(player.getAp());

        Label header = new Label(ap, skin);
        root.add(header).padTop(10).padBottom(10).row();

        root.pack();

        // Position
        float x = (Gdx.graphics.getHeight() - root.getHeight()) / 2f;
        float y = (Gdx.graphics.getHeight() - root.getHeight()) / 2f;
        root.setPosition(x, y);
    }
}
