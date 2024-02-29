package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.DistanceFinder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceFinderTest {
    @Test
    public void distanceTest(){
        //test values converted to radians, actual long and lat in comments
        double latitude1 = -1.151917306; //-66.0
        double longitude1 = 1.084896663; //62.16
        double latitude2 = -1.117010721; //-64.0
        double longitude2 = 1.08768919; //62.32
        double expectedResult = 3.8938310894808135;

        //extra example
        double latitude3 = -1.151917306; //-66.0
        double longitude3 = 1.084896663; //62.16
        double latitude4 = -1.151917306; //-66.0
        double longitude4 = 1.08768919; //62.32
        double expectedResult2 = 0.31045208183444206;

        double result = DistanceFinder.haverSine(latitude1,longitude1,latitude2,longitude2);
//        double result = DistanceFinder.haverSine(latitude3,longitude3,latitude4,longitude4);
        assertEquals(expectedResult, result);
//        assertEquals(expectedResult2, result);
    }
}


