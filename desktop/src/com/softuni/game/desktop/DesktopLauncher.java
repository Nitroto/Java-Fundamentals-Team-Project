package com.softuni.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.softuni.game.GameEngine;

import static com.softuni.game.GameEngine.*;


public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = TITLE + " v." + VERSION;
        config.vSyncEnabled = true;
        config.width = 800;
        config.height = 480;
        //config.addIcon("img/icon.png", FileType.Internal);
        //config.useGL30 = true;
        new LwjglApplication(new GameEngine(), config);
    }
}
