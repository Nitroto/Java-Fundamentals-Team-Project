package com.softuni.game.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.softuni.game.tween.ActorAccessor;

public class LevelMenu implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;
    private List list;
    private ScrollPane scrollPane;
    private Button playButton, backButton;
    private TweenManager tweenManager;

    public void show() {
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);

        this.skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/buttons.pack"));

        this.list = new List(this.skin);
        this.list.setItems(new String[]{"Level 1", "Level 2", "Level 3"});

        this.scrollPane = new ScrollPane(this.list, this.skin);

        this.playButton = new Button(this.skin, "play");
        this.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        this.playButton.pad(15);
        this.backButton = new Button(this.skin, "back");
        this.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        });
        this.backButton.pad(10);

        this.setupTable();
        this.stage.addActor(this.table);

        //creating animations
        this.tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        //table fade-in
        Tween.from(this.table, ActorAccessor.ALPHA, 1.5f)
                .target(0)
                .start(this.tweenManager);
        Tween.from(this.table, ActorAccessor.Y, 1.5f)
                .target(Gdx.graphics.getHeight())
                .start(this.tweenManager);

        this.tweenManager.update(Gdx.graphics.getDeltaTime());
    }

    private void setupTable() {
        this.table = new Table(this.skin);
        this.table.setFillParent(true);
        this.table.clear();
        this.table.setBounds(0, 0, this.stage.getWidth(), this.stage.getHeight());
        this.table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/bacground.png")))));

        // putting stuff together
        this.table.add("Select Level").colspan(3).expandX().spaceBottom(25).row();

        this.table.add(this.scrollPane).uniformX().expandY().top().left();
        this.table.add(this.playButton).uniformX().size(200, 200);
        this.table.add(this.backButton).size(120, 120).uniformX().bottom();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act(delta);

        this.stage.draw();

        this.tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        //this.stage.setViewport(this.stage.getWidth()width, height, false));
        this.table.invalidateHierarchy();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.skin.dispose();
    }
}
