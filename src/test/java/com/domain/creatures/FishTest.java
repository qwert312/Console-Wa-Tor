package com.domain.creatures;

import com.q1w2.domain.Ocean;

import com.q1w2.domain.creatures.Fish;
import com.q1w2.domain.creatures.SeaCreaturesConfiguration;
import com.q1w2.domain.creatures.Shark;
import com.domain.DomainTestHelperMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FishTest {
    private Ocean testOcean;

    @BeforeEach
    public void initializeOcean() {
        testOcean = new Ocean(10, 10,
                new SeaCreaturesConfiguration(3, 9, 5));
    }

    @Test
    public void fishMovesWhenFreeCellNearby() {
        int x = 4, y = 5;
        Fish testFish = new Fish(x, y, testOcean);

        testFish.move();

        assertNull(testOcean.getCell(x, y));
        assertTrue(testOcean.getCell(x + 1, y) == testFish || testOcean.getCell(x - 1, y) == testFish ||
                testOcean.getCell(x, y + 1) == testFish || testOcean.getCell(x, y - 1) == testFish);
    }

    @Test
    public void fishDoesNotMoveWhenSurroundedByFishes() {
        int x = 4, y = 5;
        Fish testFish = new Fish(x, y, testOcean);
        Fish blockingFish1 = new Fish(x + 1, y, testOcean);
        Fish blockingFish2 = new Fish(x - 1, y, testOcean);
        Fish blockingFish3 = new Fish(x, y + 1, testOcean);
        Fish blockingFish4 = new Fish(x, y - 1, testOcean);

        testFish.move();
        assertEquals(testFish, testOcean.getCell(x, y));
        assertFalse(testOcean.getCell(x + 1, y) == testFish || testOcean.getCell(x - 1, y) == testFish ||
                testOcean.getCell(x, y + 1) == testFish || testOcean.getCell(x, y - 1) == testFish);
    }

    @Test
    public void fishDoesNotMoveWhenSurroundedBySharks() {
        int x = 4, y = 5;
        Fish testFish = new Fish(x, y, testOcean);
        Shark blockingShark1 = new Shark(x + 1, y, testOcean);
        Shark blockingShark2 = new Shark(x - 1, y, testOcean);
        Shark blockingShark3 = new Shark(x, y + 1, testOcean);
        Shark blockingShark4 = new Shark(x, y - 1, testOcean);

        testFish.move();
        assertEquals(testFish, testOcean.getCell(x, y));
        assertFalse(testOcean.getCell(x + 1, y) == testFish || testOcean.getCell(x - 1, y) == testFish ||
                testOcean.getCell(x, y + 1) == testFish || testOcean.getCell(x, y - 1) == testFish);
    }

    @Test
    public void fishInOceanCreatesAnotherFishAfterReachingReproductivePeriod() {
        int numberOfFishes = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Fish.class);
        assertEquals(numberOfFishes, 0);

        Fish testFish = new Fish(5, 5, testOcean);
        numberOfFishes = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Fish.class);
        assertEquals(numberOfFishes, 1);

        for (int i = 0; i < testOcean.getCreaturesConfig().getFishReproductionPeriod(); i++) { // тут какая-то ошибка
            testFish.move();
        }
        numberOfFishes = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Fish.class);
        assertEquals(numberOfFishes, 2);
    }

    @Test
    public void fishInOceanReproducesInPreviousCell() {
        Fish testFish = new Fish(5, 5, testOcean);
        int[] testFishCoordinatesRightBeforeReproduction = new int [2];

        for (int i = 0; i < testOcean.getCreaturesConfig().getFishReproductionPeriod(); i++) {
            if (i == testOcean.getCreaturesConfig().getFishReproductionPeriod() - 1)
                testFishCoordinatesRightBeforeReproduction = DomainTestHelperMethods
                        .findXYCoordinatesOfFirstSeaCreature(testOcean);
            testFish.move();
        }
        assertInstanceOf(Fish.class, testOcean.getCell(testFishCoordinatesRightBeforeReproduction[0],
                testFishCoordinatesRightBeforeReproduction[1]));
        assertNotEquals(testOcean.getCell(testFishCoordinatesRightBeforeReproduction[0],
                testFishCoordinatesRightBeforeReproduction[1]), testFish);
    }
}
