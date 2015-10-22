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
import com.softuni.game.GameEngine;
import com.softuni.game.tween.ActorAccessor;

public class MainMenu implements Screen {

    private Stage stage;
    private Skin skin;
    private Table table;
    private Button exitButton, playButton;
    private Label heading;
    private TweenManager tweenManager;

    @Override
    public void show() {

        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage);
        this.skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/buttons.pack"));

        // creating heading
        this.heading = new Label(GameEngine.TITLE, this.skin);
        this.heading.setFontScale(2);

        this.playButton = new Button(this.skin, "play");
        this.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
            }
        });
        this.playButton.pad(15);

        this.exitButton = new Button(this.skin, "exit");
        this.exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        this.setupTable();
        this.stage.addActor(this.table);

        //creating animations
        this.tweenManager = new TweenManager();
        Tween.registerAccessor(Actor.class, new ActorAccessor());

        //heading color animation
        Timeline.createSequence()
                .beginSequence()
                .push(Tween.to(this.heading, ActorAccessor.RGB, .8f).target(0, 0, 1))
                .push(Tween.to(this.heading, ActorAccessor.RGB, .8f).target(0, 1, 0))
                .push(Tween.to(this.heading, ActorAccessor.RGB, .8f).target(1, 0, 0))
                .push(Tween.to(this.heading, ActorAccessor.RGB, .8f).target(1, 1, 0))
                .push(Tween.to(this.heading, ActorAccessor.RGB, .8f).target(1, 0, 1))
                .push(Tween.to(this.heading, ActorAccessor.RGB, .8f).target(0, 1, 1))
                .push(Tween.to(this.heading, ActorAccessor.RGB, .8f).target(1, 1, 1))
                .end()
                .repeat(Tween.INFINITY, 0)
                .start(this.tweenManager);

        // heading and buttons fade-in
        Timeline.createSequence()
                .beginSequence()
                .push(Tween.set(this.playButton, ActorAccessor.ALPHA).target(0))
                .push(Tween.set(this.exitButton, ActorAccessor.ALPHA).target(0))
                .push(Tween.from(this.heading, ActorAccessor.ALPHA, .5f).target(0))
                .push(Tween.to(this.playButton, ActorAccessor.ALPHA, 1.f).target(1))
                .push(Tween.to(this.exitButton, ActorAccessor.ALPHA, 1.f).target(1))
                .end()
                .start(this.tweenManager);

        //table fade-in
        Tween.from(this.table, ActorAccessor.ALPHA, 1.5f)
                .target(0)
                .start(this.tweenManager);
        Tween.from(this.table, ActorAccessor.Y, 1.5f)
                .target(Gdx.graphics.getHeight() / 8)
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
        this.table.add(this.heading).maxWidth(this.stage.getWidth()).colspan(3).expandX().spaceBottom(25).row();
        this.table.add().uniformX().top().left();
        this.table.add(this.playButton).size(200, 200).uniformX().center();
        this.table.add(this.exitButton).size(120, 120).uniformX().bottom();
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
        //this.table.setSize(width, height);
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
