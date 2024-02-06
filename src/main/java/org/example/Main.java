package org.example;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        //check if args is empty
        if (args.length == 0) {
            System.out.println("The arguments array is empty. Please pass at least 1 argument into main.");
            System.exit(0);
        }

        try {
            //Passing folder path as an argument
            File gribFolderPath = new File(args[0]);
            if (gribFolderPath.exists()){

                //Create list of grib file paths in the folder
                ArrayList<String>gribFilePathList = new ArrayList<>();
                for (File gribFileEntry : Objects.requireNonNull(gribFolderPath.listFiles())) {
                    gribFilePathList.add(gribFileEntry.getPath());
                }

                //test all grib file read with ucar
                if (!gribFilePathList.isEmpty()) {
                    for (String gribFilePath : gribFilePathList) {
                        var testGrib = ucar.nc2.dt.grid.GridDataset.open(gribFilePath);
                        System.out.println(testGrib.getDataVariables() + "\n");
                    }
                } else {
                    System.out.println("List of grib file paths is empty.");
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