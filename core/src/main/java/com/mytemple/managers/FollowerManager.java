package com.mytemple.managers;

import java.util.Random;

public class FollowerManager {

    private int followers = 10;  // Seguidores iniciales
    private float conversionRate = 0.01f;  // Tasa de conversión base (1%)
    private int propagandaLevel = 0;  // Nivel de mejoras
    private int propagandaCost = 10;  // Costo inicial de mejora
    private Random random = new Random();

    public int getFollowers() {
        return followers;
    }

    public float getConversionRate() {
        return conversionRate;
    }

    public int getPropagandaLevel() {
        return propagandaLevel;
    }

    public int getPropagandaCost() {
        return propagandaCost;
    }

    // Método para aumentar seguidores de forma pasiva
    public void updateFollowers(float delta) {
        int newFollowers = Math.max(1, (int) (followers * conversionRate * delta));
        followers += newFollowers;
    }

    // Gastar Fe para mejorar la conversión de seguidores
    public boolean upgradePropaganda(float playerFaith) {
        if (playerFaith >= propagandaCost) {
            conversionRate += 0.005f;  // Aumenta la conversión en 0.5%
            propagandaLevel++;
            propagandaCost *= 2;  // El costo se duplica con cada mejora
            return true;  // La mejora fue exitosa
        }
        return false;  // No hay suficiente Fe
    }

    // Simular un evento aleatorio de seguidores
    public String generateFollowerEvent() {
        int eventType = random.nextInt(3);
        int gainedFollowers = (int) (random.nextInt(50) * conversionRate);

        if (eventType == 0) {
            followers += gainedFollowers;
            return "Un líder carismático se une al culto. +" + gainedFollowers + " seguidores.";
        } else if (eventType == 1) {
            int lostFollowers = random.nextInt(20);
            followers = Math.max(0, followers - lostFollowers);
            return "Una secta rival desacredita tu culto. -" + lostFollowers + " seguidores.";
        } else {
            return "Un milagro ha sido presenciado. La gente habla de tu culto.";
        }
    }
}
