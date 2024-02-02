package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //check if args is empty
        if (args.length == 0) {
            System.out.println("The arguments array is empty. Please pass at least 1 argument into main.");
            System.exit(0);
        }
        //Creating String array of grib file names from folder.
        try {
            File folderPath = new File(args[0]);
            String[] gribFileNameArray = folderPath.list();

            if (gribFileNameArray != null && gribFileNameArray.length != 0) {
                //Combining folder path with grib file name to create full grib file path to read all grib files
                for (String gribFileName : gribFileNameArray) {
                    System.out.println(gribFileName);
                    String gribFilePath = folderPath + "\\" + gribFileName;

                    //test grib file read with ucar
                    var testGrib = ucar.nc2.dt.grid.GridDataset.open(gribFilePath);
                    System.out.println(testGrib.getDataVariables() + "\n");
                }
            } else {
                System.out.println("The folder is empty. Please check folder.");
                System.exit(0);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}