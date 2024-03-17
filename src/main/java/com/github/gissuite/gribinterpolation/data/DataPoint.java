package com.github.gissuite.gribinterpolation.data;

public class DataPoint {

    private float longitude;
    private float latitude;
    private float  temperatureF;

    public DataPoint(float longitude, float latitude, float temperatureF) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperatureF = temperatureF;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getTemperatureF() {
        return temperatureF;
    }
}
