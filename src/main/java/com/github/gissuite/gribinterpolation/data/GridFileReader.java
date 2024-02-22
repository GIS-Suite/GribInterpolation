package com.github.gissuite.gribinterpolation.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.nc2.dt.grid.GridDataset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GridFileReader extends FileReader {

    private final Logger logger = LoggerFactory.getLogger(GridFileReader.class);
    public Set<String> listFilesInDirectory(Path path) throws IOException {
        try (Stream<Path> stream = Files.walk(path)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }
    public GridDataset generateDatasetFromGridFile(String filePath) {
        try(GridDataset dataset = GridDataset.open(filePath)) {
            return dataset;
        } catch (IOException exception) {
            logger.error("Unable to create dataset from file", exception);
            return null;
        }
    }

    private boolean isGribFile(Path file) {
        final String gribFileExtension = ".grb2";
        return file.toString().toLowerCase().endsWith(gribFileExtension);
    }
}
