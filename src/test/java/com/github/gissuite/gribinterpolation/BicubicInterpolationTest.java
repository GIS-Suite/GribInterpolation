package com.github.gissuite.gribinterpolation;

import static java.lang.Float.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.gissuite.gribinterpolation.core.BicubicInterpolation;
import com.github.gissuite.gribinterpolation.data.DataPoint;
import org.junit.jupiter.api.Test;

public class BicubicInterpolationTest{
    @Test
    public void bicubicInterpolation_LatLong_Should_Return_DataPoint(){
        
        //point to interpolate
        DataPoint UnknownTemp = new DataPoint(60.55f, 60.55f, NaN, 0f);

        //known data points
        DataPoint[][] Points = {{new DataPoint(60f, 60f, 300, 0f), new DataPoint(60.5f, 60f, 320f, 0f), new DataPoint(61f, 60f, 325f, 0f), new DataPoint(61.5f, 60f, 300f, 0f)},
                                {new DataPoint(60f, 60.5f, 320f, 0f), new DataPoint(60.5f, 60.5f, 350f, 0f), new DataPoint(61f, 60.5f, 355f, 0f), new DataPoint(61.5f, 60.5f, 325f, 0f)},
                                {new DataPoint (60f, 61f, 325f, 0f), new DataPoint(60.5f, 61f, 355f, 0f), new DataPoint(61f, 61f, 360f, 0f), new DataPoint(61.5f, 61f, 330f, 0f)},
                                {new DataPoint(60f, 61.5f, 300f, 0f), new DataPoint(60.5f, 61.5f, 325f), new DataPoint(61f, 61.5f, 330f, 0f), new DataPoint(61.5f, 61.5f, 300f, 0f)}};

        //expected data point
        DataPoint ExpectedTemp = new DataPoint(60.55f, 60.55f, 353.3582f, 0f);

        DataPoint ResultTemp = BicubicInterpolation.interpolateLatLong(Points, UnknownTemp);
        assertEquals(ExpectedTemp.getTemperatureK(), ResultTemp.getTemperatureK());
    }

    @Test
    public void bicubicInterpolation_LatDepth_Should_Return_DataPoint(){

        //point to interpolate
        DataPoint UnknownTemp = new DataPoint(60f, 60.55f, NaN, 17.5f);

        //known data points
        DataPoint[][] Points = {{new DataPoint(60f, 60f, 300f, 10f), new DataPoint(60f, 60.5f, 305f, 10f), new DataPoint(60f, 61f, 310f, 10f), new DataPoint(60f, 61.5f, 315f, 10f)},
                                {new DataPoint(60f, 60f, 320f, 15f), new DataPoint(60f, 60.5f, 325f, 15f), new DataPoint(60f, 61f, 330f, 15f), new DataPoint(60f, 61.5f, 335f, 15f)},
                                {new DataPoint(60f, 60f, 340f, 20f), new DataPoint(60f, 60.5f, 345f, 20f), new DataPoint(60f, 61f, 350f, 20f), new DataPoint(60f, 61.5f, 355f, 20f)},
                                {new DataPoint(60f, 60f, 360f, 25f), new DataPoint(60f, 60.5f, 365f, 25f), new DataPoint(60f, 61f, 370f, 25f), new DataPoint(60f, 61.5f, 375f, 25f)}};

        //expected data point
        DataPoint ExpectedTemp = new DataPoint(60f, 60.55f, 329.49997f, 17.5f);

        DataPoint ResultTemp = BicubicInterpolation.interpolateLatDepth(Points, UnknownTemp);
        assertEquals(ExpectedTemp.getTemperatureK(), ResultTemp.getTemperatureK());
    }

    @Test
    public void bicubicInterpolation_LongDepth_Should_Return_DataPoint(){

        //point to interpolate
        DataPoint UnknownTemp = new DataPoint(60.55f, 60f, NaN, 17.5f);

        //known data points
        DataPoint[][] Points = {{new DataPoint(60f, 60f, 300f, 10f), new DataPoint(60.5f, 60f, 305f, 10f), new DataPoint(61f, 60f, 310f, 10f), new DataPoint(61.5f, 60f, 315f, 10f)},
                                {new DataPoint(60f, 60f, 320f, 15f), new DataPoint(60.5f, 60f, 325f, 15f), new DataPoint(61f, 60f, 330f, 15f), new DataPoint(61.5f, 60f, 335f, 15f)},
                                {new DataPoint(60f, 60f, 340f, 20f), new DataPoint(60.5f, 60f, 345f, 20f), new DataPoint(61f, 60f, 350f, 20f), new DataPoint(61.5f, 60f, 355f, 20f)},
                                {new DataPoint(60f, 60f, 360f, 25f), new DataPoint(60.5f, 60f, 365f, 25f), new DataPoint(61f, 60f, 370f, 25f), new DataPoint(61.5f, 60f, 375f, 25f)}};

        //expected data point
        DataPoint ExpectedTemp = new DataPoint(60.55f, 60f, 329.49997f, 17.5f);

        DataPoint ResultTemp = BicubicInterpolation.interpolateLongDepth(Points, UnknownTemp);
        assertEquals(ExpectedTemp.getTemperatureK(), ResultTemp.getTemperatureK());
    }
}