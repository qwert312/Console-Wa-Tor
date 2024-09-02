package com.q1w2.domain;

import com.q1w2.domain.creatures.SeaCreature;
import com.q1w2.domain.creatures.SeaCreaturesConfiguration;

import java.util.Objects;


public class Ocean {

    private final SeaCreature[][] oceanGrid;
    private final SeaCreaturesConfiguration creaturesConfig;

    public Ocean(int height, int width, SeaCreaturesConfiguration creaturesConfig) {
        this.oceanGrid = new SeaCreature[height][width];
        this.creaturesConfig = creaturesConfig;
    }

    public int getWidth() {
        return oceanGrid.length;
    }

    public int getHeight() {
        return oceanGrid[0].length;
    }

    public SeaCreaturesConfiguration getCreaturesConfig() {
        return this.creaturesConfig;
    }

    public SeaCreature getCell(int x, int y) {
        return oceanGrid[y][x];
    }

    public void setCell(int x, int y, SeaCreature seaCreature) {
        if (Objects.nonNull(seaCreature) && seaCreature.getOcean() != this)
            throw new IllegalArgumentException("Sea creature cannot be placed in a cell of an ocean it doesn't belong to.");

        oceanGrid[y][x] = seaCreature;
    }
}
