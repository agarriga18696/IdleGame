package com.mytemple;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mytemple.screens.MainMenuScreen;

public class CultIdleGame extends Game {

    private Skin skin; // Skin global para la UI.

    @Override
    public void create() {
        // Cargar la skin de LibGDX predeterminada.
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Iniciar con la pantalla del menú principal.
        this.setScreen(new MainMenuScreen(this));
    }

    public Skin getSkin() {
        return skin;
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose(); // Liberar la skin cuando el juego se cierra.
    }
}
