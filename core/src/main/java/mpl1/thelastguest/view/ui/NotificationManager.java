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

public class NotificationManager {

    private List<Notification> notifications = new ArrayList<>();

    private Stage stage;
    private Skin skin;

    private final Table root;

    public NotificationManager() {
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.stage = new Stage();
        this.root = new Table();

        stage.addActor(root);

        root.bottom().right();
        root.defaults().fillX();
    }

    public Stage getStage() {
        return stage;
    }

    public void rebuild() {
        root.clearChildren();

        for (Notification n : notifications) {
            Label notif = new Label(n.getText(), skin);
            notif.setAlignment(Align.right);          // text inside the label
            root.add(notif).align(Align.right).pad(10).row(); // cell aligned right
        }

        reposition();
    }

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

        notif.setAlignment(Align.right);          // text inside the label
        root.add(notif).align(Align.right).pad(10).row(); // cell aligned right
        reposition();
    }

    private void reposition() {
        root.pack();
        float x = Gdx.graphics.getWidth() - root.getWidth() - 10;
        float y = 10;
        root.setPosition(x, y);
    }

}
