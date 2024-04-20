package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import java.util.*;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;
import java.text.DecimalFormat;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.oracle.labs.mlrg.olcut.util.Pair;
import com.oracle.labs.mlrg.olcut.util.StreamUtil;
import org.tribuo.*;
import org.tribuo.common.nearest.KNNModel.*;
import org.tribuo.ensemble.EnsembleCombiner;
import org.tribuo.math.la.DenseVector;
import org.tribuo.math.la.SGDVector;
import org.tribuo.math.la.SparseVector;
import org.tribuo.math.neighbour.NeighboursQuery;
import org.tribuo.math.neighbour.NeighboursQueryFactory;
import org.tribuo.provenance.ModelProvenance;

import static com.github.gissuite.gribinterpolation.core.DistanceFinder.haverSine;

//gets k nearest neighbors' memory locations stored in a DataPoint array
//amountOfDataPoints is how many points to find the distance for; so should be how many DataPoints in total we have.
//k is how many neighbors to look for

//can get temperature this way:
//ArrayList<DataPoint> nearestNeighbors = (getNearestNeighbor(arrayOfDataPoints, longitudeToInterpolate, latitudeToInterpolate, k, amountOfDataPoints));
//DataPoint a = (DataPoint) nearestNeighbors.get(0);
//System.out.println(a.getTemperatureK());

public class NearestNeighbor {

