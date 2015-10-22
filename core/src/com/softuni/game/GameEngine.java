package com.softuni.game;

import com.badlogic.gdx.Game;

import com.softuni.game.screens.GameScreen;
import com.softuni.game.screens.MainMenu;
import com.softuni.game.screens.Splash;

public class GameEngine extends Game {

    public static final String TITLE = "Java Car Game", VERSION = "0.0.0.1";

    @Override
    public void create() {

        setScreen(new MainMenu());
    }

    @Override
    public void dispose() {

        super.dispose();
    }

    @Override
    public void render() {

        super.render();
    }

    @Override
    public void resize(int width, int height) {

        super.resize(width, height);
    }

    @Override
    public void pause() {

        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

}
