package com.q1w2.domain.creatures;

public class SeaCreaturesConfiguration {
    private final int fishReproductionPeriod;
    private final int sharkReproductionPeriod;
    private final int sharkStarvationLimit;

    public SeaCreaturesConfiguration(int fishReproductionPeriod, int sharkReproductionPeriod,
                                     int sharkStarvationLimit)
    {
        this.fishReproductionPeriod = fishReproductionPeriod;
        this.sharkReproductionPeriod = sharkReproductionPeriod;
        this.sharkStarvationLimit = sharkStarvationLimit;
    }

    public int getFishReproductionPeriod() {
        return this.fishReproductionPeriod;
    }

    public int getSharkReproductionPeriod() {
        return this.sharkReproductionPeriod;
    }

    public int getSharkStarvationLimit() {
        return this.sharkStarvationLimit;
    }
}
