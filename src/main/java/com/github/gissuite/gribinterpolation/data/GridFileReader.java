/*
* MIT License
*
* Copyright (c) 2024 OpenGISViewer
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*
 */
package com.github.gissuite.gribinterpolation.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucar.nc2.dataset.Enhancements;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.dt.grid.GridDataset;

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

    /**
     *
     * @summary Read a GRIB file and return a GridDataset.
     *
     * @param filePath the path to the file
     * @param enhancements any enhancements
     * @return the GridDataset or null if an error occurred
     */
    public GridDataset generateDataset(String filePath, Set<NetcdfDataset.Enhance> enhancements) {
       try {
            Set<NetcdfDataset.Enhance> defaultEnhancements = new HashSet<>();
            defaultEnhancements.add(NetcdfDataset.Enhance.IncompleteCoordSystems);
            defaultEnhancements.add(NetcdfDataset.Enhance.ConvertUnsigned);
            defaultEnhancements.addAll(enhancements);
					 return GridDataset.open(filePath, defaultEnhancements);
        } catch (IOException exception) {
            logger.error("Unable to create dataset from file", exception);
            return null;
        }
    }
    /**
     * @summary Reads a GRIB file and returns a GridDataset.
     *
     * @param filePath the path to the file
     * @return the GridDataset or null if an error occurred
     */
    public GridDataset generateDataset(String filePath) {
        Set<NetcdfDataset.Enhance> enhancements = Collections.emptySet();
        return generateDataset(filePath, enhancements);
    }

    private boolean isGribFile(Path file) {
        final String gribFileExtension = ".grb2";
        return file.toString().toLowerCase().endsWith(gribFileExtension);
    }
}
