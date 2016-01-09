package edu.pku.id;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class FileAssert {
    public static  void assertFileEquals(String actualFileName, String expectedFileName)
            throws FileNotFoundException, IOException {
        LineNumberReader actualFileReader = new LineNumberReader(new BufferedReader(
                new FileReader(actualFileName)));        
        LineNumberReader expectedFileReader = new LineNumberReader(new BufferedReader(
                new FileReader(expectedFileName)));
        String actualLine;
        String expectedLine;
        while ((actualLine = actualFileReader.readLine()) != null
                && (expectedLine = expectedFileReader.readLine()) != null) {
            assertEquals(String.format("Line %d:",
                    actualFileReader.getLineNumber(), expectedLine, actualLine),
                    expectedLine, actualLine);
        }
    }
}
