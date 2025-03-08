package com.mytemple.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mytemple.CultIdleGame;

import java.util.HashMap;
import java.util.Map;

public class GameScreen implements Screen {

	// Atributos.
	private CultIdleGame game;
	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;
	private Texture worldMap;
	private Map<String, Texture> regionTextures;
	private Map<String, Pixmap> regionPixmaps;
	private Label regionLabel;
	private int mapWidth, mapHeight;
	private String selectedRegion = null;

	private static final String[] REGIONS = {
			"Northern Africa", "Eastern Africa", "Western Africa", "Middle Africa", "Southern Africa",
			"United States", "Canada", "Greenland", "Central America", "Caribbean", "Southern America",
			"Northern Europe", "Western Europe", "Eastern Europe", "Southern Europe",
			"Central Asia", "Eastern Asia", "Western Asia", "Southern Asia", "South-eastern Asia",
			"Russia", "Oceania"
	};

	// Constructor.
	public GameScreen(CultIdleGame game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		this.skin = game.getSkin();

		// Cargar la imagen base del mapa.
		worldMap = new Texture(Gdx.files.internal("ui/map/world_map.png"));
		mapWidth = worldMap.getWidth();
		mapHeight = worldMap.getHeight();

		// Cargar imágenes de cada región.
		regionTextures = new HashMap<>();
		regionPixmaps = new HashMap<>();

		for (String region : REGIONS) {
			String path = "ui/map/" + region + ".png";
			Texture texture = new Texture(Gdx.files.internal(path));
			regionTextures.put(region, texture);
			regionPixmaps.put(region, new Pixmap(Gdx.files.internal(path)));
		}

		// Etiqueta para mostrar la región seleccionada.
		regionLabel = new Label("Selecciona una región", skin);
		regionLabel.setPosition(20, Gdx.graphics.getHeight() - 40);
		stage.addActor(regionLabel);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		// Dibujar el mapa base.
		batch.draw(worldMap, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Obtener coordenadas del cursor.
		int screenX = Gdx.input.getX();
		int screenY = Gdx.graphics.getHeight() - Gdx.input.getY();

		// Ajustar escala correctamente.
		float scaleX = Gdx.graphics.getWidth() / (float) mapWidth;
		float scaleY = Gdx.graphics.getHeight() / (float) mapHeight;
		int pixelX = (int) (screenX / scaleX);
		int pixelY = (int) (screenY / scaleY);

		// Invertir Y para la máscara.
		int correctedY = mapHeight - pixelY - 1; // Invertir Y para que el mapa no quede girado verticalmente.

		// Detectar en qué región está el cursor.
		selectedRegion = null;
		for(Map.Entry<String, Pixmap> entry : regionPixmaps.entrySet()) {
			String region = entry.getKey();
			Pixmap pixmap = entry.getValue();

			if(pixelX >= 0 && pixelX < pixmap.getWidth() && correctedY >= 0 && correctedY < pixmap.getHeight()) {
				int pixel = pixmap.getPixel(pixelX, correctedY);
				int alpha = (pixel & 0xff); // Extrae el canal de transparencia.

				if(alpha > 50) { // Si el píxel es opaco, el cursor está sobre esta región.
					selectedRegion = region;
					break;
				}
			}
		}

		// Dibujar la región seleccionada en rojo con transparencia.
		if(selectedRegion != null) {
			batch.setColor(0, 0, 0, 0.25f); // Rojo con opacidad.
			batch.draw(regionTextures.get(selectedRegion), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.setColor(Color.WHITE);
			Gdx.graphics.setSystemCursor(SystemCursor.Hand);
			
		} else {
		    Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
		}

		batch.end();

		// Mostrar el nombre de la región detectada.
		if(selectedRegion != null) {
			regionLabel.setText("Región: " + selectedRegion);

		} else {
			regionLabel.setText("Selecciona una región");
		}

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		batch.dispose();
		worldMap.dispose();
		for (Texture texture : regionTextures.values()) texture.dispose();
		for (Pixmap pixmap : regionPixmaps.values()) pixmap.dispose();
		stage.dispose();
		skin.dispose();
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
