package com.mytemple.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mytemple.CultIdleGame;
import com.mytemple.ui.MainMenuUI;

public class MainMenuScreen implements Screen {
	
	// Atributos.
	private CultIdleGame game;
	private MainMenuUI ui;
	
	// Constructor.
	public MainMenuScreen(CultIdleGame game) {
		this.game = game;
		this.ui = new MainMenuUI(game, game.getSkin());
		Gdx.input.setInputProcessor(ui.getStage());
		
		// Evento al botón JUGAR.
		ui.getPlayButton().addListener(new ClickListener() {
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		        game.setScreen(new CultCreationScreen(game));
		    }
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ui.getStage().act(delta);
		ui.getStage().draw();
	}

	@Override
	public void resize(int width, int height) {
		ui.getStage().getViewport().update(width, height, true);		
	}
	
	@Override
	public void dispose() {
		ui.getStage().dispose();
	}
	
	@Override
	public void show() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
	
}
