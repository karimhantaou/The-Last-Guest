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
import mpl1.thelastguest.model.Character.Character;
import mpl1.thelastguest.model.Dialogue;
import mpl1.thelastguest.model.Item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TalkMenu {

    private Stage stage;
    private final Skin skin;
    private final GameController controller;
    private final Player player;
    private final List<Dialogue> greetings;
    private final List<Dialogue> refusals;
    private final List<Dialogue> alibis;


    public TalkMenu(GameController controller, Player player, List<Dialogue> dialogues) {
        this.controller = controller;
        this.player = player;
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.greetings = getGreetings(dialogues);
        this.refusals = getRefusal(dialogues);
        this.alibis = getAlibis(dialogues);
    }

    public List<Dialogue> getGreetings(List<Dialogue> dialogues){
        List<Dialogue> greetings = new ArrayList<>();
        for (Dialogue dialogue : dialogues) {
            if(Objects.equals(dialogue.getType(), "greeting")){
                greetings.add(dialogue);
            }
        }
        return greetings;
    }

    public Dialogue getRandomGreeting() {
        return this.greetings.get((int) (Math.random() * this.greetings.size()));
    }


    public List<Dialogue> getRefusal(List<Dialogue> dialogues){
        List<Dialogue> refusals = new ArrayList<>();
        for (Dialogue dialogue : dialogues) {
            if(Objects.equals(dialogue.getType(), "refusal")){
                refusals.add(dialogue);
            }
        }
        return refusals;
    }

    public Dialogue getRandomRefusal() {
        return this.refusals.get((int) (Math.random() * this.refusals.size()));
    }

    public List<Dialogue> getAlibis(List<Dialogue> dialogues){
        List<Dialogue> alibis = new ArrayList<>();
        for (Dialogue dialogue : dialogues) {
            if(Objects.equals(dialogue.getType(), "alibi")){
                alibis.add(dialogue);
            }
        }
        return alibis;
    }

    public Dialogue getRandomAlibi() {
        return this.alibis.get((int) (Math.random() * this.alibis.size()));
    }

    public void display(Character npc, String answer) {

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

        float width = 500;

        root.setWidth(width);
        root.setPosition((float) Gdx.graphics.getWidth() /2 - width / 2, (float) Gdx.graphics.getHeight() /2);
        root.defaults().width(width).fillX();

        // HEADER

        String headerText = npc.getName();

        if(npc.isFingerPrintFound()) headerText += " " +  npc.getFingerprint();

        Label header = new Label(headerText, skin);
        root.add(header).pad(10).row();


        Label greeting = new Label(getDialogue(answer), skin);
        root.add(greeting).pad(10).row();

        if(npc.isFingerPrintFound()){
            for(Item weapon: player.getWeapons()){
                if(weapon.isFingerPrintFound() && Objects.equals(weapon.getFingerprint(), npc.getFingerprint())){
                    TextButton fpAccuse = new TextButton("Why does your fingerprint are on this " + weapon.getName() + " ?", skin);
                    fpAccuse.addListener(new ClickListener() {
                        @Override public void clicked(InputEvent ev, float x, float y) {
                            close();
                            controller.askForFingerprint(npc);                        }
                    });
                    root.add(fpAccuse).row();
                }
            }
        }


        TextButton btnClose = new TextButton("Close", skin);
        btnClose.addListener(new ClickListener() {
            @Override public void clicked(InputEvent ev, float x, float y) {
                close();
            }
        });
        root.add(btnClose).padBottom(5).padTop(10).row();

        root.pack();
    }

    public String getDialogue(String answer){
        switch (answer){
            case "refusal": return getRandomRefusal().getMessage();
            case "alibi": return getRandomAlibi().getMessage();
            default: return getRandomGreeting().getMessage();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void close() {
        controller.closeTalkMenu();
        if(stage != null) stage.dispose();
        stage = null;
    }

}
