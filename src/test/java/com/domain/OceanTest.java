package com.domain;

import com.q1w2.domain.creatures.*;
import com.q1w2.domain.Ocean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OceanTest {
    @Test
    public void oceanSetterThrowsExceptionWhenCreatureBelongsToDifferentOcean() {
        Ocean testOcean = new Ocean(10, 10,
                new SeaCreaturesConfiguration(3, 9, 5));

        Ocean anotherOcean = new Ocean(10, 10,
                new SeaCreaturesConfiguration(2, 7, 4));
        Fish testFish = new Fish(0, 0, anotherOcean);

        assertThrows(Exception.class, () -> testOcean.setCell(1, 1, testFish));
    }
}
