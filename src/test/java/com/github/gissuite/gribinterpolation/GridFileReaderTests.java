package com.github.gissuite.gribinterpolation;

import com.github.gissuite.gribinterpolation.data.GridFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class GridFileReaderTests {

    @Test
    public void listFilesInDirectory_Should_Return_PopulatedSet() throws IOException {
        GridFileReader fileReader = new GridFileReader();
        Path path = Paths.get("src/test/java/com/github/gissuite/gribinterpolation/NAVGEM");
        Set<String> files = fileReader.listFilesInDirectory(path);
        Assertions.assertNotEquals(files.size(), 0);
    }
}
