package com.mytemple.managers;

import com.badlogic.gdx.utils.TimeUtils;

public class GameClock {
	// Atributos.
	private long startTime;
	private int gameDaysElapsed;
	private static final float SECONDS_PER_GAME_DAY = 60f; // 1 día = 60s.

	// Constructor.
	public GameClock() {
		this.startTime = TimeUtils.nanoTime();
		this.gameDaysElapsed = 0;
	}

	public void update() {
		float elapsedSeconds = (TimeUtils.nanoTime() - startTime) / 1_000_000_000f;
		gameDaysElapsed = (int) (elapsedSeconds / SECONDS_PER_GAME_DAY);
	}

	// Método para devolver la fecha.
	public String getCurrentDate() {
		int day = (gameDaysElapsed % 30) + 1;
		int month = ((gameDaysElapsed / 30) % 12) + 1;
		int year = 2025 + (gameDaysElapsed / 365);
		return String.format("%02d/%02d/%04d", day, month, year);
	}

	// Método para devolver la hora.
	public String getCurrentTime() {
		int hour = getCurrentHour();
		int minute = (int) ((getDayProgress() * 1440) % 60);
		return String.format("%02d:%02d", hour, minute);
	}

	public float getDayProgress() {
		float elapsedSeconds = (TimeUtils.nanoTime() - startTime) / 1_000_000_000f;
		return (elapsedSeconds % SECONDS_PER_GAME_DAY) / SECONDS_PER_GAME_DAY;
	}

	public int getCurrentHour() {
		return (int) (getDayProgress() * 24);
	}
}
