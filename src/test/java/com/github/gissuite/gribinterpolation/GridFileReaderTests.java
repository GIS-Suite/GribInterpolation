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
package com.github.gissuite.gribinterpolation;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

import com.github.gissuite.gribinterpolation.data.GridFileReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ucar.nc2.dataset.NetcdfDataset;
import ucar.nc2.dt.grid.GridDataset;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GridFileReaderTests {

    @Test
    public void listFilesInDirectory_Should_Return_PopulatedSet() throws IOException {
        GridFileReader fileReader = new GridFileReader();
        Path path = Paths.get("src/test/java/com/github/gissuite/gribinterpolation/NAVGEM");
        Set<String> files = fileReader.listFilesInDirectory(path);
        Assertions.assertNotEquals(files.size(), 0);
    }

    @Test
    public void testGenerateDataset() throws IOException {
        GridFileReader reader = new GridFileReader();
        String filePath = "src/main/resources/dev-dataset.HYCOM/2023-07-30T12_00_00Z/HYCOM__2023073012__hycom-glbu-a1__sea_temp__dpth_sfc__00020000__00000000__fcst_ops__0360.grb";
        Set<NetcdfDataset.Enhance> enhancements = Collections.emptySet();
        GridDataset dataset = reader.generateDataset(filePath, enhancements);
        assertNotNull(dataset);
    }
}
