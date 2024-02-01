package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try{
            File directoryPath = new File(args[0]);
            String[] grbFilePaths = directoryPath.list();
            System.out.println(directoryPath);

        for (int i = 0; i < grbFilePaths.length; i++) {
            System.out.println(grbFilePaths[i]);
       }
            for (int i = 0; i < grbFilePaths.length; ++i) {
                System.out.println(grbFilePaths);
                String gribFile = grbFilePaths[i];
                var testGrib1 = ucar.nc2.dt.grid.GridDataset.open(directoryPath + "\\" + gribFile);
                System.out.println(testGrib1.getDataVariables() + "\n");
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello world!");
        
    }
}