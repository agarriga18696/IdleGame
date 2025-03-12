package com.mytemple.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapManager {
	// Atributos.
	private Texture worldMapDay, worldMapNight, worldMapDuskDawn, worldMapLights, worldMapBorders;
	private Map<String, Texture> regionTextures;
	private Map<String, Pixmap> regionPixmaps;
	private int mapWidth, mapHeight;

	// Constructor.
	public MapManager() {
		// Cargar las texturas del mapa.
		worldMapDay = new Texture(Gdx.files.internal("ui/map/world_map_day.png"));
		worldMapNight = new Texture(Gdx.files.internal("ui/map/world_map_night.png"));
		worldMapDuskDawn = new Texture(Gdx.files.internal("ui/map/world_map_dusk_dawn.png"));
		worldMapLights = new Texture(Gdx.files.internal("ui/map/world_map_lights.png"));
		worldMapBorders = new Texture(Gdx.files.internal("ui/map/world_map_borders.png"));

		// Coger medidas de ancho y alto del mapa.
		mapWidth = worldMapDay.getWidth();
		mapHeight = worldMapDay.getHeight();

		regionTextures = new HashMap<>();
		regionPixmaps = new HashMap<>();

		// Obtener todas las regiones de RegionManager.
		Set<String> allRegions = RegionManager.getAllRegionCodes();

		for(String region : allRegions) {
			String path = "ui/map/regions/" + region + ".png";
			Texture texture = new Texture(Gdx.files.internal(path));
			regionTextures.put(region, texture);
			regionPixmaps.put(region, new Pixmap(Gdx.files.internal(path)));
		}
	}

	// Devuelve la textura del mapa según la progresión del ciclo día/noche. 
	public Texture getCurrentMapTexture(float transitionAlpha) {
		return (transitionAlpha < 0.5f) ? worldMapDay : worldMapNight;
	}

	// Devuelve la textura del día.
	public Texture getDayTexture() {
		return worldMapDay;
	}

	// Devuelve la textura de la noche.
	public Texture getNightTexture() {
		return worldMapNight;
	}

	// Devuelve la textura del atardecer/amanecer.
	public Texture getDuskDawnTexture() {
		return worldMapDuskDawn;
	}

	// Devuelve la textura de las luces.
	public Texture getLightsTexture() {
		return worldMapLights;
	}

	// Devuelve la textura de los bordes.
	public Texture getBordersTexture() {
		return worldMapBorders;
	}

	// Devuelve la textura base de una región.
	public Texture getRegionTexture(String region) {
		return regionTextures.get(region);
	}

	// Devuelve el mapa de Pixmaps de las regiones.
	public Map<String, Pixmap> getRegionPixmaps() {
		return regionPixmaps;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void dispose() {
		worldMapDay.dispose();
		worldMapNight.dispose();
		worldMapLights.dispose();

		for(Texture texture : regionTextures.values()) {
			if(texture != null) texture.dispose();
		}
		regionTextures.clear();

		for(String region : regionPixmaps.keySet()) {
			Pixmap pixmap = regionPixmaps.get(region);

			if(pixmap != null && !pixmap.isDisposed()) {
				pixmap.dispose();
			}
		}
		regionPixmaps.clear();
	}

}
