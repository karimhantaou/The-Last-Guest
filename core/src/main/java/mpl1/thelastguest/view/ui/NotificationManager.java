package mpl1.thelastguest.view.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import mpl1.thelastguest.model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages and displays temporary on-screen notifications.
 * <p>
 * Notifications appear as stacked labels aligned to the bottom-right of the screen.
 * Each notification persists for a configured duration, then fades out and removes itself.
 * </p>
 *
 * <p>The manager owns a dedicated {@link Stage} and a root {@link Table}
 * used to layout and animate notifications.</p>
 */
public class NotificationManager {

    /** The list of currently active notifications. */
    private List<Notification> notifications = new ArrayList<>();

    private Stage stage;
    private Skin skin;

    /** Root layout table containing all notifications. */
    private final Table root;

    /**
     * Constructs a new {@code NotificationManager}, creating its own {@link Stage},
     * loading the UI skin, and preparing the root table used for layout.
     */
    public NotificationManager() {
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.stage = new Stage();
        this.root = new Table();

        stage.addActor(root);

        root.bottom().right();
        root.defaults().fillX();
    }

    /**
     * Returns the stage associated with this notification manager.
     * This stage must be drawn and updated by the game's rendering logic.
     *
     * @return the notification stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Rebuilds the UI by clearing and re-adding all currently stored notifications.
     * <p>This is primarily used when notifications are modified outside of standard flow
     * (e.g., batch updates or debugging).</p>
     */
    public void rebuild() {
        root.clearChildren();

        for (Notification n : notifications) {
            Label notif = new Label(n.getText(), skin);
            notif.setAlignment(Align.right);
            root.add(notif).align(Align.right).pad(10).row();
        }

        reposition();
    }

    /**
     * Adds a new notification to the screen, animating it according to its duration.
     * <p>
     * The notification will:
     * <ul>
     *   <li>appear immediately</li>
     *   <li>remain visible for {@link Notification#getDuration()}</li>
     *   <li>fade out smoothly</li>
     *   <li>remove itself and update layout</li>
     * </ul>
     *
     * @param notification the notification to display
     */
    public void addNotification(Notification notification) {
        notifications.add(notification);

        Label notif = new Label(notification.getText(), skin);
        notif.getColor().a = 1f;

        notif.addAction(Actions.sequence(
            Actions.delay(notification.getDuration()),
            Actions.fadeOut(1f),
            Actions.run(() -> {
                notif.remove();
                notifications.remove(notification);
                notif.remove();
                reposition();
            })
        ));

        notif.setAlignment(Align.right);
        root.add(notif).align(Align.right).pad(10).row();
        reposition();
    }

    /**
     * Recalculates and updates the position of the root container so that
     * the entire notification block remains anchored to the bottom-right
     * corner of the screen.
     */
    private void reposition() {
        root.pack();
        float x = Gdx.graphics.getWidth() - root.getWidth() - 10;
        float y = 10;
        root.setPosition(x, y);
    }

}
