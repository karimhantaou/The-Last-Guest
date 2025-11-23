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

import java.util.List;
import java.util.Objects;

/**
 * UI overlay that displays a context–sensitive action menu when
 * the player right-clicks on a tile, character, or empty space.
 * <p>
 * The menu allows performing actions such as:
 * <ul>
 *     <li>Moving to a location</li>
 *     <li>Searching</li>
 *     <li>Talking to NPCs</li>
 *     <li>Inspecting dead characters</li>
 *     <li>Scanning fingerprints</li>
 *     <li>Unlocking rooms</li>
 * </ul>
 * The menu dynamically adapts to the clicked tile and the player's
 * available actions. It is rendered on a separate {@link Stage}.
 */
public class ActionMenu {

    private GameController controller;
    private Player player;
    private Stage stage;
    private Skin skin;

    /**
     * Creates a new {@code ActionMenu}.
     *
     * @param controller The game controller used to trigger gameplay actions.
     * @param player     The player interacting with the menu.
     */
    public ActionMenu(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    /**
     * Displays the action menu at the given mouse position.
     * <p>
     * The method determines whether the clicked tile contains a character.
     * Depending on the target (NPC or empty tile), the menu displays different
     * sets of actionable buttons (move, talk, inspect, scan, unlock, etc.).
     *
     * @param mousePosition The screen coordinates where the menu should appear.
     * @param npcs          A list of NPCs used to determine whether one occupies the clicked tile.
     * @param board         The board, used primarily to check room lock states.
     */
    public void display(Vector2 mousePosition, List<Character> npcs, Board board) {

        Vector3 worldPos = controller.getView().getCamera()
            .unproject(new Vector3(mousePosition.x, mousePosition.y, 0));

        int tileX = (int)(worldPos.x / 32);
        int tileY = (int)(worldPos.y / 32);

        Character target = null;

        for (Character npc : npcs) {
            if (npc.getX() == tileX &&
                npc.getY() == tileY &&
                player.isClose(npc)) {

                target = npc;
            }
        }

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        stage.addActor(root);

        // Semi-transparent background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.1f, 0.8f);
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
        if (target != null) {

            String lifeStatus = target.isAlive() ? ": Alive" : ": Dead";

            if (target.isFingerPrintFound()) {
                lifeStatus += " " + target.getFingerprint();
            }

            Label header = new Label(target.getName() + lifeStatus, skin);
            root.add(header).pad(10).row();
        }

        // BUTTONS
        if (target == null) {

            // Move
            TextButton btnMove = new TextButton("Move", skin);
            btnMove.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.move(mousePosition.x, mousePosition.y);
                    close();
                }
            });
            root.add(btnMove).row();

            // Search
            TextButton btnSearch = new TextButton("Search", skin);
            btnSearch.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    controller.search(1);
                    close();
                }
            });
            root.add(btnSearch).row();

            // Unlock
            if (player.canDoAction("unlock")
                && board.findRoom(player.getRoom()).isLocked()) {

                TextButton unlock = new TextButton("Unlock", skin);
                unlock.addListener(new ClickListener() {
                    @Override public void clicked(InputEvent ev, float x, float y) {
                        controller.unlock();
                        controller.search(1);
                        close();
                    }
                });
                root.add(unlock).row();
            }
        }

        // Targeted actions
        if (target != null) {

            // Talk
            if (target.isAlive()) {
                TextButton talk = new TextButton("Talk", skin);
                Character finalTarget = target;
                talk.addListener(new ClickListener() {
                    @Override public void clicked(InputEvent ev, float x, float y) {
                        controller.displayTalkMenu(finalTarget, "");
                    }
                });
                root.add(talk).row();
            }

            // Inspect dead body
            if (player.canDoAction("inspect") && !target.isAlive()) {
                TextButton btnInspect = new TextButton("Inspect", skin);
                Character finalTarget = target;
                btnInspect.addListener(new ClickListener() {
                    @Override public void clicked(InputEvent ev, float x, float y) {
                        controller.inspect(finalTarget);
                    }
                });
                root.add(btnInspect).row();
            }

            // Scan fingerprints (alive target)
            if (player.canDoAction("scan_fingerprints") && target.isAlive()) {
                TextButton scan = new TextButton("Scan fingerprints", skin);
                Character finalTarget = target;
                scan.addListener(new ClickListener() {
                    @Override public void clicked(InputEvent ev, float x, float y) {
                        controller.scanFingerprints(finalTarget);
                    }
                });
                root.add(scan).row();
            }

            // Scan fingerprints (dead target clues)
            if (player.canDoAction("scan_fingerprints") && !target.isAlive()) {
                TextButton scan = new TextButton("Scan clues fingerprints", skin);
                Character finalTarget = target;
                scan.addListener(new ClickListener() {
                    @Override public void clicked(InputEvent ev, float x, float y) {
                        controller.scanClueFingerprints(finalTarget);
                    }
                });
                root.add(scan).row();
            }
        }

        // Close button
        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.add(btnClose).row();

        root.pack();
    }

    /**
     * Returns the stage used to render the menu.
     *
     * @return The Stage containing the menu UI, or {@code null} if closed.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Closes the action menu, disposes its stage, and informs the controller.
     */
    public void close() {
        controller.closeActionMenu();
        if (stage != null) stage.dispose();
        stage = null;
    }
}
