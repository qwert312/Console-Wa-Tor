package com.q1w2.domain.creatures;

import com.q1w2.domain.Ocean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fish extends SeaCreature {

    public Fish(int x, int y, Ocean ocean) {
        super(x, y, ocean);
    }

    @Override
    public void move() {
        List<int[]> coordinatesOfPossibleCells = getCoordinatesOfPossibleCells();

        this.reproductionCounter++;

        if (coordinatesOfPossibleCells.isEmpty()) {
            if (reproductionCounter >= ocean.getCreaturesConfig().getFishReproductionPeriod())
                reproductionCounter = 0;
        } else {
            int initialX = this.x;
            int initialY = this.y;
            Random rand = new Random();
            int[] cellCoordinates = coordinatesOfPossibleCells.get(rand.nextInt(coordinatesOfPossibleCells.size()));

            this.x = cellCoordinates[0];
            this.y = cellCoordinates[1];
            ocean.setCell(this.x, this.y, this);

            if (reproductionCounter >= ocean.getCreaturesConfig().getFishReproductionPeriod()) {
                reproductionCounter = 0;
                Fish newFish = new Fish(initialX, initialY, this.ocean);
                ocean.setCell(initialX, initialY, newFish);
            } else
                ocean.setCell(initialX, initialY, null);
        }
    }

    protected List<int[]> getCoordinatesOfPossibleCells() {
        int topY = y + 1, bottomY = y - 1, rightX = x + 1, leftX = x - 1;

        if (topY >= ocean.getHeight())
            topY = 0;
        else if (bottomY < 0)
            bottomY = ocean.getHeight() - 1;

        if (rightX >= ocean.getHeight())
            rightX = 0;
        else if (leftX < 0)
            leftX = ocean.getHeight() - 1;

        ArrayList<int[]> coordinatesOfPossibleCells = new ArrayList<>();

        addPossibleCoordinatesToList(x, topY, coordinatesOfPossibleCells);
        addPossibleCoordinatesToList(x, bottomY, coordinatesOfPossibleCells);
        addPossibleCoordinatesToList(rightX, y, coordinatesOfPossibleCells);
        addPossibleCoordinatesToList(leftX, y, coordinatesOfPossibleCells);

        return coordinatesOfPossibleCells;
    }

    private void addPossibleCoordinatesToList(int x, int y, List<int[]> coordinatesOfPossibleCells) {
        if (this.ocean.getCell(x, y) == null) {
            int[] coordinatesOfPossibleCell = new int[]{x, y};
            coordinatesOfPossibleCells.add(coordinatesOfPossibleCell);
        }
    }
}
