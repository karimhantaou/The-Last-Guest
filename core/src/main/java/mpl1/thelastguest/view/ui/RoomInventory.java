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
import mpl1.thelastguest.model.Room;

import java.util.List;

public class RoomInventory {

    private Stage stage;
    private final Skin skin;
    private final GameController controller;
    private final Player player;

    public RoomInventory(GameController controller, Player player) {
        this.controller = controller;
        this.player = player;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    public void display(Room room, int nbrItems) {

        List<Item> items = room.getItems();

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

        float width = 200;

        root.setWidth(width);
        root.setPosition((float) Gdx.graphics.getWidth() /2 - width / 2, (float) Gdx.graphics.getHeight() /2);
        root.defaults().width(width).fillX();

        // STATS TEST

        int luck = player.getLck();
        int perception = player.getPer();
        int maxItems = items.size();

        if (luck > 8) {
            nbrItems += 2;
        } else if (luck > 5) {
            nbrItems += 1;
        }

        nbrItems = Math.min(nbrItems, maxItems);

        String roomItems = "";
        if (perception >= 6) {
            roomItems = ": " + nbrItems + "/" + maxItems;
        }

        // HEADER

        Label header = new Label(room.getName() + roomItems, skin);
        root.add(header).pad(10).row();

        for(int i = 0; i < nbrItems; i++){
         if(i <= items.size() - 1){
             TextButton itemBtn = new TextButton(items.get(i).getName(), skin);
             int finalI = i;
             itemBtn.addListener(new ClickListener() {
                 @Override public void clicked(InputEvent ev, float x, float y) {
                     controller.pickItem(items.get(finalI));
                 }
             });
             root.add(itemBtn).row();
         }
        }

        if(nbrItems < items.size()){
            TextButton searchAgain = new TextButton("Search again", skin);
            int finalNbrItems = nbrItems;
            searchAgain.addListener(new ClickListener() {
                @Override public void clicked(InputEvent ev, float x, float y) {
                    close();
                    controller.search(finalNbrItems + 1);
                }
            });
            root.add(searchAgain).padTop(10).row();
        }


        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });

        if(nbrItems == items.size()){
            root.add(btnClose).padBottom(5).padTop(10).row();
        } else{
            root.add(btnClose).padBottom(5).row();
        }

        root.pack();
    }

    public Stage getStage() {
        return stage;
    }

    public void close() {
        controller.closeRoomInventory();
        if(stage != null) stage.dispose();
        stage = null;
    }
}
