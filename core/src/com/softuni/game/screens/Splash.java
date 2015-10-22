package com.softuni.game.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.softuni.game.InputController;
import com.softuni.game.tween.SpriteAccessor;

public class Splash implements Screen {
    private SpriteBatch batch;
    private Sprite splash;
    private TweenManager tweenManager;

    @Override
    public void show() {
        this.batch = new SpriteBatch();

        Gdx.input.setInputProcessor(new InputController() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                }
                return true;
            }
        });

        this.tweenManager = new TweenManager();

        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        Texture splashTexture = new Texture("./screens/splash/splash.png");
        this.splash = new Sprite(splashTexture);
        this.splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Tween.set(this.splash, SpriteAccessor.ALPHA).target(0).start(this.tweenManager);
        Tween.to(this.splash, SpriteAccessor.ALPHA, 0.5f)
                .target(1)
                .repeatYoyo(1, .5f) // to be fixed
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    }
                })
                .start(this.tweenManager);

        this.tweenManager.update(Float.MIN_VALUE);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();
        this.splash.draw(this.batch);
        this.batch.end();

        this.tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {

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
        this.batch.dispose();
        this.splash.getTexture().dispose();
    }
}
