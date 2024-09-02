package com.domain.creatures;

import com.q1w2.domain.Ocean;
import com.q1w2.domain.creatures.Fish;
import com.q1w2.domain.creatures.SeaCreaturesConfiguration;
import com.q1w2.domain.creatures.Shark;
import com.domain.DomainTestHelperMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SharkTest {
    private Ocean testOcean;

    @BeforeEach
    public void initializeOcean() {
        testOcean = new Ocean(10, 10,
                new SeaCreaturesConfiguration(3, 8, 5));
    }

    @Test
    public void sharkMovesWhenFreeCellNearby() {
        int x = 4, y = 5;
        Shark testShark = new Shark(x, y, testOcean);

        testShark.move();

        assertNull(testOcean.getCell(x, y));
        assertTrue(testOcean.getCell(x + 1, y) == testShark || testOcean.getCell(x - 1, y) == testShark ||
                testOcean.getCell(x, y + 1) == testShark || testOcean.getCell(x, y - 1) == testShark);
    }

    @Test
    public void sharkMovesWhenFishNearby() {
        int x = 4, y = 5;
        Shark testShark = new Shark(x, y, testOcean);
        Shark blockingFish1 = new Shark(x + 1, y, testOcean);
        Shark blockingFish2 = new Shark(x - 1, y, testOcean);
        Shark blockingFish3 = new Shark(x, y + 1, testOcean);
        Shark blockingFish4 = new Shark(x + 1, y - 1, testOcean);

        testShark.move();

        assertNull(testOcean.getCell(x, y));
        assertTrue(testOcean.getCell(x + 1, y) == testShark || testOcean.getCell(x - 1, y) == testShark ||
                testOcean.getCell(x, y + 1) == testShark || testOcean.getCell(x, y - 1) == testShark);
    }

    @Test
    public void sharkPrioritizesFishOverFreeCell() {
        int x = 4, y = 5;
        Shark testShark = new Shark(x, y, testOcean);
        Fish fishForFeeding1 = new Fish(x + 1, y, testOcean);
        Fish fishForFeeding2 = new Fish(x + 2, y, testOcean);
        Fish fishForFeeding3 = new Fish(x + 3, y, testOcean);
        Fish fishForFeeding4 = new Fish(x + 4, y, testOcean);

        for (int i = 0; i < 4; i++)
            testShark.move();

        assertEquals(testShark.getX(), x + 4);
    }

    @Test
    public void sharkDoesNotMoveWhenSurroundedBySharks() {
        int x = 4, y = 5;
        Shark testShark = new Shark(x, y, testOcean);
        Shark blockingShark1 = new Shark(x + 1, y, testOcean);
        Shark blockingShark2 = new Shark(x - 1, y, testOcean);
        Shark blockingShark3 = new Shark(x, y + 1, testOcean);
        Shark blockingShark4 = new Shark(x, y - 1, testOcean);

        testShark.move();
        assertEquals(testShark, testOcean.getCell(x, y));
    }

    @Test
    public void sharkInOceanCreatesAnotherSharkAfterReachingReproductivePeriod() {
        int numberOfSharks = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Shark.class);
        assertEquals(numberOfSharks, 0);

        Shark testShark = new Shark(5, 5, testOcean);
        numberOfSharks = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Shark.class);
        assertEquals(numberOfSharks, 1);
        Fish fishForFeeding1 = new Fish(5, 6, testOcean);
        Fish fishForFeeding2 = new Fish(5, 7, testOcean);
        Fish fishForFeeding3 = new Fish(5, 8, testOcean);
        Fish fishForFeeding4 = new Fish(5, 9, testOcean);

        for (int i = 0; i < testOcean.getCreaturesConfig().getSharkReproductionPeriod(); i++) {
            testShark.move();
        }
        numberOfSharks = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Shark.class);
        assertEquals(numberOfSharks, 2);
    }

    @Test
    public void sharkInOceanReproducesInPreviousCell() {
        Shark testShark = new Shark(5, 5, testOcean);
        Fish fishForFeeding1 = new Fish(5, 6, testOcean);
        Fish fishForFeeding2 = new Fish(5, 7, testOcean);
        Fish fishForFeeding3 = new Fish(5, 8, testOcean);
        Fish fishForFeeding4 = new Fish(5, 9, testOcean);
        int[] testSharkCoordinatesRightBeforeReproduction = new int [2];

        for (int i = 0; i < testOcean.getCreaturesConfig().getSharkReproductionPeriod(); i++) {
            if (i == testOcean.getCreaturesConfig().getSharkReproductionPeriod() - 1)
                testSharkCoordinatesRightBeforeReproduction = DomainTestHelperMethods
                        .findXYCoordinatesOfFirstSeaCreature(testOcean);
            testShark.move();
        }
        assertInstanceOf(Shark.class, testOcean.getCell(testSharkCoordinatesRightBeforeReproduction[0],
                testSharkCoordinatesRightBeforeReproduction[1]));
        assertNotEquals(testOcean.getCell(testSharkCoordinatesRightBeforeReproduction[0],
                testSharkCoordinatesRightBeforeReproduction[1]), testShark);
    }

    @Test
    public void starvationCounterResetsWhenSharkEatsFish() {
        Shark testShark = new Shark(5, 5, testOcean);

        assertEquals(testShark.getStarvationCounter(), 0);
        testShark.move();
        testShark.move();
        assertEquals(testShark.getStarvationCounter(), 2);

        Fish fishForFeeding = new Fish(testShark.getX() + 1, testShark.getY(), testOcean);
        testShark.move();
        assertEquals(testShark.getStarvationCounter(), 0);
    }

    @Test
    public void sharkInOceanReplacedWithNullAfterReachingStarvationLimit() {
        int numberOfSharks = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Shark.class);
        assertEquals(numberOfSharks, 0);

        Shark testShark = new Shark(5, 5, testOcean);
        numberOfSharks = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Shark.class);
        assertEquals(numberOfSharks, 1);

        for (int i = 0; i < testOcean.getCreaturesConfig().getSharkStarvationLimit(); i++) {
            testShark.move();
        }
        numberOfSharks = DomainTestHelperMethods
                .checkNumberOfSeaCreaturesInOcean(testOcean, Shark.class);
        assertEquals(numberOfSharks, 0);
        assertNull(DomainTestHelperMethods.findXYCoordinatesOfFirstSeaCreature(testOcean));
    }
}
