package com.github.gissuite.gribinterpolation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {

    private int addNumbers(int x, int y) {
        return x + y;
    }

    @Test
    public void exampleTest() {
        int x = 10;
        int y = 5;
        int expectedResult = 15;

        int result = addNumbers(x, y);

        assertEquals(expectedResult, result);
    }

}
