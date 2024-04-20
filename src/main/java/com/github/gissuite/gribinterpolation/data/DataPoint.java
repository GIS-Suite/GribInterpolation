package com.github.gissuite.gribinterpolation.data;

public class DataPoint {

    private float longitude;
    private float latitude;
    private float temperatureK;
    private float depth;
    private float distance;

    public DataPoint(float longitude, float latitude, float temperaturek, float depth) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperatureK = temperaturek;
        this.depth = depth;
    }
    public DataPoint(float longitude, float latitude, float temperaturek) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.temperatureK = temperaturek;
        this.depth = 0;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getTemperatureK() {
        return temperatureK;
    }

    public float getDepth() {
        return depth;
    }

    public void setTemperatureK(float temperatureK) {
        this.temperatureK = temperatureK;
    }
    public float getDistance(){ return distance; }
    public void setDistance(float distance){ this.distance = distance; }
}
