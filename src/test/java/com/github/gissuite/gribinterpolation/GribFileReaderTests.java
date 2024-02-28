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
import java.util.Optional;
import java.util.Set;

import com.github.gissuite.gribinterpolation.data.GribFileReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ucar.nc2.dt.grid.GridDataset;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class GribFileReaderTests {
  private final Path path = Paths.get("src/test/java/com/github/gissuite/gribinterpolation/NAVGEM");
  @Test
  public void listFilesInDirectory_Should_Return_PopulatedSet() throws IOException {
    GribFileReader fileReader = new GribFileReader();

    Set<String> files = fileReader.listFilesInDirectory(path);
    Assertions.assertNotEquals(files.size(), 0);
  }

  @Test
  public void generateDatasetShouldNotReturnNull() throws IOException {
    GribFileReader fileReader = new GribFileReader();
    Set<String> files = fileReader.listFilesInDirectory(path);
    Optional<String> file = files.stream().findFirst();

    String data = file.get();
    GridDataset dataset = fileReader.generateDatasetFromGribFile(data);
    Assertions.assertNotNull(dataset);
  }
}
