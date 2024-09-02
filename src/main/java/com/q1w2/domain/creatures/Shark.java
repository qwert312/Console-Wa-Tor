package com.q1w2.domain.creatures;

import com.q1w2.domain.Ocean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shark extends SeaCreature {

    private int starvationCounter = 0;

    public Shark(int x, int y, Ocean ocean) {
        super(x, y, ocean);
    }

    public int getStarvationCounter() {
        return starvationCounter;
    }

    @Override
    public void move() {
        List<int[]> coordinatesOfPossibleCells = getCoordinatesOfPossibleCells().stream()
                .filter(coordinates -> ocean.getCell(coordinates[0], coordinates[1]) instanceof Fish).toList();
        if (coordinatesOfPossibleCells.isEmpty())
            coordinatesOfPossibleCells = getCoordinatesOfPossibleCells().stream()
                    .filter(coordinates -> ocean.getCell(coordinates[0], coordinates[1]) == null).toList();

        this.reproductionCounter++;
        this.starvationCounter++;

        if (coordinatesOfPossibleCells.isEmpty()) {
            if (reproductionCounter >= ocean.getCreaturesConfig().getFishReproductionPeriod())
                reproductionCounter = 0;
            ocean.setCell(x, y, this);
        } else {
            int initialX = this.x;
            int initialY = this.y;
            Random rand = new Random();
            int[] cellCoordinates = coordinatesOfPossibleCells.get(rand.nextInt(coordinatesOfPossibleCells.size()));

            if (ocean.getCell(cellCoordinates[0], cellCoordinates[1]) instanceof Fish)
                starvationCounter = 0;
            this.x = cellCoordinates[0];
            this.y = cellCoordinates[1];
            ocean.setCell(this.x, this.y, this);

            if (reproductionCounter >= ocean.getCreaturesConfig().getSharkReproductionPeriod()) {
                reproductionCounter = 0;
                Shark newShark = new Shark(initialX, initialY, this.ocean);
                ocean.setCell(initialX, initialY, newShark);
            } else
                ocean.setCell(initialX, initialY, null);
        }

        if (starvationCounter >= ocean.getCreaturesConfig().getSharkStarvationLimit())
            ocean.setCell(this.x, this.y, null);
    }

    protected List<int[]> getCoordinatesOfPossibleCells() {
        int topY = y + 1, bottomY = y - 1, rightX = x + 1, leftX = x - 1;

        if (topY >= ocean.getHeight())
            topY = 0;
        else if (bottomY < 0)
            bottomY = ocean.getHeight() - 1;

        if (rightX >= ocean.getWidth())
            rightX = 0;
        else if (leftX < 0)
            leftX = ocean.getWidth() - 1;

        ArrayList<int[]> coordinatesOfPossibleCells = new ArrayList<>();

        addPossibleCoordinatesToList(x, topY, coordinatesOfPossibleCells);
        addPossibleCoordinatesToList(x, bottomY, coordinatesOfPossibleCells);
        addPossibleCoordinatesToList(rightX, y, coordinatesOfPossibleCells);
        addPossibleCoordinatesToList(leftX, y, coordinatesOfPossibleCells);

        return coordinatesOfPossibleCells;
    }

    private void addPossibleCoordinatesToList(int x, int y, List<int[]> coordinatesOfPossibleCells) {
        if (this.ocean.getCell(x, y) == null || ocean.getCell(x, y) instanceof Fish) {
            int[] coordinatesOfPossibleCell = new int[]{x, y};
            coordinatesOfPossibleCells.add(coordinatesOfPossibleCell);
        }
    }
}