        public static ArrayList<DataPoint> getNearestNeighbor(ArrayList<DataPoint> dataPoints, float longitudeToInterpolate, float latitudeToInterpolate, int k, int amountOfDataPoints) {
            ArrayList<DataPoint> nearestNeighbors = new ArrayList<>();
            HashMap<DataPoint, Double> hashMap = new HashMap<>();
            for (int i = 0; i < amountOfDataPoints; i++) {
                double distance = DistanceFinder.haverSine(dataPoints.get(i).getLatitude(), dataPoints.get(i).getLongitude(), latitudeToInterpolate, longitudeToInterpolate);
                hashMap.put(dataPoints.get(i), distance);
            }
            Map<DataPoint, Double> sortedHashMap = sortByValue(hashMap);
            Set<DataPoint> keySet = sortedHashMap.keySet();
            Iterator<DataPoint> iterator = keySet.iterator();
            int count = 0;
            while (iterator.hasNext() && count < k) {
                DataPoint key = iterator.next();
                nearestNeighbors.add(key);
                count++;
            }
        return nearestNeighbors;
        }

private static Map<DataPoint, Double> sortByValue(HashMap<DataPoint, Double> map) {
    return map.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (firstValue, streamValue) -> firstValue, LinkedHashMap::new));
}

    /**
     * for the interpolation of temperature, we'll need the
     * (summation from 1 to k of (temp over distance)) over
     * the (summation from 1 to k of (the inverse of distance))
     */
    public static double knnInterpolation(ArrayList<DataPoint> nearestNeighbors, DataPoint toInterpolate){
        DecimalFormat df = new DecimalFormat("#.##");
        // placeholder values
        double interpolatedTemp = 0.0;
        double totalDistance = 0.0;
        double totalTemp = 0.0;
        double tempOverDist = 0.0;
        double totalDistanceInverse = 0.0;
        double totalTempOverDist = 0.0;
        double distance = 0.0;
        double num = 0.0;

        try {
            for (DataPoint nearestNeighbor : nearestNeighbors) { // look through the array list of nearest neighbor
                // get the distance
                distance = DistanceFinder.haverSine(nearestNeighbor.getLatitude(), nearestNeighbor.getLongitude(), toInterpolate.getLatitude(), toInterpolate.getLongitude());
                // getting temperature over distance
                tempOverDist = (nearestNeighbor.getTemperatureK()) / distance;
                // getting total temperature over distance
                totalTempOverDist += tempOverDist; // the numerator of our formula


                if(Double.isFinite(distance)){
                    // need to check for when distance is zero because I was getting infinity (not good)
                    if (distance != 0){
                        // get inverse of distance and store it
                        totalDistanceInverse += 1 / distance; // the denominator of our formula
                    }
                    else {
                        totalDistanceInverse += 0; // probably need to change this
                    }
                }
                else{
                    throw new ArithmeticException("Distance is infinity");
                }
                totalDistance += distance; // store the distance
            }
            num = totalTempOverDist/totalDistanceInverse;
            interpolatedTemp = Double.parseDouble(df.format(num));
        }
        catch(Exception e){
            System.out.println("Problem with knnInterpolation: " + e);
            System.out.println("String being parsed: " + num); // Print the string being parsed
        }
        return interpolatedTemp;
    } // end of knnInterpolation() :)

    public class KNNModel<T extends Output<T>> extends Model<T> {
        private final int k;
        private final EnsembleCombiner<T> combiner;
        private final DistanceFinder dist;
        private final int numThreads;
        private final org.tribuo.common.nearest.KNNModel.Backend parallelBackend;
        private final Pair<SGDVector,T>[] vectors;
        private NeighboursQueryFactory neighboursQueryFactory;
        private transient NeighboursQuery neighboursQuery;
        // private static final CustomForkJoinWorkerThreadFactory THREAD_FACTORY = new org.tribuo.common.nearest.KNNModel.CustomForkJoinWorkerThreadFactory();
        protected KNNModel(String name,
                           ModelProvenance provenance,
                           ImmutableFeatureMap featureIDMap,
                           ImmutableOutputInfo<T> outputIDInfo,
                           boolean generatesProbabilities,
                           int k,
                           DistanceFinder dist, int numThreads,
                           EnsembleCombiner<T> combiner,
                           Pair<SGDVector,T>[] vectors,
                           org.tribuo.common.nearest.KNNModel.Backend backend,
                           NeighboursQueryFactory neighboursQueryFactory) {
            super(name, provenance, featureIDMap, outputIDInfo, generatesProbabilities);
            this.k = k;
            this.dist = dist;
            this.numThreads = numThreads;
            this.combiner = combiner;
            this.parallelBackend = backend;
            this.vectors = vectors;
            this.neighboursQueryFactory = neighboursQueryFactory;
            this.neighboursQuery = neighboursQueryFactory.createNeighboursQuery(getSGDVectorArr());
        }

        /**
         *
         */
        private SGDVector[] getSGDVectorArr() { // Converting Pair<SGDVector> to SGDVector[]
            SGDVector[] sgdVectors = new SGDVector[vectors.length];
            int n = 0;
            for (Pair<SGDVector,T> vector : vectors) { // look at each element inside
                sgdVectors[n] = vector.getA(); // receive the element info
                n++; // moving on
            }
            return sgdVectors;
        }

        /**
         * SGDVector input
         * featureIDMap to keep the features of data consist
         * DenseVector vector in the feature are non-zero (there is something we can use)
         * SparseVector vector in the feature are zero
        **/
        @Override
        public Prediction<T> predict(Example<T> example) {
            SGDVector input;
            try{
                // example matches our accepted dataPoint (lat,long,depth,temp)
                if (example.size() == featureIDMap.size()) {
                    input = DenseVector.createDenseVector(example, featureIDMap, false);
                }
                // Something was found, but example doesn't match
                else {
                    input = SparseVector.createSparseVector(example, featureIDMap, false);
                }
                // nothing is found
                if (input.numActiveElements() == 0) {
                    throw new IllegalArgumentException("No features found in Example " + example);
                }
            }
            catch(Exception e){
                System.out.print("Error at Prediction<T> predict:" + e);
            }
            /*
            Function<Pair<SGDVector,T>, OutputDoublePair<T>> distanceFunc = (a) -> new OutputDoublePair<>(a.getB(), dist.computeDistance(a.getA(), input));

            Function<Pair<SGDVector,T>, <T>> distanceFunc

            List<Prediction<T>> predictions; // store predictions
            Stream<Pair<SGDVector,T>> stream = Stream.of(vectors);
            if(numThreads > 1){
               ForkJoinPool fjp = System.getSecurityManager() == null ? new ForkJoinPool(numThreads) : new ForkJoinPool(numThreads, THREAD_FACTORY, null, false);
                try{
                    predictions = fjp.submit(()->StreamUtil.boundParallelism(stream.parallel()).map().sorted().limit(k).map((a) -> new Prediction<>(a.output, input.numActiveElements(), example)).collect(Collectors.toList()).get());
                }
                catch(InterruptedException | ExecutionException e){
                    throw new IllegalStateException("Failed to process example in parallel",e);
                }
            }
            else{
                predictions = stream.map().sorted().limit(k).map((a) -> new Prediction<>(a.output, input.numActiveElements(), example)).collect(Collectors.toList());
            }


            return combiner.combine(outputIDInfo,predictions);
            */
            return null;
        } // end of Prediction<T> predict

        @Override
        public Map<String, List<Pair<String, Double>>> getTopFeatures(int i) {
            return Collections.emptyMap();
        }
        @Override
        public Optional<Excuse<T>> getExcuse(Example<T> example) {
            return Optional.empty();
        }
        @Override
        protected KNNModel<T> copy(String s, ModelProvenance modelProvenance) {
            Pair<SGDVector,T>[] vectorCopy = new Pair[vectors.length];
            for (int i = 0; i < vectors.length; i++) {
                vectorCopy[i] = new Pair<>(vectors[i].getA().copy(),vectors[i].getB().copy());
            }
            return new KNNModel<>(s,modelProvenance,featureIDMap,outputIDInfo,generatesProbabilities,k,dist,
                    numThreads,combiner,vectorCopy,parallelBackend,neighboursQueryFactory);
        }
    }
}
