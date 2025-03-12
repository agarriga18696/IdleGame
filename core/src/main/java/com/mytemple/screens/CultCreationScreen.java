package com.mytemple.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mytemple.CultIdleGame;
import com.mytemple.managers.FontManager;

public class CultCreationScreen implements Screen {

	private CultIdleGame game;
	private Stage stage;
	private Skin skin;
	private TextField cultNameField;
	private TextButton startButton;

	public CultCreationScreen(CultIdleGame game) {
		this.game = game;
		this.stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		this.skin = game.getSkin();

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		Label nameLabel = new Label("Nombre del Culto:", skin, "title");
		cultNameField = new TextField("Culto Anónimo", skin, "textField");
		cultNameField.setAlignment(Align.center);

		startButton = new TextButton("Empezar", skin, "default");
		startButton.getLabel().setStyle(new Label.LabelStyle(FontManager.getFont("button"), Color.WHITE));
		startButton.addListener(event -> {
			if(event.toString().equals("touchDown")) {
				String cultName = cultNameField.getText();

				if(cultName.isEmpty()) {
					cultName = "Culto Anónimo";
				}

				game.setCultName(cultName);
				game.setScreen(new GameScreen(game));
			}

			return true;
		});

		table.add(nameLabel).padBottom(10);
		table.row();
		table.add(cultNameField).width(300).padBottom(40);
		table.row();
		table.add(startButton).width(200).height(50);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void show() {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
}
