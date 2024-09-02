package com.q1w2.logic;

import com.q1w2.domain.Ocean;
import com.q1w2.domain.creatures.Fish;
import com.q1w2.domain.creatures.SeaCreature;
import com.q1w2.domain.creatures.SeaCreaturesConfiguration;
import com.q1w2.domain.creatures.Shark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {
    private int currentCycle = 0;
    private final Ocean ocean;

    public Simulation(int oceanHeight, int oceanWidth,
                      int fishReproductionPeriod, int sharkReproductionPeriod, int sharkStarvationLimit) {
        SeaCreaturesConfiguration seaCreaturesconfiguration =
                new SeaCreaturesConfiguration(fishReproductionPeriod, sharkReproductionPeriod, sharkStarvationLimit);
        this.ocean = new Ocean(oceanHeight, oceanWidth, seaCreaturesconfiguration);
        this.fill();
    }

    public Simulation(Ocean ocean) {
        this.ocean = ocean;
        this.fill();
    }

    private void fill() {
        Random rand = new Random();

        for (int i = 0; i < this.ocean.getHeight(); i++) {
            for (int j = 0; j < ocean.getWidth(); j++) {
                int fillChoice = rand.nextInt(5);
                switch (fillChoice) {
                    case 0, 1, 2:
                        this.ocean.setCell(j, i, null);
                        break;
                    case 3:
                        this.ocean.setCell(j, i, new Fish(j, i, this.ocean));
                        break;
                    case 4:
                        this.ocean.setCell(j, i, new Shark(j, i, this.ocean));
                        break;
                }
            }
        }
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public void update() {
        List<SeaCreature> creaturesThatAlreadyMoved = new ArrayList<>();
        for (int i = 0; i < ocean.getHeight(); i++) {
            for (int j = 0; j < ocean.getWidth(); j++) {
                SeaCreature currentCreature = ocean.getCell(j, i);
                if (currentCreature != null && !creaturesThatAlreadyMoved.contains(currentCreature)) {
                    currentCreature.move();
                    creaturesThatAlreadyMoved.add(currentCreature);
                }
            }
        }
        currentCycle++;
    }

    public SeaCreature[][] getCurrentOceanGrid() {
        SeaCreature[][] oceanGrid = new SeaCreature[ocean.getHeight()][ocean.getWidth()];

        for (int i = 0; i < oceanGrid.length; i++) {
            for (int j = 0; j < oceanGrid[i].length; j++) {
                oceanGrid[i][j] = ocean.getCell(j, i);
            }
        }

        return oceanGrid;
    }
}
