package com.mytemple.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mytemple.CultIdleGame;

public class MainMenuUI {

	// Atributos.
	private Stage stage;
	private CultIdleGame game;
	private Skin skin;
	private Label titleLabel;
	private TextButton playButton;

	// Constructor.
	public MainMenuUI(CultIdleGame game) {
		this.game = game;
		this.stage = new Stage(new ScreenViewport());

		// Usar el Skin predeterminado.
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

		// Crear título y botón.
		titleLabel = new Label("MY CULT", skin);
		titleLabel.setFontScale(2.5f);

		playButton = new TextButton("JUGAR", skin);

		// Organizar UI con una tabla.
		Table table = new Table();
		table.setFillParent(true);
		table.add(titleLabel).padBottom(20);
		table.row();
		table.add(playButton).width(200).height(50);

		stage.addActor(table);
	}

	public Stage getStage() {
		return stage;
	}

	public TextButton getPlayButton() {
		return playButton;
	}
}
