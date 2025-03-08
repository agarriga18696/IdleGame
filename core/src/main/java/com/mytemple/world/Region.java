package com.mytemple.world;

public class Region {

    private String name;
    private int followers;
    private float conversionRate;  // % de población convertida.
    private long totalPopulation;
    
    public Region(String name, long totalPopulation, float conversionRate) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.conversionRate = conversionRate;
        this.followers = 0;
    }

    public String getName() {
        return name;
    }

    public int getFollowers() {
        return followers;
    }

    public float getConversionRate() {
        return conversionRate;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public float getConversionProgress() {
        return (float) followers / totalPopulation;
    }

    public void convertFollowers(int amount) {
        followers = (int) Math.min(followers + amount, totalPopulation);
    }

    public boolean isFullyConverted() {
        return followers >= totalPopulation;
    }
}
