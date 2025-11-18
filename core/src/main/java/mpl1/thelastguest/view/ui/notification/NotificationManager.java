package mpl1.thelastguest.view.ui.notification;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mpl1.thelastguest.controller.GameController;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.Item;
import mpl1.thelastguest.model.Item.StatItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        root.setWidth(200);
        root.left();
        root.defaults().width(200).fillX();
    }

    public Stage getStage() {
        return stage;
    }

    public void rebuild() {
        root.clearChildren();

        for (Notification n : notifications) {
            Label notif = new Label(n.getText(), skin);
            root.add(notif).row();
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

        root.add(notif).pad(10).row();
        reposition();
    }

    private void reposition() {
        root.pack();
        float x = Gdx.graphics.getWidth() - root.getWidth();
        float y = 0;
        root.setPosition(x, y);
    }

}
