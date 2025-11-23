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

/**
 * Displays a context menu with actions the player can perform on a specific item.
 * <p>
 * The menu appears at the mouse's position and lists only the actions currently
 * permitted for the player (such as scanning for fingerprints, inspecting, or dropping).
 * </p>
 *
 * <p>This UI element uses its own {@link Stage} instance, set as the active
 * input processor while displayed.</p>
 */
public class ItemActionMenu {

    private GameController controller;
    private Player player;
    private Stage stage;
    private Skin skin;

    /**
     * Constructs a new item action menu for the given controller and player.
     *
     * @param controller the game controller used to trigger actions
     * @param player     the player performing the actions
     */
    public ItemActionMenu(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    /**
     * Displays the item action menu at a given mouse position.
     *
     * @param mousePosition the position of the mouse in screen coordinates
     * @param item          the item the player wants to interact with
     */
    public void display(Vector2 mousePosition, Item item) {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        float x = mousePosition.x;
        float y = Gdx.graphics.getHeight() - mousePosition.y; // convert to top-left origin
        float width = 200;

        root.setWidth(width);
        root.setPosition(x, y);
        root.defaults().width(width).fillX();

        // HEADER
        Label header = new Label(item.getName(), skin);
        root.add(header).pad(10).row();

        // ACTIONS -----------------------------------------------------------

        // Scan item's fingerprints
        if (player.canDoAction("scan_fingerprints")) {
            TextButton btnScan = new TextButton("Scan", skin);
            btnScan.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.scanFingerprints(item);
                }
            });
            root.row();
            root.add(btnScan);
        }

        // Inspect / display description
        if (player.canDoAction("inspect")) {
            TextButton btnInspect = new TextButton("Inspect", skin);
            btnInspect.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.displayDescription(item);
                }
            });
            root.row();
            root.add(btnInspect);
        }

        // Drop item
        TextButton btnDrop = new TextButton("Drop", skin);
        btnDrop.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                controller.dropItem(item);
                close();
            }
        });
        root.row();
        root.add(btnDrop);

        // Close menu
        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.row();
        root.add(btnClose);

        root.pack();
    }

    /**
     * Returns the stage containing the menu.
     *
     * @return the current stage, or {@code null} if not displayed
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Closes the action menu, disposes of the stage, and informs the controller.
     */
    public void close() {
        controller.closeItemActionMenu();
        if (stage != null) stage.dispose();
        stage = null;
    }
}
