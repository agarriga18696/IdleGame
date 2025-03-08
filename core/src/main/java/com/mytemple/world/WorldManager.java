package com.mytemple.world;

import java.util.HashMap;

public class WorldManager {

	private HashMap<String, Region> regions;

	public WorldManager() {
		regions = new HashMap<>();

		// Agregamos regiones con su población y tasa de conversión inicial
		regions.put("Norteamérica", new Region("Norteamérica", 300_000_000L, 0.01f));
		regions.put("Sudamérica", new Region("Sudamérica", 420_000_000L, 0.015f));
		regions.put("Europa Occidental", new Region("Europa Occidental", 200_000_000L, 0.02f));
		regions.put("Europa del Este", new Region("Europa del Este", 250_000_000L, 0.018f));
		regions.put("África", new Region("África", 1_300_000_000L, 0.005f));
		regions.put("Oriente Medio", new Region("Oriente Medio", 500_000_000L, 0.01f));
		regions.put("Asia", new Region("Asia", 4_000_000_000L, 0.008f));  // ← AHORA SÍ FUNCIONA
		regions.put("Oceanía", new Region("Oceanía", 30_000_000L, 0.02f));
	}

	public Region getRegion(String name) {
		return regions.get(name);
	}

	public HashMap<String, Region> getAllRegions() {
		return regions;
	}
}
