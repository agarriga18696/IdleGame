package com.mytemple;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mytemple.managers.FontManager;
import com.mytemple.screens.GameScreen;
import com.mytemple.screens.MainMenuScreen;

public class CultIdleGame extends Game {

	private Skin skin; // Skin global para la UI.
	private String cultName; // Nombre del Culto elegido por el jugador.
	private String startingRegion;

	@Override
	public void create() {
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		// Llamar al Skin de cargar la pantalla.
		FontManager.initialize(skin);

		// Asegurse de que la pantalla del menú solo se muestra cuando todo está listo.
		Gdx.app.postRunnable(() -> {
			this.setScreen(new MainMenuScreen(this));
		});
	}

	public Skin getSkin() {
		return skin;
	}

	public void setCultName(String name) {
		this.cultName = name;
	}

	public String getCultName() {
		return cultName;
	}

	public void setStartingRegion(String region) {
		this.startingRegion = region;
	}

	public String getStartingRegion() {
		return startingRegion;
	}

	public void startGame() {
		this.setScreen(new GameScreen(this)); // Inicia GameScreen después de seleccionar una región.
	}

	@Override
	public void dispose() {   
		super.dispose();

		if(skin != null) {
			skin.dispose();
			skin = null;
		}

		FontManager.dispose(); // Se eliminan fuentes solo al salir del juego.
	}

}