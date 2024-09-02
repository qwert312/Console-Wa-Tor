package com.logic;

import com.q1w2.domain.Ocean;
import com.q1w2.domain.creatures.Fish;
import com.q1w2.domain.creatures.SeaCreaturesConfiguration;
import com.q1w2.domain.creatures.Shark;
import com.q1w2.logic.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SimulationTest {
    private Ocean testOcean;

    @BeforeEach
    public void initializeOcean() {
        testOcean = new Ocean(10, 10,
                new SeaCreaturesConfiguration(2, 7, 4));
    }

    @Test
    public void simulationFillsOceanAfterCreation() {
        assertFalse(IntStream.range(0, testOcean.getHeight()).anyMatch(y ->
                IntStream.range(0, testOcean.getWidth()).anyMatch(x -> testOcean.getCell(x, y) != null)));
        Simulation testSimulation = new Simulation(testOcean);
        assertTrue(IntStream.range(0, testOcean.getHeight()).anyMatch(y ->
                IntStream.range(0, testOcean.getWidth()).anyMatch(x -> testOcean.getCell(x, y) != null)));
    }

    @Test
    public void simulationUpdateCallsMoveMethodOnEachSeaCreatureOnlyOnce() {
        Simulation testSimulation = new Simulation(testOcean);

        for (int i = 0; i < testOcean.getHeight(); i++) {
            for (int j = 0; j < testOcean.getWidth(); j++) {
                testOcean.setCell(j, i, null);
            }
        }

        Shark testShark = Mockito.mock(Shark.class);
        when(testShark.getOcean()).thenReturn(testOcean);

        testOcean.setCell(5, 5, testShark);
        testOcean.setCell(7, 6, testShark);

        Fish testFish = Mockito.mock(Fish.class);
        when(testFish.getOcean()).thenReturn(testOcean);

        testOcean.setCell(8, 8, testFish);
        testOcean.setCell(9, 9, testFish);

        testSimulation.update();

        verify(testShark, times(1)).move();
        verify(testFish, times(1)).move();
    }
}
