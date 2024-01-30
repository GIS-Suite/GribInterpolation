package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);

            String grib1_path = args[0];
            //String grib2_path = "src/main/java/test/data/NAVGEM__2023081518__global_1440x721__air_temp__flight_lvl__10000000__00000000__fcst_ops__					0060.grb2";
            try{
                var testGrib1 = ucar.nc2.dt.grid.GridDataset.open(grib1_path);
                //var testGrib2 = ucar.nc2.dt.grid.GridDataset.open(grib2_path);
                System.out.println(testGrib1.getDataVariables() + "\n");
                //System.out.println(testGrib2.getDataVariables());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Hello world!");
        
    }
}