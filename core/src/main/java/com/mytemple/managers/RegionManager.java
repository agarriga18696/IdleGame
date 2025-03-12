package com.mytemple.managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RegionManager {

	// Mapa de continentes con sus regiones y nombres traducidos.
	private static final Map<String, Map<String, String>> CONTINENT_REGIONS = new HashMap<>();

	static {
		// Norteamérica.
		Map<String, String> northamerica = new HashMap<>();
		northamerica.put("na_greenland", "Groenlandia");
		northamerica.put("na_canada", "Canadá");
		northamerica.put("na_united_states", "Estados Unidos");

		// Centroamérica.
		Map<String, String> centralamerica = new HashMap<>();
		centralamerica.put("ca_central_america", "América Central");
		centralamerica.put("ca_caribbean", "Caribe");

		// Sudamérica.
		Map<String, String> southamerica = new HashMap<>();
		southamerica.put("sa_northern_south_america", "Sudamérica Septentrional");
		southamerica.put("sa_central_south_america", "Sudamérica Central");
		southamerica.put("sa_southern_south_america", "Sudamérica Meridional");
		southamerica.put("sa_brazil", "Brasil");

		// Europa.
		Map<String, String> europe = new HashMap<>();
		europe.put("eu_northern_europe", "Europa Septentrional");
		europe.put("eu_western_europe", "Europa Occidental");
		europe.put("eu_eastern_europe", "Europa Oriental");
		europe.put("eu_southern_europe", "Europa Meridional");
		europe.put("eu_south_eastern_europe", "Europa Sudoriental");

		// Asia.
		Map<String, String> asia = new HashMap<>();
		asia.put("as_central_asia", "Asia Central");
		asia.put("as_eastern_asia", "Asia Oriental");
		asia.put("as_south_eastern_asia", "Sudeste Asiático");
		asia.put("as_southern_asia", "Asia Meridional");
		asia.put("as_western_asia", "Asia Occidental");

		// Rusia.
		Map<String, String> russia = new HashMap<>();
		russia.put("ru_russia", "Rusia");

		// África.
		Map<String, String> africa = new HashMap<>();
		africa.put("af_northern_africa", "África del Norte");
		africa.put("af_western_africa", "África Occidental");
		africa.put("af_central_africa", "África Central");
		africa.put("af_eastern_africa", "África Oriental");
		africa.put("af_southern_africa", "África Meridional");

		// Oceanía.
		Map<String, String> oceania = new HashMap<>();
		oceania.put("oc_australia_new_zealand", "Australia y Nueva Zelanda");
		oceania.put("oc_pacific_islands", "Islas del Pacífico");

		// Agregar continentes al mapa global.
		CONTINENT_REGIONS.put("Norteamérica", northamerica);
		CONTINENT_REGIONS.put("Centroamérica", centralamerica);
		CONTINENT_REGIONS.put("Sudamérica", southamerica);
		CONTINENT_REGIONS.put("Europa", europe);
		CONTINENT_REGIONS.put("Asia", asia);
		CONTINENT_REGIONS.put("Rusia", russia);
		CONTINENT_REGIONS.put("África", africa);
		CONTINENT_REGIONS.put("Oceanía", oceania);
	}

	// Obtener el nombre en español de una región desde su código.
	public static String getRegionName(String regionCode) {
		for(Map<String, String> regions : CONTINENT_REGIONS.values()) {
			if(regions.containsKey(regionCode)) {
				return regions.get(regionCode);
			}
		}

		return "Región Desconocida";
	}

	// Obtener el continente de una región.
	public static String getContinentByRegion(String regionCode) {
		for(Map.Entry<String, Map<String, String>> entry : CONTINENT_REGIONS.entrySet()) {
			if(entry.getValue().containsKey(regionCode)) {
				return entry.getKey();
			}
		}

		return "Continente Desconocido";
	}

	// Obtener todas las regiones de un continente.
	public static Set<String> getRegionsByContinent(String continent) {
		if(CONTINENT_REGIONS.containsKey(continent)) {
			return CONTINENT_REGIONS.get(continent).keySet();
		}

		return null;
	}

	// Verificar si una región existe en la estructura.
	public static boolean regionExists(String regionCode) {
		for(Map<String, String> regions : CONTINENT_REGIONS.values()) {
			if(regions.containsKey(regionCode)) {
				return true;
			}
		}

		return false;
	}

	// Obtener todos los códigos de regiones.
	public static Set<String> getAllRegionCodes() {
		Set<String> allRegions = new HashSet<>();
		
		for(Map<String, String> regions : CONTINENT_REGIONS.values()) {
			allRegions.addAll(regions.keySet());
		}
		
		return allRegions;
	}
}
