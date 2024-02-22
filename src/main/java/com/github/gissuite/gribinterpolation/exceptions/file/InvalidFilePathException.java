package com.github.gissuite.gribinterpolation.exceptions.file;

public class InvalidFilePathException extends Exception {
    public InvalidFilePathException() {
        super("The provided file path is invalid.");
    }

    public InvalidFilePathException(String message) {
        super(message);
    }
}
