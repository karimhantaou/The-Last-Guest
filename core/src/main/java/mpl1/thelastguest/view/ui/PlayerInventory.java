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

/**
 * Displays the player's inventory and current Action Points (AP) on-screen.
 * <p>
 * The inventory UI lists all items held by the player. Items of type {@link StatItem}
 * display their associated stat bonuses. Each item is rendered as a button that, when
 * clicked, triggers the item action menu via the {@link GameController}.
 * </p>
 * <p>
 * The UI is rendered on a dedicated {@link Stage} and laid out using a {@link Table}.
 * </p>
 */
public class PlayerInventory {

    private GameController controller;
    private Player player;
    private Stage stage;
    private Skin skin;

    /** Root table containing all inventory UI elements. */
    private final Table root;

    /**
     * Constructs a new PlayerInventory UI for the given player and controller.
     *
     * @param controller The game controller used to handle item actions.
     * @param player     The player whose inventory will be displayed.
     */
    public PlayerInventory(GameController controller, Player player) {
        this.player = player;
        this.controller = controller;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.stage = new Stage();
        this.root = new Table();

        stage.addActor(root);

        // Semi-transparent background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        root.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        root.setWidth(200);
        root.left();
        root.defaults().width(200).fillX();
    }

    /**
     * Returns the Stage containing the inventory UI.
     *
     * @return The {@link Stage} used to render the player's inventory.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Rebuilds the inventory UI to reflect the player's current items and AP.
     * <p>
     * Displays:
     * <ul>
     *     <li>Current AP / starting AP</li>
     *     <li>Inventory count / maximum inventory size</li>
     *     <li>All items as buttons, with stat bonuses for {@link StatItem}</li>
     * </ul>
     * Clicking an item button triggers {@link GameController#displayItemActionMenu(Item)}.
     * </p>
     * The inventory table is repositioned on the left side of the screen after rebuilding.
     */
    public void rebuild() {
        Gdx.input.setInputProcessor(stage);

        root.clearChildren();

        int count = player.countItems();
        int max = player.getInv();

        String actualAp = String.valueOf(player.getAp());
        String maxAp = String.valueOf(player.getStartAp());

        // Action points label
        Label ap = new Label("Action points: " + actualAp + "/" + maxAp, skin);
        root.add(ap).pad(10).row();

        // Inventory header
        Label header = new Label("Inventory " + count + "/" + max, skin);
        root.add(header).pad(10).row();

        // Add item buttons
        for (Item item : player.getItems()) {
            StringBuilder name = new StringBuilder(item.getName());

            if (item.getClass() == StatItem.class) {
                Map<String, Integer> stats = ((StatItem) item).getStats();
                for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                    if (entry.getValue() > 0) {
                        name.append(" +").append(entry.getValue()).append(" ").append(entry.getKey());
                    }
                }
            }

            TextButton itemBtn = new TextButton(name.toString(), skin);
            itemBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent ev, float x, float y) {
                    controller.displayItemActionMenu(item);
                }
            });
            root.row();
            root.add(itemBtn);
        }

        root.pack();

        // Position the inventory on the left side
        float y = (Gdx.graphics.getHeight() - root.getHeight()) / 2f;
        root.setPosition(10, y);
    }
}
