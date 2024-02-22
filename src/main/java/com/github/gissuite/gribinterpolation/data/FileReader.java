package com.github.gissuite.gribinterpolation.data;

import com.github.gissuite.gribinterpolation.exceptions.file.InvalidFilePathException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class FileReader {
    protected void checkIfPathIsEmpty(String path) throws InvalidFilePathException {
        if (path.isEmpty() || path.isBlank()) {
            throw new InvalidFilePathException();
        }
    }
    protected boolean isPathDirectory(Path path) {
        return Files.isDirectory(path);
    }

    protected Set<String> listFilesInDirectory(Path path) throws IOException {
        try (Stream<Path> stream = Files.list(path)) {
            Set<String> files = stream
                    .filter(this::isPathDirectory)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());

            return files;
        }
    }

}
