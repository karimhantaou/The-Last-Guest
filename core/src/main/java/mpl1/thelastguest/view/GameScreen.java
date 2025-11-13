package mpl1.thelastguest.view;

// Import des composants du jeu
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mpl1.thelastguest.Main;
import mpl1.thelastguest.controller.GameController;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import mpl1.thelastguest.model.Character.Murderer;
import mpl1.thelastguest.model.Character.Npc;
import mpl1.thelastguest.model.Character.Player;
import mpl1.thelastguest.model.Item.ActionItem;
import mpl1.thelastguest.model.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private final Main game;
    private final BitmapFont font;
    private final GameController controller;

    private List<Npc> npcs =  new ArrayList<Npc>();
    private Player player;
    private Murderer murderer;

    private Stage stageMenu = new Stage();
    private Skin skin;


    // Constructeur de salopard
    public GameScreen(Main game, Player player, List<Npc> npcs, Murderer murderer, List<Item> items) {
        this.game = game;
        this.font = new BitmapFont();
        this.controller = new GameController(game, this, player, npcs, murderer, items);

        this.npcs = npcs;
        this.player = player;
        this.murderer = murderer;

        ActionItem spoof = new ActionItem("spoof", "scan_fingerprints");
        player.pickItem(spoof);

    }

    // Boucle principal de la vue (pour afficher les élements)
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(10f, 0, 0, 1);
        controller.update(delta);

        stageMenu.act(delta);
        stageMenu.draw();
    }

    //C'est ici on initialise les élements
    @Override
    public void show() {
    }

    // Permet de gérer le comportement du jeu lors du resize
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
    }


    public void displayActionMenu(Vector2 mousePosition) {
        // Load skin
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Create stage
        stageMenu = new Stage();
        Gdx.input.setInputProcessor(stageMenu);

        // Create table
        Table tableMenu = new Table();
        stageMenu.addActor(tableMenu);

        // Position setup
        float x = mousePosition.x;
        float y = Gdx.graphics.getHeight() - mousePosition.y;

        float tableWidth = 200f;
        tableMenu.setWidth(tableWidth);
        tableMenu.pack(); // Adjust size to content
        tableMenu.setPosition(x, y - tableMenu.getHeight()); // Adjust so top aligns with click

        // Buttons

        TextButton move = new TextButton("Move", skin);
        move.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.move();
            }
        });

        TextButton kill = new TextButton("Kill", skin);
        kill.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Kill");
            }
        });

        TextButton inspect = new TextButton("Inspect", skin);
        inspect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.inspect(npcs.get(0));
            }
        });

        TextButton getFinger = new TextButton("Scan fingerprints", skin);
        getFinger.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.scanFingerprints(npcs.get(0));
            }
        });

        TextButton spoof = new TextButton("Spoof fingerprints", skin);
        spoof.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.spoofFingerprints();
            }
        });

        // Layout buttons
        tableMenu.add(move).width(tableWidth).fillX().row();
        if(player.canDoAction("inspect")) tableMenu.add(inspect).width(tableWidth).fillX().row();
        if(player.canDoAction("scan_fingerprints")) tableMenu.add(getFinger).width(tableWidth).fillX().row();
        if(player.canDoAction("kill")) tableMenu.add(kill).width(tableWidth).fillX().row();
        if(player.canDoAction("spoof_fingerprints")) tableMenu.add(spoof).width(tableWidth).fillX().row();

        TextButton close = new TextButton("X", skin);
        close.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.closeActionMenu();
            }
        });

        tableMenu.add(close).width(tableWidth).fillX().row();

        // re-pack to resize table height automatically
        tableMenu.pack();
    }

    public void closeActionMenu(){
        stageMenu.dispose();
    }



}
