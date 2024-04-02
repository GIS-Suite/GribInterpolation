package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;

import java.util.Arrays;

import com.oracle.labs.mlrg.olcut.provenance.ProvenanceUtil;
import org.tribuo.MutableDataset;
import org.tribuo.common.nearest.KNNModel;
import org.tribuo.common.nearest.KNNTrainer;
import org.tribuo.evaluation.TrainTestSplitter;
import org.tribuo.regression.Regressor;
import org.tribuo.regression.evaluation.RegressionEvaluator;

import static com.github.gissuite.gribinterpolation.core.NearestNeighbor.getNearestNeighbor;
import static ucar.nc2.util.rc.RC.log;

public class NearestNeighborAlgorithm {
    public static void main (String[] args) {
        NearestNeighborAlgorithm nearestNeighbor = new NearestNeighborAlgorithm();

        nearestNeighbor.testSet();
        // nearestNeighbor.createDatasets();
        nearestNeighbor.trainAndEvaluate();
    } // end main

    public static float nearestNeighborAlgorithm ( int k, DataPoint[] nearestNeighbors){
        KNNModel<Regressor> knnModel;
        KNNTrainer<Regressor> knnTrainer;


        return 2F;


    } // end nearestNeighborAlgorithm
    protected void createTrainer() {
        log.info("Creating trainer....");


    }

    protected void testSet(){
        log.info("Creating a test set....");
        // creating data points
        DataPoint first = new DataPoint(-66, 38.8f, 271.4f, 300);
        DataPoint second = new DataPoint(-62, 38.8f, 273.3f, 400);
        DataPoint third = new DataPoint(-68, 38.8f, 273.3f, 400);
        // DataPoint expectedResult = new DataPoint(-62, 38.8f, 272.44498f, 355);

        // putting test data into an array
        DataPoint[] arrayOfDataPoints = {first, second, third};
        DataPoint[] nearestNeighbors = getNearestNeighbor(arrayOfDataPoints, -62, 38.8f, 2, 3);
        System.out.println(Arrays.toString(nearestNeighbors)); //prints out the location of the DataPoints

        //can get temperature this way:
        DataPoint a = nearestNeighbors[0];
        System.out.println(a.getTemperatureK());


    }

    // getting our test set in a usable state for tribuo
    public void createDatasets() throws Exception {
        log.info("Creating datasets....");
        
    }

    private void evaluate(){
        log.info("Evaluating datasets....");
        RegressionEvaluator evaluator = new RegressionEvaluator();
    }

    private void trainingAndEvaluate() {
        log.info("Training model...");

        evaluate(model, "testSet", testSet);

        log.info("Dataset Provenance: --------------------");
        log.info(ProvenanceUtil.formattedProvenanceString(model.getProvenance().getDatasetProvenance()));
        log.info("Trainer Provenance: --------------------");
        log.info(ProvenanceUtil.formattedProvenanceString(model.getProvenance().getTrainerProvenance()));
    }
} // end NearestNeighborAlgorithm
