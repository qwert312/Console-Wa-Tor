package com.q1w2.domain.creatures;

import com.q1w2.domain.Ocean;


public abstract class SeaCreature {

    protected int x;
    protected int y;
    protected Ocean ocean;
    protected int reproductionCounter = 0;

    protected SeaCreature(int x, int y, Ocean ocean) {
        if (y >= ocean.getHeight() || y < 0 || x >= ocean.getWidth() || x < 0) {
            throw new IllegalArgumentException("Coordinates of the created sea creature are outside the ocean!");
        }

        this.x = x;
        this.y = y;
        this.ocean = ocean;
        ocean.setCell(x, y, this);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getReproductionCounter() {
        return reproductionCounter;
    }

    public Ocean getOcean() {
        return ocean;
    }

    public abstract void move();
}
