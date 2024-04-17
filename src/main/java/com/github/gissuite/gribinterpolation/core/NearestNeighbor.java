package com.github.gissuite.gribinterpolation.core;

import com.github.gissuite.gribinterpolation.data.DataPoint;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
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
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;


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
    public class KNNModel<T extends Output<T>> extends Model<T> {
        private final int k;
        private final EnsembleCombiner<T> combiner;
        private final DistanceFinder dist;
        private final int numThreads;
        private final org.tribuo.common.nearest.KNNModel.Backend parallelBackend;
        private final Pair<SGDVector,T>[] vectors;
        private NeighboursQueryFactory neighboursQueryFactory;
        private transient NeighboursQuery neighboursQuery;
        private static final CustomForkJoinWorkerThreadFactory THREAD_FACTORY = new org.tribuo.common.nearest.KNNModel.CustomForkJoinWorkerThreadFactory();
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
            // example matches our accepted data (lat,long,depth,temp)
            if (example.size() == featureIDMap.size()) {
                input = DenseVector.createDenseVector(example, featureIDMap, false);
            }
            // example doesn't match
            else {
                input = SparseVector.createSparseVector(example, featureIDMap, false);
            }
            // if nothing is found throw exception
            if (input.numActiveElements() == 0) {
                throw new IllegalArgumentException("No features found in Example " + example);
            }

            List<Prediction<T>> predictions; // store predictions
            Stream<Pair<SGDVector,T>> stream = Stream.of(vectors);
            if(numThreads > 1){
                ForkJoinPool fjp = System.getSecurityManager() == null ? new ForkJoinPool(numThreads) : new ForkJoinPool(numThreads, THREAD_FACTORY, null, false);
                try{
                    predictions = fjp.submit(()->StreamUtil.boundParallelism(stream.parallel()).map().sorted().limit(k).map((a) -> new Prediction<>(a.output, input.numActiveElements(), example)).collect(Collectors.toList()).get();
                }
                catch(InterruptedException | ExecutionException e){
                    throw new IllegalStateException("Failed to process example in parallel",e);
                }
            }
            else{
                predictions = stream.map().sorted().limit(k).map((a) -> new Prediction<>(a.output, input.numActiveElements(), example)).collect(Collectors.toList());
            }

            return combiner.combine(outputIDInfo,predictions);
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