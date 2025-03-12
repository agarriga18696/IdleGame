package com.mytemple.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import java.util.Map;

public class RegionSelector {
	private Map<String, Pixmap> regionPixmaps;
	private int mapWidth, mapHeight;
	private String hoveredRegion = null;

	public RegionSelector(Map<String, Pixmap> regionPixmaps, int mapWidth, int mapHeight) {
		this.regionPixmaps = regionPixmaps;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}

	public String getSelectedRegion() {
		return hoveredRegion; // 🔹 Devuelve el código de la región
	}

	public String getSelectedRegionName() {
		return hoveredRegion != null ? RegionManager.getRegionName(hoveredRegion) : null;
	}

	public String getRegionName(String regionCode) {
		return RegionManager.getRegionName(regionCode);
	}

	public boolean isHoveringOverRegion() {
		return hoveredRegion != null;
	}

	public void updateRegionHover() {
		int screenX = Gdx.input.getX();
		int screenY = Gdx.graphics.getHeight() - Gdx.input.getY();

		float scaleX = Gdx.graphics.getWidth() / (float) mapWidth;
		float scaleY = Gdx.graphics.getHeight() / (float) mapHeight;
		int pixelX = (int) (screenX / scaleX);
		int pixelY = (int) (screenY / scaleY);

		int correctedY = mapHeight - pixelY - 1;
		hoveredRegion = null;

		for(String regionCode : regionPixmaps.keySet()) {
			Pixmap pixmap = regionPixmaps.get(regionCode);

			if(pixelX >= 0 && pixelX < pixmap.getWidth() && correctedY >= 0 && correctedY < pixmap.getHeight()) {
				int pixel = pixmap.getPixel(pixelX, correctedY);
				int alpha = (pixel & 0xff);

				if(alpha > 50) {
					hoveredRegion = regionCode; // Guardar el código, no el nombre en español.
					return;
				}
			}
		}
	}

}
