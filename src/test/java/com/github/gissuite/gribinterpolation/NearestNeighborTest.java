package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.core.NearestNeighbor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NearestNeighborTest {
    @Test
    public void distanceTest(){
        //test values converted to radians, actual long and lat in comments
        double latitude1 = -1.151917306; //-66.0
        double longitude1 = 1.084896663; //62.16
        double latitude2 = -1.117010721; //-64.0
        double longitude2 = 1.08768919; //62.32
        double expectedResult = 223.0605246;

        //extra example
        double latitude3 = -1.151917306; //-66.0
        double longitude3 = 1.084896663; //62.16
        double latitude4 = -1.151917306; //-66.0
        double longitude4 = 1.08768919; //62.32
        double expectedResult2 = 17.79118952;

        double result = NearestNeighbor.haverSine(latitude1,longitude1,latitude2,longitude2);
        assertEquals(expectedResult, result);
    }
}


