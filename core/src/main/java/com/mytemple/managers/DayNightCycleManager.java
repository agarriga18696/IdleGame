package com.mytemple.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class DayNightCycleManager {
	private float dayAlpha = 1f;
	private float duskDawnAlpha = 0f;
	private float nightAlpha = 0f;
	private float lightsAlpha = 0f;
	private float brightnessFactor = 1f;  // Factor de brillo y contraste.
	private float flickerTimer = 0f;
	private float flickerSpeed = 0.7f;  // Velocidad del parpadeo.
	private MapManager mapManager;

	public DayNightCycleManager(MapManager mapManager) {
		this.mapManager = mapManager;
	}

	public void update(float delta, int currentHour) {
		// Reset de valores base.
		float targetDay = 0f, targetDuskDawn = 0f, targetNight = 0f;

		// Transiciones entre los mapas.
		if(currentHour >= 0 && currentHour < 5) {  
			targetNight = 1f;

		} else if(currentHour >= 5 && currentHour < 8) {  
			// Amanecer (de noche a amanecer).
			targetDuskDawn = (currentHour - 5) / 2f;
			targetNight = 1f - targetDuskDawn;

		} else if(currentHour >= 8 && currentHour < 18) {  
			// Día (de amanecer a día).
			targetDay = 1f;

		} else if(currentHour >= 18 && currentHour < 19.5) {  
			// Atardecer (de día a atardecer).
			targetDuskDawn = MathUtils.lerp(1f, 0f, (currentHour - 18f) / 2f);
			targetDay = MathUtils.lerp(1f, 0f, (currentHour - 18f) / 2f);

		} else {  
			// Noche (de atardecer a noche).
			targetNight = MathUtils.lerp(0f, 1f, (currentHour - 19.5f) / 2f);
			targetDuskDawn = MathUtils.lerp(1f, 0f, (currentHour - 19.5f) / 2f);
		}

		// Interpolación progresiva de las transiciones.
		dayAlpha = MathUtils.lerp(dayAlpha, targetDay, delta * 0.8f);
		duskDawnAlpha = MathUtils.lerp(duskDawnAlpha, targetDuskDawn, delta * 0.8f);
		nightAlpha = MathUtils.lerp(nightAlpha, targetNight, delta * 0.8f);

		// Efecto de brillo y contraste en el mediodía.
		brightContrastFilter(currentHour, delta);

		// Parpadeo de luces.
		lightsFlickering(currentHour, delta);

	}

	// Método para aplicar un filtro de brillo al mapa.
	private void brightContrastFilter(int currentHour, float delta) {
		if(currentHour >= 11 && currentHour < 15) {
			brightnessFactor = MathUtils.lerp(brightnessFactor, 2.5f, delta * 0.5f); // Ajuste progresivo.

		} else {
			brightnessFactor = MathUtils.lerp(brightnessFactor, 1f, delta * 0.5f);
		}
	}

	// Método para controlar el parpadeo de las luces durante la noche.
	private void lightsFlickering(int currentHour, float delta) {
		// Verificar si es de noche.
		if(currentHour >= 21 || currentHour < 5.2) {
			lightsAlpha = MathUtils.lerp(lightsAlpha, nightAlpha, delta * 0.15f);

			// Ajuste del parpadeo suave de luces.
			flickerTimer += delta * flickerSpeed;
			float flickerEffect = 0.1f * (float) Math.sin(flickerTimer * MathUtils.PI);
			lightsAlpha = MathUtils.clamp(0.45f + flickerEffect, 0.4f, 0.95f);

		} else {
			lightsAlpha = MathUtils.lerp(lightsAlpha, 0f, delta * 0.45f);
		}
	}

	// Métodos para obtener los valores actuales.
	public float getDayAlpha() {
		return dayAlpha;
	}

	public float getDuskDawnAlpha() {
		return duskDawnAlpha;
	}

	public float getNightAlpha() {
		return nightAlpha;
	}

	public float getLightsAlpha() {
		return lightsAlpha;
	}

	public float getBrightnessFactor() {
		return brightnessFactor;
	}
}
