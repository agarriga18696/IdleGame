package com.mytemple.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mytemple.CultIdleGame;
import com.mytemple.graphics.WaterShader;
import com.mytemple.managers.DayNightCycleManager;
import com.mytemple.managers.FontManager;
import com.mytemple.managers.GameClock;
import com.mytemple.managers.MapManager;
import com.mytemple.managers.RegionManager;
import com.mytemple.managers.RegionSelector;
import com.mytemple.ui.GameUI;

public class GameScreen implements Screen {

	// Atributos.
	private CultIdleGame game;
	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;
	private MapManager mapManager;
	private RegionSelector regionSelector;
	private WaterShader waterShader;
	private GameUI gameUI;
	private GameClock gameClock;
	private DayNightCycleManager dayNightCycleManager;
	private Label dateLabel;
	private Label clockLabel;
	private Label regionLabel;
	private String selectedRegion = null;
	private String hoveredRegion = null;
	private boolean allowSelection = false;

	// Constructor.
	public GameScreen(CultIdleGame game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		this.skin = game.getSkin();
		this.mapManager = new MapManager();
		this.gameClock = new GameClock();
		this.dayNightCycleManager = new DayNightCycleManager(mapManager);

		regionSelector = new RegionSelector(mapManager.getRegionPixmaps(), mapManager.getMapWidth(), mapManager.getMapHeight());
		waterShader = new WaterShader();
		gameUI = new GameUI(stage, skin);

		// UI del label de selección de región.
		regionLabel = new Label("Selecciona una región", new Label.LabelStyle(FontManager.getFont("button"), Color.WHITE));
		regionLabel.setPosition(20, Gdx.graphics.getHeight() - 50);
		stage.addActor(regionLabel);

		// UI del calendario y reloj.
		dateLabel = new Label("...", skin, "button");
		dateLabel.setColor(Color.WHITE);
		dateLabel.setPosition(Gdx.graphics.getWidth() - 145, Gdx.graphics.getHeight() - 50);
		stage.addActor(dateLabel);
		
		clockLabel = new Label("...", skin, "button");
		clockLabel.setColor(Color.WHITE);
		clockLabel.setPosition(Gdx.graphics.getWidth() - 78, Gdx.graphics.getHeight() - 85);
		stage.addActor(clockLabel);

		// Mostrar mensaje inicial con la nueva GameUI.
		gameUI.showStandardDialog("Selecciona una región donde empezar el culto.");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		// Actualizar el tiempo del juego.
		gameClock.update();
		dateLabel.setText(gameClock.getCurrentDate());
		clockLabel.setText(gameClock.getCurrentTime());

		// Obtener la hora actual en el juego.
		int currentHour = gameClock.getCurrentHour();

		// Actualizar ciclo de día/noche.
		dayNightCycleManager.update(delta, currentHour);

		// Obtener valores de interpolación y luces.
		float dayAlpha = dayNightCycleManager.getDayAlpha();
		float duskDawnAlpha = dayNightCycleManager.getDuskDawnAlpha();
		float nightAlpha = dayNightCycleManager.getNightAlpha();
		float lightsAlpha = dayNightCycleManager.getLightsAlpha();
		float brightnessFactor = dayNightCycleManager.getBrightnessFactor();

		// Aplicar efecto de brillo en el mediodía.
		batch.setColor(brightnessFactor, brightnessFactor * 0.95f, brightnessFactor * 0.95f, dayAlpha);
		batch.draw(mapManager.getDayTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Dibujar el mapa de atardecer/amanecer con transición progresiva.
		batch.setColor(brightnessFactor, 1, brightnessFactor * 0.95f, duskDawnAlpha);
		batch.draw(mapManager.getDuskDawnTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Dibujar el mapa de noche con transición progresiva.
		batch.setColor(0.45f, 0.47f, 0.6f, nightAlpha);
		batch.draw(mapManager.getNightTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Restaurar color blanco
		batch.setColor(Color.WHITE);

		// Dibujar el mapa de luces.
		if(lightsAlpha > 0.01f) {
			batch.setColor(1, 1, 1, lightsAlpha);
			batch.draw(mapManager.getLightsTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.setColor(Color.WHITE);
		}

		//System.out.println("Fecha y hora actual: " + clockLabel.getText() + " | Textura Mapa actual: " + (lightsAlpha > 0.35 ? "Noche" : "Día") + " | Luces (lightsAlpha): " + lightsAlpha);

		// TODO Activar shader de efecto agua.
		batch.setShader(waterShader.getShader());
		waterShader.update(delta);
		waterShader.apply();
		batch.setShader(null);

		// Actualizar hover de la región.
		regionSelector.updateRegionHover();
		hoveredRegion = regionSelector.getSelectedRegion();

		// Efecto de hover para oscurecer la región.
		if(hoveredRegion != null && selectedRegion == null) {
			Texture regionTexture = mapManager.getRegionTexture(hoveredRegion);
			if(regionTexture != null) {
				batch.setColor(1, 1, 1, 0.25f);
				batch.draw(regionTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				batch.setColor(Color.WHITE);
				Gdx.graphics.setSystemCursor(SystemCursor.Hand);
			}

		} else {
			Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
		}

		// Si ya se ha seleccionado una región, marcarla con otro color.
		if(selectedRegion != null) {
			Texture regionTexture = mapManager.getRegionTexture(selectedRegion);
			if(regionTexture != null) {
				batch.setColor(0.3f, 0f, 0f, 0.5f); // Color translúcido.
				batch.draw(regionTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				batch.setColor(Color.WHITE);
			}
		}

		batch.end();

		// Mostrar el nombre en español de la región detectada.
		regionLabel.setText(hoveredRegion != null ? "Región: " + regionSelector.getSelectedRegionName() : "Selecciona una región");

		// Detectar clic del jugador para seleccionar la región.
		if(Gdx.input.justTouched()) {
			if(!allowSelection) {
				allowSelection = true;

			} else if(hoveredRegion != null && selectedRegion == null) {
				selectedRegion = hoveredRegion; 
				game.setStartingRegion(selectedRegion);

				// Mostrar cuadro de diálogo tras la selección.
				gameUI.showTimedDialog("[RED]" + game.getCultName() + "[] ha surgido en " + RegionManager.getRegionName(game.getStartingRegion()), 3f);
				//gameUI.showManualDialog("¡Evento importante! Se ha descubierto una nueva secta en tu región.");
				//gameUI.showEventDialog("Gran Evento", "El gobierno ha comenzado a sospechar de tu culto.");
			}
		}

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
		mapManager.dispose();
		waterShader.dispose();
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void show() {}
}
