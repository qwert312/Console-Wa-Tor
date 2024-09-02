package com.domain;

import com.q1w2.domain.Ocean;

public class DomainTestHelperMethods {

    public static <T> int checkNumberOfSeaCreaturesInOcean(Ocean ocean, Class<T> clazz) {
        int number = 0;
        for (int i = 0; i < ocean.getWidth(); i++) {
            for (int j = 0; j < ocean.getWidth(); j++) {
                if (clazz.isInstance(ocean.getCell(j, i)))
                    number++;
            }
        }
        return number;
    }

    public static int[] findXYCoordinatesOfFirstSeaCreature(Ocean ocean) {
        for (int i = 0; i < ocean.getWidth(); i++) {
            for (int j = 0; j < ocean.getWidth(); j++) {
                if (ocean.getCell(j, i) != null)
                    return new int[]{j, i};
            }
        }
        return null;
    }
}
