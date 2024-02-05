package org.example.FileTypes;

import org.apache.http.MethodNotSupportedException;
import org.example.Interpolations.Bicubic.Bicubic;
import org.example.Interpolations.Bilinear.Bilinear;
import org.example.Interpolations.Interpolation;
import org.example.Interpolations.InverseDistanceWeighted.InverseDistanceWeighted;
import org.example.Interpolations.KNearestNeighbor.KNearestNeighbor;

import java.io.File;

public class Grib {
    public String folderPath;
    public Object[] oldValues;
    public Object[] correctedValues;

    public Grib(String path) {
        this.folderPath = path;
        try {
            this.oldValues = readValues();
        }catch(MethodNotSupportedException e){
            System.out.println("ReadValues method has not been implemented.");
        }
    }

    public Object[] readValues() throws MethodNotSupportedException {
        throw new MethodNotSupportedException("This method has not been implemented");
    }

    public Object[] interpolate(String method) throws MethodNotSupportedException {
        Interpolation interpolationMethod = switch (method) {
            case "KNearestNeighbor" -> new KNearestNeighbor();
            case "Bilinear" -> new Bilinear();
            case "Bicubic" -> new Bicubic();
            case "InverseDistanceWeighted" -> new InverseDistanceWeighted();
            default -> throw new IllegalStateException("Unexpected value: " + method);
        };

        return interpolationMethod.interpolate();
    }

    public File[] export(String targetPath) throws MethodNotSupportedException{
        throw new MethodNotSupportedException("This method has not been implemented");
    }
}
